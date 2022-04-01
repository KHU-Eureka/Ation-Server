package com.eureka.ationserver.service;

import com.eureka.ationserver.dto.insight.InsightRequest;
import com.eureka.ationserver.dto.insight.InsightResponse;
import com.eureka.ationserver.model.category.MainCategory;
import com.eureka.ationserver.model.category.SubCategory;
import com.eureka.ationserver.model.insight.Insight;
import com.eureka.ationserver.model.insight.InsightSubCategory;
import com.eureka.ationserver.model.insight.InsightTag;
import com.eureka.ationserver.model.insight.InsightView;
import com.eureka.ationserver.model.user.User;
import com.eureka.ationserver.repository.category.MainCategoryRepository;
import com.eureka.ationserver.repository.category.SubCategoryRepository;
import com.eureka.ationserver.repository.insight.InsightRepository;
import com.eureka.ationserver.repository.insight.InsightSubCategoryRepository;
import com.eureka.ationserver.repository.insight.InsightTagRepository;
import com.eureka.ationserver.repository.insight.InsightViewRepository;
import com.eureka.ationserver.utils.image.ImageUtil;
import com.eureka.ationserver.utils.parse.Parse;
import com.eureka.ationserver.utils.parse.ParseUtil;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.http.HttpClient;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.codehaus.jackson.JsonParser;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class InsightService {

    private final InsightRepository insightRepository;
    private final MainCategoryRepository mainCategoryRepository;
    private final SubCategoryRepository subCategoryRepository;
    private final InsightTagRepository insightTagRepository;
    private final InsightSubCategoryRepository insightSubCategoryRepository;
    private final InsightViewRepository insightViewRepository;
    private final AuthService authService;

    @Transactional
    public Long savePublic(InsightRequest insightRequest) throws Exception {

        Parse parse = ParseUtil.parse(Insight.INSIGHT_PREFIX, insightRequest.getUrl());

        // public
        MainCategory mainCategory = mainCategoryRepository.findById(
            insightRequest.getMainCategoryId()).get();
        List<SubCategory> subCategoryList = subCategoryRepository.findAllByIdIn(
            insightRequest.getSubCategoryIdList());

        Insight insight = Insight.builder()
            .url(insightRequest.getUrl())
            .title(parse.getTitle())
            .description(parse.getDescription())
            .imgPath(parse.getImageUrl())
            .siteName(parse.getSiteName())
            .icon(parse.getIcon())
            .insightMainCategory(mainCategory)
            .open(true)
            .build();

        Insight saved = insightRepository.save(insight);

        for(SubCategory subCategory : subCategoryList){
            InsightSubCategory insightSubCategory = InsightSubCategory.builder()
                .insight(insight)
                .subCategory(subCategory)
                .build();
            insightSubCategoryRepository.save(insightSubCategory);
        }


        for(String tag : insightRequest.getTagList()){
            InsightTag insightTag = InsightTag.builder()
                .insight(insight)
                .name(tag)
                .build();
            insightTagRepository.save(insightTag);
        }

        return saved.getId();


    }

    @Transactional(readOnly = true)
    public List<InsightResponse> findPublicAll(){
        List<Insight> insightList = insightRepository.findByOpenOrderByCreatedAtDesc(true);
        List<InsightResponse> insightResponseList = new ArrayList<>();
        for(Insight insight : insightList){
            insightResponseList.add(new InsightResponse(insight));
        }
        return insightResponseList;
    }

    @Transactional
    public InsightResponse saveImg(Long insightId, MultipartFile insightImg) throws IOException{
        Insight insight = insightRepository.getById(insightId);

        List<String> pathList = ImageUtil.getImagePath(Insight.INSIGHT_PREFIX, insightId);
        File file = new File(pathList.get(1));
        insightImg.transferTo(file);
        insight.setImgPath(pathList.get(0));


       return new InsightResponse(insight);
    }

    @Transactional
    public InsightResponse findPublic(Long insightId) {
        User user = authService.auth();
        Insight insight = insightRepository.getById(insightId);

        Optional<InsightView> insightView = insightViewRepository.findByUserAndInsight(user,
            insight);
        if (!insightView.isPresent()) {
            InsightView newInsightView = InsightView.builder()
                .insight(insight)
                .user(user)
                .build();
            insightViewRepository.save(newInsightView);
        }
        return new InsightResponse(insight);
    }

    @Transactional(readOnly = true)
    public List<InsightResponse> findByMainCategory(Long mainCategoryId){
        List<Insight> insightList = insightRepository.findByInsightMainCategoryIdOrderByCreatedAtDesc(mainCategoryId);
        List<InsightResponse> insightResponseList = new ArrayList<>();
        for(Insight insight : insightList){
            insightResponseList.add(new InsightResponse(insight));
        }
        return insightResponseList;
    }

    @Transactional(readOnly = true)
    public Set<InsightResponse> search(String keyword){
        Set<InsightResponse> insightResponseList = insightRepository.findByOpenAndTitleContainingOrInsightMainCategoryNameContainingOrInsightSubCategoryList_SubCategoryNameContainingOrInsightTagList_NameContainingOrderByCreatedAtDesc(true, keyword, keyword, keyword, keyword).stream().map(InsightResponse::new).collect(Collectors.toSet());
        return insightResponseList;
    }

    @Transactional(readOnly = true)
    public List<InsightResponse> getRecommend() throws Exception {
        User user = authService.auth();
        String recommendApiUrl = "http://16.170.173.74:5000";
        URL url = new URL(String.format("%s/%d", recommendApiUrl, user.getId()));

        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");

        BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        StringBuffer stringBuffer = new StringBuffer();
        String inputLine;
        while ((inputLine = br.readLine()) != null)  {
            stringBuffer.append(inputLine);
        }
        br.close();
        String response = stringBuffer.toString();

        JSONParser jsonParser = new JSONParser();
        JSONObject jsonObject = (JSONObject) jsonParser.parse(response);

        List<Long> idList = (List<Long>) jsonObject.get("recommend-list");

        List<InsightResponse> insightResponseList = new ArrayList<>();
        for(Long e : idList){
            insightResponseList.add(new InsightResponse(insightRepository.getById(e)));
        }
        return insightResponseList;
    }

    @Transactional(readOnly = true)
    public List<InsightResponse> getRandom(){

        List<Insight> insightList = insightRepository.findByOpen(true);
        int len = insightList.size();
        List<InsightResponse> insightResponseList = new ArrayList<>();
        Random generator = new Random();
        List<Integer> randomIdxList = new ArrayList<>();
        for(int i=0;i<4;i++){
            int randomIdx = generator.nextInt(len);
            if(randomIdxList.contains(randomIdx)){
                i--;
                continue;
            }
            randomIdxList.add(randomIdx);
            insightResponseList.add(new InsightResponse(insightList.get(randomIdx)));
        }
        return insightResponseList;
    }


}


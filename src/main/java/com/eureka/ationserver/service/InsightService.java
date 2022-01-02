package com.eureka.ationserver.service;

import com.eureka.ationserver.advice.exception.DuplicateException;
import com.eureka.ationserver.advice.exception.ForbiddenException;
import com.eureka.ationserver.domain.insight.*;
import com.eureka.ationserver.domain.persona.Interest;
import com.eureka.ationserver.domain.persona.PersonaInterest;
import com.eureka.ationserver.domain.user.User;
import com.eureka.ationserver.dto.insight.*;
import com.eureka.ationserver.dto.persona.PersonaSimpleResponse;
import com.eureka.ationserver.repository.insight.*;
import com.eureka.ationserver.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class InsightService {

    private final InsightRepository insightRepository;
    private final InsightMainCategoryRepository insightMainCategoryRepository;
    private final InsightSubCategoryRepository insightSubCategoryRepository;
    private final InsightTagRepository insightTagRepository;
    private final InsightCategoryRepository insightCategoryRepository;
    private final InsightViewRepository insightViewRepository;
    private final UserRepository userRepository;

    @Transactional
    public Long savePublic(InsightRequest insightRequest) throws IOException {

        String crawlingUrl = insightRequest.getUrl();
        Document document = Jsoup.connect(crawlingUrl).get();


        String title = document.select("meta[property=og:title]").first().attr("content");


        String description;
        try {
            description = document.select("meta[property=og:description]").get(0).attr("content");
            if(description.length()>255){
                description = description.substring(0,255);
            }

        } catch (Exception e) {
            description = null;
        }

        String imageUrl;
        try {
            imageUrl = document.select("meta[property=og:image]").get(0).attr("content");

        } catch (Exception e) {
            imageUrl = getInsightImageDefaultPath();
        }

        String siteName;
        try {
            siteName = document.select("meta[property=og:site_name]").get(0).attr("content");

        } catch (Exception e) {
            siteName = "-";
        }

        String icon;
        try {
            icon = document.select("link[rel=apple-touch-icon-precomposed]").get(0).attr("href");

        } catch (Exception e) {
            icon = getInsightIconImageDefaultPath();
        }


        // public
        InsightMainCategory insightMainCategory = insightMainCategoryRepository.findById(insightRequest.getInsightMainCategoryId()).get();
        List<InsightSubCategory> insightSubCategoryList= insightSubCategoryRepository.findAllByIdIn(insightRequest.getInsightSubCategoryIdList());


        Insight insight = Insight.builder()
                .url(insightRequest.getUrl())
                .title(title)
                .description(description)
                .imgPath(imageUrl)
                .siteName(siteName)
                .icon(icon)
                .insightMainCategory(insightMainCategory)
                .open(true)
                .build();
        Insight saved = insightRepository.save(insight);

        for(InsightSubCategory insightSubCategory : insightSubCategoryList){
            InsightCategory insightCategory = InsightCategory.builder()
                    .insight(insight)
                    .insightSubCategory(insightSubCategory)
                    .build();
            insightCategoryRepository.save(insightCategory);
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

    @Value("${erueka.app.publicIp}")
    private String HOST;

    @Value("${server.port}")
    private String PORT;

    @Value("${eureka.app.imagePath}")
    private String IMAGEPATH;

    public String getInsightImageDefaultPath(){
        // set file name
        List<String> pathList = new ArrayList<>();

        String fileName = "insight.png";
        String url = "http://"+HOST+":"+PORT+"/api/image?path=";
        String apiPath = url + IMAGEPATH+"insight/" + fileName;
        return apiPath;
    }
    public String getInsightIconImageDefaultPath(){
        // set file name
        List<String> pathList = new ArrayList<>();

        String fileName = "insight-icon.png";
        String url = "http://"+HOST+":"+PORT+"/api/image?path=";
        String apiPath = url + IMAGEPATH+"insight-icon/" + fileName;
        return apiPath;
    }

    public List<String> getInsightImagePath(Long insightId){
        // set file name
        List<String> pathList = new ArrayList<>();

        String fileName = "insight-"+ insightId +".png";
        String url = "http://"+HOST+":"+PORT+"/api/image?path=";
        String apiPath = url +IMAGEPATH+"insight/"+ fileName;

        String path = IMAGEPATH + "insight/"+ fileName;
        pathList.add(apiPath);
        pathList.add(path);
        return pathList;
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

        List<String> pathList = getInsightImagePath(insightId);
        File file = new File(pathList.get(1));
        insightImg.transferTo(file);
        insight.setImgPath(pathList.get(0));


       return new InsightResponse(insight);
    }

    @Transactional()
    public InsightResponse findPublic(User user, Long insightId){
        Insight insight = insightRepository.getById(insightId);

        Optional<InsightView> insightView = insightViewRepository.findByUserAndInsight(user, insight);
        if(!insightView.isPresent()){
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
        Set<InsightResponse> insightResponseList = insightRepository.findByOpenAndTitleContainingOrInsightMainCategoryNameContainingOrInsightSubCategoryList_InsightSubCategoryNameContainingOrInsightTagList_NameContainingOrderByCreatedAtDesc(true, keyword, keyword, keyword, keyword).stream().map(InsightResponse::new).collect(Collectors.toSet());
        return insightResponseList;
    }




}


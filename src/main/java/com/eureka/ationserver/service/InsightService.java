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
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
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
  private final UserService userService;

  @Transactional
  public InsightResponse.IdOut savePublic(InsightRequest.In in) throws Exception {

    Parse parse = ParseUtil.parse(Insight.INSIGHT_PREFIX, in.getUrl());

    // public
    MainCategory mainCategory = mainCategoryRepository.findById(
        in.getMainCategoryId()).get();
    List<SubCategory> subCategoryList = subCategoryRepository.findAllByIdIn(
        in.getSubCategoryIdList());

    Insight insight = Insight.builder()
        .url(in.getUrl())
        .title(parse.getTitle())
        .description(parse.getDescription())
        .imgPath(parse.getImageUrl())
        .siteName(parse.getSiteName())
        .icon(parse.getIcon())
        .insightMainCategory(mainCategory)
        .open(true)
        .build();

    Insight saved = insightRepository.save(insight);

    for (SubCategory subCategory : subCategoryList) {
      InsightSubCategory insightSubCategory = InsightSubCategory.builder()
          .insight(insight)
          .subCategory(subCategory)
          .build();
      insightSubCategoryRepository.save(insightSubCategory);
    }

    for (String tag : in.getTagList()) {
      InsightTag insightTag = InsightTag.builder()
          .insight(insight)
          .name(tag)
          .build();
      insightTagRepository.save(insightTag);
    }

    return InsightResponse.toIdOut(saved.getId());


  }

  @Transactional(readOnly = true)
  public List<InsightResponse.Out> findPublicAll() {
    List<InsightResponse.Out> outList = insightRepository.findByOpenOrderByCreatedAtDesc(
        true).stream().map(InsightResponse::toOut).collect(
        Collectors.toList());

    return outList;
  }

  @Transactional
  public InsightResponse.Out saveImg(Long insightId, MultipartFile insightImg) throws IOException {
    Insight insight = insightRepository.getById(insightId);

    List<String> pathList = ImageUtil.getImagePath(Insight.INSIGHT_PREFIX, insightId);
    File file = new File(pathList.get(1));
    insightImg.transferTo(file);
    insight.setImgPath(pathList.get(0));

    return InsightResponse.toOut(insight);
  }

  @Transactional
  public InsightResponse.Out findPublic(Long insightId) {
    User user = userService.auth();
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
    return InsightResponse.toOut(insight);
  }

  @Transactional(readOnly = true)
  public List<InsightResponse.Out> findByMainCategory(Long mainCategoryId) {
    List<Insight> insightList = insightRepository.findByInsightMainCategoryIdOrderByCreatedAtDesc(
        mainCategoryId);
    List<InsightResponse.Out> outList = insightList.stream().map(InsightResponse::toOut).collect(
        Collectors.toList());

    return outList;
  }

  @Transactional(readOnly = true)
  public Set<InsightResponse.Out> search(String keyword) {
    Set<InsightResponse.Out> outSet = insightRepository.findByOpenAndTitleContainingOrInsightMainCategoryNameContainingOrInsightSubCategoryList_SubCategoryNameContainingOrInsightTagList_NameContainingOrderByCreatedAtDesc(
            true, keyword, keyword, keyword, keyword).stream().map(InsightResponse::toOut)
        .collect(Collectors.toSet());
    return outSet;
  }

  @Transactional(readOnly = true)
  public List<InsightResponse.Out> getRecommend() throws Exception {
    User user = userService.auth();
    String recommendApiUrl = "http://16.170.173.74:5000";
    URL url = new URL(String.format("%s/%d", recommendApiUrl, user.getId()));

    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
    connection.setRequestMethod("GET");

    BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
    StringBuffer stringBuffer = new StringBuffer();
    String inputLine;
    while ((inputLine = br.readLine()) != null) {
      stringBuffer.append(inputLine);
    }
    br.close();
    String response = stringBuffer.toString();

    JSONParser jsonParser = new JSONParser();
    JSONObject jsonObject = (JSONObject) jsonParser.parse(response);

    List<Long> idList = (List<Long>) jsonObject.get("recommend-list");

    List<InsightResponse.Out> outList = new ArrayList<>();
    for (Long e : idList) {
      outList.add(InsightResponse.toOut(insightRepository.getById(e)));
    }
    return outList;
  }

  @Transactional(readOnly = true)
  public List<InsightResponse.Out> getRandom() {

    List<Insight> insightList = insightRepository.findAll();
    int len = insightList.size();
    List<InsightResponse.Out> outList = new ArrayList<>();
    Random generator = new Random();
    List<Integer> randomIdxList = new ArrayList<>();
    for (int i = 0; i < 4; i++) {
      int randomIdx = generator.nextInt(len);
      if (randomIdxList.contains(randomIdx)) {
        i--;
        continue;
      }
      randomIdxList.add(randomIdx);
      outList.add(InsightResponse.toOut(insightList.get(randomIdx)));
    }
    return outList;
  }


}


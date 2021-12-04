package com.eureka.ationserver.service;

import com.eureka.ationserver.domain.insight.*;
import com.eureka.ationserver.dto.insight.InsightMainCategoryResponse;
import com.eureka.ationserver.dto.insight.InsightRequest;
import com.eureka.ationserver.dto.insight.InsightResponse;
import com.eureka.ationserver.dto.insight.InsightSubCategoryResponse;
import com.eureka.ationserver.repository.insight.*;
import lombok.RequiredArgsConstructor;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class InsightService {

    private final InsightRepository insightRepository;
    private final InsightMainCategoryRepository insightMainCategoryRepository;
    private final InsightSubCategoryRepository insightSubCategoryRepository;
    private final InsightTagRepository insightTagRepository;
    private final PinBoardRepository pinBoardRepository;

    @Transactional
    public Long savePublic(InsightRequest insightRequest) throws IOException {

        String crawlingUrl = insightRequest.getUrl();
        Document document = Jsoup.connect(crawlingUrl).get();


        String title = document.select("meta[property=og:title]").first().attr("content");


        String description;
        try {
            description = document.select("meta[property=og:description]").get(0).attr("content");

        } catch (Exception e) {
            description = null;
        }

        String imageUrl;
        try {
            imageUrl = document.select("meta[property=og:image]").get(0).attr("content");

        } catch (Exception e) {
            imageUrl = null;
        }

        String siteName;
        try {
            siteName = document.select("meta[property=og:site_name]").get(0).attr("content");

        } catch (Exception e) {
            siteName = "-";
        }

        // public
        InsightMainCategory insightMainCategory = insightMainCategoryRepository.findById(insightRequest.getInsightMainCategoryId()).get();
        InsightSubCategory insightSubCategory= insightSubCategoryRepository.findById(insightRequest.getInsightSubCategoryId()).get();

        Insight insight = Insight.builder()
                .url(insightRequest.getUrl())
                .title(title)
                .description(description)
                .imgPath(imageUrl)
                .siteName(siteName)
                .insightMainCategory(insightMainCategory)
                .insightSubCategory(insightSubCategory)
                .open(true)
                .build();
        Insight saved = insightRepository.save(insight);

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
        List<Insight> insightList = insightRepository.findByOpen(true);
        List<InsightResponse> insightResponseList = new ArrayList<>();
        for(Insight insight : insightList){
            InsightMainCategoryResponse insightMainCategoryResponse = new InsightMainCategoryResponse(insight.getInsightMainCategory());
            InsightSubCategoryResponse insightSubCategoryResponse = new InsightSubCategoryResponse(insight.getInsightSubCategory());
            List<String> tagList = new ArrayList<>();
            insight.getInsightTagList().stream().forEach(x-> tagList.add(x.getName()));
            insightResponseList.add(new InsightResponse(insight, insightMainCategoryResponse, insightSubCategoryResponse, tagList));
        }
        return insightResponseList;
    }


}


package com.eureka.ationserver.service;

import com.eureka.ationserver.domain.insight.*;
import com.eureka.ationserver.dto.insight.InsightRequest;
import com.eureka.ationserver.dto.insight.InsightResponse;
import com.eureka.ationserver.repository.insight.InsightMainCategoryRepository;
import com.eureka.ationserver.repository.insight.InsightRepository;
import com.eureka.ationserver.repository.insight.InsightSubCategoryRepository;
import lombok.RequiredArgsConstructor;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class InsightService {

    private final InsightRepository insightRepository;
    private final InsightMainCategoryRepository insightMainCategoryRepository;
    private final InsightSubCategoryRepository insightSubCategoryRepository;

    @Transactional
    public Long save(InsightRequest insightRequest) throws IOException {

        String crawlingUrl = insightRequest.getUrl();
        Document document = Jsoup.connect(crawlingUrl).get();


        String title = document.select("meta[property=og:title]").first().attr("content");
        String description = document.select("meta[property=og:description]").get(0).attr("content");
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

        InsightMainCategory insightMainCategory = insightMainCategoryRepository.findById(insightRequest.getInsightMainCategoryId()).get();
        InsightSubCategory insightSubCategory= insightSubCategoryRepository.findById(insightRequest.getInsightSubCategoryId()).get();

        Insight insight = Insight.builder()
                .url(insightRequest.getUrl())
                .title(title)
                .description(description)
                .imageUrl(imageUrl)
                .sightName(siteName)
                .insightMainCategory(insightMainCategory)
                .insightSubCategory(insightSubCategory)
                .build();
        Insight saved = insightRepository.save(insight);

        return saved.getId();
    }

    @Transactional(readOnly = true)
    public List<InsightResponse> findAll(){
        List<InsightResponse> insightResponseList = insightRepository.findAll().stream().map(InsightResponse::new).collect(Collectors.toList());
        return insightResponseList;
    }

    @Transactional
    public List<InsightMainCategoryResponse> findAllMainCategory(){
        List<InsightMainCategoryResponse> insightMainCategoryResponseList = insightMainCategoryRepository.findAll().stream().map(InsightMainCategoryResponse::new).collect(Collectors.toList());
        return insightMainCategoryResponseList;
    }

    @Transactional(readOnly = true)
    public List<InsightSubCategoryResponse> findSubCategory(Long insightMainCategoryId){
        List<InsightSubCategoryResponse> insightSubCategoryResponseList = insightSubCategoryRepository.findByInsightMainCategory_Id(insightMainCategoryId).stream().map(InsightSubCategoryResponse::new).collect(Collectors.toList());
        return insightSubCategoryResponseList;
    }

}


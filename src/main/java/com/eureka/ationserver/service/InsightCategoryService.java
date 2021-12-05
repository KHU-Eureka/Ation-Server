package com.eureka.ationserver.service;

import com.eureka.ationserver.dto.insight.InsightMainCategoryResponse;
import com.eureka.ationserver.dto.insight.InsightSubCategoryResponse;
import com.eureka.ationserver.repository.insight.InsightMainCategoryRepository;
import com.eureka.ationserver.repository.insight.InsightSubCategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class InsightCategoryService {

    private final InsightMainCategoryRepository insightMainCategoryRepository;
    private final InsightSubCategoryRepository insightSubCategoryRepository;

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

package com.eureka.ationserver.service;

import com.eureka.ationserver.dto.category.MainCategoryResponse;
import com.eureka.ationserver.dto.category.SubCategoryResponse;
import com.eureka.ationserver.repository.category.MainCategoryRepository;
import com.eureka.ationserver.repository.category.SubCategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final MainCategoryRepository mainCategoryRepository;
    private final SubCategoryRepository subCategoryRepository;

    @Transactional
    public List<MainCategoryResponse> findAllMainCategory(){
        List<MainCategoryResponse> mainCategoryResponseList = mainCategoryRepository.findAll().stream().map(
            MainCategoryResponse::new).collect(Collectors.toList());
        return mainCategoryResponseList;
    }

    @Transactional(readOnly = true)
    public List<SubCategoryResponse> findSubCategory(Long mainCategoryId){
        List<SubCategoryResponse> subCategoryResponseList = subCategoryRepository.findByMainCategory_Id(mainCategoryId).stream().map(
            SubCategoryResponse::new).collect(Collectors.toList());
        return subCategoryResponseList;
    }

}

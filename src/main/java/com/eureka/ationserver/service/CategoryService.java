package com.eureka.ationserver.service;

import com.eureka.ationserver.dto.category.CategoryResponse;
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

    @Transactional(readOnly = true)
    public List<CategoryResponse.Main> findAllMainCategory(){
        List<CategoryResponse.Main> mainCategoryResponseList = mainCategoryRepository.findAll().stream().map(
            CategoryResponse::toMain).collect(Collectors.toList());
        return mainCategoryResponseList;
    }

    @Transactional(readOnly = true)
    public List<CategoryResponse.Sub> findSubCategory(Long mainCategoryId){
        List<CategoryResponse.Sub> subCategoryResponseList = subCategoryRepository.findByMainCategory_Id(mainCategoryId).stream().map(
            CategoryResponse::toSub).collect(Collectors.toList());
        return subCategoryResponseList;
    }

}

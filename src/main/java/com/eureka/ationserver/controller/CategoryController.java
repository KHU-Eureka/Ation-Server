package com.eureka.ationserver.controller;

import com.eureka.ationserver.dto.category.CategoryResponse;
import com.eureka.ationserver.service.CategoryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@Api(tags = {"Category"})
@RequiredArgsConstructor
public class CategoryController {

  private final CategoryService categoryService;

  @GetMapping("/category/main")
  @ApiOperation(value = "대분류 조회")
  public List<CategoryResponse.Main> findAllMainCategory() {
    return categoryService.findAllMainCategory();
  }

  @GetMapping("/category/sub")
  @ApiOperation(value = "중분류 조회")
  public List<CategoryResponse.Sub> findSubCategory(
      @RequestParam(value = "mainCategoryId") Long mainCategoryId) {
    return categoryService.findSubCategory(mainCategoryId);
  }

}

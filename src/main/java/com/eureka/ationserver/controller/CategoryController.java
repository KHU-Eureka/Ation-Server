package com.eureka.ationserver.controller;

import com.eureka.ationserver.service.CategoryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    @ApiOperation(value="대분류 조회")
    public ResponseEntity findAllMainCategory(){
        return new ResponseEntity(categoryService.findAllMainCategory(), null, HttpStatus.OK);
    }

    @GetMapping("/category/sub")
    @ApiOperation(value="중분류 조회")
    public ResponseEntity findSubCategory(@RequestParam(value="mainCategoryId")Long mainCategoryId){
        return new ResponseEntity(categoryService.findSubCategory(mainCategoryId), null, HttpStatus.OK);
    }
}

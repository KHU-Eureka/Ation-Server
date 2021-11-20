package com.eureka.sensationserver.config.swagger;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

    @Bean
    public Docket restApi(){
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.eureka.sensationserver.controller"))
                .paths(PathSelectors.ant("/api/**"))
                .build();


    }

    private static final String INFO_TITLE = "유레카 API";
    private static final String INFO_VERSION = "0.0.1";
    private static final String INFO_DESC = "유레카 api 명세 입니다.";


    private ApiInfo apiInfo(){
        return new ApiInfoBuilder()
                .title(INFO_TITLE)
                .version(INFO_VERSION)
                .description(INFO_DESC)
                .build();
    }
}
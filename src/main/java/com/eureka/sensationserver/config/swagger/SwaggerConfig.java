package com.eureka.sensationserver.config.swagger;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Parameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.List;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

    @Bean
    public Docket restApi(){

        ParameterBuilder aParameterBuilder = new ParameterBuilder();
        aParameterBuilder.name("Authorization")
                .description("Access Tocken")
                .modelRef(new ModelRef("string"))
                .parameterType("header")
                .required(false)
                .build();


        List<Parameter> aParameters = new ArrayList<>();
        aParameters.add(aParameterBuilder.build());


        return new Docket(DocumentationType.SWAGGER_2)
                .globalOperationParameters(aParameters)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.eureka.sensationserver.controller"))
                .paths(PathSelectors.ant("/api/**"))
                .build();


    }

    private static final String INFO_TITLE = "Sensation API";
    private static final String INFO_VERSION = "0.0.1";
    private static final String INFO_DESC = "Sensation api Docs.";


    private ApiInfo apiInfo(){
        return new ApiInfoBuilder()
                .title(INFO_TITLE)
                .version(INFO_VERSION)
                .description(INFO_DESC)
                .build();
    }
}
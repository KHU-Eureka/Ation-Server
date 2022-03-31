package com.eureka.ationserver.config.swagger;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.ArrayList;
import java.util.List;
import lombok.Data;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Parameter;
import springfox.documentation.service.Tag;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger.web.DocExpansion;
import springfox.documentation.swagger.web.ModelRendering;
import springfox.documentation.swagger.web.OperationsSorter;
import springfox.documentation.swagger.web.TagsSorter;
import springfox.documentation.swagger.web.UiConfiguration;
import springfox.documentation.swagger.web.UiConfigurationBuilder;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

  @Bean
  public Docket restApi() {

    ParameterBuilder aParameterBuilder = new ParameterBuilder();
    aParameterBuilder.name("Authorization")
        .description("Access Tocken")
        .modelRef(new ModelRef("string"))
        .parameterType("header")
        .required(false)
        .build();

    List<Parameter> aParameters = new ArrayList<>();
    aParameters.add(aParameterBuilder.build());

    Docket docket = new Docket(DocumentationType.SWAGGER_2);

    docket.globalOperationParameters(aParameters)
        .apiInfo(apiInfo())
        .select()
        .apis(RequestHandlerSelectors.basePackage("com.eureka.ationserver.controller"))
        .paths(PathSelectors.ant("/api/**"))
        .build();
    int tagOrd = 0;
    docket.tags(
        new Tag("Auth", "인증 API", ++tagOrd),
        new Tag("Persona", "페르소나 API", ++tagOrd),
        new Tag("MyPage", "마이페이지 API", ++tagOrd),
        new Tag("Ideation", "아이데이션 API", ++tagOrd),
        new Tag("Insight", "인사이트 API", ++tagOrd),
        new Tag("Pin", "핀 API", ++tagOrd),
        new Tag("PinBoard", "핀보드 API", ++tagOrd),
        new Tag("Lounge", "라운지 API", ++tagOrd),
        new Tag("Image", "이미지 API", ++tagOrd),
        new Tag("Category", "카테고리 API", ++tagOrd),
        new Tag("Recommend", "추천 기반 데이터 API", ++tagOrd)
    );

    return docket;
  }

  private static final String INFO_TITLE = "Ation API";
  private static final String INFO_VERSION = "0.0.1";
  private static final String INFO_DESC = "Ation api Docs.";


  private ApiInfo apiInfo() {
    return new ApiInfoBuilder()
        .title(INFO_TITLE)
        .version(INFO_VERSION)
        .description(INFO_DESC)
        .build();
  }

  @Bean
  public UiConfiguration uiConfiguration() {
    return UiConfigurationBuilder.builder()
        .deepLinking(false)
        .displayOperationId(false)
        .defaultModelsExpandDepth(0)
        .defaultModelExpandDepth(5)
        .defaultModelRendering(ModelRendering.MODEL)
        .displayRequestDuration(false)
        .docExpansion(DocExpansion.LIST)
        .filter(false)
        .maxDisplayedTags(null)
        .operationsSorter(OperationsSorter.ALPHA)
        .showExtensions(false)
        .tagsSorter(TagsSorter.ALPHA)
        .validatorUrl(null)
        .build();
  }

  @Bean
  public SwaggerDefaultTypeNameProvider swaggerDefaultTypeNameProvider() {
    return new SwaggerDefaultTypeNameProvider();
  }

  @Data
  @ApiModel
  static class Page {

    @ApiModelProperty(value = "페이지 번호")
    private Integer page;

    @ApiModelProperty(value = "페이지 크기")
    private Integer size;

    @ApiModelProperty(value = "정렬")
    private List<String> sort;
  }
}
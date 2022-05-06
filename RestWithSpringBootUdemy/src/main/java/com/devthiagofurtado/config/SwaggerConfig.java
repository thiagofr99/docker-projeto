package com.devthiagofurtado.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Collections;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

    @Bean
    public Docket api(){

        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.devthiagofurtado"))
                .paths(PathSelectors.any())
                .build()
                .apiInfo(apiInfo());
    }

    private ApiInfo apiInfo(){
        return new ApiInfo("RESTFul API With Spring Boot 2.1.3",
                "Some desciption about your API.",
                "v1",
                "Terms Of Service Url",
                new Contact("@DevThiagoFurtado","https://devthiagofurtado.com","devthiagofurtado@gmail.com"),
                "license terms of API",
                "Site of license",
                Collections.emptyList()
                );
    }
}

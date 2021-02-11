package com.lambdaschool.school.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.Pageable;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

// need to add swagger2 and swagger-ui to pom file dependencies
// swagger-ui - open in browser localhost:2019/swagger-ui.html


// Website - http://localhost:2019/swagger-ui.html

@Configuration
@EnableSwagger2
public class Swagger2Config
{
    @Bean
    public Docket api()
    {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors
                        .basePackage("com.lambdaschool.school")) // this changes depending on the application
                .paths(PathSelectors.any())
                .build() // need 'build()' for useDefaultResponseMessages to work
                //                .paths(PathSelectors.regex("/**"))
                .useDefaultResponseMessages(false) // Allows only my exception responses
                .ignoredParameterTypes(Pageable.class) // allows only my paging parameter list
                .apiInfo(apiEndPointsInfo());
    }

    private ApiInfo apiEndPointsInfo()
    {
        return new ApiInfoBuilder().title("Project School Pages Swagger")
                .description("Project School Pages Swagger")
                .contact(new Contact("Alexis Panyathong", "http://www.lambdaschool.com", "alexispanyathong@gmail.com"))
                .license("MIT").licenseUrl("https://github.com/LambdaSchool/java-crudysnacks/blob/master/LICENSE")
                .version("1.0.0").build();
    }
}


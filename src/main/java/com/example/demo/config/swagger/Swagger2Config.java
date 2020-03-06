package com.example.demo.config.swagger;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.AuthorizationScope;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@EnableSwagger2
@Configuration
public class Swagger2Config {
   
	 private ApiInfo apiInfo() {
	        return new ApiInfoBuilder()
	                .title("贴吧接口文档")
	                .description("贴吧接口说明文档")
	                .termsOfServiceUrl("")
	                .version("1.0")
	                .build();
	    }
		
	  
		@Bean
	    public Docket api() {
	        return new Docket(DocumentationType.SWAGGER_2).
	                useDefaultResponseMessages(false)
	                .select()
	                .apis(RequestHandlerSelectors.basePackage("com.example.demo.api"))
	                .paths(PathSelectors.any())//regex("/api/.*")
	                .build()
	                //.securitySchemes(securitySchemes())
	               // .securityContexts(securityContexts())
//	                .globalOperationParameters(pars)
	                .apiInfo(apiInfo());
	    }

//	    private List<ApiKey> securitySchemes() {
//	    	List<ApiKey> list = Lists.newArrayList();
//	    	list.add(new ApiKey("Authorization","Authorization",  "header"));//当前用户的访问令牌，内容为：Bearer token
//	        return list;
//	    }
	    
	    private AuthorizationScope[] scopes() {
	        AuthorizationScope[] scopes = { 
	          new AuthorizationScope("read", "for read operations"), 
	          new AuthorizationScope("write", "for write operations"), 
	          new AuthorizationScope("foo", "Access foo API") };
	        return scopes;
	    }
//	    private List<SecurityContext> securityContexts() {
//	        return Lists.newArrayList(
//	                SecurityContext.builder()
//	                .securityReferences(Lists.newArrayList(new SecurityReference("Authorization", scopes())))
////	              .forPaths(PathSelectors.regex("^(?!auth).*$"))
//	                .forPaths(PathSelectors.any()) //regex("/api/.*"
//	                .build()
//	        );
//	    }
}

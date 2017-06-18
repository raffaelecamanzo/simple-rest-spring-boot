package io.rc.config;

import org.joda.time.DateTime;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * User:    raffaelecamanzo
 * Date:    18/06/2017
 * <p>
 * Description:
 *   Swagger UI configuration file
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig {

	@Bean
	public Docket api() {
		return new Docket(DocumentationType.SWAGGER_2)
				.groupName("My Resources")
				.select()
				.apis(RequestHandlerSelectors.any())
				.paths(PathSelectors.regex("/path/.*"))
				.build()
				.apiInfo(apiInfo())
				.pathMapping("/")
				.directModelSubstitute(DateTime.class, String.class);
	}

	private ApiInfo apiInfo() {
		return new ApiInfoBuilder()
				.title("My Resources - /path REST API")
				.description("Demo Spring RESTful API")
				.termsOfServiceUrl("http://springfox.io")
				.version("2.0")
				.build();
	}

}

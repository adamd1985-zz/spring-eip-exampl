package adamd.web.config;

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * Web configurations.
 * <p/>
 * Swagger ui API configuration defined here.
 * 
 * @author adamd
 * @version 1
 */
@EnableWebMvc
@EnableSwagger2
@Configuration
public class RestConfig {

	/**
	 * To configure HTTP Server.
	 * 
	 * @return The adapted configuration.
	 */
	@Bean
	public WebMvcConfigurer webConfigurer() {
		return new WebMvcConfigurerAdapter() {

			@Override
			public void addResourceHandlers(ResourceHandlerRegistry registry) {
				registry.addResourceHandler("**/swagger-ui.html")
						.addResourceLocations("classpath:/META-INF/resources/");

				registry.addResourceHandler("/webjars/**")
						.addResourceLocations("classpath:/META-INF/resources/webjars/");

			}

			/*
			 * (non-Javadoc)
			 * 
			 * @see org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter#
			 * extendMessageConverters(java.util.List)
			 * 
			 * Only to make JSON human-readable.
			 */
			@Override
			public void extendMessageConverters(List<HttpMessageConverter<?>> converters) {
				for (HttpMessageConverter<?> converter : converters) {
					if (converter instanceof MappingJackson2HttpMessageConverter) {
						MappingJackson2HttpMessageConverter jacksonConverter = (MappingJackson2HttpMessageConverter) converter;
						jacksonConverter.setPrettyPrint(true);
					}
				}
			}
		};
	}

	/**
	 * This bean is used to configure the swagger interface for API documentation. Note above the
	 * configuration of the webjars.
	 * 
	 * @return The configuration.
	 */
	@Bean
	public Docket newsApi() {
		return new Docket(DocumentationType.SWAGGER_2)
				.select()
				.paths(PathSelectors.any())
				.apis(RequestHandlerSelectors.basePackage("adamd"))
				.build();
	}
}

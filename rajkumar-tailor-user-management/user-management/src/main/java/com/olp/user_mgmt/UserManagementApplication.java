package com.olp.user_mgmt;

import java.util.Calendar;
import java.util.Date;
import java.util.Properties;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@SpringBootApplication
@EnableEurekaClient
@EnableFeignClients
@PropertySources({ @PropertySource("classpath:restmessages.properties"),
		@PropertySource("classpath:queries.properties") })
public class UserManagementApplication extends SpringBootServletInitializer {

	@Value("${cors.url}")
	private String corsUrl;
	@Value("${cors.mapping}")
	private String corsMapping;

	// To run the app in prod environment
	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {

		System.out.println("Application is up now from prod environment " + new Date());
		System.out.println(System.getProperty("catalina.base"));
		Properties properties = new Properties();
		try {
			System.out.println(System.getProperty("catalina.base"));
			System.out.println(properties.getProperty("app-name"));
			System.setProperty("spring.profiles.active", "prod");
		} catch (Exception e) {
			System.out.println("config file not exist on directory :: " + e);
		}
		System.out.println("***************************************************");
		return application.sources(UserManagementApplication.class);
	}

	public static void main(String[] args) {
		SpringApplication.run(UserManagementApplication.class, args);

		System.out.println("*******************************************************************");
		System.out.println("User Management Application Started @" + Calendar.getInstance().getTime());
	}

	@SuppressWarnings("deprecation")
	@Bean
	public WebMvcConfigurer corsConfigurer() {
		return new WebMvcConfigurerAdapter() {

			@Override
			public void addCorsMappings(CorsRegistry registry) {
				registry.addMapping(corsMapping).allowedOrigins(corsUrl).allowedMethods("PUT", "DELETE", "GET", "POST",
						"OPTIONS");
			}
		};
	}

	@Bean
	@LoadBalanced
	public RestTemplate getRestTemplate() {
		return new RestTemplate();
	}
}

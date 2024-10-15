package com.cryptic_cat.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
	private static final String PROFILE_PICTURES_UPLOAD_DIR = "src/main/resources/static/profile-pictures/";
	private static final String POST_IMAGE_UPLOAD_DIR = "src/main/resources/static/post-images/";

	public static String getProfilePicturesUploadDir() {
		return PROFILE_PICTURES_UPLOAD_DIR;
	}

	public static String getPostImageUploadDir() {
		return POST_IMAGE_UPLOAD_DIR;
	}

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/post-images/**").addResourceLocations("classpath:/static/post-images/");
		registry.addResourceHandler("/profile-pictures/**").addResourceLocations("classpath:/static/profile-pictures/");
		
	}
}

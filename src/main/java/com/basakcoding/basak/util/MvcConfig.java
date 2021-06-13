package com.basakcoding.basak.util;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MvcConfig implements WebMvcConfigurer {
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		String dirName = "upload/member";
		Path memberAvatarDir = Paths.get(dirName);
		
		String memberAvatarPath = memberAvatarDir.toFile().getAbsolutePath();
		
		registry.addResourceHandler("/"+dirName+"/**")
			.addResourceLocations("file:/" + memberAvatarPath + "/");
	}
}

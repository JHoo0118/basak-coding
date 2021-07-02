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
		String dirName = "upload/";
		Path dir = Paths.get(dirName);
		
		String path = dir.toFile().getAbsolutePath();
		
		registry.addResourceHandler("/"+dirName+"/**")
			.addResourceLocations("file:/" + path + "/");
	}
}

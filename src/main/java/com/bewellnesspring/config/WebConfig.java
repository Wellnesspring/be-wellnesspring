package com.bewellnesspring.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

	@Override
	public void addViewControllers(ViewControllerRegistry registry) {
		// 변수로 처리할 URL 경로 배열
		String[] urls = {"/", "/home", "/about", "/contact", "/any-other-route"};

		// 각 URL 패턴을 동일한 HTML 파일로 매핑
		for (String url : urls) {
			registry.addViewController(url).setViewName("index");
		}
	}
}

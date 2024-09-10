package com.bewellnesspring.config;

import java.util.Arrays;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.bewellnesspring.certification.filter.JsonLoginFilter;
import com.bewellnesspring.certification.service.CertificationService;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

	private final CertificationService service;
	private final BCryptPasswordEncoder bCryptEncoder;

	@Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
      
//		사용자 인증을 간단하게 처리하는 방식 비활성화
		http.httpBasic(AbstractHttpConfigurer::disable);
		http.cors(cors -> cors
				.configurationSource(corsConfigurationSource())
				);
		http.csrf(AbstractHttpConfigurer::disable);
		http.authorizeHttpRequests(request -> request
				.requestMatchers("/auth/**")
				.permitAll()
				.anyRequest()
				.permitAll()
				);
		http.formLogin(AbstractHttpConfigurer::disable);
		http.addFilterAfter(jsonLoginFilter(), UsernamePasswordAuthenticationFilter.class);
		http.logout(logout -> logout
				.logoutUrl("/auth/signout")
				.invalidateHttpSession(true)
				.deleteCookies("JSESSIONID")
				.logoutSuccessUrl("/auth/signoutOk")
				.permitAll()
				);
		http.sessionManagement(session -> session
				.maximumSessions(2)
				);
		return http.build();
	}

//	로그인 처리에 사용할 userDetailService 객체 커스텀
//	스프링 시큐리티가 직접 db에 접근하는 경우 mybatis의 결과값 자동 매핑을 사용할 수 없기에 커스텀화 함
	@Bean
	AuthenticationManager authenticationManager() throws Exception {
		DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
		provider.setUserDetailsService(service);
		provider.setPasswordEncoder(bCryptEncoder);
		return new ProviderManager(provider);
	}
	
//	로그인을 처리할 loginfilter 객체 커스텀
	@Bean
	JsonLoginFilter jsonLoginFilter() throws Exception {
	    JsonLoginFilter jsonLoginFilter = new JsonLoginFilter();
	    jsonLoginFilter.setAuthenticationManager(authenticationManager());
	    return jsonLoginFilter;
	}
	
//	개발용 crossOrigin 지정
//	프로젝트 전체에 적용되어 컨트롤러 별로 CrossOrigin을 적용하지 않아도 됨
	@Bean
	CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList("http://localhost:3000"));
        configuration.setAllowedMethods(Arrays.asList("*"));
        configuration.setAllowedHeaders(Arrays.asList("*"));
        configuration.setAllowCredentials(true);
        
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}


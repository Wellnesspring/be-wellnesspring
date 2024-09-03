package com.bewellnesspring.config;

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
		http.authorizeHttpRequests(request -> request
				.requestMatchers("/auth/**")
				.permitAll()
				.anyRequest()
				.permitAll()
				);
		http.formLogin(formLogin -> formLogin
//				.loginPage("/auth/signin")
				.loginProcessingUrl("/auth/signinProc")
				.usernameParameter("userId")
				.passwordParameter("userPw")
				.defaultSuccessUrl("/auth/signinOk")
				.permitAll()
				);
		http.logout(logout -> logout
				.logoutUrl("/auth/signout")
				.invalidateHttpSession(true)
				.deleteCookies("JSESSIONID")
				.logoutSuccessUrl("/auth/signoutOk")
				.permitAll()
				);
		http.sessionManagement(session -> session
				.maximumSessions(1)
				);
//		사용자 인증을 간단하게 처리하는 방식 비활성화
		http.httpBasic(AbstractHttpConfigurer::disable);
		http.csrf(AbstractHttpConfigurer::disable);
		return http.build();
	}

	@Bean
	AuthenticationManager authenticationManager() throws Exception {
		DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
		provider.setUserDetailsService(service);
		provider.setPasswordEncoder(bCryptEncoder);
		return new ProviderManager(provider);
	}
}


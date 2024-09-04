package com.bewellnesspring.common;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Map;

import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.util.StreamUtils;

import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class JsonLoginFilter extends AbstractAuthenticationProcessingFilter {
	
	private static final String DEFAULT_FILTER_PROCESSES_URL = "/auth/signinProc";
	private final ObjectMapper objectMapper = new ObjectMapper();
	
	public JsonLoginFilter() {
		super(DEFAULT_FILTER_PROCESSES_URL);
	}

	/**
	 * 시큐리티가 json 데이터를 자동 매핑 받아서 로그인 처리를 하기 휘한 메서드 재정의
	 * @param request 로그인 요청
	 * @param response 응답
	 */
	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
			throws AuthenticationException, IOException, ServletException {
		if(request.getContentType() == null || !request.getContentType().equals("application/json")  ) {
			throw new AuthenticationServiceException("Authentication Content-Type not supported: " + request.getContentType());
		}
		
		String messageBody = StreamUtils.copyToString(request.getInputStream(), StandardCharsets.UTF_8);
		
		// JSON 요청에서 로그인 정보를 추출
	    Map<String, String> credentials = objectMapper.readValue(messageBody, Map.class);
	    String userId = credentials.get("userId");
	    String userPw = credentials.get("userPw");
	
	    // UsernamePasswordAuthenticationToken 생성
	    UsernamePasswordAuthenticationToken authRequest = UsernamePasswordAuthenticationToken.unauthenticated(userId, userPw);
	
	    // 인증 시도
	    return this.getAuthenticationManager().authenticate(authRequest);
	}

	/**
	 * 사용자 인증이 성공할 경우 실행될 메서드 재정의
	 * @param request 로그인 요청
	 * @param response 응답
	 * @param authResult 인증 결과
	 */
	@Override
	protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
			Authentication authResult) throws IOException, ServletException {
		 // 권한 검사
	    boolean isUser = authResult.getAuthorities().stream()
	            .anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("ROLE_USER"));
	    if(!isUser) {
	    	unsuccessfulAuthentication(request, response, new BadCredentialsException("The authenticated authority is " + authResult.getName()));
	    }
		SecurityContextHolder.getContext().setAuthentication(authResult);
		response.sendRedirect("/auth/signinOk");
	}

	/**
	 * 사용자 인증이 실패할 경우 실행될 메서드 정의
	 * @param request 로그인 요청
	 * @param response 응답
	 * @param failed AuthenticationException
	 */
	@Override
	protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException failed) throws IOException, ServletException {
		super.unsuccessfulAuthentication(request, response, failed);
	}

}

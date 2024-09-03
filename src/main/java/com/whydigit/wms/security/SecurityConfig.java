package com.whydigit.wms.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true, jsr250Enabled = true, prePostEnabled = true)
public class SecurityConfig {

	@Bean
	TokenAuthenticationFilter tokenAuthenticationFilter() {
		return new TokenAuthenticationFilter();
	}

	@Bean
	RestAuthenticationEntryPoint restAuthenticationEntryPoint() {
		return new RestAuthenticationEntryPoint();
	}

	@Bean
	SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http.cors().and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and().csrf()
				.disable().formLogin().disable().httpBasic().disable().exceptionHandling()
				.authenticationEntryPoint(restAuthenticationEntryPoint()).and().authorizeRequests()
				.antMatchers("/", "/error", "/favicon.ico", "/**/*.png", "/**/*.gif", "/**/*.svg", "/**/*.jpg",
						"/**/*.html", "/**/*.css", "/**/*.js")
				.permitAll()
				.antMatchers("/v2/api-docs", "/configuration/ui", "/swagger-resources/**", "/configuration/security",
						"/swagger-ui.html", "/swagger-ui/*", "/api/auth/signup", "/api/auth/login", "/api/auth/logout",
						"/api/auth/getRefreshToken", "/api/auth/logout", "/api/auth/**"
//						"/api/inward/**", "/api/user/**", "/api/outward/**","/api/reversePick/**",
//						"/api/deliverychallan/**", "/api/codeconversion/**", "/api/vasputaway/**","/api/vasPick/**",
//						"/api/stockprocess/**", "/api/warehousemastercontroller/**",
//						"/api/inward/**", "/api/user/**", "/api/guhan/**", "/api/outward/**","/api/buyerOrder/**",
//						"/api/warehousemastercontroller/**", "/api/vasanth/**", "/api/vascontroller/**","/api/kitting/**",
//						"/api/gatePassIn/**", "/api/grn/**", "/api/putaway/**", "/api/stockRestate/**","/api/cycleCount/**",
//						"/api/inward/**", "/api/user/**", "/api/guhan/**", "/api/outward/**",
//						"/api/warehousemastercontroller/**", "/api/vasanth/**", "/api/vascontroller/**",
//						"/api/gatePassIn/**", "/api/grn/**", "/api/putaway/**", "/api/stockRestate/**","/api/salesReturn/**","/api/locationMovement/**","/api/deKitting/**","/api/pickrequest/**"
								+ "")
				.permitAll().antMatchers("/api/**").hasAnyRole("USER", "GUEST_USER").anyRequest().authenticated();
		http.addFilterBefore(tokenAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);
		return http.build();
	} 
}

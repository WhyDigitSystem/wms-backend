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
						"/api/auth/getRefreshToken","/api/country","/api/state","/api/city","/api/state/country/{country}","/api/region","/api/group","/api/group/{groupid}",
						"/api/unit","/api/unit/{unitid}","/api/locationType/{locationtypeid}","/api/locationType","/api/cellType","/api/cellType/{celltypeid}","/api/company","/api/country/{countryid}",
						"/api/company/{companyid}","/api/branch","/api/branch/{branchid}","/api/getAllBranchByCompany/{company}","/api/customer","/api/customer/{customerid}",
						"/api/warehouse/company","/api/warehouse","/api/warehouselocation/{warehouselocationid}","/api/warehouselocation","/warehouselocation/company",
						"/api/globalparam","/api/material/company/client","/api//material/{materialid}","/api//material","/api/material/company/client1",
						"/api/locationtype/warehouse","/api/rowno/locationtype/warehouse","/api/levelno/rowno/locationtype/warehouse","/api/bins/levelno/rowno/locationtype/warehouse")
				.permitAll().antMatchers("/api/**").hasAnyRole("USER", "GUEST_USER").anyRequest().authenticated();

		http.addFilterBefore(tokenAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);
		return http.build();
	}

	
}

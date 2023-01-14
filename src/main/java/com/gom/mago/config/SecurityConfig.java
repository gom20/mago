package com.gom.mago.config;

import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.gom.mago.jwt.JwtAuthenticationFilter;

import lombok.RequiredArgsConstructor;

@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true)
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	private final JwtAuthenticationFilter jwtAuthenticationFilter;

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/v2/api-docs", "/configuration/**", "/swagger*/**", "/webjars/**");
    }

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		
		http.csrf().disable()
			.httpBasic().disable()
			.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
			.and()	
			.authorizeRequests()
			.antMatchers("/api/auth/login").permitAll()
			.antMatchers("/api/auth/logout").permitAll()
			.antMatchers("/api/auth/signup").permitAll()
			.antMatchers("/api/auth/sendPassword").permitAll()
			.antMatchers("/api/auth/refresh").permitAll()
			.antMatchers("/api/auth/sendConfirmEmail").permitAll()
			.antMatchers("/api/auth/confirmEmail").permitAll()
			.antMatchers("/api/auth/isEmailAthenticated").permitAll()
			.anyRequest().authenticated()
			.and()
			.addFilterBefore(jwtAuthenticationFilter,
						UsernamePasswordAuthenticationFilter.class);
	}
} 

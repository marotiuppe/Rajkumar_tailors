package com.olp.user_mgmt;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.olp.user_mgmt.dao.UserRepository;
import com.olp.user_mgmt.to.JwtTokenFilter;

@EnableWebSecurity
public class ApplicationSecurity extends WebSecurityConfigurerAdapter {

	@Autowired
	private UserRepository userRepo;
	@Autowired
	JwtTokenFilter jwtTokenFilter;
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf().disable();
		http.authorizeRequests().antMatchers("/noauth/**","/rest/**","/resources/**").permitAll();
		http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
		
		http.exceptionHandling().authenticationEntryPoint((request, response, ex) -> {
			response.sendError(HttpServletResponse.SC_UNAUTHORIZED, ex.getMessage());
		});


		http.authorizeRequests().antMatchers("/auth/login","/auth/forgotPassword","/auth/resetPassword","/auth/captcha", "/resources/**").permitAll().anyRequest().authenticated();
		
		
		
		http.addFilterBefore(jwtTokenFilter,UsernamePasswordAuthenticationFilter.class);

	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(username -> userRepo.findByUsername(username)
				.orElseThrow(() -> new UsernameNotFoundException("User " + username + " not found.")));
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Override
	@Bean
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}

//	@Override
//	protected void configure(HttpSecurity http) throws Exception {
//		http.csrf().disable();
//		http.authorizeRequests().antMatchers("/noauth/**","/resources/**").permitAll();
//		//http.authorizeRequests().anyRequest().permitAll();
//		http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
//		
//		http.exceptionHandling().authenticationEntryPoint((request, response, ex) -> {
//			response.sendError(HttpServletResponse.SC_UNAUTHORIZED, ex.getMessage());
//		});
//
//
//		http.authorizeRequests().antMatchers("/auth/login","/auth/forgotPassword","/auth/resetPassword","/auth/captcha", "/resources/**").permitAll().anyRequest().authenticated();
//		
//		http.addFilterBefore(jwtTokenFilter,UsernamePasswordAuthenticationFilter.class);
//
//	}
//
//	@Override
//	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//		auth.userDetailsService(username -> userRepo.findByUsername(username)
//				.orElseThrow(() -> new UsernameNotFoundException("User " + username + " not found.")));
//	}
//
//	@Bean
//	public PasswordEncoder passwordEncoder() {
//		return new BCryptPasswordEncoder();
//	}
//
//	@Override
//	@Bean
//	public AuthenticationManager authenticationManagerBean() throws Exception {
//		return super.authenticationManagerBean();
//	}

}
package com.basakcoding.basak.frontend.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@EnableWebSecurity
@Configuration
public class FrontendSecurityConfig extends WebSecurityConfigurerAdapter {
	
	private static final String[] PERMIT_ALL = {
	        "/",
	        "/auth/**",
	        "/catalog/**",
	        "/admin",
	};
	
	public FrontendSecurityConfig() {
        super();
    }
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
			.csrf().disable()
			.authorizeRequests()
					.antMatchers(PERMIT_ALL).permitAll()
					.antMatchers(
						"/personal/**",
						"/orders/**",
						"/class/**"
					).authenticated()
			.and()
			.formLogin()
				.loginProcessingUrl("/auth/signin")
				.loginPage("/auth/signin")
				.usernameParameter("email")
				.defaultSuccessUrl("/personal/dashboard", true)
				.permitAll()
			.and()
			.logout()
				.logoutUrl("/logout")
				.permitAll()
			.and()
				.exceptionHandling()
				.accessDeniedPage("/admin/login");
	}
	
	@Override
	public void configure(WebSecurity web) throws Exception {
		web.ignoring().antMatchers("/images/**", "/js/**", "/webjars/**", "/css/**", "/fontawesome/**", "/webfonts/**");
	}
	
	@Bean
	public UserDetailsService userDetailsService() {
		return new MemberDetailsService();
	}
	
	@Bean
	public DaoAuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
		authProvider.setUserDetailsService(userDetailsService());
		authProvider.setPasswordEncoder(passwordEncoder());
		return authProvider;
	}
}

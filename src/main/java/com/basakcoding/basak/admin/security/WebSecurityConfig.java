package com.basakcoding.basak.admin.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;


@Configuration
@EnableWebSecurity
@Order(Ordered.HIGHEST_PRECEDENCE)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
	
	public WebSecurityConfig() {
        super();
    }
	
	@Bean
	public UserDetailsService userDetailsService() {
		return new AdminDetailsService();
	}
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	public DaoAuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
		authProvider.setUserDetailsService(userDetailsService());
		authProvider.setPasswordEncoder(passwordEncoder());
	
		return authProvider;
	}
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.authenticationProvider(authenticationProvider());
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
			.csrf().disable()
//			.authorizeRequests()
//				.antMatchers("/admin/**").hasAnyAuthority("Admin")
			.requestMatchers().antMatchers("/admin/**")
			.and()
			.authorizeRequests().anyRequest().hasAnyAuthority("Admin")
			.and()
			.formLogin()
				.loginProcessingUrl("/admin/login")
				.loginPage("/admin/login")
				.usernameParameter("email")
				.defaultSuccessUrl("/admin", true)
				.permitAll()
			.and()
			.logout()
				.logoutUrl("/admin/logout")
				.permitAll()
			.and()
				.exceptionHandling()
				.accessDeniedPage("/admim/login")
			.and()
				.sessionManagement()
				.maximumSessions(1)
                .expiredUrl("/admin/login");
	}

	@Override
	public void configure(WebSecurity web) throws Exception {
		web.ignoring().antMatchers("/images/**", "/js/**", "/webjars/**", "/css/**", "/fontawesome/**", "/webfonts/**");
	}
	
}

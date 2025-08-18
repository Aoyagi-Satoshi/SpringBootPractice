package com.example.demo.Config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableAspectJAutoProxy
public class SecurityConfig {

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http
				.authorizeHttpRequests(authorizeRequests -> authorizeRequests
						.requestMatchers("/admin/signup", "/admin/signin").permitAll()
						.anyRequest().authenticated())
				.formLogin(formLogin -> formLogin
						.loginPage("/admin/signin")
						.usernameParameter("email")
						.passwordParameter("password")
						.defaultSuccessUrl("/admin/contacts", true)
						.permitAll())
				.logout(logout -> logout
						.logoutRequestMatcher(new AntPathRequestMatcher("/admin/logout"))
						.logoutSuccessUrl("/admin/signin?logout")
						.permitAll());
		return http.build();
	}
}

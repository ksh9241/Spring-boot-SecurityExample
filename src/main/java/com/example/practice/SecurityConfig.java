package com.example.practice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter{
	
	@Autowired
	LoginIdPwValidator loginIdPwValidator;

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests()
				.antMatchers("/chk").permitAll()					// chk uri로 들어오는 경우 인증안함.
				.antMatchers("/manage").hasAuthority("ROLE_ADMIN")	// ROLE이 ROLE_ADMIN인 경우 인증됨.
				.anyRequest().authenticated()						// 위의 조건을 제외한 경우는 인증이 필요하다.
			.and()	
				.formLogin()
				.loginPage("/view/login")
				.loginProcessingUrl("/loginProc")
				.usernameParameter("id")
				.passwordParameter("pw")
				.defaultSuccessUrl("/view/dashboard", true)			// 로그인 성공 시 이동할 uri
				.permitAll()
			.and()
				.logout()
				.logoutRequestMatcher(new AntPathRequestMatcher("/logoutProc"))
			;
	}

	@Override
	public void configure(WebSecurity web) throws Exception {
		web.ignoring().antMatchers("/static/js/**"
									,"static/css/**"
									,"/static/img/**"
									,"static/frontend/**"
									);
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(loginIdPwValidator);
	}
}

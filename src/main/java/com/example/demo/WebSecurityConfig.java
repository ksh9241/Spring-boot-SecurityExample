package com.example.demo;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

@Configuration
@EnableWebSecurity // 이 어노테이션을 쓰는 순간 스프링부트가 제공하는 기본적인 시큐리티 설정은 없어진다.  
public class WebSecurityConfig extends WebSecurityConfigurerAdapter{

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests() // 요청을 어떻게 보안을 걸 것인지 설정하는 메소드
				.antMatchers("/","/home").permitAll() // 인증받지 않은 사용자에게도 '/', '/home' 요청이 들어오면 securityFilter로 가지 않고 접속을 허용해라.
				.anyRequest().authenticated() // 그 외 모든 요청은 인증된 사용자에게만 접속을 허용해라.
				.and() // Authentication Filter Chain이 인증되지 않은 사용자를 가로채서 FormLogin 설정이 된 URL로 보낸다.
			.formLogin() //formLogin과 관련된 설정. 
				.loginPage("/login") // 로그인 페이지로 가는 매핑정보 설정 (WebConfig에서 viewResolver가 매핑된 url을 가지고 html로 보낸다.)
				.permitAll() // 모든 사용자가 인증 없이 로그인페이지로 접근 허용
				.and()
			.logout()
				.logoutSuccessUrl("/home") //logout이 성공하면 이동하는 url
				.permitAll();
	}

	@Bean
	@Override
	protected UserDetailsService userDetailsService() {
		UserDetails user =
				User.withDefaultPasswordEncoder()
				.username("user")
				.password("password")
				.roles("USER")
				.build();
		// 위의 코드를 통해 User를 생성한다.
		
		// 실제 사용 시 User의 username,password 등을 String으로 넣지않고,
		// DB에서 조회하여 처리한다.
		
		return new InMemoryUserDetailsManager(user);
	}
	
	
	
}

package com.example.demo;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

//@Configuration
//@EnableWebSecurity // 이 어노테이션을 쓰는 순간 스프링부트가 제공하는 기본적인 시큐리티 설정은 없어진다.  
public class WebSecurityConfig extends WebSecurityConfigurerAdapter{

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests() // 요청을 어떻게 보안을 걸 것인지 설정하는 메소드
				.antMatchers("/","/home","/create").permitAll() // 인증받지 않은 사용자에게도 '/', '/home' 요청이 들어오면 securityFilter로 가지 않고 접속을 허용해라.
				.antMatchers("/admin/**").hasRole("ADMIN") //특정 ROLE만 접근이 가능하다.
				.anyRequest().authenticated() // 그 외 모든 요청은 인증된 사용자에게만 접속을 허용해라.
				// ↑ 위 메소드에서 모든 요청에 대한 인증이 필요하기 때문에 SecurityContext는 authentication 객체가 있는지 확인한다. 존재한다면 이동하려는 url로 이동이 가능하고,
				// authentication이 존재하지 않는다면 formLogin의 url로 이동한다.
				.and() // Authentication Filter Chain이 인증되지 않은 사용자를 가로채서 FormLogin 설정이 된 URL로 보낸다.
			.formLogin() //formLogin과 관련된 설정. 
				.loginPage("/login") // 로그인 페이지로 가는 매핑정보 설정 (WebConfig에서 viewResolver가 매핑된 url을 가지고 html로 보낸다.)
				.permitAll() // 모든 사용자가 인증 없이 로그인페이지로 접근 허용
				.and()
			.logout()
				.logoutSuccessUrl("/home") //logout이 성공하면 이동하는 url
				.permitAll();
	}
	// formLogin 페이지에서 보낸 username의 데이터로 UserDetails라는 객체를 읽어온다. UserDetails의 객체의 비밀번호와 formLogin페이지에서 보낸 비밀번호가 일치하는지 확인한다.
	// 보낸데이터와 UserDetails의 객체 정보가 동일하면 authentication의 객체 후 원래 가고자 했던 Url로 보내준다.
	
	// UserDetails 는 다양한 페이지에서 보내는 데이터를 (user,account ...) 추상화 한 인터페이스이다.

	// There is no PasswordEncoder mapped for the id "null" 라는 오류는
	// Spring Security 5.0 이상부터 암호화된 패스워드를 기본으로 사용하게 되어있다.
	// DelegatingPasswordEncoder를 제공한다. 이 클래스를 사용하게 되면 password앞에 {id}(인코딩 타입)가 포함된다.
	// 타입은 크게 5가지로 평문의 경우 noop, bcrypt, pbkdf2, scrypt, sha256 타입이 존재한다.
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return PasswordEncoderFactories.createDelegatingPasswordEncoder();
	}
	
// 밑에 모듈은 테스트 용이기 때문에 주석처리.
//	@Bean
//	@Override
//	protected UserDetailsService userDetailsService() {
//		UserDetails user =
//				User.withDefaultPasswordEncoder()
//				.username("user")
//				.password("password")
//				.roles("USER")
//				.build();
//		// 위의 코드를 통해 User를 생성한다.
//		
//		// 실제 사용 시 User의 username,password 등을 String으로 넣지않고,
//		// DB에서 조회하여 처리한다.
//		
//		return new InMemoryUserDetailsManager(user);
//	}
}

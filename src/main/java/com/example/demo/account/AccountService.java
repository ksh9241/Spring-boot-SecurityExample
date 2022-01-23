package com.example.demo.account;

import java.util.ArrayList;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AccountService implements UserDetailsService{
	//UserDetailsService 사용하는 클래스를 따로 만들 필요 없이 implements로 메소드 하나만 재정의 해주면 된다.

	//@Autowired
	//private AccountRepository accounts;
	
	//Spring 5 이상부터 빈의 생성자가 하나만 있고, 생성자의 매개변수들이 전부 빈으로 등록되어 있을 때 알아서 주입을 해준다.
	private final AccountRepository accounts;
	private final PasswordEncoder passwordEncoder;
	public AccountService(AccountRepository accounts,PasswordEncoder passwordEncoder) {
		this.accounts = accounts;
		this.passwordEncoder = passwordEncoder;
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Account account = accounts.findByEmail(username);
		
		//authorities의 정보는 WebSecurityConfig -> configure 메소드의 ROLE를 설정할 때 사용한다.
		List<GrantedAuthority> authorities = new ArrayList<>();
		authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
//		UserDetails user = new UserDetails() {
//
//			//이 메소드는 어떤 유저 객체가 가지고 있는 권한.
//			@Override
//			public Collection<? extends GrantedAuthority> getAuthorities() {
//				List<GrantedAuthority> authorities = new ArrayList<>();
//				authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
//				return null;
//			}
//
//			@Override
//			public String getPassword() {
//				return account.getPassword();
//			}
//
//			@Override
//			public String getUsername() { // 유의할 점. username은 unique한 값이여야 한다.
//				return account.getEmail();
//			}
//
//			@Override
//			public boolean isAccountNonExpired() { // 인증정보가 만료되지 않았는지 체크함. (Expired : 만료된)
//				return true;
//			}
//
//			@Override
//			public boolean isAccountNonLocked() { // 인증정보가 잠기지 않았는지 체크함. 
//				return true;
//			}
//
//			@Override
//			public boolean isCredentialsNonExpired() { // 인증정보가 만료되지않은 자격(authentication)인지 체크함.
//				// 일정기간 이후 패스워드를 변경하게하는 기능을 추가할 때 사용하는 메소드
//				return true;
//			}
//
//			@Override
//			public boolean isEnabled() { // 너무 오래되어 계정을 비활성화 시키는 메소드
//				return true;
//			}
//		};
		
		// User 클래스는 UserDetails를 구현하고있는 클래스이다.
		return new User(account.getEmail(), account.getPassword(),authorities);
	}

	//PasswordEncoding하는 메소드
	public Account save(Account account) {
		account.setPassword(passwordEncoder.encode(account.getPassword()));
		return accounts.save(account);
	}

}

package com.training.demo;

import java.security.Principal;
import java.util.Collections;
import java.util.Map;
import java.util.Optional;
import java.util.Scanner;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.repository.CrudRepository;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
public class SpringBootSecurityApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringBootSecurityApplication.class, args);
	}
}

//@Component
class CreateAccount implements CommandLineRunner {
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private AccountRepository accountRepository;
	
	@Override
	public void run(String... args) throws Exception {
		System.out.println("Let's add an user first in the database..");
		Scanner scanner = new Scanner(System.in);
		System.out.println("Enter username : ");
		String username = scanner.next();
		System.out.println("Enter password : ");
		String password = scanner.next();
		
		Account account = new Account();
		account.setUsername(username);
		account.setPassword(passwordEncoder.encode(password));
		account.setActive(true);
		accountRepository.save(account);
	}
}

@Configuration
@EnableGlobalMethodSecurity(prePostEnabled=true)
@EnableWebSecurity
class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	public WebMvcConfigurer corsConfigurer() {
		return new WebMvcConfigurer() {
			
			@Override
			public void addCorsMappings(CorsRegistry registry) {
				registry.addMapping("/**");
			}
		};
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
			.authorizeRequests()
			.antMatchers("/","/index","/css/*","/js/*")
				.permitAll()
			.antMatchers("/v2/**")
				.hasRole("ADMIN")
			.anyRequest()
				.authenticated()
			.and()
				.httpBasic()
			/*.and()
				.x509()
			.and()
				.sessionManagement()
					.sessionCreationPolicy(SessionCreationPolicy.NEVER)*/
			.and()
				.csrf()
					.disable();
			
			
	}
	
}

@RestController
@RequestMapping("/v2/*")
class HelloController2 {
	
	@GetMapping("/hello")
	public Map<String, String> hello(Principal p) {
		return Collections.singletonMap("message", "Hello " + p.getName());
	}

	@GetMapping("/hi")
	public Map<String, String> hi(Principal p) {
		return Collections.singletonMap("message", "Hello " + p.getName());
	}

	@GetMapping("/welcome")
	public Map<String, String> welcome(Principal p) {
		return Collections.singletonMap("message", "Hello " + p.getName());
	}
}


@RestController
class HelloController {
	
	@GetMapping("/hello")
	public Map<String, String> hello(Principal p) {
		return Collections.singletonMap("message", "Hello " + p.getName());
	}

	@GetMapping("/hi")
	@PreAuthorize("hasRole('ROLE_USER')")
	public Map<String, String> hi(Principal p) {
		return Collections.singletonMap("message", "Hello " + p.getName());
	}

	@GetMapping("/welcome")
	@PreAuthorize("hasRole('ROLE_GUEST')")
	public Map<String, String> welcome(Principal p) {
		return Collections.singletonMap("message", "Hello " + p.getName());
	}

	
}

@Service
class AccountDetailsService implements UserDetailsService {

	@Autowired
	private AccountRepository accountRepository;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		return accountRepository.findByUsername(username)
				.map(account ->
						new User(username, 
								 account.getPassword(), 
								 AuthorityUtils.createAuthorityList("ROLE_ADMIN", "ROLE_USER")))
				.orElseThrow(() ->
						new UsernameNotFoundException("couldn't find user " + username));
	}
}

interface AccountRepository extends CrudRepository<Account, Integer> {
	
	Optional<Account> findByUsername(String username);
}

@Entity
class Account {
	
	@Id
	@GeneratedValue
	private int id;
	
	private String username;
	private String password;
	private boolean active;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public boolean isActive() {
		return active;
	}
	public void setActive(boolean active) {
		this.active = active;
	}
	
	
}
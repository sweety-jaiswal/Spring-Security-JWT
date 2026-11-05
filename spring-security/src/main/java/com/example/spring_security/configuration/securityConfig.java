package com.example.spring_security.configuration;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class securityConfig {
	
	@Autowired
	private JwtFilter jwtFilter;
	
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception{
		
		
		//  call -->> http://localhost:8080/home  ||  spring security 6
		httpSecurity.csrf(csrf -> csrf.disable())
		            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
		            .authorizeHttpRequests(authorize -> authorize
		            .requestMatchers("/home", "/login", "/get-data-from-kafka").permitAll()
		            .requestMatchers(HttpMethod.POST, "/signupAsStudent").permitAll()
		            .requestMatchers(HttpMethod.POST, "/signupAsAdmin").permitAll()
		            .requestMatchers(HttpMethod.POST, "/add-data-to-kafka").permitAll()
		            .requestMatchers("/student").hasRole("STUDENT")
		            .requestMatchers("/admin").hasRole("ADMIN")
		            .requestMatchers("/check-shared-endpoint").hasAnyRole("STUDENT", "ADMIN")
		            // till here authorization part
		            .anyRequest()
		            .authenticated()
	                )
		            // till here authentication part
		            .httpBasic(Customizer.withDefaults())
		            .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
		           //  httpBasic-- for postman
		           // .formLogin(Customizer.withDefaults());     
		          //  used to declare medium of login page (either form login or postman)
		
		return httpSecurity.build();
	}
	
	@Bean
	public UserDetailsService userDetailsService() {
		
//		UserDetails user1 = User.builder()
//				                .username("kohli")
//				                .password(passwordEncoder().encode("kohli123"))
//				                .roles("STUDENT")
//				                .build();
//		
//		UserDetails user3 = User.builder()
//                .username("bumrah")
//                .password(passwordEncoder().encode("bumrah123"))
//                .roles("STUDENT")
//                .build();
//		
//		UserDetails user2 = User.builder()
//                .username("rohit")
//                .password(passwordEncoder().encode("rohit123"))
//                .roles("ADMIN")
//                .build();
//		
//		return new InMemoryUserDetailsManager(user1, user2, user3);
		
		return new CustomUserDetailsService();
	}
	
	//   extra this + 2 custom classes
	
	
	@Bean
	public AuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider(userDetailsService());
		// daoAuthenticationProvider.setUserDetailsService(userDetailsService());
	    daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
		return daoAuthenticationProvider;
	} 
	
	@Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }
	
	
	@Bean
	 public PasswordEncoder passwordEncoder() {
		 return new BCryptPasswordEncoder();
	 }
	

}

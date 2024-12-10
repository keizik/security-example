package lt.keizik.securityexample;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

	@Autowired
	BCryptPasswordEncoder passwordEncoder;

	/*
	 * @Autowired public void configureGlobal(AuthenticationManagerBuilder auth) //
	 * teisingas variantas, kaip ivesti usrname su // password throws Exception {
	 * auth.jdbcAuthentication() .dataSource(dataSource) .withDefaultSchema()
	 * .withUser(User.withUsername("user")
	 * .password(PasswordEncoderFactories.createDelegatingPasswordEncoder().encode(
	 * "pass")) .roles("USER"));
	 * 
	 * }
	 */
	@Bean
	public AuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
		authenticationProvider.setUserDetailsService(userDetailService);
		authenticationProvider.setPasswordEncoder(passwordEncoder);
		return authenticationProvider;
	}

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

		http.authorizeHttpRequests((authz) -> authz.requestMatchers(AntPathRequestMatcher.antMatcher("/h2-console/**"))
				.permitAll().requestMatchers(AntPathRequestMatcher.antMatcher("/h2-console")).permitAll()
				.requestMatchers("/").permitAll().requestMatchers("/register").permitAll().requestMatchers("/admin")

				.hasAuthority("ADMIN").anyRequest().authenticated()

		).csrf(csrf -> csrf.ignoringRequestMatchers(AntPathRequestMatcher.antMatcher("/h2-console/**"))) // apsauga
																											// nuo
																											// isilauzimo

				.formLogin().loginPage("/login").permitAll().and().authenticationProvider(authenticationProvider())

				.logout().logoutRequestMatcher(new AntPathRequestMatcher("/logout"))

				.logoutSuccessUrl("/login").permitAll();

		return http.build();
	}

	@Autowired
	private SSUserDetailsService userDetailService;
	/*
	 * @Bean public UserDetailsService userDetailsServiceBean() throws Exception {
	 * return new SSUserDetailsService(userRepository); }
	 */

	/*
	 * @Bean public InMemoryUserDetailsManager userDetailsService() {//
	 * PasswordEncoder encoder =
	 * PasswordEncoderFactories.createDelegatingPasswordEncoder(); UserDetails user1
	 * = User.withUsername("dave").password(encoder.encode("begreat")).authorities(
	 * "ADMIN").build(); UserDetails user2 =
	 * User.withUsername("user").password(encoder.encode("password")).authorities(
	 * "USER") .build();
	 * 
	 * return new InMemoryUserDetailsManager(user1,user2); }
	 */

}

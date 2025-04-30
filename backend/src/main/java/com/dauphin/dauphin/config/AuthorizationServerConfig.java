package com.dauphin.dauphin.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.MediaType;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.server.authorization.OAuth2Authorization;
import org.springframework.security.oauth2.server.authorization.config.annotation.web.configurers.OAuth2AuthorizationServerConfigurer;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;
import org.springframework.security.web.util.matcher.MediaTypeRequestMatcher;

@Configuration
public class AuthorizationServerConfig {
    // @Bean
    // @Order(1)
    // public SecurityFilterChain authorizationServerSecurityChain(HttpSecurity http) throws Exception{
    //     OAuth2AuthorizationServerConfigurer authorizationServerConfigurer = OAuth2AuthorizationServerConfigurer.authorizationServer();

    //     http
    //         .cors(Customizer.withDefaults())
    //         .securityMatcher(authorizationServerConfigurer.getEndpointsMatcher())
    //         .with(authorizationServerConfigurer, (authorizationServer) ->
    //             authorizationServer.oidc(Customizer.withDefaults())
    //         )
    //         .authorizeHttpRequests((authorize)->
    //             authorize.anyRequest().permitAll()
    //         )
    //         .oauth2ResourceServer((resourceServer) ->
    //             resourceServer.jwt(Customizer.withDefaults())
    //         )
    //         .exceptionHandling((exceptions)->
    //             exceptions.defaultAuthenticationEntryPointFor(new LoginUrlAuthenticationEntryPoint("/login"), new MediaTypeRequestMatcher(MediaType.TEXT_HTML))
    //         )
    //         .defaultAuthenticationEntryPointFor(
    //             (request, response, authException) -> {
    //                 response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
    //                 response.setContentType("application/json");
    //                 response.getWriter().write("{\"error\": \"Unauthorized\"}");
    //             },
    //             new MediaTypeRequestMatcher(MediaType.APPLICATION_JSON)
    //         );

    //         return http.build();
    // }

    @Bean
	@Order(2)
	public SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http)
			throws Exception {
		http
            .httpBasic(Customizer.withDefaults())
            .csrf(csrf -> csrf.disable()) // Desativa CSRF para facilitar testes com POST
            .cors(Customizer.withDefaults())
			.authorizeHttpRequests((authorize) -> authorize
                // .requestMatchers("/auth/**").permitAll()
				.anyRequest().authenticated()
			)
            .oauth2ResourceServer(resourceServer -> resourceServer
				.jwt(Customizer.withDefaults())
			)
            .formLogin(Customizer.withDefaults());
		return http.build();
	}

    @Bean
	public UserDetailsService userDetailsService(PasswordEncoder passwordEncoder) {
		UserDetails userDetails = User.builder()
				.username("user")
				.password(passwordEncoder.encode("password"))
				.roles("USER")
				.build();

		return new InMemoryUserDetailsManager(userDetails);
	}
}

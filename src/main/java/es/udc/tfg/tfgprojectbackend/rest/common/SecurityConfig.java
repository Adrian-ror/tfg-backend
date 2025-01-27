package es.udc.tfg.tfgprojectbackend.rest.common;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import org.springframework.web.filter.CorsFilter;

import java.util.Arrays;

import static org.springframework.security.web.util.matcher.AntPathRequestMatcher.antMatcher;

/**
 * The Class SecurityConfig.
 */

@Configuration
@EnableWebSecurity
public class SecurityConfig {

	/**
	 * The jwt filter.
	 */
	@Autowired
	private JwtFilter jwtFilter;

	/**
	 * Security filter chain.
	 *
	 * @param http the http
	 * @return the security filter chain
	 * @throws Exception the exception
	 */

	@Bean
	protected SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		// @formatter:off
		http.cors(cors -> cors.configurationSource(corsConfigurationSource()))
				.csrf(csrf -> csrf.disable())
				.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
				.authorizeHttpRequests(authorize -> authorize
						// Public endpoints
						.requestMatchers(HttpMethod.POST, "/users/signUp").permitAll()
						.requestMatchers(HttpMethod.POST, "/users/login").permitAll()
						.requestMatchers(HttpMethod.POST, "/users/loginFromServiceToken").permitAll()
						.requestMatchers("/shopping/shipping-methods").permitAll()
						.requestMatchers(HttpMethod.GET,"/catalog/products/**").permitAll()
						.requestMatchers("/catalog/parent_categories").permitAll()
						.requestMatchers(HttpMethod.GET,"/catalog/categories/{id}").permitAll()
						.requestMatchers(HttpMethod.GET, "/sellers/products/{productId}/is-purchased").permitAll()
						.requestMatchers(HttpMethod.GET, "/shopping/products/{productId}/is-reviewed").permitAll()
						// User endpoints
						.requestMatchers(HttpMethod.PUT, "/users/*").hasAnyRole("CLIENT", "PROVIDER")
						.requestMatchers(HttpMethod.POST, "/users/*/changePassword").hasAnyRole("CLIENT", "PROVIDER")
						.requestMatchers(HttpMethod.POST, "/users/*/ban").hasRole("ADMIN")
						.requestMatchers(HttpMethod.POST, "/users/*/unban").hasRole("ADMIN")
						.requestMatchers(HttpMethod.GET, "/users/list").hasRole("ADMIN")
						.requestMatchers(HttpMethod.GET, "/users/active").hasRole("ADMIN")
						.requestMatchers(HttpMethod.GET, "/users/banned").hasRole("ADMIN")

						// Catalog endpoints
						.requestMatchers(HttpMethod.POST, "/catalog/categories/new").hasRole("ADMIN")
						.requestMatchers(HttpMethod.PUT, "/catalog/categories/{id}/update").hasRole("ADMIN")
						.requestMatchers(HttpMethod.DELETE, "/catalog/categories/{id}/delete").hasRole("ADMIN")
						.requestMatchers(HttpMethod.POST, "/catalog/categories/{parentId}/subcategories").hasRole("ADMIN")
						.requestMatchers(HttpMethod.GET, "/catalog/all-products").hasRole("ADMIN")

						// Order endpoints
						.requestMatchers(HttpMethod.GET, "/orders/**").hasAnyRole("CLIENT", "ADMIN")
						.requestMatchers(HttpMethod.GET, "/orders/all").hasAnyRole( "ADMIN")
						.requestMatchers(HttpMethod.PUT, "/orders/{orderId}/state").hasRole("ADMIN")
						.requestMatchers(HttpMethod.GET, "/orders/state").hasAnyRole("CLIENT", "ADMIN")

						// Payment methods endpoints
						.requestMatchers(HttpMethod.POST, "/paymentMethods/add").hasAnyRole("CLIENT")
						.requestMatchers(HttpMethod.PUT, "/paymentMethods/update/{methodId}").hasAnyRole("CLIENT")
						.requestMatchers(HttpMethod.DELETE, "/paymentMethods/remove/{methodId}").hasAnyRole("CLIENT")
						.requestMatchers(HttpMethod.GET, "/paymentMethods/list").hasAnyRole("CLIENT")
						.requestMatchers(HttpMethod.GET, "/paymentMethods/get/{methodId}").hasAnyRole("CLIENT")
						.requestMatchers(HttpMethod.GET, "/paymentMethods/default").hasAnyRole("CLIENT")

						// Seller endpoints
						.requestMatchers(HttpMethod.POST, "/sellers/products").hasRole("PROVIDER")
						.requestMatchers(HttpMethod.PUT, "/sellers/products/{productId}").hasRole("PROVIDER")
						.requestMatchers(HttpMethod.DELETE, "/sellers/products/{productId}").hasRole("PROVIDER")
						.requestMatchers(HttpMethod.GET, "/sellers/products").hasRole("PROVIDER")
                        .requestMatchers(HttpMethod.GET, "/sellers/products/{productId}").hasRole("PROVIDER")
                        .requestMatchers(HttpMethod.GET, "/sellers/sold-products").hasRole("PROVIDER")

						// Shopping cart endpoints
						.requestMatchers(HttpMethod.POST, "/shopping/shopping-carts/*/add").hasAnyRole("CLIENT")
						.requestMatchers(HttpMethod.POST, "/shopping/shopping-carts/*/update").hasAnyRole("CLIENT")
						.requestMatchers(HttpMethod.POST, "/shopping/shopping-carts/*/remove").hasAnyRole("CLIENT")
						.requestMatchers(HttpMethod.POST, "/shopping/shopping-carts/*/buy").hasAnyRole("CLIENT")
						.requestMatchers(HttpMethod.POST, "/shopping/wish-lists/*/add").hasAnyRole("CLIENT")
						.requestMatchers(HttpMethod.POST, "/shopping/wish-lists/*/remove").hasAnyRole("CLIENT")
						.requestMatchers(HttpMethod.POST, "/shopping/products/*/review").hasAnyRole("CLIENT")
						.requestMatchers(HttpMethod.GET, "/shopping/products/*/reviews").permitAll()

						// User addresses endpoints
						.requestMatchers(HttpMethod.POST, "/user-addresses").hasAnyRole("CLIENT")
						.requestMatchers(HttpMethod.PUT, "/user-addresses/{addressId}").hasAnyRole("CLIENT")
						.requestMatchers(HttpMethod.DELETE, "/user-addresses/{addressId}").hasAnyRole("CLIENT")
						.requestMatchers(HttpMethod.GET, "/user-addresses/default").hasAnyRole("CLIENT")
						.requestMatchers(HttpMethod.GET, "/user-addresses/{addressId}").hasAnyRole("CLIENT")
						.requestMatchers(HttpMethod.GET, "/user-addresses").hasAnyRole("CLIENT")

						// Reports endpoints
						.requestMatchers(HttpMethod.POST, "/reports").hasAnyRole("CLIENT")
						.requestMatchers(HttpMethod.GET, "/reports/{reportId}").hasAnyRole("CLIENT", "ADMIN")
						.requestMatchers(HttpMethod.GET, "/reports").hasAnyRole("ADMIN")
						.requestMatchers(HttpMethod.PATCH, "/reports/{reportId}/status").hasRole("ADMIN")
						.requestMatchers(HttpMethod.GET, "/reports/status").hasAnyRole("ADMIN")

						// Chat endpoints
						.requestMatchers(HttpMethod.POST, "/chats").hasAnyRole("CLIENT", "PROVIDER", "ADMIN")
						.requestMatchers(HttpMethod.GET, "/chats/{chatId}").hasAnyRole("CLIENT", "PROVIDER", "ADMIN")
						.requestMatchers(HttpMethod.GET, "/chats/user/{userId}").hasAnyRole("CLIENT", "PROVIDER", "ADMIN")
						.requestMatchers(HttpMethod.DELETE, "/chats/{chatId}").hasAnyRole("CLIENT", "PROVIDER", "ADMIN")
						.requestMatchers(HttpMethod.POST, "/chats/{chatId}/messages").hasAnyRole("CLIENT", "PROVIDER", "ADMIN")
						.requestMatchers(HttpMethod.GET, "/chats/{participantUserName}/messages").hasAnyRole("CLIENT", "PROVIDER", "ADMIN")

						// Deny all other requests
						.anyRequest().denyAll()
				)
				.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
		// @formatter:on
		http.headers(headers -> headers.frameOptions(frameOptions -> frameOptions.sameOrigin()));
		return http.build();
	}

	/**
	 * Authentication manager.
	 *
	 * @param authenticationConfiguration the authentication configuration
	 * @return the authentication manager
	 * @throws Exception the exception
	 */
	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration)
			throws Exception {
		return authenticationConfiguration.getAuthenticationManager();
	}

	/**
	 * Cors configuration source.
	 *
	 * @return the cors configuration source
	 */

	@Bean
	public CorsConfigurationSource corsConfigurationSource() {
		CorsConfiguration config = new CorsConfiguration();
		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		config.addAllowedOrigin("http://localhost:5173");
		config.addAllowedOrigin("https://tfg-frontend-alpha.vercel.app");
		config.setAllowCredentials(true);
		config.addAllowedHeader("*");
		config.addAllowedMethod("*");
		source.registerCorsConfiguration("/**", config);
		return source;
	}

	@Bean
	public CorsFilter corsFilter() {
		CorsConfiguration config = new CorsConfiguration();
		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();

		// Permitir todas las solicitudes de origen desde http://localhost:3000
		config.addAllowedOrigin("http://localhost:5173");
		config.addAllowedOrigin("https://tfg-frontend-alpha.vercel.app");

		// Permitir todas las cabeceras
		config.addAllowedHeader("*");

		// Permitir todos los métodos (GET, POST, PUT, DELETE, etc.)
		config.addAllowedMethod("*");

		// Permitir las credenciales
		config.setAllowCredentials(true);

		// Registrar la configuración para todas las rutas
		source.registerCorsConfiguration("/**", config);

		// Retornar un filtro CORS
		return new CorsFilter(source);
	}

}

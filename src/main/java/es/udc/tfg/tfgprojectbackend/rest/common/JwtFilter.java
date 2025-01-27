package es.udc.tfg.tfgprojectbackend.rest.common;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

/**
 * A filter that intercepts requests to check if they contain a valid JWT token.
 * If the token is valid, the filter sets the security context with the user's information.
 */

//Extends OncePerRequestFilter to ensure a single execution per request dispatch.

@Component
public class JwtFilter extends OncePerRequestFilter {

	/**
	 * The generator of JWT tokens.
	 */
	@Autowired
	private JwtGenerator jwtGenerator;


	/**
	 * Checks if the request contains a valid JWT token.
	 * If the token is valid, the filter sets the security context with the user's information.
	 * @param request the request.
	 * @param response the response.
	 * @param filterChain the filter chain.
	 * @throws ServletException if an error occurs.
	 * @throws IOException if an error occurs.
	 */
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
		throws ServletException, IOException {
		
		String authHeaderValue = request.getHeader(HttpHeaders.AUTHORIZATION);
		
		if (authHeaderValue == null || !authHeaderValue.startsWith("Bearer ")) {
			filterChain.doFilter(request, response);
			return;
		}
		
		try {
			
			String serviceToken = authHeaderValue.replace("Bearer ", "");
			JwtInfo jwtInfo = jwtGenerator.getInfo(serviceToken);
			
			request.setAttribute("serviceToken", serviceToken);
			request.setAttribute("userId", jwtInfo.getUserId());
			
			configureSecurityContext(jwtInfo.getUserName(), jwtInfo.getRole());
			
		} catch (Exception e) {
			 response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
			 return;
		}
		
		filterChain.doFilter(request, response);
		
	}

	/**
	 * Configures the security context with the given user's information.
	 * @param userName the user's name.
	 * @param role the user's role.
	 */
	private void configureSecurityContext(String userName, String role) {
		
		Set<GrantedAuthority> authorities = new HashSet<>();
		
		authorities.add(new SimpleGrantedAuthority("ROLE_" + role));
		
		SecurityContextHolder.getContext().setAuthentication(
			new UsernamePasswordAuthenticationToken(userName, null, authorities));
		
	}

}

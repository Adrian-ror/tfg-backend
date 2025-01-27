package es.udc.tfg.tfgprojectbackend.rest.common;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * Implementation of the JwtGenerator interface.
 */
// @Component annotation indicates that an annotated class is a "component".
// Such classes are considered as candidates for auto-detection
// when using annotation-based configuration and classpath scanning.
@Component
public class JwtGeneratorImpl implements JwtGenerator {

	/**
	 * The sign key used to sign the JWT.
	 */
	// @Value annotation is used to assign default values to the fields.
	@Value("${project.jwt.signKey}")
	private String signKey;

	/**
	 * The expiration time of the JWT.
	 */
	@Value("${project.jwt.expirationMinutes}")
	private long expirationMinutes;

	@Override
	public String generate(JwtInfo info) {

		return Jwts.builder()
			.claim("userId", info.getUserId())
			.claim("role", info.getRole())
			.setExpiration(new Date(System.currentTimeMillis() + expirationMinutes*60*1000))
			.signWith(SignatureAlgorithm.HS512, signKey.getBytes())
			.compact();

	}

	@Override
	public JwtInfo getInfo(String token) {
		
		Claims claims = Jwts.parser()
	        .setSigningKey(signKey.getBytes())
	        .parseClaimsJws(token)
	        .getBody();
		
		return new JwtInfo(
			((Integer) claims.get("userId")).longValue(), 
			claims.getSubject(), 
			(String) claims.get("role"));
		
	}

}

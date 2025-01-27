package es.udc.tfg.tfgprojectbackend.rest.common;

/**
 * Interface for the JWT generator.
 */
public interface JwtGenerator {

	/**
	 * Generates a JWT token.
	 *
	 * @param info the information to be included in the token.
	 * @return the generated token.
	 */
	String generate(JwtInfo info);

	/**
	 * Gets the information from a token.
	 *
	 * @param token the token.
	 * @return the information from the token.
	 */
	JwtInfo getInfo(String token);

}

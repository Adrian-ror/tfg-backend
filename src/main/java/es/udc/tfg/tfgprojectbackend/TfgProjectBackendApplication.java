package es.udc.tfg.tfgprojectbackend;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
/**
 * Main class of the Spring Boot application.
 */

//@SpringBootApplication is a convenience annotation that adds all the following:
// - @Configuration: Tags the class as a source of bean definitions for the application context.
// - @EnableAutoConfiguration: Tells Spring Boot to start adding beans based on classpath settings, other beans, and various property settings.
// - @ComponentScan: Tells Spring to look for other components, configurations, and services in the package, allowing it to find the controllers.


@SpringBootApplication
public class TfgProjectBackendApplication {

	/**
	 * Main method of the Spring Boot application.
	 * @param args Command line arguments.
	 */
	public static void main(String[] args) {
		SpringApplication.run(TfgProjectBackendApplication.class, args);
	}

	/**
	 * Method that creates a BCryptPasswordEncoder bean.
	 * @return BCryptPasswordEncoder bean.
	 */
	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}


	/**
	 * Message source.
	 *
	 * @return the message source
	 */
	@Bean
	public MessageSource messageSource() {

		ReloadableResourceBundleMessageSource bean = new ReloadableResourceBundleMessageSource();

		bean.setBasename("classpath:messages");
		bean.setDefaultEncoding("UTF-8");

		return bean;
	}

	/**
	 * Validator.
	 *
	 * @return the local validator factory bean
	 */
	@Bean
	public LocalValidatorFactoryBean validator() {

		LocalValidatorFactoryBean bean = new LocalValidatorFactoryBean();

		bean.setValidationMessageSource(messageSource());

		return bean;
	}
}

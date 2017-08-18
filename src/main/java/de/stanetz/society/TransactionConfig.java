/**
 *
 */
package de.stanetz.society;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.neo4j.web.support.OpenSessionInViewInterceptor;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.handler.MappedInterceptor;

/**
 * Configuration which ensures that the transaction starts which each rest-call,
 * this is necessary because otherwise at a put the read and save happens in two
 * transactions and sessions. As a consequence the update of relations fails.
 * See
 * https://stackoverflow.com/questions/44907670/spring-data-rest-with-neo4j-how-to-remove-relationship
 * for details.
 *
 * @author niels
 *
 */
@Configuration
@EnableTransactionManagement
public class TransactionConfig {

	/**
	 * Create an {@link OpenSessionInViewInterceptor}.
	 * @return an {@link OpenSessionInViewInterceptor}.
	 */
	@Bean
	public OpenSessionInViewInterceptor openSessionInViewInterceptor() {
		return new OpenSessionInViewInterceptor();
	}


	/**
	 * Register the {@link OpenSessionInViewInterceptor} for each request.
	 * @return
	 */
	@Bean
	public MappedInterceptor myMappedInterceptor() {
		return new MappedInterceptor(new String[] { "/**" }, openSessionInViewInterceptor());
	}
}

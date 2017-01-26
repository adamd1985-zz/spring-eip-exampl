package adamd;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.stereotype.Component;

/**
 * Main entry point.
 * 
 * @author adamd
 * @version 1
 */
@SpringBootApplication
@EnableAutoConfiguration
@EnableJms
@Component("springIntPocApplication")
public class Main {
	private static final Logger LOGGER = LoggerFactory.getLogger(Main.class);

	public static void main(String[] args) throws IOException, InterruptedException, ExecutionException {
		LOGGER.info("Starting App");

		final ConfigurableApplicationContext ctx = SpringApplication.run(Main.class, args);
		ctx.registerShutdownHook();
	}
}

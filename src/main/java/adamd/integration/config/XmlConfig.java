package adamd.integration.config;

import java.util.concurrent.Executor;

import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

@Configuration
@ConditionalOnExpression("'${app.integration.style}' eq 'XML'")
@ImportResource(locations = "classpath:/META-INF/spring/integration/lotto-flow-config.xml")
public class XmlConfig {

	@Bean("asyncExecutor")
	public Executor getAsyncExecutor() {
		ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
		executor.setCorePoolSize(2);
		executor.setMaxPoolSize(2);
		executor.setQueueCapacity(50);
		executor.setThreadNamePrefix("TestThread-");
		executor.initialize();
		return executor;
	}

	@Bean("httpRequestFactory")
	public ClientHttpRequestFactory getHttpRequestFactory() {
		SimpleClientHttpRequestFactory reqFactory = new SimpleClientHttpRequestFactory();
		reqFactory.setConnectTimeout(1000 * 60 * 4);
		reqFactory.setReadTimeout(1000 * 60 * 4);
		return reqFactory;
	}
}

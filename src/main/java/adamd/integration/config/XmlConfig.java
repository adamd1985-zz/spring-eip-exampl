package adamd.integration.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;

@Configuration
@ConditionalOnExpression("'${app.integration.style}' eq 'XML'")
@ImportResource(locations = "classpath:/META-INF/spring/integration/lotto-flow-config.xml")
public class XmlConfig {

}

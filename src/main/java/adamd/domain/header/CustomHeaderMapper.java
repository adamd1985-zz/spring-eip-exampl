package adamd.domain.header;

import org.springframework.integration.jms.DefaultJmsHeaderMapper;
import org.springframework.messaging.MessageHeaders;

import javax.jms.JMSException;
import javax.jms.Message;

/**
 * Created by ANDREA-SE on 14/03/2017.
 */
public class CustomHeaderMapper extends DefaultJmsHeaderMapper {

    @Override
    public void fromHeaders(MessageHeaders headers, Message jmsMessage) {
        super.fromHeaders(headers, jmsMessage);

        try {
            jmsMessage.setObjectProperty("history", headers.get("history"));
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }
}

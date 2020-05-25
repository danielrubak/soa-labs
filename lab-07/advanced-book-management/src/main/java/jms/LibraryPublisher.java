package jms;

import javax.annotation.Resource;
import javax.faces.bean.ManagedBean;
import javax.jms.*;

@ManagedBean(name="LibraryPublisher")
public class LibraryPublisher {
    @Resource(mappedName = "java:/jms/topic/SOA_test")
    Topic myTopic;
    @Resource
    ConnectionFactory topicConnectionFactory;

    public void sendMessage(String msgTopic, String msgValue){
        Connection con = null;
        try {
            con = topicConnectionFactory.createConnection();
            Session ses=con.createSession();
            MessageProducer publisher=ses.createProducer(myTopic);
            Message msg=ses.createTextMessage();

            msg.setStringProperty("msgTopic", msgTopic);
            msg.setStringProperty("msgValue", msgValue);
            publisher.send(msg);

            System.out.println("Message '" + msgValue + "' for topic '" + msgTopic + "' has been sent!.");

        } catch (JMSException e) {
            e.printStackTrace();
        }
    }
}

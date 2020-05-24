package forum;

import javax.annotation.Resource;
import javax.faces.bean.ManagedBean;
import javax.jms.*;

@ManagedBean(name= "ForumBean")
public class ForumBean {
    @Resource(mappedName = "java:/jms/topic/SOA_test")
    Topic myTopic;
    @Resource
    ConnectionFactory topicConnectionFactory;

    public void sendMessage(String targetTopicName, String targetTopicMsg){
        Connection con = null;

        try {
            con = topicConnectionFactory.createConnection();
            Session ses = con.createSession();
            MessageProducer publisher = ses.createProducer(myTopic);
            Message msg=ses.createTextMessage();

            msg.setStringProperty("msgTopic", targetTopicName);
            msg.setStringProperty("msgValue", targetTopicMsg);
            publisher.send(msg);

            System.out.println("Message '" + targetTopicMsg + "' for '" + targetTopicName + "' has been sent!.");
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }

    public void sendDirectMessage(String user, String msgValue){
        Connection con = null;

        try {
            con = topicConnectionFactory.createConnection();
            Session ses = con.createSession();
            MessageProducer publisher = ses.createProducer(myTopic);
            Message msg = ses.createTextMessage();

            msg.setStringProperty("user", user);
            msg.setStringProperty("msgValue", msgValue);
            publisher.send(msg);

            System.out.println("Message '" + msgValue + "' to '" + user + "' has been sent!.");
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }
}
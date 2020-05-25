package jms;

import javax.faces.bean.ApplicationScoped;
import javax.jms.JMSException;
import javax.jms.Message;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@ApplicationScoped
public class NotificationService {
    public static Map<String, List<String>> notifications = new HashMap<String, List<String>>();

    public static void addMessage(Message msg){
        Message message = msg;
        String msgTopic= "";
        String msgValue = "";

        try {
            msgTopic = msg.getStringProperty("msgTopic");
            msgValue = msg.getStringProperty("msgValue");
        } catch (JMSException e) {
            msgTopic = "";
        }

        if ( !notifications.containsKey(msgTopic) ) {
            ArrayList<String> messages = new ArrayList<String>();
            notifications.put(msgTopic, messages);
        }

        notifications.get(msgTopic).add(msgValue);
    }

}
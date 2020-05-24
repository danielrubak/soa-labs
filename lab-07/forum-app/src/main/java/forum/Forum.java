package forum;

import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.jms.JMSException;
import javax.jms.Message;
import java.util.*;

@ApplicationScoped
public class Forum {
    public static List<String> topics = new ArrayList<String>();
    public static List<String> users = new ArrayList<String>();
    public static Map<String, List<String>> notifications = new HashMap<String, List<String>>();

    public static void addMessage(Message msg) throws JMSException {
        Message message = msg;

        if (msg.propertyExists("msgTopic")) {
            String msgTopic = msg.getStringProperty("msgTopic");
            String msgValue = msg.getStringProperty("msgValue");

            if ( topics.contains(msgTopic) ) {
                // there is no messages for topic in notifications map
                if ( !notifications.containsKey(msgTopic) ) {
                    ArrayList<String> messages = new ArrayList<String>();
                    notifications.put(msgTopic, messages);
                }

                notifications.get(msgTopic).add(msgValue);
            }
        } else if (msg.propertyExists("user")) {
            String user = msg.getStringProperty("user");
            String msgValue = msg.getStringProperty("msgValue");

            if ( users.contains(user) ) {
                // there is no messages for user in notifications map
                if ( !notifications.containsKey(user) ) {
                    ArrayList<String> messages = new ArrayList<String>();
                    notifications.put(user, messages);
                }

                notifications.get(user).add(msgValue);
            }
        }
    }
}

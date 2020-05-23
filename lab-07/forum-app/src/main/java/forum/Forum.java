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

    public static void addMessage(Message msg){
        Message message = msg;
        String messageTopic= "";
        String messageText = "";
        try {
            messageTopic = msg.getStringProperty("topic");
            messageText = msg.getStringProperty("text");
        } catch (JMSException e) {
            messageTopic="";
        }
        boolean isInSubscribed = false;
        for (String topic: topics){
            if(messageTopic.equals(topic)){
                isInSubscribed=true;
                break;
            }
        }
        for (String topic: users){
            if(messageTopic.equals(topic)){
                isInSubscribed=true;
                break;
            }
        }
        if(isInSubscribed){
            if (!notifications.containsKey(messageTopic)) {
                ArrayList<String> al = new ArrayList<String>();
                notifications.put(messageTopic, al);
            }
            notifications.get(messageTopic).add(messageText);
        }
    }
}

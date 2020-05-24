package forum;

import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.jms.*;
import java.util.*;

@ManagedBean(name = "UserBean")
@SessionScoped
@MessageDriven(
        activationConfig = {
                @ActivationConfigProperty(
                        propertyName = "destination",
                        propertyValue = "java:/jms/topic/SOA_test"),
                @ActivationConfigProperty(
                        propertyName = "destinationType",
                        propertyValue = "javax.jms.Topic")
        })
public class UserBean implements MessageListener {

    private List<String> subscribedTopics = new ArrayList<String>();
    private Map<String, List<String>> myNotifications = new HashMap<String, List<String>>();
    private String username;
    private String newTopicName;
    private String topicToSubscribe;

    private String targetTopicName;
    private String targetTopicMsg;

    private String directMsgUser;
    private String directMsgValue;

    public String login(String username) {
        if (Forum.users.contains(username)) {
            FacesContext context = FacesContext.getCurrentInstance();
            FacesMessage fm = new FacesMessage( "User already logged in");
            context.addMessage( "login-form:user-name-field", fm);

            return null;
        } else {
            setUsername(username);
            Forum.users.add(this.username);
            System.out.println("Logged as: " + this.username);

            return "/user-page";
        }
    }

    public void addTopic() {
        if (Forum.topics.contains(this.newTopicName)) {
            FacesContext context = FacesContext.getCurrentInstance();
            FacesMessage fm = new FacesMessage( "Topic already exists");
            context.addMessage( "new-topic-form:new-topic-name-field", fm);
        } else {
            Forum.topics.add(this.newTopicName);
            addSubscription(newTopicName);
            setNewTopicName(null);
        }
    }

    public void addSubscription() {
        if (!Forum.topics.contains(topicToSubscribe)) {
            FacesContext context = FacesContext.getCurrentInstance();
            FacesMessage fm = new FacesMessage( "Topic does not exists");
            context.addMessage( "add-topic-subscription:subscribe-topic-name-field", fm);
        } else if (subscribedTopics.contains(this.topicToSubscribe)) {
            FacesContext context = FacesContext.getCurrentInstance();
            FacesMessage fm = new FacesMessage( "Topic already subscribed");
            context.addMessage( "add-topic-subscription:subscribe-topic-name-field", fm);
        } else {
            subscribedTopics.add(topicToSubscribe);
            setTopicToSubscribe(null);
        }
    }

    public void addSubscription(String newTopicName) {
        this.subscribedTopics.add(newTopicName);
    }

    public void onMessage(final Message msg) {
        try {
            Forum.addMessage(msg);
        } catch (JMSException e) {
            e.printStackTrace();
        }
        this.getMessages();
    }

    public void getMessages() {
        for (String topic: subscribedTopics) {
            if(Forum.notifications.containsKey(topic)){
                this.myNotifications.put(topic,Forum.notifications.get(topic));
            }
        }

        if ( Forum.notifications.containsKey(username)) {
            this.myNotifications.put(username,Forum.notifications.get(username));
        }
    }

    public List<String> getSubscribedTopics() {
        return subscribedTopics;
    }

    public void setSubscribedTopics(List<String> subscribedTopics) {
        this.subscribedTopics = subscribedTopics;
    }

    public Map<String, List<String>> getMyNotifications() {
        return myNotifications;
    }

    public void setMyNotifications(Map<String, List<String>> myNotifications) {
        this.myNotifications = myNotifications;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getNewTopicName() {
        return newTopicName;
    }

    public void setNewTopicName(String newTopicName) {
        this.newTopicName = newTopicName;
    }

    public String getTopicToSubscribe() {
        return topicToSubscribe;
    }

    public void setTopicToSubscribe(String topicToSubscribe) {
        this.topicToSubscribe = topicToSubscribe;
    }

    public String getTargetTopicName() {
        return targetTopicName;
    }

    public void setTargetTopicName(String targetTopicName) {
        this.targetTopicName = targetTopicName;
    }

    public String getTargetTopicMsg() {
        return targetTopicMsg;
    }

    public void setTargetTopicMsg(String targetTopicMsg) {
        this.targetTopicMsg = targetTopicMsg;
    }

    public String getDirectMsgUser() {
        return directMsgUser;
    }

    public void setDirectMsgUser(String directMsgUser) {
        this.directMsgUser = directMsgUser;
    }

    public String getDirectMsgValue() {
        return directMsgValue;
    }

    public void setDirectMsgValue(String directMsgValue) {
        this.directMsgValue = directMsgValue;
    }
}

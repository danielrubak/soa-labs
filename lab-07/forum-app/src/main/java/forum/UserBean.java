package forum;

import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.AjaxBehaviorEvent;
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

    public String login(String username) {
        setUsername(username);
        System.out.println("Logged as: " + this.username);
        System.out.println("Subscribed topics: " + subscribedTopics);
        Forum.users.add(this.username);

        return "/user-page";
    }

    public void addTopic() {
        List<String> topics = Forum.topics;
        System.out.println("All topics: " + topics);

        if (topics.contains(this.newTopicName)) {
            System.out.println("Topic exists");
            FacesContext context = FacesContext.getCurrentInstance();
            FacesMessage fm = new FacesMessage( "Topic already exists");
            context.addMessage( "new-topic-form:new-topic-name-field", fm);
        } else {
            System.out.println("Topic '" + this.newTopicName + "' has been added");
            Forum.topics.add(this.newTopicName);
            addSubscription(newTopicName);
            setNewTopicName(null);
        }
    }

    public void addSubscription() {
        System.out.println("All subscriptions: " + subscribedTopics);

        if (!Forum.topics.contains(topicToSubscribe)) {
            FacesContext context = FacesContext.getCurrentInstance();
            FacesMessage fm = new FacesMessage( "Topic does not exists");
            context.addMessage( "add-topic-subscription:subscribe-topic-name-field", fm);
        } else if (subscribedTopics.contains(this.topicToSubscribe)) {
            FacesContext context = FacesContext.getCurrentInstance();
            FacesMessage fm = new FacesMessage( "Topic already subscribed");
            context.addMessage( "add-topic-subscription:subscribe-topic-name-field", fm);
        } else {
            System.out.println("Topic '" + this.newTopicName + "' has been subscribed!");
            subscribedTopics.add(topicToSubscribe);
            setTopicToSubscribe(null);
        }
    }

    public void addSubscription(String newTopicName) {
        this.subscribedTopics.add(newTopicName);
    }

    public void onMessage(final Message msg) {
        Forum.addMessage(msg);
        this.loadMessages();
    }

    public void loadMessages(){
        System.out.println("Subscribed Topics: " + subscribedTopics);
        for (String topic: subscribedTopics) {
            if(Forum.notifications.containsKey(topic)){
                System.out.println("Topic: " + topic);
                System.out.println("Notification: " + Forum.notifications.get(topic));
                this.myNotifications.put(topic,Forum.notifications.get(topic));
            }
        }
    }

    public List<String> getMyNotificationsList(){
        List<String> notifications = new ArrayList<String>();

        for (String topic: subscribedTopics){
            if ( !myNotifications.containsKey(topic) ) continue;

            for (String element:myNotifications.get(topic)) {
                notifications.add(element);
            }
        }

        return notifications;
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
}

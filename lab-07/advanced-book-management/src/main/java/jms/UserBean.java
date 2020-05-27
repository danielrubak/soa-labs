package jms;

import model.Reader;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@ManagedBean(name = "UserBean")
@SessionScoped
public class UserBean {
    private Integer userId;
    private Map<Integer, List<String>> subscriptions = new HashMap();
    private Map<String, String> notifications = new HashMap<String, String>();

    public List<Reader> getReadersMap(List<Reader> readers) {
        // reset settings for every user
        for (Reader reader: readers) {
            this.setUserSettings(reader);
        }

        return readers;
    }

    public void setUserSettings(Reader reader){
        int user = reader.getId();

        if( !this.subscriptions.containsKey(user) ) {
            this.subscriptions.put(user, new ArrayList<String>());
        }

        if( this.subscriptions.get(user).isEmpty() && reader.getNotifyMe() ) {
            this.setDefaultSubscriptions(user);
        }

        notifications.clear();
    }

    public void setDefaultSubscriptions(int userId){
        subscriptions.get(userId).add("author");
        subscriptions.get(userId).add("book");
        subscriptions.get(userId).add("bookCategory");
        subscriptions.get(userId).add("borrowing");
        subscriptions.get(userId).add("reader");
    }

    public void loadNotifications(){
        notifications.clear();

        for (String topic : subscriptions.get(userId)) {
            if (NotificationService.notifications.containsKey(topic)) {
                for (String msgValue : NotificationService.notifications.get(topic)) {
                    notifications.put(topic, msgValue);
                }
            }
        }
    }

    public List<String> getUserSubscriptions() {
        return subscriptions.get(this.userId);
    }

    public Boolean hasSubscription(int bookId) {
        if ( this.getUserId() == null) return false;

        if ( this.subscriptions.get(this.getUserId()).contains(String.valueOf(bookId)))
            return true;
        return false;
    }

    public void addSubscription (int bookId) {
        if ( this.getUserId() == null )
            return;

        if( this.subscriptions.get(getUserId()) == null ) {
            this.subscriptions.put(getUserId(), new ArrayList<String>());
        }

        if( !this.subscriptions.get(getUserId()).contains(String.valueOf(bookId)) ) {
            this.subscriptions.get(getUserId()).add(String.valueOf(bookId));
        }
    }

    public void removeSubscription (int bookId) {
        if ( this.getUserId() == null )
            return;

        try{
            this.subscriptions.get(getUserId()).remove(String.valueOf(bookId));
        } catch (Exception e) {}
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Map<Integer, List<String>> getSubscriptions() {
        return subscriptions;
    }

    public void setSubscriptions(Map<Integer, List<String>> subscriptions) {
        this.subscriptions = subscriptions;
    }

    public Map<String, String> getNotifications() {
        return notifications;
    }

    public void setNotifications(Map<String, String> notifications) {
        this.notifications = notifications;
    }
}
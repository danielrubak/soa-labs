package bean;

import model.User;

import javax.enterprise.context.SessionScoped;
import javax.faces.event.AjaxBehaviorEvent;
import javax.inject.Named;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Named("UserBean")
@SessionScoped
public class UserBean implements Serializable {
    javax.ws.rs.client.Client userClient = ClientBuilder.newClient();
    static String BASE_URI = "http://localhost:8080/rest-app/api/users";

    private String name;
    private Integer age;
    private String avatar;

    private List<User> users = new ArrayList<>();
    private Integer selectedUserId;

    public List<User> getUsers() {
        Response response = userClient.target(BASE_URI).request().accept(MediaType.APPLICATION_JSON_TYPE).get();

        if (response.getStatus()!= 200){
            throw new RuntimeException("Error");
        } else {
            users = response.readEntity(new GenericType<List<User>>(){});
            return users;
        }
    }

    public String addUser() {
        User user = new User(this.name, this.age, this.avatar);

        Entity entity = Entity.entity(user, MediaType.APPLICATION_JSON);
        userClient.target(BASE_URI).request().post(entity).close();

        setEmptyValues();
        return "/users/users";
    }

    public String updateUser() {
        Response response = userClient.target(BASE_URI+"/"+selectedUserId).request()
                .accept(MediaType.APPLICATION_JSON_TYPE).get();

        if (response.getStatus()!= 200){
            throw new RuntimeException("Error");
        } else {
            User user = response.readEntity(new GenericType<User>(){});
            user.setName(name);
            user.setAge(age);
            user.setAvatar(avatar);

            Entity entity = Entity.entity(user, MediaType.APPLICATION_JSON_TYPE);
            userClient.target(BASE_URI+"/"+selectedUserId).request().put(entity).close();

            setEmptyValues();
            return "/users/users";
        }
    }

    public String deleteUser() {
        userClient.target(BASE_URI+"/"+selectedUserId).request().delete().close();

        setEmptyValues();
        return "/users/users";
    }

    public Map<String, Integer> getUserMap() {
        Map<String, Integer> userMap = new LinkedHashMap<>();

        for (User user: users) {
            userMap.put(user.getName(), user.getId());
        }
        return userMap;
    }

    public void onUserSelection(AjaxBehaviorEvent ajaxBehaviorEvent) {
        if (selectedUserId != null) {
            for (User user: users) {
                if (selectedUserId == user.getId()) {
                    name = user.getName();
                    age = user.getAge();
                    avatar = user.getAvatar();
                }
            }
        } else {
            setEmptyValues();
        }
    }

    public String onBackButton () {
        setEmptyValues();

        return "/users/users";
    }

    public void setEmptyValues () {
        name = null;
        age = null;
        avatar = null;
        selectedUserId = null;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public Integer getSelectedUserId() {
        return selectedUserId;
    }

    public void setSelectedUserId(Integer selectedUserId) {
        this.selectedUserId = selectedUserId;
    }
}

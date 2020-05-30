package bean;

import model.User;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.Serializable;
import java.util.List;

@Named("UserBean")
@SessionScoped
public class UserBean implements Serializable {
    javax.ws.rs.client.Client userClient = ClientBuilder.newClient();

    static String REST_TARGET = "http://localhost:8080/rest-app/api/users";

    public List<User> getUsers() {
        Response response = userClient.target(REST_TARGET).request().accept(MediaType.APPLICATION_JSON_TYPE).get();
        if (response.getStatus()!= 200){
            throw new RuntimeException("Error");
        }

        return response.readEntity(new GenericType<List<User>>(){});
    }
}

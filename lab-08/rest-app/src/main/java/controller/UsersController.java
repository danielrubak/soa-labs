package controller;

import model.User;
import repository.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("users")
public class UsersController {
    @Inject
    private UserRepository userRepository;

    private ObjectMapper objectMapper = new ObjectMapper();

//    @GET
//    @Produces(MediaType.APPLICATION_JSON)
//    public String index() throws JsonProcessingException {
//        return objectMapper.writeValueAsString(usersRepository.get());
//    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getUsers() {
        List<User> users = userRepository.getAllUsers();
        return Response.status(200).entity(users).build();
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response get(@PathParam("id") int id) {
        User user = userRepository.getUserById(id);

        if (user == null) {
            return Response.status(404).build();
        }

        return Response.ok(user).build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public User store(User user) {
        userRepository.addUser(user);

        return user;
    }

    @PATCH
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response update(@PathParam("id") int id, User user_) {
        User user = userRepository.getUserById(id);

        if (user == null) {
            return Response.status(422).build();
        }

        user.setName((user_.getName() != null) ? user_.getName() : user.getName());
        user.setAge((user_.getAge() != 0) ? user_.getAge() : user.getAge());
        user.setAvatar((user_.getAvatar() != null) ? user_.getAvatar() : user.getAvatar());

        userRepository.updateUser(user);

        return Response.ok(user).build();
    }

    @DELETE
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response destroy(@PathParam("id") int id) {
        boolean successful = userRepository.deleteUser(id);

        if (!successful) {
            return Response.status(422).build();
        }

        return Response.noContent().build();
    }
}

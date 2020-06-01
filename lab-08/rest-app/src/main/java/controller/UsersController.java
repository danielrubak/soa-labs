package controller;

import model.Movie;
import model.User;
import service.MovieService;
import service.UserService;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("users")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class UsersController {
    @Inject
    UserService userService;

    @Inject
    MovieService movieService;

    @GET
    public Response getUsers() {
        List<User> users = userService.getAllUsers();
        return Response.status(200).entity(users).build();
    }

    @GET
    @Path("/{id}")
    public Response get(@PathParam("id") int id) {
        User user = userService.getUserById(id);

        if ( user == null ) {
            return Response.status(404).build();
        } else {
            return Response.ok(user).build();
        }
    }

    @POST
    public Response add(User user) {
        try {
            userService.addUser(user);
            return Response.status(201).build();
        } catch (RuntimeException e) {
            return Response.status(500).build();
        }
    }

    @PUT
    @Path("/{id}")
    public Response update(@PathParam("id") int id, User user) {
        try {
            userService.updateUser(id, user);

            return Response.status(200).build();
        } catch (RuntimeException e) {
            return Response.status(500).build();
        }
    }

    @DELETE
    @Path("/{id}")
    public Response delete(@PathParam("id") int id) {
        try {
            userService.deleteUser(id);
            return Response.status(200).build();
        } catch (Exception e) {
            return Response.status(405).build();
        }
    }

    @POST
    @Path("/{user_id}/movies")
    public Response addUserMovie(@PathParam("user_id") int userId, Movie movie){
        User user = userService.getUserById(userId);
        user.getFavouriteMovies().add(movie);
        movie.getUsers().add(user);
        userService.updateUser(userId, user);
        movieService.updateMovie(movie.getId(), movie);

        return Response.status(201).build();
    }

    @DELETE
    @Path("/{user_id}/movies/{movie_id}")
    public Response removeUserMovie(
            @PathParam("user_id") int userId,
            @PathParam("movie_id") int movieId
    ) {
        User user = userService.getUserById(userId);
        Movie movie = movieService.getMovieById(movieId);

        user.getFavouriteMovies().remove(movie);
        movie.getUsers().remove(user);

        userService.updateUser(userId, user);
        movieService.updateMovie(movie.getId(), movie);

        return Response.status(201).build();
    }
}

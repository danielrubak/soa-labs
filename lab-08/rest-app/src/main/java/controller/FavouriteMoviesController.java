package controller;

import model.Movie;
import model.User;
import repository.MovieRepository;
import repository.UserRepository;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Path("users")
public class FavouriteMoviesController {
    @Inject
    private UserRepository userRepository;

    @Inject
    private MovieRepository moviesRepository;

    @POST
    @Path("/{user_id}/favourites/movies/{movie_id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response favourite(
        @PathParam("user_id") int userId,
        @PathParam("movie_id") int movieId
    ) {
        User user = userRepository.getUserById(userId);
        Movie movie = moviesRepository.getMovieById(movieId);

        if (user == null || movie == null) {
            return Response.status(404).build();
        }

        List<Movie> favouriteMovies = new ArrayList<>(user.getFavouriteMovies());
        favouriteMovies.add(movie);

        user.setFavouriteMovies(favouriteMovies);

//        userRepository.updateUser(user);

        return Response.status(201).entity(user).build();
    }

    @DELETE
    @Path("/{user_id}/favourites/movies/{movie_id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response unfavourite(
        @PathParam("user_id") int userId,
        @PathParam("movie_id") int movieId
    ) {
        User user = userRepository.getUserById(userId);

        if (user == null) {
            return Response.status(404).build();
        }

        user.setFavouriteMovies(
            new ArrayList<>(user.getFavouriteMovies())
                .stream()
                .filter(movie -> movie.getId() != movieId)
                .collect(Collectors.toList())
        );

//        userRepository.updateUser(user);

        return Response.status(201).entity(user).build();
    }
}

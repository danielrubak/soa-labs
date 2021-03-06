package controller;

import model.Movie;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import service.MovieService;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Path("movies")
public class MoviesController {
    @Inject
    MovieService movieService;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getMovies(@QueryParam("title") String title) {
        List<Movie> movies = new ArrayList<>();

        if ( title != null && !title.isEmpty() ) {
            movies = movieService.getMovieByTitle(title);
        } else {
            movies = movieService.getAllMovies();
        }

        return Response.status(200).entity(movies).build();
    }

    @GET
    @Path("/uri-list")
    @Produces("text/uri-list")
    public String getUris() throws JsonProcessingException {
        List<Movie> movies = movieService.getAllMovies();

        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(
                movies.stream().
                        map(Movie::getUri).
                        collect(Collectors.toList())
        );
    }

    @GET
    @Path("/text-plain")
    @Produces(MediaType.TEXT_PLAIN)
    public String indexText(@QueryParam("title") String title) {
        return movieService.getMovieByTitle(title)
            .stream()
            .map(movie -> "\"" + movie.getTitle() + "\"\n" + movie.getUri() + "\n")
            .reduce("", (String acc, String movie) -> acc + movie + "\n");
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response get(@PathParam("id") int id) {
        Movie movie = movieService.getMovieById(id);

        if (movie == null) {
            return Response.status(404).build();
        }

        return Response.ok(movie).build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Movie store(Movie movie) {
        movieService.addMovie(movie);

        return movie;
    }

    @PATCH
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response partUpdate(@PathParam("id") int id, Movie movie_) {
        Movie movie = movieService.getMovieById(id);

        if (movie == null) {
            return Response.status(422).build();
        }

        movie.setTitle((movie_.getTitle() != null) ? movie_.getTitle() : movie.getTitle());
        movie.setUri((movie_.getUri() != null) ? movie_.getUri() : movie.getUri());

        movieService.updateMovie(id, movie);

        return Response.ok(movie).build();
    }

    @PUT
    @Path("/{id}")
    public Response update(@PathParam("id") int id, Movie movie) {
        try {
            movieService.updateMovie(id, movie);

            return Response.status(200).build();
        } catch (RuntimeException e) {
            return Response.status(500).build();
        }
    }

    @DELETE
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response delete(@PathParam("id") int id) {
        try {
            movieService.deleteMovie(id);
            return Response.status(200).build();
        } catch (Exception e) {
            return Response.status(405).build();
        }
    }
}

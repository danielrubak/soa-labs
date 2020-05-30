package controller;

import model.Movie;
import repository.MovieRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.stream.Collectors;

@Path("movies")
public class MoviesController {
    @Inject
    private MovieRepository moviesRepository;

    private ObjectMapper objectMapper = new ObjectMapper();

    private List<Movie> getMovies(String title) {
        return (title != null && !title.isEmpty())
            ? moviesRepository.getMovieByTitle(title)
            : moviesRepository.getAllMovies();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String indexJson(@QueryParam("title") String title) throws JsonProcessingException {
        return objectMapper.writeValueAsString(getMovies(title));
    }

    @GET
    @Produces("text/uri-list")
    public String indexUris(@QueryParam("title") String title) throws JsonProcessingException {
        return objectMapper.writeValueAsString(getMovies(title).stream().map(Movie::getUri).collect(Collectors.toList()));
    }

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String indexText(@QueryParam("title") String title) {
        return getMovies(title)
            .stream()
            .map(movie -> "\"" + movie.getTitle() + "\"\n" + movie.getUri() + "\n")
            .reduce("", (String acc, String movie) -> acc + movie + "\n");
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response get(@PathParam("id") int id) {
        Movie movie = moviesRepository.getMovieById(id);

        if (movie == null) {
            return Response.status(404).build();
        }

        return Response.ok(movie).build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Movie store(Movie movie) {
        moviesRepository.addMovie(movie);

        return movie;
    }

    @PATCH
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response update(@PathParam("id") int id, Movie movie_) {
        Movie movie = moviesRepository.getMovieById(id);

        if (movie == null) {
            return Response.status(422).build();
        }

        movie.setTitle((movie_.getTitle() != null) ? movie_.getTitle() : movie.getTitle());
        movie.setUri((movie_.getUri() != null) ? movie_.getUri() : movie.getUri());

        moviesRepository.updateMovie(movie);

        return Response.ok(movie).build();
    }

    @DELETE
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response destroy(@PathParam("id") int id) {
        boolean successful = moviesRepository.deleteMovie(id);

        if (!successful) {
            return Response.status(422).build();
        }

        return Response.noContent().build();
    }
}

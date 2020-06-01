package bean;

import model.Movie;

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

@Named("MovieBean")
@SessionScoped
public class MovieBean implements Serializable {
    javax.ws.rs.client.Client movieClient = ClientBuilder.newClient();
    static String BASE_URI = "http://localhost:8080/rest-app/api/movies";

    private String title;
    private String uri;

    private List<Movie> movies = new ArrayList<>();
    private Integer selectedMovieId;

    public List<Movie> getMovies() {
        Response response = movieClient.target(BASE_URI).request().accept(MediaType.APPLICATION_JSON_TYPE).get();

        if (response.getStatus()!= 200){
            throw new RuntimeException("Error");
        } else {
            movies = response.readEntity(new GenericType<List<Movie>>(){});
            return movies;
        }
    }

    public String addMovie() {
        Movie movie = new Movie(title);
        movie.setUri(BASE_URI + "/");

        Entity entity = Entity.entity(movie, MediaType.APPLICATION_JSON);
        movieClient.target(BASE_URI).request().post(entity).close();

        setEmptyValues();
        return "/movies/movies";
    }

    public String updateMovie() {
        Response response = movieClient.target(BASE_URI+"/"+selectedMovieId).request()
                .accept(MediaType.APPLICATION_JSON_TYPE).get();

        if (response.getStatus()!= 200){
            throw new RuntimeException("Error");
        } else {
            Movie movie = response.readEntity(new GenericType<Movie>(){});
            movie.setTitle(title);

            Entity entity = Entity.entity(movie, MediaType.APPLICATION_JSON_TYPE);
            movieClient.target(BASE_URI+"/"+selectedMovieId).request().put(entity).close();

            setEmptyValues();
            return "/movies/movies";
        }
    }

    public String deleteMovie() {
        movieClient.target(BASE_URI+"/"+selectedMovieId).request().delete().close();
        setEmptyValues();

        return "/movies/movies";
    }

    public Map<String, Integer> getMovieMap() {
        Map<String, Integer> movieMap = new LinkedHashMap<>();

        for ( Movie movie: movies) {
            movieMap.put(movie.getTitle(), movie.getId());
        }

        return movieMap;
    }

    public void onMovieSelection(AjaxBehaviorEvent ajaxBehaviorEvent) {
        if (selectedMovieId != null) {
            for ( Movie movie: movies) {
                if (selectedMovieId == movie.getId()) {
                    title = movie.getTitle();
                }
            }
        } else {
            setEmptyValues();
        }
    }

    public String onBackButton () {
        setEmptyValues();

        return "/movies/movies";
    }

    public void setEmptyValues () {
        title = null;
        uri = null;
        selectedMovieId = null;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public Integer getSelectedMovieId() {
        return selectedMovieId;
    }

    public void setSelectedMovieId(Integer selectedMovieId) {
        this.selectedMovieId = selectedMovieId;
    }
}

package bean;

import model.Movie;
import model.User;
import service.MovieService;
import service.UserService;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.Response;
import java.io.InputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Named("InitBean")
@SessionScoped
public class InitBean implements Serializable {
    @Inject
    MovieService movieService;

    @Inject
    UserService userService;

    public void setupDatabase() {
        String moviesUri = "http://localhost:8080/rest-app/api/movies/";

        Movie movie1 = new Movie("Movie 1", moviesUri);
        Movie movie2 = new Movie("Movie 2", moviesUri);
        Movie movie3 = new Movie("Movie 3", moviesUri);
        Movie movie4 = new Movie("Movie 4", moviesUri);
        Movie movie5 = new Movie("Movie 5", moviesUri);

        movieService.addMovie(movie1);
        movieService.addMovie(movie2);
        movieService.addMovie(movie3);
        movieService.addMovie(movie4);
        movieService.addMovie(movie5);

        List<Movie> user1Movies = new ArrayList<Movie>() {{
            add(movie1);
            add(movie2);
            add(movie3);
        }};
        User user1 = new User("User 1", 20, "User01", user1Movies);

        List<Movie> user2Movies = new ArrayList<Movie>() {{
            add(movie3);
            add(movie4);
            add(movie5);
        }};
        User user2 = new User("User 2", 21, "User02", user2Movies);


        List<Movie> user3Movies = new ArrayList<Movie>() {{
            add(movie5);
        }};
        User user3 = new User("User 3", 22, "User03", user3Movies);

        User user4 = new User("User 4", 23, "User04");
        User user5 = new User("User 5", 24, "User05");

        userService.addUser(user1);
        userService.addUser(user2);
        userService.addUser(user3);
        userService.addUser(user4);
        userService.addUser(user5);
    }
}

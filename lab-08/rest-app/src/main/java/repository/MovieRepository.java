package repository;

import model.Movie;

import java.util.List;

public interface MovieRepository {
    public List<Movie> getAllMovies();
    public Movie getMovieById(int id);
    public List<Movie> getMovieByTitle(String title);
    public void addMovie(Movie movie);
    public void updateMovie(Movie movie);
    public boolean deleteMovie(int id);
}

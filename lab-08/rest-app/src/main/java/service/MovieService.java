package service;

import model.Movie;
import repository.MovieRepository;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.List;

@ApplicationScoped
public class MovieService implements MovieRepository {
    @Inject
    ApplicationEntityManager em;

    public List<Movie> getAllMovies() {
        return em.get().createQuery("SELECT m FROM Movie m").getResultList();
    }

    @Override
    public Movie getMovieById(int id) {
        return em.get().find(Movie.class, id);
    }

    @Override
    public List<Movie> getMovieByTitle(String title) {
        return em.get()
                .createQuery("SELECT m FROM Movie m WHERE m.title = :title")
                .setParameter("title", title)
                .getResultList();
    }

    @Override
    public void addMovie(Movie movie) {
        try {
            em.get().getTransaction().begin();
            em.get().persist(movie);
            movie.setUri(movie.getUri() + movie.getId());
            em.get().getTransaction().commit();

        } catch (Exception e){
            System.err.println("An error occurred during adding movie: " + e);
        }
    }

    @Override
    public void updateMovie(int id, Movie movie) {
        try {
            em.get().getTransaction().begin();

            Movie updateObject = em.get().find(Movie.class, id);

            if ( movie.getTitle() != null ) {
                updateObject.setTitle(movie.getTitle());
            }

            em.get().persist(updateObject);
            em.get().getTransaction().commit();
        } catch (Exception e) {
            System.out.println("An error occurred during updating a movie object. Id = " + id + ":\n" +e);
        }
    }

    @Override
    public void deleteMovie(int id) {
        try {
            em.get().getTransaction().begin();

            Movie removeObject = em.get().find(Movie.class, id);
            em.get().remove(removeObject);

            em.get().getTransaction().commit();

        } catch (Exception e) {
            System.out.println("An error occurred during removing a movie object. Id = " + id + ":\n" +e);
        }
    }
}

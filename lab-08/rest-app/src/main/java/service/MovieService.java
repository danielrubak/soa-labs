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
        updateMovie(movie);
    }

    @Override
    public void updateMovie(Movie movie) {
        em.get().getTransaction().begin();
        em.get().persist(movie);
        em.get().getTransaction().commit();
        em.get().clear();
    }

    @Override
    public boolean deleteMovie(int id) {
        em.get().getTransaction().begin();
        int status = em.get().createQuery("DELETE FROM Movie u where u.id = :id").setParameter("id", id).executeUpdate();
        em.get().getTransaction().commit();
        em.get().clear();

        return status == 1;
    }
}

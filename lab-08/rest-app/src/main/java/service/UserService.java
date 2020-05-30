package service;

import model.User;
import repository.UserRepository;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.List;

@ApplicationScoped
public class UserService implements UserRepository {
    @Inject
    ApplicationEntityManager em;

    @Override
    public List<User> getAllUsers() {
        return em.get().createQuery("SELECT u FROM User u LEFT JOIN u.favouriteMovies fm").getResultList();
    }

    @Override
    public User getUserById(int id) {
        return em.get().find(User.class, id);
    }

    @Override
    public void addUser(User user) {
        updateUser(user);
    }

    @Override
    public void updateUser(User user) {
        em.get().getTransaction().begin();
        em.get().persist(user);
        em.get().getTransaction().commit();
        em.get().clear();
    }

    @Override
    public boolean deleteUser(int id) {
        em.get().getTransaction().begin();
        int status = em.get().createQuery("DELETE FROM User u where u.id = :id").setParameter("id", id).executeUpdate();
        em.get().getTransaction().commit();
        em.get().clear();

        return status == 1;
    }
}

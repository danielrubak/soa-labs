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
        try {
            em.get().getTransaction().begin();
            em.get().persist(user);
            em.get().getTransaction().commit();
        } catch (Exception e){
            System.err.println("An error occurred during adding user: " + e);
        }
    }

    @Override
    public void updateUser(int id, User user) {
        try {
            em.get().getTransaction().begin();

            User updateObject = em.get().find(User.class, id);

            if ( user.getName() != null ) {
                updateObject.setName(user.getName());
            }
            if ( user.getAge() != updateObject.getAge() ) {
                updateObject.setAge(user.getAge());
            }
            if ( user.getAvatar() != null ) {
                updateObject.setAvatar(user.getAvatar());
            }

            em.get().persist(updateObject);
            em.get().getTransaction().commit();
        } catch (Exception e) {
            System.out.println("An error occurred during updating a user object. Id = " + id + ":\n" +e);
        }
    }

    @Override
    public void deleteUser(int id) {
        try {
            em.get().getTransaction().begin();

            User removeObject = em.get().find(User.class, id);
            em.get().remove(removeObject);

            em.get().getTransaction().commit();

        } catch (Exception e) {
            System.out.println("An error occurred during removing a user object. Id = " + id + ":\n" +e);
        }
    }
}

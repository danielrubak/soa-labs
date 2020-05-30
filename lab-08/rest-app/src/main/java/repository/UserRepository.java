package repository;

import model.User;

import java.util.List;

public interface UserRepository {
    public List<User> getAllUsers();
    public User getUserById (int id);
    public void addUser(User user);
    public void updateUser(User user);
    public boolean deleteUser(int id);
}

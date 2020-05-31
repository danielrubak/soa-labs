package repository;

import model.User;

import java.util.List;

public interface UserRepository {
    public List<User> getAllUsers();
    public User getUserById (int id);
    public void addUser(User user);
    public void updateUser(int id, User user);
    public void deleteUser(int id);
}

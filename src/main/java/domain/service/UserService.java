package domain.service;

import domain.model.User;

import java.util.List;

public interface UserService {
    User get(int userid);

    List<User> getAll();

    void add(User user);

    Boolean equals(User user);

    void update(User user);

    void delete(int userid);

    int getNumberOfUsers();

    User getUserByEmail(String email);

}

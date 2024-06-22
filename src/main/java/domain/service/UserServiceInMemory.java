package domain.service;

import domain.model.Team;
import domain.model.Role;
import domain.model.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserServiceInMemory implements UserService {
    private final Map<Integer, User> users = new HashMap<Integer, User>();
    private int userid = 1;    // als je later werkt met externe databank, wordt dit userid automatisch gegenereerd

    public UserServiceInMemory() {
        User director = new User("director@ucll.be", "t", "Ad", "Director", Team.ALPHA);
        director.setRole(Role.DIRECTOR);
        add(director);
    }

    @Override
    public User get(int userid) {
        if (users.get(userid) == null) {
            throw new DbException("No user with id " + userid);
        }
        return users.get(userid);
    }

    @Override
    public List<User> getAll() {
        return new ArrayList<User>(users.values());
    }

    @Override
    public void add(User user) {
        if (user == null) {
            throw new DbException("No user given");
        }
        if (users.containsKey(user.getUserid())) {
            throw new DbException("User already exists");
        }
        if (equals(user)) {
            throw new DbException("Email isn't unique");
        }
        user.setUserid(userid);   // user toevoegen geeft altijd nieuw userid
        users.put(user.getUserid(), user);
        userid++;
    }

    @Override
    public Boolean equals(User user) {
        if (user == null) {
            throw new DbException("No user given");
        }
        for (User u : users.values()) {
            if (u.getEmail().equals(user.getEmail())) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void update(User user) {
        if (user == null) {
            throw new DbException("No user given");
        }
        if (!users.containsKey(user.getUserid())) {
            throw new DbException("No user found");
        }
        users.put(user.getUserid(), user); // user updaten: userid blijft behouden
    }

    @Override
    public void delete(int userid) {
        users.remove(userid);   // userid gaat verloren, maar wordt niet ingenomen door eventuele nieuwe user
    }

    @Override
    public int getNumberOfUsers() {
        return users.size();
    }

    @Override
    public User getUserByEmail(String email) {
        if (email == null) {
            throw new DbException("No email given");
        }
        for (User u : users.values()) {
            if (u.getEmail().equals(email)) {
                return u;
            }
        }
        throw new DbException("No user with email " + email);
    }
}

package domain.service;

import domain.model.Role;
import domain.model.Team;
import domain.model.User;
import util.DbConnectionService;

import java.sql.*;
import java.util.*;

public class UserServiceSQL implements UserService {

    private final Connection connection;
    private final String schema;

    public UserServiceSQL() {
        this.connection = DbConnectionService.getDbConnection();
        this.schema = DbConnectionService.getSchema();
    }


    @Override
    public User get(int userid) {
        for (User u : getAll()) {
            if (u.getUserid() == userid) {
                return u;
            }
        }
        throw new DbException("No user with id " + userid);
    }

    @Override
    public List<User> getAll() {
        ArrayList<User> users = new ArrayList<>();
        String sql = String.format("SELECT * from %s.users order by userid", schema);
        try {
            PreparedStatement statement = getConnection().prepareStatement(sql);
            ResultSet result = statement.executeQuery();
            while (result.next()) {
                int id = result.getInt("userid");
                String firstname = result.getString("firstname");
                String name = result.getString("name");
                String email = result.getString("email");
                String teamStr = result.getString("team");
                Team team = Team.valueOf(teamStr.toUpperCase());
                String roleStr = result.getString("role");
                Role role = Role.valueOf(roleStr.toUpperCase());
                String password = result.getString("password");
                User user = new User(id, email, password, firstname, name, team);
                user.setRole(role);
                users.add(user);
            }
        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        }
        return users;
    }

    @Override
    public void add(User user) {
        if (user == null) {
            throw new DbException("No user given");
        }
        for (User u : getAll()) {
            if (user.getUserid() == u.getUserid()) {
                throw new DbException("User already exists");
            }
        }
        if (equals(user)) {
            throw new DbException("Email isn't unique");
        }
        try {
            String sql = String.format("insert into %s.users (name, firstname, email, team, role, password) values (?,?,?,?,?,?)", schema);
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setString(1, user.getLastName());
            preparedStatement.setString(2, user.getFirstName());
            preparedStatement.setString(3, user.getEmail());
            String team = user.getTeam().toString();
            String role = user.getRole().toString();
            preparedStatement.setString(4, team);
            preparedStatement.setString(5, role);
            preparedStatement.setString(6, user.getPassword());
            preparedStatement.execute();
        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        }
    }

    @Override
    public Boolean equals(User user) {
        if (user == null) {
            throw new DbException("No user given");
        }
        for (User u : getAll()) {
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
        try {
            String sql = String.format("UPDATE %s.users SET name=?, firstname=?, email=?, team=?, role=? WHERE userid=%s", schema, user.getUserid());
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setString(1, user.getLastName());
            preparedStatement.setString(2, user.getFirstName());
            preparedStatement.setString(3, user.getEmail());
            String team = user.getTeam().toString();
            String role = user.getRole().toString();
            preparedStatement.setString(4, team);
            preparedStatement.setString(5, role);
            preparedStatement.execute();
        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        }
    }

    @Override
    public void delete(int userid) {
        try {
            String sql = String.format("delete from %s.users where userid=%s", schema, userid);
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.execute();
        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        }
    }

    @Override
    public int getNumberOfUsers() {
        return getAll().size();
    }

    @Override
    public User getUserByEmail(String email) {
        if (email == null) {
            throw new DbException("No email given");
        }
        for (User u : getAll()) {
            if (u.getEmail().equals(email)) {
                return u;
            }
        }
        throw new DbException("No user with email " + email);
    }

    private Connection getConnection() {
        return this.connection;
    }
}

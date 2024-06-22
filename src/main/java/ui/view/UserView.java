package ui.view;

import domain.model.Role;
import domain.model.Team;
import domain.model.User;
import util.Secret;

import java.sql.*;
import java.util.Properties;

public class UserView {

    public static void main(String[] args) {

        // constants for your project
        // replace "webontwerp" by your own database, e.g. '2TX34'
        String url = "jdbc:postgresql://databanken.ucll.be:62223/2TX32";
        // replace 'web3' by your own schema name, e.g. groep102
        String schema = "groep115";


        // set properties for db connection
        Properties properties = new Properties();

        // set user and password
        try {
            Class.forName("util.Secret"); // check if Secret does exist
            Secret.setPass(properties);
        } catch (ClassNotFoundException e) {
            System.out.println("Class ui.Secret with credentials not found");
        }

        properties.setProperty("ssl", "true");
        properties.setProperty("sslfactory", "org.postgresql.ssl.NonValidatingFactory");
        properties.setProperty("sslmode", "prefer");

        //open the db connection
        try (Connection connection = DriverManager.getConnection(url, properties)) {

            // add user1
            String query = String.format("insert into %s.users (name, firstname, email, team, role, password) values (?,?,?,?,?,?)", schema);
            PreparedStatement preparedStatement = connection.prepareStatement(query);

            User user1 = new User();
            user1.setPassword("t");
            preparedStatement.setString(1, "Sels");
            preparedStatement.setString(2, "Ad");
            preparedStatement.setString(3, "director@ucll.be");
            preparedStatement.setString(4, "Alpha");
            preparedStatement.setString(5, "director");
            preparedStatement.setString(6, user1.getPassword());
            preparedStatement.execute();
            // add user2
            User user2 = new User();
            user2.setPassword("t");
            preparedStatement.setString(1, "Peters");
            preparedStatement.setString(2, "Bart");
            preparedStatement.setString(3, "teamleader@ucll.be");
            preparedStatement.setString(4, "Beta");
            preparedStatement.setString(5, "teamleader");
            preparedStatement.setString(6, user2.getPassword());
            preparedStatement.execute();
            // add user3
            User user3 = new User();
            user3.setPassword("t");
            preparedStatement.setString(1, "Janssens");
            preparedStatement.setString(2, "Max");
            preparedStatement.setString(3, "employee@ucll.be");
            preparedStatement.setString(4, "Beta");
            preparedStatement.setString(5, "employee");
            preparedStatement.setString(6, user3.getPassword());
            preparedStatement.execute();

            //get all users
            query = String.format("SELECT * from %s.users order by name;", schema);
            PreparedStatement statementInsert = connection.prepareStatement(query);
            ResultSet resultSet = statementInsert.executeQuery();
            while (resultSet.next()) {
                int id = resultSet.getInt("userid");
                String firstname = resultSet.getString("firstname");
                String name = resultSet.getString("name");
                String email = resultSet.getString("email");
                String teamStr = resultSet.getString("team");
                Team team = Team.valueOf(teamStr.toUpperCase());
                String roleStr = resultSet.getString("role");
                Role role = Role.valueOf(roleStr.toUpperCase());
                String password = resultSet.getString("password");
                User user = new User(email, password, firstname, name, team);
                user.setRole(role);
                System.out.println(user.toString());
            }


        } catch (SQLException throwables) {
            throwables.printStackTrace();
            System.out.println("Connection no succes");
        }
    }

}
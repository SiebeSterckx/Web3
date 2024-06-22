package ui.view;

import domain.model.Role;
import domain.model.Team;
import domain.model.User;
import util.Secret;

import java.sql.*;
import java.util.Properties;

public class UserViewWithoutPreparedStatement {

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

            // search an animal with name
            // without prepared statement
            System.out.println("Without prepared statement");

            String searchValue = "'' OR 1=1 OR '1'='1'";
            //String searchValue = "'Max'";
            String query = String.format("SELECT * from %s.users where name = %s;", schema, searchValue);
            Statement statementInsert = connection.createStatement();
            ResultSet resultSet = statementInsert.executeQuery(query);
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

            System.out.println("With prepared statement");
            // with prepared statement
            query = String.format("SELECT * from %s.users where name = ?;", schema);
            PreparedStatement preparedStatementInsert = connection.prepareStatement(query);
            preparedStatementInsert.setString(1, searchValue);
            resultSet = preparedStatementInsert.executeQuery();
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
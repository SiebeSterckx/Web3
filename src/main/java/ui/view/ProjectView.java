package ui.view;

import domain.model.Project;
import domain.model.Team;
import util.Secret;

import java.sql.*;
import java.time.LocalDate;
import java.util.Properties;

public class ProjectView {

    public static void main(String[] args) {


        String url = "jdbc:postgresql://databanken.ucll.be:62223/2TX32";

        String schema = "groep115";

        Properties properties = new Properties();

        try {
            Class.forName("util.Secret"); // check if Secret does exist
            Secret.setPass(properties);
        } catch (ClassNotFoundException e) {
            System.out.println("Class ui.Secret with credentials not found");
        }

        properties.setProperty("ssl", "true");
        properties.setProperty("sslfactory", "org.postgresql.ssl.NonValidatingFactory");
        properties.setProperty("sslmode", "prefer");

        try (
        Connection connection = DriverManager.getConnection(url, properties)) {

            // add project1
            String query = String.format("insert into %s.projects (name, team, startdate, enddate) values (?,?, to_timestamp(?, 'yyyy-mm-dd'), to_timestamp (?, 'yyyy-mm-dd'))", schema);
            PreparedStatement preparedStatement = connection.prepareStatement(query);

            preparedStatement.setString(1, "Veranda Janssens");
            preparedStatement.setString(2, "Alpha");
            preparedStatement.setString(3, "2022-10-28");
            preparedStatement.setString(4, "2022-11-19");
            preparedStatement.execute();
            // add project2
            preparedStatement.setString(1, "Keuken De Abdij");
            preparedStatement.setString(2, "Beta");
            preparedStatement.setString(3, "2022-12-15");
            preparedStatement.setString(4, null);
            preparedStatement.execute();


            //get all projects
           query = String.format("SELECT * from %s.projects order by projectid;", schema);
            PreparedStatement statementInsert = connection.prepareStatement(query);
            ResultSet resultSet = statementInsert.executeQuery();
            while (resultSet.next()) {
                int id = resultSet.getInt("projectid");
                String name = resultSet.getString("name");
                String teamStr = resultSet.getString("team");
                Team team = Team.valueOf(teamStr.toUpperCase());
                LocalDate startDate = resultSet.getDate("startdate").toLocalDate();
                if (resultSet.getDate("enddate") != null) {
                    LocalDate endDate = resultSet.getDate("enddate").toLocalDate();
                    Project project = new Project(name, team, startDate, endDate);
                    project.setProjectid(id);
                    System.out.println(project);
                } else {
                    Project project = new Project(name, team, startDate);
                    project.setProjectid(id);
                    System.out.println(project);
                }
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            System.out.println("Connection no succes");
        }
    }
}

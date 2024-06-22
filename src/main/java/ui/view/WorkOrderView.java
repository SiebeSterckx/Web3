package ui.view;

import domain.model.*;
import util.Secret;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Properties;

public class WorkOrderView {

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

            // add workorder1
            String query = String.format("insert into %s.workorders (name, team, date, starttime, endtime, description) " +
                    "values (?, ?, to_date(?, 'yyyy-mm-dd'),to_timestamp(?, 'HH24:MI'),to_timestamp(?, 'HH24:MI'),?)", schema);
            PreparedStatement preparedStatement = connection.prepareStatement(query);

            preparedStatement.setString(1, "Jan");
            preparedStatement.setString(2, "Alpha");
            preparedStatement.setString(3, "2021-10-28");
            preparedStatement.setString(4, "19:30");
            preparedStatement.setString(5, "20:45");
            preparedStatement.setString(6, "Gewichten opbergen");
            preparedStatement.execute();
            // add workorder2
            preparedStatement.setString(1, "Piet");
            preparedStatement.setString(2, "Beta");
            preparedStatement.setString(3, "2021-11-19");
            preparedStatement.setString(4, "18:00");
            preparedStatement.setString(5, "22:45");
            preparedStatement.setString(6, "Apparaten ontsmetten");
            preparedStatement.execute();


            //get all workorders
            query = String.format("SELECT * from %s.workorders order by workorderid;", schema);
            PreparedStatement statementInsert = connection.prepareStatement(query);
            ResultSet resultSet = statementInsert.executeQuery();
            while (resultSet.next()) {
                String name = resultSet.getString("name");
                String TeamStr = resultSet.getString("team");
                Team team = Team.valueOf(TeamStr.toUpperCase());
                LocalDate date = resultSet.getDate("date").toLocalDate();
                LocalTime starttime = resultSet.getTime("starttime").toLocalTime();
                LocalTime endtime = resultSet.getTime("endtime").toLocalTime();
                String description = resultSet.getString("description");

                WorkOrder workorder = new WorkOrder(name, team, date, starttime, endtime, description);
                System.out.println(workorder);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            System.out.println("Connection no succes");
        }
    }
}

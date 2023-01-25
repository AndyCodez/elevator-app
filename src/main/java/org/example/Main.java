package org.example;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Main {
    private static Logger logger = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) {
        createDatabaseSchema();

        Building building = new Building(5);

        Elevator elevator1 = new Elevator(building,'A', 5, 0);
        Runnable r = new ElevatorCaller(elevator1, 5);
        new Thread(r).start();

        Elevator elevator2 = new Elevator(building,'B', 2, 0);
        Runnable r2 = new ElevatorCaller(elevator2, 5);
        new Thread(r2).start();
    }

    private static void createDatabaseSchema() {

        String username = Config.loadDBCredentials().get("username");
        String password = Config.loadDBCredentials().get("password");
        String database_url = Config.loadDBCredentials().get("database_url");
        String database_driver = Config.loadDBCredentials().get("database_driver");

        Connection connection;

        try {
            Class.forName(database_driver);
            connection = DriverManager.getConnection(database_url, username, password);

            if (connection != null) {
                logger.info("Database Connection established!");

                String tableSql = "CREATE TABLE IF NOT EXISTS query_log" +
                        "(log_id SERIAL PRIMARY KEY, log varchar(255), made_from varchar(30), made_at TIMESTAMP)";

                PreparedStatement preparedStatement = connection.prepareStatement(tableSql);
                preparedStatement.executeUpdate();
            } else {
                logger.error("Connection Failed!");
            }

            assert connection != null;
            connection.close();
        } catch (ClassNotFoundException | SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
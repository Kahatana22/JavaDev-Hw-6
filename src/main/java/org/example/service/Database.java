package org.example.service;

import org.flywaydb.core.Flyway;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Database {
    private static final Database INSTANCE = new Database();
    private Connection connection;

    private Database() {
        try {
            String conUrl = "jdbc:h2:./homework";
            connection = DriverManager.getConnection(conUrl);
            flywayMigration();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void flywayMigration() {
        Flyway flyway = Flyway.configure().dataSource("jdbc:h2:./homework", null, null).load();
        flyway.migrate();
    }

    public static Database getInstance() {
        return INSTANCE;
    }

    public Connection getConnection() {
        return connection;
    }
}

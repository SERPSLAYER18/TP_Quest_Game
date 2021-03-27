package server;

import JDBC.DBService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.sql.SQLException;

@SpringBootApplication
public class ServerApp {

    public static void main(String[] args) throws SQLException {

        ServerController.db = new DBService();
        SpringApplication.run(ServerApp.class);
    }
}
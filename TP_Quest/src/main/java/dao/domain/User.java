package dao.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.sql.ResultSet;
import java.sql.SQLException;

@Data
@AllArgsConstructor
public class User {

    private long id;
    private String name;
    private char[] password;
    private int score = 0;

    public User(String name, char[] password) {
        this.name = name;
        this.password = new char[8];
    }

}

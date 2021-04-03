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
    private String password = null;
    private int score = 0;

    public User(String name, String password) {
        this.name = name;
        this.password = password;
    }

    public User(ResultSet set) throws SQLException {
        this(
                set.getLong(1),
                set.getString(2),
                set.getString(3),
                set.getInt(4));

    }
}

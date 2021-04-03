package dao.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.sql.ResultSet;
import java.sql.SQLException;

@Data
@AllArgsConstructor
public class Topic {

    private long id;
    private String name;

    public Topic(String name) {
        this.name = name;
    }

    public Topic(ResultSet set) throws SQLException {
        this(
                set.getLong(1),
                set.getString(2));

    }
}

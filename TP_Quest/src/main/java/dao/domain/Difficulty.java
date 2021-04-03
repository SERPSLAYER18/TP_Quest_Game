package dao.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.sql.ResultSet;
import java.sql.SQLException;


@Data
@AllArgsConstructor
public class Difficulty {

    private long id;
    private int score;

    public Difficulty(int score) {

        this.score = score;
    }

    public Difficulty(ResultSet set) throws SQLException {
        this(set.getLong(1),
                set.getInt(2));
    }

}

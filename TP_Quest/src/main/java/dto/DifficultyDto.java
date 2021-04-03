package dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.sql.ResultSet;
import java.sql.SQLException;


@Data
@AllArgsConstructor
public class DifficultyDto {

    private long id;
    private int score;

    public DifficultyDto(int score) {

        this.score = score;
    }

    public DifficultyDto(ResultSet set) throws SQLException {
        this(set.getLong(1),
                set.getInt(2));
    }

}

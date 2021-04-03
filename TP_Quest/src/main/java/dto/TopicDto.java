package dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.sql.ResultSet;
import java.sql.SQLException;

@Data
@AllArgsConstructor
public class TopicDto {

    private long id;
    private String name;

    public TopicDto(String name) {
        this.name = name;
    }

    public TopicDto(ResultSet set) throws SQLException {
        this(
                set.getLong(1),
                set.getString(2));

    }
}

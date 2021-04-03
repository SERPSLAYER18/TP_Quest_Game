package dao.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.sql.ResultSet;
import java.sql.SQLException;

@Data
@AllArgsConstructor
public class Question {

    private long id;
    private String text;
    private String answer;
    private String topic;
    private String difficulty;

    public Question(String text, String answer, String topic, String difficulty) {

        this.text = text;
        this.answer = answer;
        this.topic = topic;
        this.difficulty = difficulty;
    }

    public Question(ResultSet set) throws SQLException {
        this(set.getLong(1),
                set.getString(2),
                set.getString(3),
                set.getString(4),
                set.getString(5));
    }
}
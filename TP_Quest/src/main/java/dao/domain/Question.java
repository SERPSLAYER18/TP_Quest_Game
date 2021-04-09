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

}

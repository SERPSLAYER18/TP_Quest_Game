package DataSets;

import lombok.*;
import server.DTO.QuestionDTO;

import java.sql.ResultSet;
import java.sql.SQLException;

@Data
@AllArgsConstructor
public class NewQuestionData extends QuestionDTO {

    long id;
    String text;
    String answer;
    String topic;
    String difficulty;

    public NewQuestionData(String text, String answer, String topic, String difficulty){
        this.text = text;
        this.answer = answer;
        this.topic = topic;
        this.difficulty = difficulty;
    }

    public NewQuestionData(ResultSet set) throws SQLException {
        this(   set.getLong(1),
                set.getString(2),
                set.getString(3),
                set.getString(4),
                set.getString(5));
    }

    @Override
    public String toString() {
        return "QuestionData{" +
                "id=" + id +
                ", text='" + text + '\'' +
                ", answer='" + answer + '\'' +
                ", topic='" + topic +'\'' +
                ", difficulty='" + difficulty +'\'' +
                '}';
    }
}

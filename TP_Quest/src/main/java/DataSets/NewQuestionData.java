package DataSets;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.ResultSet;
import java.sql.SQLException;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class NewQuestionData {

    long id;
    String text;
    String answer;

    //long id_topic;
    //long id_difficulty;

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

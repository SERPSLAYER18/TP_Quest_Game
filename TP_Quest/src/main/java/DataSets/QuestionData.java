package DataSets;

import lombok.*;

import java.sql.ResultSet;
import java.sql.SQLException;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class QuestionData {

    long id;
    String text;
    String answer;
    long id_topic;
    long id_difficulty;

    //String topic;
    //String difficulty;

    public  QuestionData(String text,String answer,long id_topic,long id_difficulty){

        this.text = text;
        this.answer = answer;
        this.id_topic = id_topic;
        this.id_difficulty = id_difficulty;
    }

    public QuestionData(ResultSet set) throws SQLException {
        this(   set.getLong(1),
                set.getString(2),
                set.getString(3),
                set.getLong(4),
                set.getLong(5));
    }

    @Override
    public String toString() {
        return "QuestionData{" +
                "id=" + id +
                ", text='" + text + '\'' +
                ", answer='" + answer + '\'' +
                ", id_topic=" + id_topic +
                ", id_difficulty=" + id_difficulty +
                '}';
    }
}

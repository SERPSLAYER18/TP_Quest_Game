package server.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class QuestionDTO {

     long id;
     String text;
     String answer;
     String topic;
     String difficulty;


    public QuestionDTO() {
    }
}

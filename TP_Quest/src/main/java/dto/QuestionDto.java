package dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class QuestionDto {

    private long id;
    private String text;
    private String answer;
    private String topic;
    private String difficulty;

}

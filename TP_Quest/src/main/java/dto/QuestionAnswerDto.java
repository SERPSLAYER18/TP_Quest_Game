package dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class QuestionAnswerDto {

    private long id;
    private String answer;

}

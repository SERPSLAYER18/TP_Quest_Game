package dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
@AllArgsConstructor
public class QuestionDto {

    private long id;
    private final String text;
    private final String answer;
    private final String topic;
    private final String difficulty;

}

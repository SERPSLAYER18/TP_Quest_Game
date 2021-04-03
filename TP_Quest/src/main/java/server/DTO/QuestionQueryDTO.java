package server.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class QuestionQueryDTO {
    String topic;
    String difficulty;
}

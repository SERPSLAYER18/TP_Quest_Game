package mapper;

import dao.domain.Question;
import dto.QuestionDto;
import lombok.experimental.UtilityClass;

@UtilityClass
public class QuestionMapper {
    public static QuestionDto questionToDto(Question question) {
        return new QuestionDto(question.getId(), question.getText(), question.getAnswer(), question.getTopic(), question.getDifficulty());
    }
}

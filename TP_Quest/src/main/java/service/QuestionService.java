package service;

import dao.domain.Question;
import dao.QuestionDao;
import dto.QuestionDto;
import lombok.RequiredArgsConstructor;
import mapper.QuestionMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class QuestionService {

    private final QuestionDao questionDAO;

    public List<QuestionDto> getQuestions(long id_topic, long id_difficulty) {
        List<QuestionDto> questions = new ArrayList<>();
        List<Question> questionsDao = questionDAO.getQuestions(id_topic, id_difficulty);
        for (var user : questionsDao) {
            questions.add(QuestionMapper.questionToDto(user));
        }
        return questions;
    }

    public String getCorrectAnswer(long id) {
        return questionDAO.getCorrectAnswer(id);
    }

    public QuestionDto getRandomQuestion(String topic, String difficulty) {
        return QuestionMapper.questionToDto(questionDAO.getRandomQuestion(topic, difficulty));
    }

}


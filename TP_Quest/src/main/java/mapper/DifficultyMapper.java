package mapper;

import dao.domain.Difficulty;
import dto.DifficultyDto;
import lombok.experimental.UtilityClass;

@UtilityClass
public class DifficultyMapper {
    public static DifficultyDto difficultyToDto(Difficulty difficulty) {
        return new DifficultyDto(difficulty.getId(), difficulty.getScore());
    }
}

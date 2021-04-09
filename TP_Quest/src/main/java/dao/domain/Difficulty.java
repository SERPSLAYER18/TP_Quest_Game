package dao.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Difficulty {

    private long id;
    private int score;

    public Difficulty(int score) {

        this.score = score;
    }

}

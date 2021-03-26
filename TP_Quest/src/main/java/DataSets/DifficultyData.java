package DataSets;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.ResultSet;
import java.sql.SQLException;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DifficultyData {

    long id;
    int score;

    public DifficultyData(int score){
        this.score = score;
    }
    public DifficultyData(ResultSet set) throws SQLException {
        this(   set.getLong(1),
                set.getInt(2));
    }

    @Override
    public String toString() {
        return "DifficultyData{" +
                "id=" + id +
                ", score=" + score +
                '}';
    }
}

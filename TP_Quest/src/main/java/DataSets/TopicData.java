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
public class TopicData {

    long id;
    String name;

    public TopicData(String name){
        this.name = name;
    }

    public TopicData(ResultSet set) throws SQLException {
        this(
                set.getLong(1),
                set.getString(2));

    }

    @Override
    public String toString() {
        return "TopicData{" +
                "id=" + id +
                ", name='" + name +
                '}';
    }
}

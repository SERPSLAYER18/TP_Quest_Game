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
public class UserData {

    long id;
    String name;
    String password = null;
    int score = 0;

    public  UserData(String name, String password){
        this.name = name;
        this.password = password;
    }

    public UserData(ResultSet set) throws SQLException {
        this(
                set.getLong(1),
                set.getString(2),
                set.getString(3),
                set.getInt(4));

    }

    @Override
    public String toString() {
        return "UserData{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", password='" + password + '\'' +
                ", score=" + score +
                '}';
    }
}

package dto;

import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
@AllArgsConstructor
public class UserDto {

    private long id;
    @NotNull
    private final String name;
    @NotNull
    private final String password;
    private int score = 0;

}

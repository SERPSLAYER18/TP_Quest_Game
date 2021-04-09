package mapper;

import dao.domain.User;
import dto.UserDto;
import lombok.experimental.UtilityClass;

@UtilityClass
public class UserMapper {
    public static UserDto userToDto(User user) {
        return new UserDto(user.getId(), user.getName(), user.getPassword().toString(), user.getScore());
    }
}

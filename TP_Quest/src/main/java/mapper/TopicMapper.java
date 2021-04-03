package mapper;

import dao.domain.Topic;
import dto.TopicDto;
import lombok.experimental.UtilityClass;

@UtilityClass

public class TopicMapper {
    public static TopicDto topicToDto(Topic topic) {
        return new TopicDto(topic.getId(), topic.getName());
    }
}

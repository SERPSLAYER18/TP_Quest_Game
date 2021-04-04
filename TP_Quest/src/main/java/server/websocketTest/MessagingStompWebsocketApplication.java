package server.websocketTest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import server.FillData;

@SpringBootApplication
public class MessagingStompWebsocketApplication {

	public static void main(String[] args) {
		FillData.fillDataBase();
		SpringApplication.run(MessagingStompWebsocketApplication.class, args);
	}
}

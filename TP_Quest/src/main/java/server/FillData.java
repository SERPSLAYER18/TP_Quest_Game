package server;

import service.DBService;

public class FillData {

    public static DBService service = DBService.instance;

    public static void main(String[] args) {
        for (int i = 0; i < 10; i++)
            System.out.println(service.getRandomQuestion("Кино", "400"));

    }
}

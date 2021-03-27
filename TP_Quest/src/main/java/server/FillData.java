package server;

import DataSets.NewQuestionData;
import DataSets.UserData;
import JDBC.DBService;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Random;

public class FillData {
    public static void fillUsers() throws SQLException {
        ArrayList<UserData> users = new ArrayList<>();
        users.add(new UserData("Kirill", "12345"));
        users.add(new UserData("Zlata", "11111"));
        users.add(new UserData("Liza", "���������"));
        users.add(new UserData("Grape", "��"));

        DBService service = new DBService();
        service.cleanUpUsers();
        Random random = new Random();
        for (UserData user : users) {
            user.setScore(random.nextInt() % 1000);
            service.saveUser(user);
        }

    }

    public static void fillQuestions() throws SQLException {
        ArrayList<NewQuestionData> questions = new ArrayList<>();
        questions.add(new NewQuestionData("��� ������ ��� ������ ������ ������� ������","�����","����","10"));
        questions.add(new NewQuestionData("\"���������\" ��� �������� ����� ������� ��� \"����� �� �����\"","������ � ���","����","20"));
        questions.add(new NewQuestionData("�� ������ ������� ���� � ������ \"�������� ���\"","����� ������","����","30"));
        questions.add(new NewQuestionData("���� ����� ����� �� ������������� ��������������. ��������, ������ ��� ���������� �������","������ � �������","����","40"));
        questions.add(new NewQuestionData("� ������ 1926 ���� �� ���������� ���������� ������������� ������-������������ ������� �����","���� ����","����","50"));
        questions.add(new NewQuestionData("�������� ���� ���:\n�������� ����� ���������\n� ������� ���������,\n� ��������� ������� ����.\"\n ����� ������ � ���� �����..","�������� ��������","����","30"));
        questions.add(new NewQuestionData("���� �������� ���� � �. �������� ������ ��������� �������� ������ (���������� �������)","����","����","20"));
        questions.add(new NewQuestionData("��� ��������� ������ � ������� ������ \"����\" ������, ���������� � ���������������� ������","������ �� ����� ������","����","10"));
        questions.add(new NewQuestionData("� ���������� ����� � �������� \"�����������, ����������� ��������� ����!\" ���������� ��� �������� ������.","������� ","����","40"));
        questions.add(new NewQuestionData(" ������ �� ��� ������ ������� �������� \"������������\"","������� ������.","����","50"));
        questions.add(new NewQuestionData(" ��� ���������� �������������� ����� � ����� ���-��������� ���� �����","����, ��������� � ���-�-����","����","40"));


        DBService service = new DBService();

        service.clearQuestions();
        for(var q:questions)
        {
            service.saveQuestion(q);
        }

    }

    public static void main(String[] args) throws SQLException {
        //fillUsers();
        //fillQuestions();
    }
}

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
            user.setScore(Math.abs(random.nextInt() % 1000));
            service.saveUser(user);
        }

    }

    public static void fillQuestions() throws SQLException {
        ArrayList<NewQuestionData> questions = new ArrayList<>();
        questions.add(new NewQuestionData("��� ������ ��� ������ ������ ������� ������","�����","����","100"));
        questions.add(new NewQuestionData("\"���������\" ��� �������� ����� ������� ��� \"����� �� �����\"","������ � ���","����","200"));
        questions.add(new NewQuestionData("�� ������ ������� ���� � ������ \"�������� ���\"","����� ������","����","300"));
        questions.add(new NewQuestionData("���� ����� ����� �� ������������� ��������������. ��������, ������ ��� ���������� �������","������ � �������","����","400"));
        questions.add(new NewQuestionData("� ������ 1926 ���� �� ���������� ���������� ������������� ������-������������ ������� �����","���� ����","����","500"));
        questions.add(new NewQuestionData("�������� ���� ���:\n�������� ����� ���������\n� ������� ���������,\n� ��������� ������� ����.\"\n ����� ������ � ���� �����..","�������� ��������","����","300"));
        questions.add(new NewQuestionData("���� �������� ���� � �. �������� ������ ��������� �������� ������ (���������� �������)","����","����","200"));
        questions.add(new NewQuestionData("��� ��������� ������ � ������� ������ \"����\" ������, ���������� � ���������������� ������","������ �� ����� ������","����","100"));
        questions.add(new NewQuestionData("� ���������� ����� � �������� \"�����������, ����������� ��������� ����!\" ���������� ��� �������� ������.","������� ","����","400"));
        questions.add(new NewQuestionData(" ������ �� ��� ������ ������� �������� \"������������\"","������� ������.","����","500"));
        questions.add(new NewQuestionData(" ��� ���������� �������������� ����� � ����� ���-��������� ���� �����","����, ��������� � ���-�-����","����","400"));


        DBService service = new DBService();

        service.clearQuestions();
        for(var q:questions)
        {
            service.saveQuestion(q);
        }

    }

    public static void main(String[] args) throws SQLException {
        fillUsers();
        fillQuestions();
    }
}

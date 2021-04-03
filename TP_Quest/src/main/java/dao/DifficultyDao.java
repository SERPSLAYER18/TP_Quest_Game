package dao;

import dao.domain.Difficulty;

public interface DifficultyDao extends Dao<Difficulty> {

    Difficulty get(String name, String password);

    int getRecord(long id);

}

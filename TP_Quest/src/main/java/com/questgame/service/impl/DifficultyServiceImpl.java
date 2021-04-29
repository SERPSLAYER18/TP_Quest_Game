package com.questgame.service.impl;

import com.questgame.dao.DifficultyDao;
import com.questgame.dto.DifficultyDto;
import com.questgame.mapper.DifficultyMapper;
import com.questgame.service.DifficultyService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DifficultyServiceImpl implements DifficultyService {

    private final DifficultyDao difficultyDAO;

    @Override
    public List<DifficultyDto> getDifficulties() {
        List<DifficultyDto> difficulties = new ArrayList<DifficultyDto>();
        for (var d : difficultyDAO.getDifficulties()) {
            difficulties.add(DifficultyMapper.difficultyToDto(d));
        }
        return difficulties;
    }
}

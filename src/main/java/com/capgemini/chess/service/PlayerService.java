package com.capgemini.chess.service;

import java.util.List;

import com.capgemini.chess.exception.PlayerValidationException;
import com.capgemini.chess.service.to.PlayerTO;

public interface PlayerService {

    List<PlayerTO> findAllPlayers();

    PlayerTO findPlayerById(long id) throws PlayerValidationException;

    List<PlayerTO> findPlayersByLevel(long level);

    long getPlayerLevelById(long id) throws PlayerValidationException;
    
    List<PlayerTO> getPlayersByUsernameAndEmail(String username, String email);
}

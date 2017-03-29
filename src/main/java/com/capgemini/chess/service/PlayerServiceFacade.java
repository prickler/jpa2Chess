package com.capgemini.chess.service;

import java.util.List;

import com.capgemini.chess.exception.PlayerValidationException;
import com.capgemini.chess.service.to.PlayerTO;

public interface PlayerServiceFacade {

	PlayerTO findPlayerByID(long id) throws PlayerValidationException;
	
	List<PlayerTO> findAllPlayers();
	
	List<PlayerTO> findPlayersByLevel(long level);
	
	PlayerTO getPlayerStats(long id) throws PlayerValidationException;
	
    List<PlayerTO> getPlayersByUsernameAndEmail(String username, String email);
}

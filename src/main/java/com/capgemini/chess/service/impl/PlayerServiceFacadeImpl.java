package com.capgemini.chess.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.capgemini.chess.exception.PlayerValidationException;
import com.capgemini.chess.service.PlayerService;
import com.capgemini.chess.service.PlayerServiceFacade;
import com.capgemini.chess.service.to.PlayerTO;

@Service
public class PlayerServiceFacadeImpl implements PlayerServiceFacade {

	@Autowired
	private PlayerService playerService;

	@Override
	public List<PlayerTO> findAllPlayers() {
		return playerService.findAllPlayers();
	}

	@Override
	public PlayerTO findPlayerByID(long id) throws PlayerValidationException {
		return playerService.findPlayerById(id);
	}

	@Override
	public List<PlayerTO> findPlayersByLevel(long level) {
		return playerService.findPlayersByLevel(level);
	}

	@Override
	public PlayerTO getPlayerStats(long id) throws PlayerValidationException {
		return playerService.findPlayerById(id);
	}
	
	@Override
   public List<PlayerTO> getPlayersByUsernameAndEmail(String username, String email){
		return playerService.getPlayersByUsernameAndEmail(username, email);
	}
}

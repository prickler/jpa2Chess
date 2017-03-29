package com.capgemini.chess.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.capgemini.chess.dataaccess.dao.PlayerDao;
import com.capgemini.chess.exception.PlayerValidationException;
import com.capgemini.chess.service.PlayerService;
import com.capgemini.chess.service.to.PlayerTO;

@Service
public class PlayerServiceImpl implements PlayerService {
	@Autowired
	private PlayerDao playerDao;

	@Override
	public List<PlayerTO> findAllPlayers() {
		return playerDao.findAll();
	}

	@Override
	public PlayerTO findPlayerById(long id) throws PlayerValidationException {
		if (!playerDao.findAll().contains(playerDao.findByID(id)))
			throw new PlayerValidationException("This player does not exist.");
		return playerDao.findByID(id);
	}

	@Override
	public List<PlayerTO> findPlayersByLevel(long level) {
		return playerDao.findByLevel(level);
	}

	@Override
	public long getPlayerLevelById(long id) throws PlayerValidationException {
		if (!playerDao.findAll().contains(playerDao.findByID(id)))
			throw new PlayerValidationException("This player does not exist.");
		return playerDao.findByID(id).getLevel();
	}
	@Override
	public List<PlayerTO> getPlayersByUsernameAndEmail(String username, String email) {
		return playerDao.findByUserameAndEmail(username, email);
	}

}

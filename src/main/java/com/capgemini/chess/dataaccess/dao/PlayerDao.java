package com.capgemini.chess.dataaccess.dao;

import java.util.List;

import com.capgemini.chess.dataaccess.entities.PlayerEntity;
import com.capgemini.chess.service.to.PlayerTO;

public interface PlayerDao extends AbstractDao<PlayerEntity, PlayerTO, Long> {

	List<PlayerTO> findByLevel(long level);

	PlayerTO findByID(long id);
	
	List<PlayerTO> findByUserameAndEmail(String username, String email);
}

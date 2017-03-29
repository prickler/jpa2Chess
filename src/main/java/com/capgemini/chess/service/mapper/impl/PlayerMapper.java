package com.capgemini.chess.service.mapper.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.capgemini.chess.dataaccess.entities.PlayerEntity;
import com.capgemini.chess.dataaccess.entities.StatsEntity;
import com.capgemini.chess.service.mapper.Mapper;
import com.capgemini.chess.service.to.PlayerTO;

@Component
public class PlayerMapper implements Mapper<PlayerEntity, PlayerTO> {

	@Override
	public PlayerTO mapE(PlayerEntity entity) {
		if (entity != null) {
			PlayerTO to = new PlayerTO();
			to.setUserID(entity.getId());
			to.setLevel(entity.getStats().getLevel());
			to.setRanking(entity.getStats().getRanking());
			to.setUsername(entity.getUsername());
			to.setEmail(entity.getEmail());
			return to;
		}
		return null;
	}

	@Override
	public PlayerEntity mapTO(PlayerTO to) {
		if (to != null) {
			PlayerEntity entity = new PlayerEntity();
			StatsEntity stats = new StatsEntity();
			entity.setId(to.getUserID());
			entity.setEmail(to.getEmail());
			entity.setUsername(to.getUsername());
			stats.setLevel(to.getLevel());
			stats.setRanking(to.getRanking());
			entity.setStats(stats);
			return entity;
		}
		return null;
	}

	@Override
	public List<PlayerTO> mapEs(List<PlayerEntity> entities) {
		return entities.stream().map(this::mapE).collect(Collectors.toList());
	}

	@Override
	public List<PlayerEntity> mapTOs(List<PlayerTO> tos) {
		return tos.stream().map(this::mapTO).collect(Collectors.toList());
	}
}
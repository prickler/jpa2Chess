package com.capgemini.chess.service.mapper.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.capgemini.chess.dataaccess.entities.ChallengeEntity;
import com.capgemini.chess.service.mapper.Mapper;
import com.capgemini.chess.service.to.ChallengeTO;

@Component
public class ChallengeMapper implements Mapper<ChallengeEntity, ChallengeTO> {

	@Autowired
	PlayerMapper playerMapper;

	@Override
	public ChallengeTO mapE(ChallengeEntity entity) {
		if (entity != null) {
			ChallengeTO to = new ChallengeTO();
			to.setId(entity.getId());
			to.setChallenger(playerMapper.mapE(entity.getChallengingPlayer()));
			to.setChallenged(playerMapper.mapE(entity.getChallengedPlayer()));
			to.setStatus(entity.getStatus());
			return to;
		}
		return null;
	}

	@Override
	public ChallengeEntity mapTO(ChallengeTO to) {
		if (to != null) {
			ChallengeEntity entity = new ChallengeEntity();
			entity.setId(to.getId());
			entity.setChallengingPlayer(playerMapper.mapTO(to.getChallenger()));
			entity.setChallengedPlayer(playerMapper.mapTO(to.getChallenged()));
			entity.setStatus(to.getStatus());
			return entity;
		}
		return null;
	}

	@Override
	public List<ChallengeTO> mapEs(List<ChallengeEntity> entities) {
		return entities.stream().map(this::mapE).collect(Collectors.toList());
	}

	@Override
	public List<ChallengeEntity> mapTOs(List<ChallengeTO> tos) {
		return tos.stream().map(this::mapTO).collect(Collectors.toList());
	}
}
package com.capgemini.chess.dataaccess.dao.impl;

import java.util.List;

import javax.persistence.TypedQuery;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.capgemini.chess.dataaccess.dao.ChallengeDao;
import com.capgemini.chess.dataaccess.entities.ChallengeEntity;
import com.capgemini.chess.enums.ChallengeStatus;
import com.capgemini.chess.queries.QueryNames;
import com.capgemini.chess.service.mapper.Mapper;
import com.capgemini.chess.service.mapper.impl.ChallengeMapper;
import com.capgemini.chess.service.to.ChallengeTO;

@Repository
public class ChallengeDaoImpl extends AbstractDaoImpl<ChallengeEntity, ChallengeTO, Long> implements ChallengeDao {

	@Autowired
	ChallengeMapper challengeMapper;

	@Override
	public List<ChallengeTO> findChallengesByChallengerIdAndChallengeStatus(long id, ChallengeStatus status) {
		TypedQuery<ChallengeEntity> query = entityManager
				.createNamedQuery(QueryNames.CHALLENGE_ENTITY_FIND_BY_CHALLENGER_ID_AND_STATUS, ChallengeEntity.class);
		query.setParameter("ID", id);
		query.setParameter("status", status);
		return getMapper().mapEs(query.getResultList());
	}

	@Override
	public List<ChallengeTO> findChallengesByChallengedIdAndChallengeStatus(long id, ChallengeStatus status) {
		TypedQuery<ChallengeEntity> query = entityManager
				.createNamedQuery(QueryNames.CHALLENGE_ENTITY_FIND_BY_CHALLENGED_ID_AND_STATUS, ChallengeEntity.class);
		query.setParameter("ID", id);
		query.setParameter("status", status);
		return getMapper().mapEs(query.getResultList());
	}

	@Override
	public List<ChallengeTO> findChallengesByChallengerId(Long userID) {
		TypedQuery<ChallengeEntity> query = entityManager
				.createNamedQuery(QueryNames.CHALLENGE_ENTITY_FIND_BY_CHALLENGER_ID, ChallengeEntity.class);
		query.setParameter("ID", userID);
		return getMapper().mapEs(query.getResultList());
	}

	@Override
	protected Mapper<ChallengeEntity, ChallengeTO> getMapper() {
		return challengeMapper;
	}
}

package com.capgemini.chess.dataaccess.dao;

import java.util.List;

import com.capgemini.chess.dataaccess.entities.ChallengeEntity;
import com.capgemini.chess.enums.ChallengeStatus;
import com.capgemini.chess.service.to.ChallengeTO;

public interface ChallengeDao extends AbstractDao<ChallengeEntity, ChallengeTO, Long> {

	List<ChallengeTO> findChallengesByChallengerIdAndChallengeStatus(long id, ChallengeStatus status);

	List<ChallengeTO> findChallengesByChallengedIdAndChallengeStatus(long id, ChallengeStatus status);

	List<ChallengeTO> findChallengesByChallengerId(Long userID);
}

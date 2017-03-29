package com.capgemini.chess.service;

import java.util.List;

import com.capgemini.chess.enums.ChallengeStatus;
import com.capgemini.chess.exception.ChallengeSuggestionException;
import com.capgemini.chess.exception.ChallengeValidationException;
import com.capgemini.chess.exception.PlayerValidationException;
import com.capgemini.chess.service.to.ChallengeTO;
import com.capgemini.chess.service.to.PlayerTO;

public interface ChallengeService {

	ChallengeTO createChallenge(PlayerTO challengerID, PlayerTO challengedID)
			throws ChallengeValidationException, PlayerValidationException;

	ChallengeTO automaticallyCreateChallenge(PlayerTO challengerID)
			throws ChallengeValidationException, PlayerValidationException;

	ChallengeTO changeChallengeStatus(Long id, ChallengeStatus status) throws ChallengeValidationException;

	List<ChallengeTO> getPlayerChallenges(PlayerTO player) throws PlayerValidationException;

	List<ChallengeTO> getPlayerThrownChallengeList(PlayerTO player) throws PlayerValidationException;

	List<ChallengeTO> getPlayerFinishedChallengeList(PlayerTO player) throws PlayerValidationException;

	List<ChallengeTO> getPlayerAcceptedChallengeList(PlayerTO player) throws PlayerValidationException;

	List<ChallengeTO> automaticallyCreateChallengeSuggestions(PlayerTO challenger)
			throws ChallengeValidationException, ChallengeSuggestionException, PlayerValidationException;

	ChallengeTO saveChallenge(ChallengeTO challenge) throws ChallengeValidationException;
}

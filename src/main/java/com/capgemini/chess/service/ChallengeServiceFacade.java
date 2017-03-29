package com.capgemini.chess.service;

import java.util.List;

import com.capgemini.chess.enums.ChallengeStatus;
import com.capgemini.chess.exception.ChallengeSuggestionException;
import com.capgemini.chess.exception.ChallengeValidationException;
import com.capgemini.chess.exception.PlayerValidationException;
import com.capgemini.chess.service.to.ChallengeTO;
import com.capgemini.chess.service.to.PlayerTO;

public interface ChallengeServiceFacade {

	ChallengeTO createChallenge(PlayerTO challenger, PlayerTO challenged) throws ChallengeValidationException, PlayerValidationException;

	ChallengeTO automaticallyCreateChallenge(PlayerTO challenger) throws ChallengeValidationException, PlayerValidationException;

	ChallengeTO changeChallengeStatus(Long id, ChallengeStatus status) throws ChallengeValidationException;
	
	List<ChallengeTO> automaticallyCreateChallengeSuggestions(PlayerTO challenger) throws ChallengeValidationException, ChallengeSuggestionException, PlayerValidationException;
		
	ChallengeTO saveSelectedChallenge(ChallengeTO challenge) throws ChallengeValidationException;
}

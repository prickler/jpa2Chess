package com.capgemini.chess.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.capgemini.chess.enums.ChallengeStatus;
import com.capgemini.chess.exception.ChallengeSuggestionException;
import com.capgemini.chess.exception.ChallengeValidationException;
import com.capgemini.chess.exception.PlayerValidationException;
import com.capgemini.chess.service.ChallengeService;
import com.capgemini.chess.service.ChallengeServiceFacade;
import com.capgemini.chess.service.to.ChallengeTO;
import com.capgemini.chess.service.to.PlayerTO;

@Service
public class ChallengeServiceFacadeImpl implements ChallengeServiceFacade {

	@Autowired
	private ChallengeService challengeService;

	@Override
	public ChallengeTO createChallenge(PlayerTO challenger, PlayerTO challenged)
			throws ChallengeValidationException, PlayerValidationException {
		return challengeService.createChallenge(challenger, challenged);
	}

	@Override
	public ChallengeTO automaticallyCreateChallenge(PlayerTO challenger)
			throws PlayerValidationException, ChallengeValidationException {
		return challengeService.automaticallyCreateChallenge(challenger);
	}

	@Override
	public ChallengeTO changeChallengeStatus(Long id, ChallengeStatus status) throws ChallengeValidationException {
		return challengeService.changeChallengeStatus(id, status);
	}

	@Override
	public List<ChallengeTO> automaticallyCreateChallengeSuggestions(PlayerTO challenger)
			throws ChallengeValidationException, ChallengeSuggestionException, PlayerValidationException {
		return challengeService.automaticallyCreateChallengeSuggestions(challenger);
	}

	@Override
	public ChallengeTO saveSelectedChallenge(ChallengeTO challenge) throws ChallengeValidationException {
		return challengeService.saveChallenge(challenge);
	}

}

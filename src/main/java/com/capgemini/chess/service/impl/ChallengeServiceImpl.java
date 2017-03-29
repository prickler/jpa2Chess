package com.capgemini.chess.service.impl;

import com.capgemini.chess.service.PlayerService;
import com.capgemini.chess.service.to.PlayerTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.capgemini.chess.dataaccess.dao.ChallengeDao;
import com.capgemini.chess.enums.ChallengeStatus;
import com.capgemini.chess.exception.ChallengeSuggestionException;
import com.capgemini.chess.exception.ChallengeValidationException;
import com.capgemini.chess.exception.PlayerValidationException;
import com.capgemini.chess.service.ChallengeService;
import com.capgemini.chess.service.to.ChallengeTO;

import java.util.List;
import java.util.Random;
import java.util.Vector;

@Service
public class ChallengeServiceImpl implements ChallengeService {

	@Autowired
	private ChallengeDao challengeDao;

	@Autowired
	private PlayerService playerService;

	@Override
	public ChallengeTO createChallenge(PlayerTO challenger, PlayerTO challenged)
			throws ChallengeValidationException, PlayerValidationException {
		if (challengeDao
				.findChallengesByChallengerIdAndChallengeStatus((challenger.getUserID()), ChallengeStatus.THROWN)
				.size() < 5) {

			validatePlayer(challenger);
			validatePlayer(challenged);

			ChallengeTO challenge = new ChallengeTO();
			challenge.setChallenged(challenged);
			challenge.setChallenger(challenger);
			challenge.setStatus(ChallengeStatus.THROWN);
			return challenge;
		} else {
			throw new ChallengeValidationException("Too many challenges thrown.");
		}
	}

	@Override
	public ChallengeTO automaticallyCreateChallenge(PlayerTO challenger)
			throws ChallengeValidationException, PlayerValidationException {
		validatePlayer(challenger);
		if (challengeDao.findChallengesByChallengerIdAndChallengeStatus(challenger.getUserID(), ChallengeStatus.THROWN)
				.size() < 5) {
			
			ChallengeTO challenge = new ChallengeTO();
			challenge.setChallenger(challenger);
			challenge.setStatus(ChallengeStatus.THROWN);

			List<PlayerTO> availablePlayersList = playerService.findPlayersByLevel(challenger.getLevel());
			int randomNumber = (new Random()).nextInt(availablePlayersList.size());

			if (isAlmostSameLevel(challenger.getLevel(), availablePlayersList.get(randomNumber).getLevel())) {
				challenge.setChallenged((availablePlayersList.get(randomNumber)));
				return challengeDao.save(challenge);
			}
		}
		throw new ChallengeValidationException("Too many challenges thrown.");
	}

	@Override
	public List<ChallengeTO> automaticallyCreateChallengeSuggestions(PlayerTO challenger)
			throws ChallengeValidationException, PlayerValidationException, ChallengeSuggestionException {
		validatePlayer(challenger);
		while (challengeDao
				.findChallengesByChallengerIdAndChallengeStatus(challenger.getUserID(), ChallengeStatus.THROWN)
				.size() < 5) {
			List<PlayerTO> suggestedPlayers = createAvailablePlayersList(challenger.getLevel());
			List<ChallengeTO> challenges = new Vector<ChallengeTO>();

			for (PlayerTO suggestedPlayer : suggestedPlayers) {
				ChallengeTO challenge = new ChallengeTO();
				challenge.setChallenger(challenger);
				challenge.setChallenged(suggestedPlayer);
				challenge.setStatus(ChallengeStatus.THROWN);
				challenges.add(challenge);
			}
			return challenges;
		}
		throw new ChallengeValidationException("Too many challenges thrown.");
	}

	@Override
	public List<ChallengeTO> getPlayerChallenges(PlayerTO player) throws PlayerValidationException {
		validatePlayer(player);
		return challengeDao.findChallengesByChallengerId(player.getUserID());
	}

	@Override
	public List<ChallengeTO> getPlayerThrownChallengeList(PlayerTO player) throws PlayerValidationException {
		validatePlayer(player);
		return challengeDao.findChallengesByChallengerIdAndChallengeStatus(player.getUserID(), ChallengeStatus.THROWN);
	}

	@Override
	public List<ChallengeTO> getPlayerFinishedChallengeList(PlayerTO player) throws PlayerValidationException {
		validatePlayer(player);
		return challengeDao.findChallengesByChallengerIdAndChallengeStatus(player.getUserID(),
				ChallengeStatus.FINISHED);
	}

	@Override
	public List<ChallengeTO> getPlayerAcceptedChallengeList(PlayerTO player) throws PlayerValidationException {
		validatePlayer(player);
		return challengeDao.findChallengesByChallengerIdAndChallengeStatus(player.getUserID(),
				ChallengeStatus.ACCEPTED);
	}

	@Override
	public ChallengeTO changeChallengeStatus(Long id, ChallengeStatus status) throws ChallengeValidationException {
		ChallengeTO challenge = challengeDao.findOne(id);
		if (challenge == null)
			throw new ChallengeValidationException("This challenge does not exist.");
		challenge.setStatus(status);
		return challengeDao.update(challenge);
	}

	@Override
	public ChallengeTO saveChallenge(ChallengeTO challenge) throws ChallengeValidationException {
		if (challenge.getChallenged() == null || challenge.getChallenger() == null || challenge.getStatus() == null)
			throw new ChallengeValidationException("This challenge is not valid.");
		return challengeDao.save(challenge);
	}

	private boolean isAlmostSameLevel(long level1, long level2) {
		long difference = level1 - level2;
		if (Math.abs(difference) <= 2)
			return true;
		else
			return false;
	}

	private List<PlayerTO> createAvailablePlayersList(long level) throws ChallengeSuggestionException {
		List<PlayerTO> availablePlayersList = playerService.findAllPlayers();
		List<PlayerTO> challengeSuggestionList = new Vector<PlayerTO>();

		for (int i = 0; challengeSuggestionList.size() < 5; i++) {
			if (i == availablePlayersList.size() - 1)
				throw new ChallengeSuggestionException("There are no available players");
			if (isAlmostSameLevel(level, availablePlayersList.get(i).getLevel())) {
				challengeSuggestionList.add(availablePlayersList.get(i));
			}
		}
		if (challengeSuggestionList.isEmpty())
			throw new ChallengeSuggestionException("There are no available players");
		return challengeSuggestionList;
	}

	private void validatePlayer(PlayerTO player) throws PlayerValidationException {
		if (!playerService.findAllPlayers().contains(player))
			throw new PlayerValidationException("This player does not exist.");
	}
}

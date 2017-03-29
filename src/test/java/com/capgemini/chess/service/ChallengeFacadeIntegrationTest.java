package com.capgemini.chess.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.List;

import javax.transaction.Transactional;

import org.fest.assertions.api.Assertions;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.capgemini.chess.enums.ChallengeStatus;
import com.capgemini.chess.exception.ChallengeSuggestionException;
import com.capgemini.chess.exception.ChallengeValidationException;
import com.capgemini.chess.exception.PlayerValidationException;
import com.capgemini.chess.service.ChallengeServiceFacade;
import com.capgemini.chess.service.PlayerServiceFacade;
import com.capgemini.chess.service.to.ChallengeTO;
import com.capgemini.chess.service.to.PlayerTO;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class ChallengeFacadeIntegrationTest {

	@Autowired
	private PlayerServiceFacade playerFacade;

	@Autowired
	private ChallengeServiceFacade challengeFacade;

	@Test
	public void shouldSaveChallenge() throws ChallengeValidationException, PlayerValidationException {
		// Given
		PlayerTO challenger = playerFacade.findPlayerByID(1L);
		PlayerTO challenged = playerFacade.findPlayerByID(2L);

		// When
		ChallengeTO challenge = challengeFacade.createChallenge(challenger, challenged);
		ChallengeTO result = challengeFacade.saveSelectedChallenge(challenge);

		// Then
		assertNotNull(result.getId());
		assertEquals(challenger, result.getChallenger());
		assertEquals(challenged, result.getChallenged());
		assertEquals(ChallengeStatus.THROWN, result.getStatus());
	}

	@Test(expected = ChallengeValidationException.class)
	public void shouldNotSaveChallengeAndThrowChallengeValidationException()
			throws PlayerValidationException, ChallengeValidationException {
		// Given
		PlayerTO challenger = playerFacade.findPlayerByID(1L);
		PlayerTO challenged = playerFacade.findPlayerByID(5L);

		// When
		ChallengeTO challenge = challengeFacade.createChallenge(challenger, challenged);
		challenge.setStatus(null);
		@SuppressWarnings("unused")
		ChallengeTO result = challengeFacade.saveSelectedChallenge(challenge);
	}

	@Test(expected = PlayerValidationException.class)
	public void shouldNotSaveChallengeAndThrowPlayerValidationException()
			throws PlayerValidationException, ChallengeValidationException {
		// Given
		PlayerTO challenger = playerFacade.findPlayerByID(1L);
		PlayerTO challenged = new PlayerTO();

		// When
		ChallengeTO challenge = challengeFacade.createChallenge(challenger, challenged);
		@SuppressWarnings("unused")
		ChallengeTO result = challengeFacade.saveSelectedChallenge(challenge);
	}

	@Test
	public void shouldReturnSuggestedChallengeListAndSaveSelectedChallenge()
			throws PlayerValidationException, ChallengeValidationException, ChallengeSuggestionException {
		// Given
		PlayerTO challenger = playerFacade.findPlayerByID(13L);

		// When
		List<ChallengeTO> results = challengeFacade.automaticallyCreateChallengeSuggestions(challenger);
		ChallengeTO saved = challengeFacade.saveSelectedChallenge(results.get(3));

		// Then
		assertEquals(5, results.size());
		assertNotNull(saved.getId());
		Assertions.assertThat(Assertions.extractProperty("status", ChallengeStatus.class).from(results)).containsOnly(ChallengeStatus.THROWN);
		Assertions.assertThat(Assertions.extractProperty("challenged.level", Long.class).from(results)).contains(1L,5L,1L,4L,5L);
	}

	@Test
	public void shouldCreateChallenge() throws PlayerValidationException, ChallengeValidationException {
		// Given
		PlayerTO challenger = playerFacade.findPlayerByID(23L);
		PlayerTO challenged = playerFacade.findPlayerByID(33L);

		// When
		ChallengeTO challenge = challengeFacade.createChallenge(challenger, challenged);

		// Then
		assertEquals(challenger, challenge.getChallenger());
		assertEquals(challenged, challenge.getChallenged());
		assertNotNull(challenge.getStatus());
	}

	@Test(expected = ChallengeValidationException.class)
	public void shouldNotCreateChallengeAndThrowChallengeValidationException()
			throws PlayerValidationException, ChallengeValidationException {
		// Given
		PlayerTO challenger = playerFacade.findPlayerByID(45L);
		PlayerTO challenged = playerFacade.findPlayerByID(33L);
		challengeFacade.automaticallyCreateChallenge(challenger);
		challengeFacade.automaticallyCreateChallenge(challenger);
		challengeFacade.automaticallyCreateChallenge(challenger);
		challengeFacade.automaticallyCreateChallenge(challenger);
		challengeFacade.automaticallyCreateChallenge(challenger);

		// When
		@SuppressWarnings("unused")
		ChallengeTO challenge = challengeFacade.createChallenge(challenger, challenged);
	}

	@Test(expected = PlayerValidationException.class)
	public void shouldNotCreateChallengeAndThrowPlayerValidationException()
			throws PlayerValidationException, ChallengeValidationException {
		// Given
		PlayerTO challenger = playerFacade.findPlayerByID(45L);
		PlayerTO challenged = new PlayerTO();

		// When
		@SuppressWarnings("unused")
		ChallengeTO challenge = challengeFacade.createChallenge(challenger, challenged);
	}

	@Test
	public void shouldAutomaticallyCreateChallenge() throws PlayerValidationException, ChallengeValidationException {
		// Given
		PlayerTO challenger = playerFacade.findPlayerByID(5L);

		// When
		ChallengeTO challenge = challengeFacade.automaticallyCreateChallenge(challenger);

		// Then
		assertNotNull(challenge.getChallenged());
		assertEquals(challenger, challenge.getChallenger());
		assertEquals(ChallengeStatus.THROWN, challenge.getStatus());
	}

	@Test(expected = PlayerValidationException.class)
	public void shouldNotAutomaticallyCreateChallengeAndThrowPlayerValidationException()
			throws PlayerValidationException, ChallengeValidationException {
		// Given
		PlayerTO challenger = new PlayerTO();
		challenger.setUsername("user");

		// When
		@SuppressWarnings("unused")
		ChallengeTO challenge = challengeFacade.automaticallyCreateChallenge(challenger);
	}

	@Test
	public void shouldChangeChallengeStatus() throws PlayerValidationException, ChallengeValidationException {
		// Given
		PlayerTO challenger = playerFacade.findPlayerByID(1L);
		PlayerTO challenged = playerFacade.findPlayerByID(2L);
		ChallengeTO challenge = challengeFacade.createChallenge(challenger, challenged);
		ChallengeTO savedChallenge = challengeFacade.saveSelectedChallenge(challenge);

		// When
		ChallengeTO updatedChallenge = challengeFacade.changeChallengeStatus(savedChallenge.getId(),
				ChallengeStatus.ACCEPTED);

		// Then
		assertEquals(ChallengeStatus.ACCEPTED, updatedChallenge.getStatus());
	}

	@Test(expected = ChallengeValidationException.class)
	public void shouldNotChangeChallengeStatusAndThrowChallengeValidationException()
			throws PlayerValidationException, ChallengeValidationException {
		// Given
		ChallengeTO challenge = new ChallengeTO();
		challenge.setId(10L);

		// When
		@SuppressWarnings("unused")
		ChallengeTO updatedChallenge = challengeFacade.changeChallengeStatus(challenge.getId(),
				ChallengeStatus.ACCEPTED);
	}

	@Test(expected = ChallengeValidationException.class)
	public void shouldNotCreateAutomaticChallengeSuggestionAndThrowChallengeValidationException()
			throws ChallengeValidationException, PlayerValidationException, ChallengeSuggestionException {
		// Given
		PlayerTO challenger = playerFacade.findPlayerByID(45L);
		challengeFacade.automaticallyCreateChallenge(challenger);
		challengeFacade.automaticallyCreateChallenge(challenger);
		challengeFacade.automaticallyCreateChallenge(challenger);
		challengeFacade.automaticallyCreateChallenge(challenger);
		challengeFacade.automaticallyCreateChallenge(challenger);

		// When
		challengeFacade.automaticallyCreateChallengeSuggestions(challenger);
	}

	@Test(expected = ChallengeSuggestionException.class)
	public void shouldNotCreateAutomaticChallengeSuggestionAndThrowChallengeSuggestionException()
			throws ChallengeValidationException, PlayerValidationException, ChallengeSuggestionException {
		// Given
		PlayerTO challenger = playerFacade.findPlayerByID(1L);

		// When
		challengeFacade.automaticallyCreateChallengeSuggestions(challenger);
	}

}
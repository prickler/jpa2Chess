package com.capgemini.chess.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;

import org.fest.assertions.api.Assertions;

import com.capgemini.chess.dataaccess.dao.ChallengeDao;
import com.capgemini.chess.enums.ChallengeStatus;
import com.capgemini.chess.exception.ChallengeSuggestionException;
import com.capgemini.chess.exception.ChallengeValidationException;
import com.capgemini.chess.exception.PlayerValidationException;
import com.capgemini.chess.service.impl.ChallengeServiceImpl;
import com.capgemini.chess.service.impl.PlayerServiceImpl;
import com.capgemini.chess.service.to.ChallengeTO;
import com.capgemini.chess.service.to.PlayerTO;

@RunWith(MockitoJUnitRunner.class)
public class ChallengeServiceTest {
	
	@InjectMocks
	private ChallengeServiceImpl challengeService;
	
	@Mock
	private PlayerServiceImpl playerService;
	
	@Mock
	private ChallengeDao challengeDao;
	
    @Before
    public void setUp() throws Exception {
    	  MockitoAnnotations.initMocks(this);
    }

    @Test
    public void shouldCreateChallenge() throws ChallengeValidationException, PlayerValidationException {
    	//	Given
    	PlayerTO challenger = new PlayerTO();
    	challenger.setUserID(25L);
    	challenger.setLevel(1L);
    	challenger.setLevel(2L);
    	
    	PlayerTO challenged = new PlayerTO();
    	challenged.setUserID(35L);
    	
    	
    	List<ChallengeTO> expected = new ArrayList<ChallengeTO>();
    	List<PlayerTO> players = new ArrayList<PlayerTO>();
    	
    	players.add(challenger);
    	players.add(challenged);
    	
    	ChallengeTO challenge = new ChallengeTO();
        challenge.setChallenged(challenged);
        challenge.setChallenger(challenger);
        challenge.setStatus(ChallengeStatus.THROWN);
        
    	Mockito.when(challengeDao.findChallengesByChallengerIdAndChallengeStatus(challenger.getUserID(), ChallengeStatus.THROWN)).thenReturn(expected);
    	Mockito.when(challengeDao.save(challenge)).thenReturn(challenge);
    	Mockito.when(playerService.findAllPlayers()).thenReturn(players);
    	
    	//	When
    	ChallengeTO result = challengeService.createChallenge(challenger, challenged);
    	
    	//	Then
    	assertEquals(challenger.getUserID(), result.getChallenger().getUserID());
    	assertEquals(challenged.getUserID(), result.getChallenged().getUserID());
    	assertEquals(ChallengeStatus.THROWN, result.getStatus());
    }
    
    @Test(expected=ChallengeValidationException.class)
    public void shouldNotCreateChallengeAndThrowChallengeValidationException() throws ChallengeValidationException, PlayerValidationException {
    	//	Given
    	PlayerTO challenger = new PlayerTO();
    	challenger.setUserID(25L);
    	challenger.setLevel(1L);
    	challenger.setLevel(2L);
    	
    	PlayerTO challenged = new PlayerTO();
    	challenged.setUserID(35L);
    	
    	ChallengeTO buffer = new ChallengeTO();
    	
    	List<ChallengeTO> expected = new ArrayList<ChallengeTO>();
    	for (int i=1; i<6; i++)
    		expected.add(buffer);
    	List<PlayerTO> players = new ArrayList<PlayerTO>();
    	
    	players.add(challenger);
    	players.add(challenged);
    	
    	ChallengeTO challenge = new ChallengeTO();
        challenge.setChallenged(challenged);
        challenge.setChallenger(challenger);
        challenge.setStatus(ChallengeStatus.THROWN);
        
    	Mockito.when(challengeDao.findChallengesByChallengerIdAndChallengeStatus(challenger.getUserID(), ChallengeStatus.THROWN)).thenReturn(expected);
    	Mockito.when(challengeDao.save(challenge)).thenReturn(challenge);
    	Mockito.when(playerService.findAllPlayers()).thenReturn(players);
    	
    	//	When
    	@SuppressWarnings("unused")
		ChallengeTO result = challengeService.createChallenge(challenger, challenged);
    }

    @Test(expected=PlayerValidationException.class)
    public void shouldNotCreateChallengeAndThrowPlayerValidationException() throws ChallengeValidationException, PlayerValidationException {
    	//	Given
    	PlayerTO challenger = new PlayerTO();
    	challenger.setUserID(25L);
    	challenger.setLevel(1L);
    	challenger.setLevel(2L);
    	
    	PlayerTO challenged = new PlayerTO();
    	challenged.setUserID(35L);
    	
    	List<ChallengeTO> expected = new ArrayList<ChallengeTO>();
    	List<PlayerTO> players = new ArrayList<PlayerTO>();
    	
    	players.add(challenger);
    	
    	ChallengeTO challenge = new ChallengeTO();
        challenge.setChallenged(challenged);
        challenge.setChallenger(challenger);
        challenge.setStatus(ChallengeStatus.THROWN);
        
    	Mockito.when(challengeDao.findChallengesByChallengerIdAndChallengeStatus(challenger.getUserID(), ChallengeStatus.THROWN)).thenReturn(expected);
    	Mockito.when(challengeDao.save(challenge)).thenReturn(challenge);
    	Mockito.when(playerService.findAllPlayers()).thenReturn(players);
    	
    	//	When
    	@SuppressWarnings("unused")
		ChallengeTO result = challengeService.createChallenge(challenger, challenged);
    }

    @Test
    public void shouldAutomaticallyCreateChallenge() throws ChallengeValidationException, PlayerValidationException {
    	//	Given
    	PlayerTO challenger = new PlayerTO();
    	challenger.setUserID(25L);
    	challenger.setLevel(1L);
    	
    	PlayerTO challenged = new PlayerTO();
    	challenged.setUserID(35L);
    	challenged.setLevel(3L);
    	
    	List<ChallengeTO> expected = new ArrayList<ChallengeTO>();
    	List<PlayerTO> players = new ArrayList<PlayerTO>();
    
    	players.add(challenged);
    	players.add(challenger);
    	
    	ChallengeTO challenge = new ChallengeTO();
        challenge.setChallenged(challenged);
        challenge.setChallenger(challenger);
        challenge.setStatus(ChallengeStatus.THROWN);
        
    	Mockito.when(challengeDao.findChallengesByChallengerIdAndChallengeStatus(challenger.getUserID(), ChallengeStatus.THROWN)).thenReturn(expected);
    	Mockito.when(challengeDao.save(Mockito.any())).thenReturn(challenge);
    	Mockito.when(playerService.findAllPlayers()).thenReturn(players);
    	Mockito.when(playerService.findPlayersByLevel(challenger.getLevel())).thenReturn(players);
    	
    	//	When
    	ChallengeTO result = challengeService.automaticallyCreateChallenge(challenger);
    	
    	//	Then
    	assertEquals(challenger.getUserID(), result.getChallenger().getUserID());
    	assertEquals(challenged.getUserID(), result.getChallenged().getUserID());
    	assertEquals(ChallengeStatus.THROWN, result.getStatus());
    }
    
    @Test(expected=ChallengeValidationException.class)
    public void shouldNotAutomaticallyCreateChallengeAndThrowChallengeValidationException() throws ChallengeValidationException, PlayerValidationException {
    	//	Given
    	PlayerTO challenger = new PlayerTO();
    	challenger.setUserID(25L);
    	challenger.setLevel(1L);
    	
    	PlayerTO challenged = new PlayerTO();
    	challenged.setUserID(35L);
    	challenged.setLevel(3L);
    	
    	ChallengeTO buffer = new ChallengeTO();
    	List<ChallengeTO> expected = new ArrayList<ChallengeTO>();
    	for (int i=1; i<6; i++)
    		expected.add(buffer);
    	
    	List<PlayerTO> players = new ArrayList<PlayerTO>();
    	players.add(challenged);
    	players.add(challenger);
    	
    	ChallengeTO challenge = new ChallengeTO();
        challenge.setChallenged(challenged);
        challenge.setChallenger(challenger);
        challenge.setStatus(ChallengeStatus.THROWN);
        
    	Mockito.when(challengeDao.findChallengesByChallengerIdAndChallengeStatus(challenger.getUserID(), ChallengeStatus.THROWN)).thenReturn(expected);
    	Mockito.when(challengeDao.save(challenge)).thenReturn(challenge);
    	Mockito.when(playerService.findAllPlayers()).thenReturn(players);
    	Mockito.when(playerService.findPlayersByLevel(challenger.getLevel())).thenReturn(players);
    	
    	//	When
    	@SuppressWarnings("unused")
		ChallengeTO result = challengeService.automaticallyCreateChallenge(challenger);
    }
    
    @Test(expected=PlayerValidationException.class)
    public void shouldNotAutomaticallyCreateChallengeAndThrowPlayerValidationException() throws ChallengeValidationException, PlayerValidationException {
    	//	Given
    	PlayerTO challenger = new PlayerTO();
    	challenger.setUserID(25L);
    	challenger.setLevel(1L);
    	
    	PlayerTO challenged = new PlayerTO();
    	challenged.setUserID(35L);
    	challenged.setLevel(3L);
    	
    	ChallengeTO buffer = new ChallengeTO();
    	List<ChallengeTO> expected = new ArrayList<ChallengeTO>();
    	for (int i=1; i<6; i++)
    		expected.add(buffer);
    	
    	List<PlayerTO> players = new ArrayList<PlayerTO>();
    	players.add(challenged);
    	
    	ChallengeTO challenge = new ChallengeTO();
        challenge.setChallenged(challenged);
        challenge.setChallenger(challenger);
        challenge.setStatus(ChallengeStatus.THROWN);
        
    	Mockito.when(challengeDao.findChallengesByChallengerIdAndChallengeStatus(challenger.getUserID(), ChallengeStatus.THROWN)).thenReturn(expected);
    	Mockito.when(challengeDao.save(challenge)).thenReturn(challenge);
    	Mockito.when(playerService.findAllPlayers()).thenReturn(players);
    	Mockito.when(playerService.findPlayersByLevel(challenger.getLevel())).thenReturn(players);
    	
    	//	When
    	@SuppressWarnings("unused")
		ChallengeTO result = challengeService.automaticallyCreateChallenge(challenger);
    }
    
    @Test
    public void shouldChangeChallengeStatus() throws ChallengeValidationException, PlayerValidationException {
    	//	Given
    	PlayerTO challenger = new PlayerTO();
    	challenger.setUserID(25L);
    	challenger.setLevel(1L);
    	
    	List<PlayerTO> players = new ArrayList<PlayerTO>();
    	players.add(challenger);
    	
    	ChallengeTO challenge = new ChallengeTO();
        challenge.setChallenger(challenger);
        challenge.setId(10L);
        challenge.setStatus(ChallengeStatus.THROWN);
        
        List<ChallengeTO> result = new ArrayList<ChallengeTO>();
        result.add(challenge);
        
        ChallengeTO newChallenge = challenge;
        newChallenge.setStatus(ChallengeStatus.ACCEPTED);
        
    	Mockito.when(challengeDao.findChallengesByChallengerIdAndChallengeStatus(challenger.getUserID(), ChallengeStatus.THROWN)).thenReturn(result);
    	Mockito.when(playerService.findAllPlayers()).thenReturn(players);
    	Mockito.when(playerService.findPlayersByLevel(challenger.getLevel())).thenReturn(players);
    	Mockito.when(challengeDao.findOne(challenge.getId())).thenReturn(challenge);
    	Mockito.when(challengeDao.update(challenge)).thenReturn(newChallenge);
    	
    	//	When
		ChallengeTO changed = challengeService.changeChallengeStatus(challenge.getId(), ChallengeStatus.ACCEPTED);
    	
    	// Then
    	assertEquals(ChallengeStatus.ACCEPTED, changed.getStatus());
    }
    
    @Test(expected=ChallengeValidationException.class)
    public void shouldNotChangeChallengeStatusAndThrowChallengeValidationException() throws ChallengeValidationException, PlayerValidationException {
    	//	Given
    	PlayerTO challenger = new PlayerTO();
    	challenger.setUserID(25L);
    	challenger.setLevel(1L);
    	
    	List<PlayerTO> players = new ArrayList<PlayerTO>();
    	players.add(challenger);
    	
    	ChallengeTO challenge = new ChallengeTO();
        challenge.setChallenger(challenger);
        challenge.setId(10L);
        challenge.setStatus(ChallengeStatus.THROWN);
        
        List<ChallengeTO> result = new ArrayList<ChallengeTO>();
        result.add(challenge);
        
        ChallengeTO newChallenge = challenge;
        newChallenge.setStatus(ChallengeStatus.ACCEPTED);
        
    	Mockito.when(challengeDao.findChallengesByChallengerIdAndChallengeStatus(challenger.getUserID(), ChallengeStatus.THROWN)).thenReturn(result);
    	Mockito.when(playerService.findAllPlayers()).thenReturn(players);
    	Mockito.when(playerService.findPlayersByLevel(challenger.getLevel())).thenReturn(players);
    	Mockito.when(challengeDao.findOne(challenge.getId())).thenReturn(challenge);
    	Mockito.when(challengeDao.update(challenge)).thenReturn(newChallenge);
    	
    	challenge.setId(36L);
    	
    	//	When
		@SuppressWarnings("unused")
		ChallengeTO changed = challengeService.changeChallengeStatus(challenge.getId(), ChallengeStatus.ACCEPTED);
    }
    
    @Test
    public void shouldCreateAutomaticChallengeSuggestions() throws ChallengeValidationException, PlayerValidationException, ChallengeSuggestionException {
    	//	Given
    	PlayerTO challenger = new PlayerTO();
    	challenger.setUserID(25L);
    	challenger.setLevel(1L);
    	
    	PlayerTO buffer = new PlayerTO();
    	buffer.setLevel(2L);
    	
    	List<PlayerTO> players = new ArrayList<PlayerTO>();
    	players.add(challenger);
    	
    	for (int i=1; i<6; i++)
    		players.add(buffer);
    	
    	ChallengeTO challenge = new ChallengeTO();
        
        List<ChallengeTO> challenges = new ArrayList<ChallengeTO>();
       challenges.add(challenge);
        
    	Mockito.when(challengeDao.findChallengesByChallengerIdAndChallengeStatus(challenger.getUserID(), ChallengeStatus.THROWN)).thenReturn(challenges);
    	Mockito.when(playerService.findAllPlayers()).thenReturn(players);
    	Mockito.when(playerService.findPlayersByLevel(challenger.getLevel())).thenReturn(players);
   
    	// When
		List<ChallengeTO> result = challengeService.automaticallyCreateChallengeSuggestions(challenger);
		
		// Then
		assertNotNull(result); 
		Assertions.assertThat(Assertions.extractProperty("challenger.userID", Long.class).from(result)).containsOnly(25L);
		Assertions.assertThat(Assertions.extractProperty("status", ChallengeStatus.class).from(result)).containsOnly(ChallengeStatus.THROWN);
    }
}
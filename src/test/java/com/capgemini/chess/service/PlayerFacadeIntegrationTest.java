package com.capgemini.chess.service;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.List;

import javax.transaction.Transactional;

import org.fest.assertions.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.capgemini.chess.exception.PlayerValidationException;
import com.capgemini.chess.service.to.PlayerTO;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class PlayerFacadeIntegrationTest {

	@Autowired
	private PlayerServiceFacade playerFacade;
	
	@Test
	public void shouldFindAllPlayers() throws PlayerValidationException {
		// When
		List<PlayerTO> players = playerFacade.findAllPlayers();
		PlayerTO player = playerFacade.findPlayerByID(27L);
		
		// Then
		assertTrue(players.size()==100);
		assertTrue(players.contains(player));
	}
	
	@Test
	public void shouldFindPlayerById() throws PlayerValidationException {
		// When
		PlayerTO player = playerFacade.findPlayerByID(27L);
		
		// Then
		assertTrue(player.getUserID() == 27L);
		assertEquals("diam", player.getUsername());
	}
	
	@Test
	public void shouldFindPlayersByLevel() throws PlayerValidationException {
		// When
		List<PlayerTO> players = playerFacade.findPlayersByLevel(5L);
		
		// Then
		assertEquals(14, players.size());
		Assertions.assertThat(Assertions.extractProperty("level", Long.class).from(players)).containsOnly(5L);
	}
	
	@Test
	public void shouldGetPlayerStats() throws PlayerValidationException {
		// Given
		PlayerTO player = playerFacade.findPlayerByID(27L);
	
		// When
		PlayerTO result = playerFacade.getPlayerStats(27L);
		
		// Then
		assertEquals(player.getLevel(), result.getLevel());
	}
	@Test
	public void shouldFindPlayersByUsernameAndEmail() throws PlayerValidationException {
		// When
		List<PlayerTO> players = playerFacade.getPlayersByUsernameAndEmail("diam", "scelerisque.mollis.Phasellus@lobortisaugue.com");
		PlayerTO player = playerFacade.findPlayerByID(27L);
		// Then
		assertEquals(1, players.size());
		assertTrue(players.contains(player));
	}
	
}

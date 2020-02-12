package com.mozzartbet.gameservice.service;

import static org.junit.Assert.*;

import java.util.List;
import java.util.Map;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.junit.runners.MethodSorters;

import static org.hamcrest.Matchers.*;

import com.mozzartbet.gameservice.domain.Player;
import com.mozzartbet.gameservice.service.dto.PlayerSearchRequest;
import com.mozzartbet.gameservice.service.dto.PlayerSearchResponse;
import com.mozzartbet.gameservice.service.impl.PlayerServiceImpl;

import lombok.extern.slf4j.Slf4j;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@Slf4j
@Transactional
public class PlayerServiceTest extends BaseServiceTest {

	@Autowired
	private PlayerService playerService;
		
	PlayerSearchResponse psr;

	@Test
	public void searchPlayerNameTest() {
		System.out.println(PlayerSearchRequest.builder().playerName("Nikol").build());
		psr = playerService.searchPlayers(PlayerSearchRequest.builder().playerName("Nikol").build());
		assertThat(psr.getPlayers().get(0).getPlayerName(), equalTo("Nikola Mirotić"));
	}
	
	@Test 
	public void findPlayerNameTest() {
		List<Player> players = playerService.findPlayersByName("Nikola", Long.valueOf(1));
		System.out.println(players.size());
		assertTrue(players.size() > 0);
	}
	
	@Test 
	public void saveTest() {
		playerService.save(Player.builder().
				playerName("Nika").
				teamName("Millwauke").
				uniformNumber("11").
				teamId(Long.valueOf(1)).
				position("va").
				height("7").
				width("232").
				birthDate("12.10.1000").
				yearsOfExp("3").
				college("University of Iowa").
				playerUrl("/home/").build());
		//assertTrue( != 0);
	}
	
	@Test 
	public void saveDuplicateTest() {
		playerService.save(Player.builder().
				playerName("Nika").
				teamName("Millwauke").
				uniformNumber("11").
				teamId(Long.valueOf(1)).
				position("va").
				height("7").
				width("232").
				birthDate("12.10.1000").
				yearsOfExp("3").
				college("University of Iowa").
				playerUrl("/home/").build());
		//assertTrue( != 0);
	}
	
	@Test
	public void cacheTest() {
		assertTrue(playerService.getPlayer(Long.valueOf(1)) instanceof Player);
	}
	
	@Test
	public void getTeamPlayersTest() {
		Map<Long, Player> teamPlayers = playerService.getTeamPlayers(Long.valueOf(2));
		teamPlayers.forEach((id, player) -> 
		{
			log.debug("Checking if player is not null and instance of Player");
			assertTrue(player instanceof Player);
		});
		assertTrue(teamPlayers.size() != 0);
		
	}
}
package com.mozzartbet.gameservice.service;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import java.util.List;
import java.util.Map;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.mozzartbet.gameservice.domain.AssistLeaders;
import com.mozzartbet.gameservice.domain.Player;
import com.mozzartbet.gameservice.service.dto.PlayerSearchRequest;
import com.mozzartbet.gameservice.service.dto.PlayerSearchResponse;

import lombok.extern.slf4j.Slf4j;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@Slf4j
@Transactional
public class AssistLeadersServiceTest extends BaseServiceTest {

	@Autowired
	private AssistLeadersService assistService;

	@Test
	public void searchByPlayerUrlTest() {
		List<AssistLeaders> assitLeaders = assistService.serachByPlayerUrl("https://www.basketball-reference.com/players/b/bealbr01.html");
		assertTrue(assitLeaders.size() != 0);
	}
	
	@Test 
	public void searchByGameUrlTest() {
		List<AssistLeaders> assistLeaders = assistService.searchByGameUrl("https://www.basketball-reference.com/boxscores/pbp/201906130GSW.html");
		System.out.println(assistLeaders.size());
		assertTrue(assistLeaders.size() > 0);
	}
	
	@Test 
	public void saveTest() {
		assistService.save(
		AssistLeaders.builder().
		gameId(Long.valueOf(1)).
		gameUrl("url").
		player("player").
		score(Long.valueOf(129)).build());
		//assertTrue( != 0);
	}
	
	@Test 
	public void saveDuplicateTest() {
		assistService.save(AssistLeaders.builder().
				gameId(Long.valueOf(1)).
				gameUrl("url").
				player("player").
				score(Long.valueOf(129)).build());
		//assertTrue( != 0);
	}
	
	@Test
	public void cacheTest() {
		assertTrue(assistService.getAssistLeaders(Long.valueOf(1)) instanceof AssistLeaders);
	}
}
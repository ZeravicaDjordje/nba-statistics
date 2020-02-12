package com.mozzartbet.gameservice.mapper;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.mozzartbet.gameservice.domain.Game;
import com.mozzartbet.gameservice.domain.QuarterValue;
import com.mozzartbet.gameservice.domain.ReboundLeaders;

import lombok.extern.slf4j.Slf4j;

@RunWith(SpringRunner.class)
@SpringBootTest
//@Transactional
public class GameMapperTest {

	@Autowired
	private GameMapper gameMapper;
	
	@Test
	public void testGetById() {
		System.out.println(gameMapper.getById(Long.valueOf(1)));
		//assertTrue(gameMapper.getById(Long.valueOf(1)) instanceof Game);
	}
	
	@Test
	public void testInsert() {
		int insertReturnValue = gameMapper.insert(Game.builder().
		gameUrl("https://www.basketball-reference.com/boxscores/pbp/201906130GSW.html").build());
		assertEquals(insertReturnValue, 1);
	}
	
	@Test
	public void testDelete() {
		int deleteReturnValue = gameMapper.deleteById(gameMapper.getAllData().get(0).getId());
		assertEquals(deleteReturnValue, 1);
	}
	
	@Test
	public void getByIdGame() {
		Game getByIdReturnValue = gameMapper.getById(Long.valueOf(1));
		assertTrue(getByIdReturnValue instanceof Game);
		System.out.println(
		"Quarter Value Size: " + 
		getByIdReturnValue.getQuarterValue().size() + 
		" Quarter Value Object " + getByIdReturnValue.getQuarterValue());
		System.out.println(
		" Rebound Leaders Value Size: " + 
		getByIdReturnValue.getReboundLeaders().size() + 
		" Rebound Leaders Object " + getByIdReturnValue.getReboundLeaders());
		System.out.println(
		" Score Leaders Size: " + 
		getByIdReturnValue.getScoreLeaders().size() + 
		" Score Leaders Object " + getByIdReturnValue.getScoreLeaders());
		System.out.println(
		" Assist Leaders Size: " + 
		getByIdReturnValue.getAssistLeaders().size() + 
		" Assist Leaders Object " + getByIdReturnValue.getAssistLeaders());
		System.out.println(
		" Team Statistics Size: " + 
		getByIdReturnValue.getTeamStats().size() + 
		" Team Statistics Object " + getByIdReturnValue.getTeamStats());
		System.out.println(
		" Player Statistics Size: " + 
		getByIdReturnValue.getPlayerStats().size() + 
		" Player Statistics Object " + getByIdReturnValue.getPlayerStats());
	}
	
	@Test 
	public void testDeleteGame() {
		int deleteValue = gameMapper.deleteById(Long.valueOf(1));
		assertEquals(deleteValue, 1);
	}
	
	@Test
	public void testUpdateGame() {
		int updateValue = gameMapper.update(Game.builder().
		gameUrl("https://www.basketball-reference.com/boxscores/pbp/201906130GSW.html").build());
		assertEquals(updateValue, 1);

	}
	
	@Test
	public void testForDuplicates() {
		List<Game> duplicates = gameMapper.checkForDuplicate();
		assertTrue(duplicates.size() == 0);
	}
}

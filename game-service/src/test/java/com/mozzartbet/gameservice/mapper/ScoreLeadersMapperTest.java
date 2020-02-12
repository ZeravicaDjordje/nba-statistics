package com.mozzartbet.gameservice.mapper;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.mozzartbet.gameservice.domain.AssistLeaders;
import com.mozzartbet.gameservice.domain.ScoreLeaders;

@RunWith(SpringRunner.class)
@SpringBootTest
//@Transactional
public class ScoreLeadersMapperTest {

	@Autowired
	private ScoreLeadersMapper slMapper;
	
	@Test
	public void testGetById() {
		System.out.println(slMapper.getById(Long.valueOf(1)));
		//assertTrue(rlMapper.getById(Long.valueOf(1)) instanceof Game);
	}
	
	@Test
	public void testInsert() {
		ScoreLeaders sl = ScoreLeaders.builder().
		player("/player").
		score(Long.valueOf(100)).
		gameUrl("https://www.basketball-reference.com/boxscores/pbp/201906130GSW.html").build();
		long gameId = slMapper.getGameId(sl);
		sl.setGameId(gameId);
		int insertReturnValue = slMapper.insert(sl);
		assertEquals(insertReturnValue, 1);
	}
	
	@Test
	public void testDelete() {
		ScoreLeaders rl = ScoreLeaders.builder().
		player("/player").
		score(Long.valueOf(100)).
		gameUrl("https://www.basketball-reference.com/boxscores/pbp/201906130GSW.html").build();
		int deleteReturnValue = slMapper.deleteById(Long.valueOf(1));
		assertEquals(deleteReturnValue, 1);
	}
	
	public void testUpdate() {
		ScoreLeaders sl = ScoreLeaders.builder().
		player("/player").
		score(Long.valueOf(100)).
		gameUrl("https://www.basketball-reference.com/boxscores/pbp/201906130GSW.html").build();
		long gameId = slMapper.getGameId(sl);
		sl.setGameId(gameId);
		int updateValue = slMapper.update(sl);
		assertEquals(updateValue, 1);
	}
	
	@Test
	public void testForDuplicates() {
		List<ScoreLeaders> duplicates = slMapper.checkForDuplicate();
		assertTrue(duplicates.size() == 0);
	}
}


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
import com.mozzartbet.gameservice.domain.ReboundLeaders;

@RunWith(SpringRunner.class)
@SpringBootTest
//@Transactional
public class ReboundLeadersMapperTest {

	@Autowired
	private ReboundLeadersMapper rlMapper;
	
	//@Test
	public void testGetById() {
		System.out.println(rlMapper.getById(Long.valueOf(1)));
		//assertTrue(rlMapper.getById(Long.valueOf(1)) instanceof Game);
	}
	
	@Test
	public void testInsert() {
		ReboundLeaders rl = ReboundLeaders.builder().
		player("/player").
		score(Long.valueOf(100)).
		gameUrl("https://www.basketball-reference.com/boxscores/pbp/201906130GSW.html").build();
		long gameId = rlMapper.getGameId(rl);
		rl.setGameId(gameId);
		int insertReturnValue = rlMapper.insert(rl);
		assertEquals(insertReturnValue, 1);
	}
	
	//@Test
	public void testDelete() {
		ReboundLeaders rl = ReboundLeaders.builder().
		player("/player").
		score(Long.valueOf(100)).
		gameUrl("https://www.basketball-reference.com/boxscores/pbp/201906130GSW.html").build();
		int deleteReturnValue = rlMapper.deleteById(Long.valueOf(1));
		assertEquals(deleteReturnValue, 1);
	}
	

	@Test
	public void testUpdateGame() {
		int updateValue = rlMapper.update(ReboundLeaders.builder().
		player("/player").
		score(Long.valueOf(100)).
		gameUrl("https://www.basketball-reference.com/boxscores/pbp/201906130GSW.html").build());
		assertEquals(updateValue, 1);

	}
	
	@Test
	public void testForDuplicates() {
		List<ReboundLeaders> duplicates = rlMapper.checkForDuplicate();
		assertTrue(duplicates.size() == 0);
	}
}

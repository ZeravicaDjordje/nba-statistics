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
import com.mozzartbet.gameservice.domain.Game;

@RunWith(SpringRunner.class)
@SpringBootTest
//@Transactional
public class AssistLeadersMapperTest {
	@Autowired
	private AssistLeadersMapper alMapper;
	
	@Test
	public void testGetById() {
		System.out.println(alMapper.getById(Long.valueOf(1)));
		//assertTrue(rlMapper.getById(Long.valueOf(1)) instanceof Game);
	}
	
	@Test
	public void testInsert() {
		AssistLeaders al = AssistLeaders.builder().
		player("/player").
		score(Long.valueOf(100)).
		gameUrl("https://www.basketball-reference.com/boxscores/pbp/201906130GSW.html").build();
		long gameId = alMapper.getGameId(al);
		al.setGameId(gameId);
		int updateValue = alMapper.insert(al);
		assertEquals(updateValue, 1);
	}
	
	@Test
	public void testDelete() {
		AssistLeaders rl = AssistLeaders.builder().
		player("/player").
		score(Long.valueOf(100)).
		gameUrl("https://www.basketball-reference.com/boxscores/pbp/201906130GSW.html").build();
		int deleteReturnValue = alMapper.deleteById(Long.valueOf(1));
		assertEquals(deleteReturnValue, 1);
	}
	
	@Test
	public void testUpdate() {
		AssistLeaders al = AssistLeaders.builder().
		player("/player").
		score(Long.valueOf(100)).
		gameUrl("https://www.basketball-reference.com/boxscores/pbp/201906130GSW.html").build();
		Long gameId = alMapper.getGameId(al);
		System.out.println(gameId + " " + al.getId());
		al.setGameId(gameId);
		int updateValue = alMapper.update(al);
	}
	
	@Test
	public void testForDuplicates() {
		List<AssistLeaders> duplicates = alMapper.checkForDuplicate();
		assertTrue(duplicates.size() == 0);
	}
}


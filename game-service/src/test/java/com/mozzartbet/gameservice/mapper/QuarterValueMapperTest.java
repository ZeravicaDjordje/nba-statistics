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
import com.mozzartbet.gameservice.domain.Player;
import com.mozzartbet.gameservice.domain.QuarterValue;
import com.mozzartbet.gameservice.domain.QuarterValue;

@RunWith(SpringRunner.class)
@SpringBootTest
//@Transactional
public class QuarterValueMapperTest {

	@Autowired
	private QuarterValueMapper qvMapper;
	
	//@Test
	public void testGetById() {
		System.out.println(qvMapper.getById(Long.valueOf(1)));
		//assertTrue(rlMapper.getById(Long.valueOf(1)) instanceof Game);
	}
	
	@Test
	public void testInsert() {
		QuarterValue qv = QuarterValue.builder().
		quarter("1 QT").
		teamUrl("Lakers").
		score(Long.valueOf(100)).
		gameUrl("https://www.basketball-reference.com/boxscores/pbp/201906130GSW.html").build();
		long gameId = qvMapper.getGameId(qv);
		qv.setGameId(gameId);
		int insertReturnValue = qvMapper.insert(qv);
		assertEquals(insertReturnValue, 1);
	}
	
	//@Test
	public void testDelete() {
		QuarterValue qv = QuarterValue.builder().
		quarter("1 QT").
		score(Long.valueOf(100)).
		gameUrl("https://www.basketball-reference.com/boxscores/pbp/201906130GSW.html").build();
		int deleteReturnValue = qvMapper.deleteById(Long.valueOf(1));
		assertEquals(deleteReturnValue, 1);
	}
	

	@Test
	public void testUpdateGame() {
		int updateValue = qvMapper.update(QuarterValue.builder().
		quarter("1 QT").
		score(Long.valueOf(100)).
		gameUrl("https://www.basketball-reference.com/boxscores/pbp/201906130GSW.html").build());
		assertEquals(updateValue, 1);

	}
	
	@Test
	public void testForDuplicates() {
		List<QuarterValue> duplicates = qvMapper.checkForDuplicate();
		assertTrue(duplicates.size() == 0);
	}
}

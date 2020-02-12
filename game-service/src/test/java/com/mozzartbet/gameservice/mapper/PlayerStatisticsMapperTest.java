package com.mozzartbet.gameservice.mapper;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import com.mozzartbet.gameservice.domain.Player;
import com.mozzartbet.gameservice.domain.stats.PlayerStatistics;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
@Transactional
public class PlayerStatisticsMapperTest {

	@Autowired
	PlayerStatisticsMapper playerStatsMapper;
	
	@Test
	public void testCrud() {
		PlayerStatistics playerStats = PlayerStatistics.builder().
				id(Long.valueOf(1)).
				enterGame(null).
				leaveGame(null).
				block(1).
				defensiveRebound(2).
				foul(2).
				makesFreeThrow(3).
				makesThreePoints(4).
				missesTwoPoints(3).
				missesThreePoints(4).
				missesTwoPoints(3).
				offensiveRebound(4).
				realizationFreeThrow(2).
				realizationThreePoints(3).
				realizationTwoPoints(4).
				steal(3).
				sumMadePoints(4).
				sumRebound(4).
				timeInPlay("32").
				turnover(3).
				assist(1).
				createdOn(new Timestamp(new Date().getTime())).
				modifiedOn(new Timestamp(new Date().getTime())).
				build();
		assertEquals(playerStatsMapper.insert(playerStats), 1);
		System.out.println(playerStatsMapper.getById(Long.valueOf(1)));
		assertTrue(playerStatsMapper.getById(Long.valueOf(1)) instanceof PlayerStatistics);
		assertEquals(playerStatsMapper.update(playerStats), 1);
		assertEquals(playerStatsMapper.deleteById(playerStats.getId()), 1);
	}
}

package com.mozzartbet.gameservice.mapper;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import com.mozzartbet.gameservice.domain.stats.PlayerStatistics;
import com.mozzartbet.gameservice.domain.stats.TeamStatistics;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
@Transactional
public class TeamStatisticsMapperTest {
	
	@Autowired
	TeamStatisticsMapper teamStatsMapper;
	
	@Test
	public void testCrud() {
		TeamStatistics teamStats = TeamStatistics.builder().totalBlock(1).build();
		System.out.println(teamStats);
		assertEquals(teamStatsMapper.insert(teamStats), 1);
		teamStatsMapper.insert(teamStats);teamStats.setTotalFoul(112);
		teamStatsMapper.insert(teamStats);teamStats.setTotalMadeTwoPoints(3);
		teamStatsMapper.insert(teamStats);
		teamStatsMapper.insert(teamStats);
		teamStatsMapper.insert(teamStats);
		System.out.println(teamStatsMapper.getAllId());
		assertEquals(teamStatsMapper.count(), teamStatsMapper.getAllId().size());
		System.out.println(teamStatsMapper.getById(Long.valueOf(1)));
		System.out.println(teamStatsMapper.getById(Long.valueOf(2)));
		System.out.println(teamStatsMapper.getById(Long.valueOf(4)));
		assertTrue(teamStatsMapper.getById(Long.valueOf(1)) instanceof TeamStatistics);
		teamStats.setId(Long.valueOf(1));
		assertEquals(teamStatsMapper.update(teamStats), 1);
		assertEquals(teamStatsMapper.deleteById(Long.valueOf(1)), 1);
	}
}

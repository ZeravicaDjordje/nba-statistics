package com.mozzartbet.gameservice.stats;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Map;

import org.junit.Test;

import com.mozzartbet.gameservice.domain.Quarter;
import com.mozzartbet.gameservice.domain.stats.Statistics;
import com.mozzartbet.gameservice.domain.stats.TeamStatistics;
import com.mozzartbet.gameservice.parser.ActionParser;
import com.mozzartbet.gameservice.parser.QuarterParser;
import com.mozzartbet.gameservice.parser.TeamMatchParser;
import com.mozzartbet.gameservice.util.MinutesPlayed;

public class TestTeamStatistics {

	Statistics statistics;
	TeamMatchParser teamMatch;
	
	@Test
	public void checkIfTeamStatisticsHasNoNaN() {
		this.teamMatch = new TeamMatchParser("2018","12","30");
		teamMatch.setInstanceJsoupDocument();
		Map<String, String> allMatches = teamMatch.serachAllMatchesPerSeason("2019","1","30");
		for(Map.Entry<String, String> matchUrls : allMatches.entrySet()) {
			System.out.println(matchUrls);
			this.statistics = 
					new Statistics(
					new TeamMatchParser(), 
					new ActionParser(), 
					new QuarterParser(), 
					new MinutesPlayed(),
					new Quarter(),
					new TeamStatistics(),
					new TeamStatistics(),
					new ArrayList<Map.Entry<String, Integer>>(),
					new ArrayList<Map.Entry<String, Integer>>(),
					new ArrayList<Map.Entry<String, Integer>>());
			statistics.setTeamPlayerStatistics(
					matchUrls.getKey(),
					matchUrls.getValue());
			statistics.setLeaders();
			System.out.println(statistics.getTeamOneStats());
			System.out.println(statistics.getTeamTwoStats());
			assertTrue(!Float.isNaN(this.statistics.getTeamStatistics(true).getTotalRealizationTwoPoints()));
			assertTrue(!Float.isNaN(this.statistics.getTeamStatistics(true).getTotalRealizationThreePoints()));
			assertTrue(!Float.isNaN(this.statistics.getTeamStatistics(true).getTotalRealizationFreeThrow()));
			assertTrue(!Float.isNaN(this.statistics.getTeamStatistics(false).getTotalRealizationTwoPoints()));
			assertTrue(!Float.isNaN(this.statistics.getTeamStatistics(false).getTotalRealizationThreePoints()));
			assertTrue(!Float.isNaN(this.statistics.getTeamStatistics(false).getTotalRealizationFreeThrow()));
		}
	}
	
	@Test
	public void checkIfTeamStatisticsIsNotNull() {
		this.teamMatch = new TeamMatchParser("2018","12","30");
		teamMatch.setInstanceJsoupDocument();
		Map<String, String> allMatches = teamMatch.serachAllMatchesPerSeason();
		for(Map.Entry<String, String> matchUrls : allMatches.entrySet()) {
			this.statistics = 
					new Statistics(
					new TeamMatchParser(), 
					new ActionParser(), 
					new QuarterParser(), 
					new MinutesPlayed(),
					new Quarter(),
					new TeamStatistics(),
					new TeamStatistics(),
					new ArrayList<Map.Entry<String, Integer>>(),
					new ArrayList<Map.Entry<String, Integer>>(),
					new ArrayList<Map.Entry<String, Integer>>());
			statistics.setTeamPlayerStatistics(
					matchUrls.getKey(),
					matchUrls.getValue());
			statistics.setLeaders();
			assertTrue(this.statistics.getTeamStatistics(true) != null);
			assertTrue(this.statistics.getTeamStatistics(false) != null);
		}
	}
}

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

public class TestQuarterStatistics {
	Statistics statistics;
	TeamMatchParser teamMatch;
	
	@Test
	public void checkQuarterIsEmpty() {
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
			this.statistics.setTeamPlayerStatistics(
					matchUrls.getKey(),
					matchUrls.getValue());
			assertTrue(!this.statistics.getQuarterStatistics(this.statistics.getQuarterValues().getQuarterForTeamOne()).isEmpty());
			assertTrue(!this.statistics.getQuarterStatistics(this.statistics.getQuarterValues().getQuarterForTeamTwo()).isEmpty());
		}
	}
	
	@Test
	public void checkIfQuartersHaveMinimumFive() {
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
			this.statistics.setTeamPlayerStatistics(
					matchUrls.getKey(),
					matchUrls.getValue());
			assertTrue(this.statistics.getQuarterStatistics(this.statistics.getQuarterValues().getQuarterForTeamOne()).size() >= 5);
			assertTrue(this.statistics.getQuarterStatistics(this.statistics.getQuarterValues().getQuarterForTeamTwo()).size() >= 5);
		}
	}
}

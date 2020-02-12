package com.mozzartbet.gameservice.stats;

import static org.junit.Assert.assertEquals;
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


public class TestAssistLeaders {
	Statistics statistics;
	TeamMatchParser teamMatch;
	
	@Test
	public void checkIfAssitIsNotEmpty() {
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
			ArrayList<Map.Entry<String, Integer>> assists = this.statistics.getLeadersInAssists();
			assertTrue(!assists.isEmpty());		
		}
	}
	
	@Test
	public void checkIfAllAssistersHaveMinimumFiveValues() {
		this.teamMatch = new TeamMatchParser("2018","12","30");
		teamMatch.setInstanceJsoupDocument();
		Map<String, String> allMatches = teamMatch.serachAllMatchesPerSeason();
		allMatches.entrySet().forEach(matchUrls -> {
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
			ArrayList<Map.Entry<String, Integer>> assists = this.statistics.getLeadersInAssists();
			assertTrue(assists.size() >= 5);
		});
	}	
	
	
}

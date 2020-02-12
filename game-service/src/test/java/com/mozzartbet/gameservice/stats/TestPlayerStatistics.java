package com.mozzartbet.gameservice.stats;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Map;

import org.junit.Test;

import com.google.common.collect.Iterables;
import com.mozzartbet.gameservice.domain.Quarter;
import com.mozzartbet.gameservice.domain.stats.PlayerStatistics;
import com.mozzartbet.gameservice.domain.stats.Statistics;
import com.mozzartbet.gameservice.domain.stats.TeamStatistics;
import com.mozzartbet.gameservice.parser.ActionParser;
import com.mozzartbet.gameservice.parser.QuarterParser;
import com.mozzartbet.gameservice.parser.TeamMatchParser;
import com.mozzartbet.gameservice.util.MinutesPlayed;

public class TestPlayerStatistics {

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
			this.statistics.setTeamPlayerStatistics
			(
					matchUrls.getKey(),
					matchUrls.getValue()
			);
			for(Map.Entry<String, PlayerStatistics> entryPlayer: Iterables.getLast(this.statistics.getQuarterValues().getQuarterForTeamOne().entrySet()).getValue().entrySet()) {
				assertTrue(this.statistics.getPlayerStatistics(entryPlayer.getKey().toString()) instanceof PlayerStatistics);		
			}
			
		}
	}		
}

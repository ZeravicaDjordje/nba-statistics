package com.mozzartbet.gameservice.domain.stats;

import java.util.ArrayList;

import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import com.google.common.collect.Iterables;
import com.mozzartbet.gameservice.domain.Event;
import com.mozzartbet.gameservice.domain.Player;
import com.mozzartbet.gameservice.domain.Quarter;
import com.mozzartbet.gameservice.domain.Player.PlayerBuilder;
import com.mozzartbet.gameservice.parser.ActionParser;
import com.mozzartbet.gameservice.parser.QuarterParser;
import com.mozzartbet.gameservice.parser.TeamMatchParser;
import com.mozzartbet.gameservice.util.MinutesPlayed;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
 
import static java.util.stream.Collectors.*;

import java.util.AbstractMap;

import static java.util.Map.Entry.*;

@Data
@AllArgsConstructor
@Builder
public class Statistics {

	private TeamMatchParser teamMatchParser;
	private ActionParser actionParser;
	private QuarterParser quarterParser;
	private MinutesPlayed minutesPlayed;
	private Quarter quarterValues;
	private TeamStatistics teamOneStats;
	private TeamStatistics teamTwoStats;
	private ArrayList<Map.Entry<String,Integer>> rebound;
	private ArrayList<Map.Entry<String,Integer>> scoring;
	private ArrayList<Map.Entry<String,Integer>> assists;
	
	public void setTeamPlayerValue(int teamID, Map<String, Integer> minutesPlayed, Map<String, Map<String, PlayerStatistics>> team, TeamStatistics teamStats, String gameUrl) {
		Iterables.getLast(team.entrySet()).getValue().forEach((playerID, playerStats)-> { 
		int time = minutesPlayed.get(playerID);
		playerStats.setGameUrl(gameUrl);
		if(!Double.isNaN((double) playerStats.getMakesFreeThrow() / (playerStats.getMissesFreeThrow() + playerStats.getMakesFreeThrow()))) {
			
			playerStats.setRealizationFreeThrow((double) playerStats.getMakesFreeThrow() / (playerStats.getMissesFreeThrow() + playerStats.getMakesFreeThrow()));
		}
		else {
			playerStats.setRealizationFreeThrow(0.0);
		}
		if(!Double.isNaN((double) playerStats.getMakesThreePoints() / (playerStats.getMissesThreePoints() + playerStats.getMakesThreePoints()))) {
			playerStats.setRealizationThreePoints((double) playerStats.getMakesThreePoints() / (playerStats.getMissesThreePoints() + playerStats.getMakesThreePoints()));
		}
		else {
			playerStats.setRealizationThreePoints(0.0);
		}
		if(!Double.isNaN((double) (playerStats.getMakesTwoPoints() + playerStats.getMakesThreePoints()) / (playerStats.getMissesTwoPoints() + playerStats.getMakesTwoPoints() + playerStats.getMissesThreePoints() + playerStats.getMakesThreePoints()))) {
			playerStats.setRealizationTwoPoints((double) (playerStats.getMakesTwoPoints() + playerStats.getMakesThreePoints()) / (playerStats.getMissesTwoPoints() + playerStats.getMakesTwoPoints() + playerStats.getMissesThreePoints() + playerStats.getMakesThreePoints()));
		}
		else {
			playerStats.setRealizationTwoPoints(0.0);
		}
		playerStats.setPlayerUrl(playerID);
		playerStats.setSumRebound(playerStats.getDefensiveRebound() + playerStats.getOffensiveRebound());
		System.out.println("Player time " + time);
		playerStats.setTimeInPlay(Integer.toString(time / 60) + ":" + Integer.toString(time % 60));
		playerStats.setSumMadePoints(playerStats.getMakesTwoPoints() * 2 + playerStats.getMakesThreePoints() * 3 + playerStats.getMakesFreeThrow());
		teamStats.setTotalAssist(playerStats.getAssist() + teamStats.getTotalAssist());
		teamStats.setTotalBlock(playerStats.getBlock() + teamStats.getTotalBlock());
		teamStats.setTotalDefensiveRebound(playerStats.getDefensiveRebound() + teamStats.getTotalDefensiveRebound());
		teamStats.setTotalOffensiveRebound(playerStats.getOffensiveRebound() + teamStats.getTotalOffensiveRebound());
		teamStats.setTotalFoul(playerStats.getFoul() + teamStats.getTotalFoul());
		teamStats.setTotalMadeFreeThrow(playerStats.getMakesFreeThrow() + teamStats.getTotalMadeFreeThrow());
		teamStats.setTotalMadeTwoPoints(playerStats.getMakesTwoPoints() + teamStats.getTotalMadeTwoPoints());
		teamStats.setTotalMadeThreePoints(playerStats.getMakesThreePoints() + teamStats.getTotalMadeThreePoints());
		teamStats.setTotalMissedFreeThrow(playerStats.getMissesFreeThrow() + teamStats.getTotalMissedFreeThrow());
		teamStats.setTotalMissedTwoPoints(playerStats.getMissesTwoPoints() + teamStats.getTotalMissedTwoPoints());
		teamStats.setTotalMissedThreePoints(playerStats.getMissesThreePoints() + teamStats.getTotalMissedThreePoints());
		teamStats.setTotalTurnover(playerStats.getTurnover() + teamStats.getTotalTurnover());
		teamStats.setTotalSteal(playerStats.getSteal() + teamStats.getTotalSteal());
		System.out.println("TOTAL TIME IN PLAY :" + teamStats.getTotalTimeInPlay() + "    Time " + time  + "   Get Total Time " + teamStats.getTotalTimeInPlay());
		teamStats.setTotalTimeInPlay(time + teamStats.getTotalTimeInPlay());
		});
		teamStats.setTotalSumPoints(teamStats.getTotalMadeFreeThrow() + teamStats.getTotalMadeTwoPoints() + teamStats.getTotalMadeThreePoints());
		teamStats.setTotalSumRebound(teamStats.getTotalDefensiveRebound() + teamStats.getTotalOffensiveRebound());
		teamStats.setTotalRealizationFreeThrow((float) teamStats.getTotalMadeFreeThrow() / (teamStats.getTotalMissedFreeThrow() + teamStats.getTotalMadeFreeThrow()));
		teamStats.setTotalRealizationTwoPoints((float) (teamStats.getTotalMadeTwoPoints() + teamStats.getTotalMadeThreePoints()) / (teamStats.getTotalMissedTwoPoints() + teamStats.getTotalMadeTwoPoints() + teamStats.getTotalMissedThreePoints() + teamStats.getTotalMadeThreePoints()));
		teamStats.setTotalRealizationThreePoints((float) teamStats.getTotalMadeThreePoints() / (teamStats.getTotalMissedThreePoints() + teamStats.getTotalMadeThreePoints()));
		System.out.println("AFTER TOTAL TIME IN PLAY " + teamStats.getTotalTimeInPlay() / 60);
		teamStats.setTotalTimeInPlay(teamStats.getTotalTimeInPlay() / 60);
		/*try {
			TimeUnit.SECONDS.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
	}
	
	public void setTeamPlayerStatistics(String pbpURL, String matchBOX) {
		this.teamMatchParser.setInstanceJsoupDocument();
		this.teamMatchParser.getInstanceJsoupDocument().setDocument(pbpURL);
		List<Event> events = this.teamMatchParser.parseEvents();
		List<String> teamURL = this.teamMatchParser.getTeamURLFromPBP();
		this.quarterValues = quarterParser.getQuarterEvents(this.teamMatchParser, this.actionParser, events, matchBOX);
		Map<String, Integer> minutesPlayedTeamOne = this.minutesPlayed.getMinutesPlayedForEveryPlayer(events, quarterValues.getQuarterForTeamOne());
		Map<String, Integer> minutesPlayedTeamTwo = this.minutesPlayed.getMinutesPlayedForEveryPlayer(events, quarterValues.getQuarterForTeamTwo());
		setTeamPlayerValue(1, minutesPlayedTeamOne, quarterValues.getQuarterForTeamOne(), this.teamOneStats, pbpURL);
		setTeamPlayerValue(2, minutesPlayedTeamTwo, quarterValues.getQuarterForTeamTwo(), this.teamTwoStats, pbpURL);
		this.teamTwoStats.setGameUrl(pbpURL);
		this.teamOneStats.setGameUrl(pbpURL);
		this.teamTwoStats.setTeamUrl(teamURL.get(1));
		this.teamOneStats.setTeamUrl(teamURL.get(0));
		System.out.println(teamURL.get(1));
		System.out.println(teamURL.get(0));
	}
	
	public PlayerStatistics getPlayerStatistics(String playerID) {	
		return Iterables.getLast(this.quarterValues.getQuarterForTeamOne().entrySet()).getValue().containsKey(playerID) 
				? Iterables.getLast(this.quarterValues.getQuarterForTeamOne().entrySet()).getValue().get(playerID) 
				: Iterables.getLast(this.quarterValues.getQuarterForTeamTwo().entrySet()).getValue().get(playerID);
		
	}
	
	public TeamStatistics getTeamStatistics(boolean teamOne) {
		return teamOne ? this.teamOneStats : this.teamTwoStats;
	}
	
	public Map<String, Integer> getQuarterStatistics(Map<String, Map<String, PlayerStatistics>> team) {
		Map<String, Integer> pointsPerQuarter = new LinkedHashMap<String, Integer>();
		team.forEach((quarter, quarterValues) ->
		{
		pointsPerQuarter.put(quarter, 0);
		int sum = 0;
		for(Map.Entry<String, PlayerStatistics> entryPlayer: quarterValues.entrySet()) {
			PlayerStatistics playerStats = entryPlayer.getValue();
			sum += (playerStats.getMakesThreePoints() * 3) + (playerStats.getMakesTwoPoints() * 2) + playerStats.getMakesFreeThrow();
		}
		pointsPerQuarter.put(quarter, sum);
		});
		int lastSum = 0;
		for(Map.Entry<String, Integer> entryPlayer: pointsPerQuarter.entrySet()) {
			pointsPerQuarter.put(entryPlayer.getKey(), entryPlayer.getValue() - lastSum);
			lastSum += entryPlayer.getValue();
		}
		int[] total = {0};
		pointsPerQuarter.entrySet().forEach(entryPlayer -> { total[0] += entryPlayer.getValue(); });
		pointsPerQuarter.put("Total", total[0]);
		return pointsPerQuarter;
	}
	
	public void setLeaders() {
		Map<String, LinkedHashMap<String, Integer>> assitsPerQuarter = new LinkedHashMap<String, LinkedHashMap<String, Integer>>();
		Map<String, LinkedHashMap<String, Integer>> reboundPerQuarter = new LinkedHashMap<String, LinkedHashMap<String, Integer>>();
		Map<String, LinkedHashMap<String, Integer>> scoringPerQuarter = new LinkedHashMap<String, LinkedHashMap<String, Integer>>();
		Map<String, Map<String, PlayerStatistics>> quartersForBothTeams = new LinkedHashMap<String, Map<String, PlayerStatistics>>();
		quartersForBothTeams.putAll(this.quarterValues.getQuarterForTeamOne());
		for (Map.Entry<String, Map<String, PlayerStatistics>> entryForBothTeams : quartersForBothTeams.entrySet()) {
			  String quarter = entryForBothTeams.getKey();
			  Map<String, PlayerStatistics> playersTeamOne = entryForBothTeams.getValue();
			  Map<String, PlayerStatistics> playersTeamTwo = this.quarterValues.getQuarterForTeamTwo().get(quarter);  
		}
		String lastQuarter = "";
		for (Map.Entry<String, Map<String, PlayerStatistics>> entryForBothTeams : quartersForBothTeams.entrySet()) {
			 String quarter = entryForBothTeams.getKey(); 
			 scoringPerQuarter.put(quarter, new LinkedHashMap<>());
			 assitsPerQuarter.put(quarter, new LinkedHashMap<>());
			 reboundPerQuarter.put(quarter, new LinkedHashMap<>());
			 Map<String, PlayerStatistics> allPlayers = entryForBothTeams.getValue();
			 for(Map.Entry<String, PlayerStatistics> playerMap: allPlayers.entrySet()) {
				 String playerID = playerMap.getKey();
				 PlayerStatistics playerStats = playerMap.getValue();
				 if("1 QT".contentEquals(quarter)) { 
					 reboundPerQuarter.get(quarter).put(playerID, (playerStats.getDefensiveRebound() + playerStats.getOffensiveRebound()));
					 assitsPerQuarter.get(quarter).put(playerID, playerStats.getAssist());
					 scoringPerQuarter.get(quarter).put(playerID, (playerStats.getMakesThreePoints() * 3 + playerStats.getMakesTwoPoints() * 2 + playerStats.getMakesFreeThrow()));
				 }
				 else {
					 PlayerStatistics lastQuarterPlayerStats = quartersForBothTeams.get(lastQuarter).get(playerID);
					 assitsPerQuarter.get(quarter).put(playerID, (playerStats.getAssist() - lastQuarterPlayerStats.getAssist()));
					 reboundPerQuarter.get(quarter).put(playerID, ((playerStats.getDefensiveRebound() + playerStats.getOffensiveRebound()) - (lastQuarterPlayerStats.getDefensiveRebound() + lastQuarterPlayerStats.getOffensiveRebound())));
					 scoringPerQuarter.get(quarter).put(playerID, ((playerStats.getMakesThreePoints() * 3 + playerStats.getMakesTwoPoints() * 2 + playerStats.getMakesFreeThrow()) - (lastQuarterPlayerStats.getMakesThreePoints() * 3 + lastQuarterPlayerStats.getMakesTwoPoints() * 2 + lastQuarterPlayerStats.getMakesFreeThrow())));
				 }
			 }
			 lastQuarter = quarter; 
		}
		
		for (Map.Entry<String, LinkedHashMap<String, Integer>> entryForBothTeams : scoringPerQuarter.entrySet()) {
			String quarter = entryForBothTeams.getKey();
			Map<String, Integer> sorted = entryForBothTeams.getValue()
			        .entrySet()
			        .stream()
			        .sorted(comparingByValue())
			        .collect(
			        		toMap(e -> e.getKey(), e -> e.getValue(), (e1, e2) -> e2,
			                LinkedHashMap::new));
			int counter = sorted.size();
			int value = 0;
			for(Map.Entry<String, Integer> playerSorted : sorted.entrySet()) {
				counter--;
				if (counter == 0) {
					value = playerSorted.getValue();
					int freq = Collections.frequency(sorted.values(), value);
					if(freq > 1)  {
						Map.Entry<String,Integer> playerSortedFreq =
							    new AbstractMap.SimpleEntry<String, Integer>(freq + " tied", playerSorted.getValue());
						this.scoring.add(playerSortedFreq);
					}
					else {
						this.scoring.add(playerSorted);
					}
				}	
			}
		}
		for (Map.Entry<String, LinkedHashMap<String, Integer>> entryForBothTeams : assitsPerQuarter.entrySet()) {
			String quarter = entryForBothTeams.getKey();
			Map<String, Integer> sorted = entryForBothTeams.getValue()
			        .entrySet()
			        .stream()
			        .sorted(comparingByValue())
			        .collect(
			        		toMap(e -> e.getKey(), e -> e.getValue(), (e1, e2) -> e2,
			                LinkedHashMap::new));
			int counter = sorted.size();
			int value = 0;
			for(Map.Entry<String, Integer> playerSorted : sorted.entrySet()) {
				counter--;
				if (counter == 0) {
					value = playerSorted.getValue();
					int freq = Collections.frequency(sorted.values(), value);
					if(freq > 1)  {
						Map.Entry<String,Integer> playerSortedFreq =
							    new AbstractMap.SimpleEntry<String, Integer>(freq + " tied", playerSorted.getValue());
						this.assists.add(playerSortedFreq);
					}
					else {
						this.assists.add(playerSorted);
					}
				}	
			}
		}
		for (Map.Entry<String, LinkedHashMap<String, Integer>> entryForBothTeams : reboundPerQuarter.entrySet()) {
			String quarter = entryForBothTeams.getKey();
			Map<String, Integer> sorted = entryForBothTeams.getValue()
			        .entrySet()
			        .stream()
			        .sorted(comparingByValue())
			        .collect(
			        		toMap(e -> e.getKey(), e -> e.getValue(), (e1, e2) -> e2,
			                LinkedHashMap::new));
			int counter = sorted.size();
			int value = 0;
			for(Map.Entry<String, Integer> playerSorted : sorted.entrySet()) {
				counter--;
				if (counter == 0) {
					value = playerSorted.getValue();
					int freq = Collections.frequency(sorted.values(), value);
					if(freq > 1)  {
						Map.Entry<String,Integer> playerSortedFreq =
							    new AbstractMap.SimpleEntry<String, Integer>(freq + " tied", playerSorted.getValue());
						this.rebound.add(playerSortedFreq);
					}
					else {
						this.rebound.add(playerSorted);
					}
				}	
			}
			
		}
		int lastTotalAssist = 0;
		String playerAssist = "";
		int lastTotalRebound = 0;
		String playerRebound = "";
		int lastTotalScore = 0;
		String playerScore = "";
		for(Map.Entry<String, PlayerStatistics> playerStats : quartersForBothTeams.get(lastQuarter).entrySet()) {
			int totalAssist = playerStats.getValue().getAssist();
			int totalRebound = playerStats.getValue().getSumRebound();
			int totalScore = playerStats.getValue().getSumMadePoints();
			if(totalAssist > lastTotalAssist) {
				lastTotalAssist = totalAssist;
				playerAssist = playerStats.getKey();
			}
			if(totalRebound > lastTotalRebound) {
				lastTotalRebound = totalRebound;
				playerRebound = playerStats.getKey();
			}
			if(totalScore > lastTotalScore) {
				lastTotalScore = totalScore;
				playerScore = playerStats.getKey();
			}
		}
		this.assists.add(new AbstractMap.SimpleEntry<String, Integer>(playerAssist, lastTotalAssist));
		this.scoring.add(new AbstractMap.SimpleEntry<String, Integer>(playerScore, lastTotalScore));
		this.rebound.add(new AbstractMap.SimpleEntry<String, Integer>(playerRebound, lastTotalRebound));
	}
	
	public ArrayList<Map.Entry<String,Integer>> getLeadersInScoring() {
		return this.scoring;
		
	}
	
	public ArrayList<Map.Entry<String,Integer>> getLeadersInRebounding() {
		return this.rebound;
		
	}
	
	public ArrayList<Map.Entry<String,Integer>> getLeadersInAssists() {
		return this.assists;
		
	}
	
}

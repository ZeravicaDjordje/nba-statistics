package com.mozzartbet.gameservice.parser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.mozzartbet.gameservice.domain.Event;
import com.mozzartbet.gameservice.domain.Quarter;
import com.mozzartbet.gameservice.domain.stats.PlayerStatistics;
import com.mozzartbet.gameservice.exception.ScoreException;
import com.mozzartbet.gameservice.util.MinutesPlayed;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.io.*; 

@Slf4j
@Data
@Getter
@Setter
public class ActionParser extends GameParser{
	
	// Advance statistics
	
	Map<Integer, Integer> gameTiedDiff = new HashMap<>();
	String previousScore = ""; 
	int tied = 0;
	int leadChangeFlag = 0;
	int leadChange = 0;
	String quarter = "";
	int timeGameTied = 720;
	int previousScoreOne = 0;
	int previousScoreTwo = 0;
	int beggingSeconds = 0;
	int teamOneLead = 0;
	int teamTwoLead = 0;
	int beggingSecondsTeamOne = 0;
	
	public int getScoreOne(int scoreType, Event event) throws ScoreException {
		String[] score = event.getScoreSoFar().split("-");
		int scoreOne = Integer.valueOf(score[0]);
		int scoreTwo = Integer.valueOf(score[1]);
		switch(scoreType) {
			case 1:
				return scoreOne;
			case 2:
				return scoreTwo;
		}
		throw new ScoreException("Score Type Exception");
	}
	
	public void ties(Event event) throws ScoreException  {
		if(getScoreOne(1, event) == getScoreOne(2, event) && !previousScore.equals(event.getScoreSoFar())) {
			this.tied += 1;
			log.info("Tied ", tied);
		}
	}
	
	public void leadChanges(Event event) throws ScoreException {
		if(getScoreOne(1, event) > getScoreOne(2, event) && !previousScore.equals(event.getScoreSoFar()) && this.leadChangeFlag != 1) {
			log.debug("Lead changed " + event.getScoreSoFar());
			this.leadChangeFlag = 1;
			this.teamOneLead += 1;
			this.leadChange += 1;
		}
		else if(getScoreOne(1, event) < getScoreOne(2, event) && !previousScore.equals(event.getScoreSoFar()) && this.leadChangeFlag != 2) {
			log.debug("Lead changed " + event.getScoreSoFar());
			this.leadChangeFlag = 2;
			this.teamTwoLead += 1;
			this.leadChange += 1;
		}
	}
	
	public void gameTied(Event event) throws ScoreException {
		int scoreOne = getScoreOne(1, event);
		int scoreTwo = getScoreOne(2, event);
		
		
		if(scoreOne == scoreTwo && previousScoreOne != previousScoreTwo ) {
			this.beggingSeconds = MinutesPlayed.timeSplit(event.getTimeStamp());		
			System.out.println(	
			"Score so far " 
			+ event.getScoreSoFar() 
			+ " " + event.getTimeStamp()
			+ " Begging seconds " + this.beggingSeconds		
			);
		}
			
		
		if(previousScoreOne == previousScoreTwo && scoreOne != scoreTwo) {
			int seconds = MinutesPlayed.timeSplit(event.getTimeStamp());
			System.out.println(
			"Score so far " 
			+ event.getScoreSoFar() 
			+ " " + event.getTimeStamp()
			+ " seconds" + seconds		
			);
			if(this.beggingSeconds == 0) {
				gameTiedDiff.put(720, seconds);
			}
			else {
				gameTiedDiff.put(this.beggingSeconds, seconds);
			}
		}
		previousScoreOne = scoreOne;
		previousScoreTwo = scoreTwo;
		
	}
	
	public void teamOneLed(Event event) throws ScoreException {
		int scoreOne = getScoreOne(1, event);
		int scoreTwo = getScoreOne(2, event);
		
		
		if(scoreOne > scoreTwo && previousScoreOne >= previousScoreTwo) {
			this.beggingSecondsTeamOne = MinutesPlayed.timeSplit(event.getTimeStamp());		
			System.out.println(	
			"Score so far " 
			+ event.getScoreSoFar() 
			+ " " + event.getTimeStamp()
			+ " Begging seconds " + this.beggingSeconds		
			);
		}
			
		/*
		if(previousScoreOne == previousScoreTwo && scoreOne != scoreTwo) {
			int seconds = MinutesPlayed.timeSplit(event.getTimeStamp());
			System.out.println(
			"Score so far " 
			+ event.getScoreSoFar() 
			+ " " + event.getTimeStamp()
			+ " seconds" + seconds		
			);
			if(this.beggingSeconds == 0) {
				gameTiedDiff.put(720, seconds);
			}
			else {
				gameTiedDiff.put(this.beggingSeconds, seconds);
			}
		}*/
		previousScoreOne = scoreOne;
		previousScoreTwo = scoreTwo;
		
	}
	public void teamScoreS(Event event) throws ScoreException {
		int scoreOne = getScoreOne(1, event);
		int scoreTwo = getScoreOne(2, event);
		if()
	}
	
	
	// End of Advance Stats
	
	public String[] timeSplit(String time) {
		return  time.split(":");
		//String minutes = minutesSeconds[0];
		//String seconds = minutesSeconds[1];
	}
	
	public void actionParser(Event action, Map<String, PlayerStatistics> teamOne, Map<String, PlayerStatistics> teamTwo, Map<String, Map<String, PlayerStatistics>> quarterForTeamOne, Map<String, Map<String, PlayerStatistics>> quarterForTeamTwo ) {

		Elements links = action.eventToJsoupElement().select("a");
		String text = action.getEvent();
		switch(links.size()) {
		case 0:
			//System.out.println("case 0");
			searchForAction(action, action.getEvent(), "", teamOne, teamTwo, quarterForTeamOne, quarterForTeamTwo);
			break;
		case 1:
			//System.out.println("case 1");
			String onlyLink = links.get(0).attr("href");
			searchForAction(action, action.getEvent(), onlyLink, teamOne, teamTwo, quarterForTeamOne, quarterForTeamTwo);
			break;
		case 2:
			//System.out.println("case 2");
			String firstLink = links.get(0).attr("href");
			String secondLink = links.get(1).attr("href");
			String newText = searchForAction(action, text, firstLink, teamOne, teamTwo, quarterForTeamOne, quarterForTeamTwo);
			searchForAction(action, newText, secondLink, teamOne, teamTwo, quarterForTeamOne, quarterForTeamTwo);
			break;
		}

	}
			
	public void playerEnterGame(String link, Event event, Map<String, PlayerStatistics> team) {
		//.containsKey(event.getQuarter()));
		System.out.println(team.get(link).getEnterGame());//.containsKey(event.getQuarter()));
		if(team.get(link).getEnterGame().containsKey(event.getQuarter())) {
			team.get(link).getEnterGame().get(event.getQuarter()).add(event.getTimeStamp());
			
		}			
		else {
			List<String> data = new ArrayList<>();
			data.add(event.getTimeStamp());
			team.get(link).getEnterGame().put(event.getQuarter(), data);
		}
		
	}
	public void updateQuarter(Map<String, PlayerStatistics> team, Event event, Map<String, Map<String, PlayerStatistics>> quarter) throws CloneNotSupportedException {
		Map<String, PlayerStatistics> teamClone = new LinkedHashMap<String, PlayerStatistics>();
		for(Map.Entry<String, PlayerStatistics> entry: team.entrySet()) {
			PlayerStatistics tempValue = (PlayerStatistics) entry.getValue().clone();
			teamClone.put(entry.getKey(), tempValue);
		}
		quarter.put(event.getQuarter(), teamClone);
	}
	
	public void playerLeaveGame(String link, Event event, Map<String, PlayerStatistics> team) {
		
		if(team.get(link).getLeaveGame().containsKey(event.getQuarter())) {
			team.get(link).getLeaveGame().get(event.getQuarter()).add(event.getTimeStamp());
					
		}			
		else {
			List<String> data = new ArrayList<>();
			data.add(event.getTimeStamp());
			team.get(link).getLeaveGame().put(event.getQuarter(), data);
			}
				
		}
	
	
	public String searchForAction(Event event, String action , String link, Map<String, PlayerStatistics> teamOne, Map<String, PlayerStatistics> teamTwo, Map<String, Map<String, PlayerStatistics>> quarterForTeamOne, Map<String, Map<String, PlayerStatistics>> quarterForTeamTwo) {
		
		try {
			updateQuarter(teamOne, event, quarterForTeamOne);
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
		try {
			updateQuarter(teamTwo, event, quarterForTeamTwo);
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
		
		if(action.contains("makes 3-pt")) {
			action = action.replace("makes 3-pt", "");
			if(teamOne.containsKey(link)) {
				teamOne.get(link).setMakesThreePoints(teamOne.get(link).getMakesThreePoints() + 1);
			}
			if(teamTwo.containsKey(link)) {
				teamTwo.get(link).setMakesThreePoints(teamTwo.get(link).getMakesThreePoints() + 1);
			}
		}
		
		else if(action.contains("makes 2-pt")) {
			action = action.replace("makes 2-pt", "");
			if(teamOne.containsKey(link)) {
				teamOne.get(link).setMakesTwoPoints(teamOne.get(link).getMakesTwoPoints() + 1);
			}
			if(teamTwo.containsKey(link)) {
				teamTwo.get(link).setMakesTwoPoints(teamTwo.get(link).getMakesTwoPoints() + 1);
			}	

		}

		else if(action.contains("Shooting foul")) {
			action = action.replace("Shooting foul", "");
			if(teamOne.containsKey(link)) {
				teamOne.get(link).setFoul(teamOne.get(link).getFoul() + 1);
			}
			if(teamTwo.containsKey(link)) {
				teamTwo.get(link).setFoul(teamTwo.get(link).getFoul() + 1);
			}	
			
		}

		/*else if(action.contains("Technical foul")) {
			action = action.replace("Technical foul", "");
			if(teamOne.containsKey(link)) {
				teamOne.get(link).setFoul(teamOne.get(link).getFoul() + 1);
			}
			if(teamTwo.containsKey(link)) {
				teamTwo.get(link).setFoul(teamTwo.get(link).getFoul() + 1);
			}	
		}*/


		else if(action.contains("Offensive foul")) {
			action = action.replace("Offensive foul", "");
			if(teamOne.containsKey(link)) {
				teamOne.get(link).setFoul(teamOne.get(link).getFoul() + 1);
			}
			if(teamTwo.containsKey(link)) {
				teamTwo.get(link).setFoul(teamTwo.get(link).getFoul() + 1);
			}	
		}
		
		else if(action.contains("misses 2-pt")) {
			action = action.replace("misses 2-pt", "");
			if(teamOne.containsKey(link)) {
				teamOne.get(link).setMissesTwoPoints(teamOne.get(link).getMissesTwoPoints() + 1);
			}
			if(teamTwo.containsKey(link)) {
				teamTwo.get(link).setMissesTwoPoints(teamTwo.get(link).getMissesTwoPoints() + 1);
			}	
		}

		else if(action.contains("misses 3-pt")) {
			action = action.replace("misses 3-pt", "");
			if(teamOne.containsKey(link)) {
				teamOne.get(link).setMissesThreePoints(teamOne.get(link).getMissesThreePoints() + 1);
			}
			if(teamTwo.containsKey(link)) {
				teamTwo.get(link).setMissesThreePoints(teamTwo.get(link).getMissesThreePoints() + 1);
			}	
		}
		
		else if(action.contains("Defensive rebound")) {
			action = action.replace("Defensive rebound", "");
			if(teamOne.containsKey(link)) {
				teamOne.get(link).setDefensiveRebound(teamOne.get(link).getDefensiveRebound() + 1);
			}
			if(teamTwo.containsKey(link)) {
				teamTwo.get(link).setDefensiveRebound(teamTwo.get(link).getDefensiveRebound() + 1);
			}	
		}		
		else if(action.contains("Offensive rebound")) {
			action = action.replace("Offensive rebound", "");
			if(teamOne.containsKey(link)) {
				teamOne.get(link).setOffensiveRebound(teamOne.get(link).getOffensiveRebound() + 1);
			}
			if(teamTwo.containsKey(link)) {
				teamTwo.get(link).setOffensiveRebound(teamTwo.get(link).getOffensiveRebound() + 1);
			}	

		}

		else if(action.contains("Turnover")) {
			action = action.replace("Turnover", "");
			if(teamOne.containsKey(link)) {
				teamOne.get(link).setTurnover(teamOne.get(link).getTurnover() + 1);
			}
			if(teamTwo.containsKey(link)) {
				teamTwo.get(link).setTurnover(teamTwo.get(link).getTurnover() + 1);
			}	
		}
		
		else if(action.contains("makes technical free throw")) {
			action = action.replace("makes technical free throw", "");
			if(teamOne.containsKey(link)) {
				teamOne.get(link).setMakesFreeThrow(teamOne.get(link).getMakesFreeThrow() + 1);
			}
			if(teamTwo.containsKey(link)) {
				teamTwo.get(link).setMakesFreeThrow(teamTwo.get(link).getMakesFreeThrow() + 1);
			}	
		}
		
		else if(action.contains("misses technical free throw")) {
			action = action.replace("misses technical free throw", "");
			if(teamOne.containsKey(link)) {
				teamOne.get(link).setMissesFreeThrow(teamOne.get(link).getMissesFreeThrow() + 1);
			}
			if(teamTwo.containsKey(link)) {
				teamTwo.get(link).setMissesFreeThrow(teamTwo.get(link).getMissesFreeThrow() + 1);
			}	
		}
		
		else if(action.contains("makes free throw")) {
			action = action.replace("makes free throw", "");
			if(teamOne.containsKey(link)) {
				teamOne.get(link).setMakesFreeThrow(teamOne.get(link).getMakesFreeThrow() + 1);
			}
			if(teamTwo.containsKey(link)) {
				teamTwo.get(link).setMakesFreeThrow(teamTwo.get(link).getMakesFreeThrow() + 1);
			}	
		}
		
		else if(action.contains("misses free throw")) {
			action = action.replace("misses free throw", "");
			if(teamOne.containsKey(link)) {
				teamOne.get(link).setMissesFreeThrow(teamOne.get(link).getMissesFreeThrow() + 1);
			}
			if(teamTwo.containsKey(link)) {
				teamTwo.get(link).setMissesFreeThrow(teamTwo.get(link).getMissesFreeThrow() + 1);
			}	
		}	
		else if(action.contains("Personal foul")) {
			action = action.replace("Personal foul", "");
			if(teamOne.containsKey(link)) {
				teamOne.get(link).setFoul(teamOne.get(link).getFoul() + 1);
			}
			if(teamTwo.containsKey(link)) {
				teamTwo.get(link).setFoul(teamTwo.get(link).getFoul() + 1);
			}	

		}
		
		else if(action.contains("steal")) {
			action = action.replace("steal", "");
			if(teamOne.containsKey(link)) {
				teamOne.get(link).setSteal(teamOne.get(link).getSteal() + 1);
			}
			if(teamTwo.containsKey(link)) {
				teamTwo.get(link).setSteal(teamTwo.get(link).getSteal() + 1);
			}	
		}
		
		else if(action.contains("assist by")) {
			action = action.replace("assist by", "");
			if(teamOne.containsKey(link)) {
				teamOne.get(link).setAssist(teamOne.get(link).getAssist() + 1);
			}
			if(teamTwo.containsKey(link)) {
				teamTwo.get(link).setAssist(teamTwo.get(link).getAssist() + 1);
			}	
		}
		
		else if(action.contains("Loose ball foul")) {
			action = action.replace("Loose ball foul", "");
			if(teamOne.containsKey(link)) {
				teamOne.get(link).setFoul(teamOne.get(link).getFoul() + 1);
			}
			if(teamTwo.containsKey(link)) {
				teamTwo.get(link).setFoul(teamTwo.get(link).getFoul() + 1);
			}	
		}
				
		else if(action.contains("block by")) {
			action = action.replace("block by", "");
			if(teamOne.containsKey(link)) {
				teamOne.get(link).setBlock(teamOne.get(link).getBlock() + 1);
			}
			if(teamTwo.containsKey(link)) {
				teamTwo.get(link).setBlock(teamTwo.get(link).getBlock() + 1);
			}	
		}
		else if(action.contains("Personal take foul")) {
			action = action.replace("Personal take foul", "");
			if(teamOne.containsKey(link)) {
				teamOne.get(link).setFoul(teamOne.get(link).getFoul() + 1);
			}
			if(teamTwo.containsKey(link)) {
				teamTwo.get(link).setFoul(teamTwo.get(link).getFoul() + 1);
			}	
		}
		if(action.contains("enters the game")) {
			action = action.replace("enters the game", "");
			if(teamOne.containsKey(link)) {
				System.out.println(link + " " +  event + " " + teamOne);
				playerEnterGame(link, event, teamOne);
			}
			if(teamTwo.containsKey(link)) {
				playerEnterGame(link, event, teamTwo);
			}	
			
		}
		else if(action.contains(" for ")) {
			action = action.replace(" for ", "");
			if(teamOne.containsKey(link)) {
				playerLeaveGame(link, event, teamOne);
			}
			if(teamTwo.containsKey(link)) {
				playerLeaveGame(link, event, teamTwo);
			}	
			//System.out.println("Leavs the game" + "===================== " + link + " " + action);
		}
		/*else if(action.contains("(drawn by ")) {
			action = action.replace("(drawn by ", "");
			if(teamOne.containsKey(link)) {
				teamOne.get(link).setMakesThreePoints(teamOne.get(link).getMakesThreePoints() + 1);
			}
			if(teamTwo.containsKey(link)) {
				teamTwo.get(link).setMakesThreePoints(teamOne.get(link).getMakesThreePoints() + 1);
			}
			//System.out.println("Drawn By" + "===================== " + link + " " + action);
		}
		else if(action.contains("full timeout")) {
			action = action.replace("full timeout", "");
			if(teamOne.containsKey(link)) {
				teamOne.get(link).setMakesThreePoints(teamOne.get(link).getMakesThreePoints() + 1);
			}
			if(teamTwo.containsKey(link)) {
				teamTwo.get(link).setMakesThreePoints(teamOne.get(link).getMakesThreePoints() + 1);
			}
			//System.out.println("Drawn By" + "===================== " + link + " " + action);
		}
		
		else if(action.contains("Violation by Team")) {
			action = action.replace("Violation by Team", "");
			if(teamOne.containsKey(link)) {
				teamOne.get(link).setMakesThreePoints(teamOne.get(link).getMakesThreePoints() + 1);
			}
			if(teamTwo.containsKey(link)) {
				teamTwo.get(link).setMakesThreePoints(teamOne.get(link).getMakesThreePoints() + 1);
			}	
			//System.out.println("Drawn By" + "===================== " + link + " " + action);
		}
		*/
		/*else {
			System.out.println("Nothing Above" + "+++++++++++++++++++++++++++++++++++++++++++++++++++" + action);
		}*/
		return action;
	}
	
	public void p(Map<String,Map<String, PlayerStatistics>> quarter, Event event, Map<String, PlayerStatistics> team) {
		if(quarter.containsKey(event.getQuarter())) {
			quarter.replace(event.getQuarter(), team);
		}
		else {
			quarter.put(event.getQuarter(), team);
		}
	}
	// Map<String, List<String>>
	public Quarter parseMatchValues(List<Event> match, Map<String,PlayerStatistics> teamOne, Map<String, PlayerStatistics> teamTwo) {
		Quarter quarter = new Quarter();
		for(Event action : match) {
			if(action.getTeamOneAction().length() != 1) {
				
				actionParser(action, teamOne, teamTwo, quarter.getQuarterForTeamOne(), quarter.getQuarterForTeamTwo());
			}
			else {
				actionParser(action, teamOne, teamTwo, quarter.getQuarterForTeamOne(), quarter.getQuarterForTeamTwo());
			}
		}	
		return quarter;
	}
}

package com.mozzartbet.gameservice.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.mozzartbet.gameservice.domain.Event;
import com.mozzartbet.gameservice.domain.stats.PlayerStatistics;
import com.mozzartbet.gameservice.parser.ActionParser;
import com.mozzartbet.gameservice.parser.QuarterParser;
import com.mozzartbet.gameservice.parser.TeamMatchParser;

public class MinutesPlayed {

	public static int timeSplit(String time) {
		String[] minutesSeconds = time.split(":");
		int sum = 0;
		int minutes = Integer.parseInt(minutesSeconds[0]);
		int seconds = Integer.parseInt(minutesSeconds[1].replace(".0",""));
		for(int x = 0; x < minutes; x++) {
			sum += 60;
		}
		return seconds + sum;
	}
	
	public Map<String, Integer> getMinutesPlayedForEveryPlayer(List<Event> events, Map<String, Map<String, PlayerStatistics>> quarters) {
		Map<String, Integer> minutesPlayed = new HashMap<String, Integer>();
		Map<String, Integer> playerSum = new HashMap<String, Integer>();
		int fullTime = 12 * 60;
		for(Map.Entry<String, Map<String, PlayerStatistics>> entryQuarter: quarters.entrySet()) {
			String quarter = entryQuarter.getKey();
			if(quarter.contains("OT")) {
				fullTime = 5 * 60;
			}
			for(Map.Entry<String, PlayerStatistics> entryPlayer: entryQuarter.getValue().entrySet()) {
				String playerID = entryPlayer.getKey();
				if(!playerSum.containsKey(playerID)) {
					playerSum.put(playerID,  0);
				}	
				if(!minutesPlayed.containsKey(playerID)) {
					minutesPlayed.put(playerID, 0);
				}
				PlayerStatistics playerStats = entryPlayer.getValue();
				//playerStats.setEnterGame(new HashMap<>());
				//playerStats.setLeaveGame(new HashMap<>());
				boolean enterContainsQuarter = playerStats.getEnterGame().containsKey(quarter);
				boolean leaveContainsQuarter = playerStats.getLeaveGame().containsKey(quarter);	
				int timeInPlay = 0;
				int sumOfQuarterPlay = 
						playerStats.getAssist() + playerStats.getBlock() + playerStats.getDefensiveRebound() + 
						playerStats.getFoul() + playerStats.getMakesFreeThrow() + playerStats.getMakesThreePoints() +
						playerStats.getMakesTwoPoints() + playerStats.getMissesFreeThrow() +
						playerStats.getMissesThreePoints() + playerStats.getMissesTwoPoints() + playerStats.getOffensiveRebound() +
						playerStats.getSteal() + playerStats.getTurnover();
				
				if(!enterContainsQuarter && !leaveContainsQuarter) {
					if(sumOfQuarterPlay - playerSum.get(playerID) == 0) {
						minutesPlayed.put(playerID, minutesPlayed.get(playerID) + 0);
						playerSum.put(playerID, sumOfQuarterPlay);
						continue;
					}
					else {
						minutesPlayed.put(playerID, minutesPlayed.get(playerID) + fullTime);
						playerSum.put(playerID, sumOfQuarterPlay);
						continue;
					}
				}
				
				else if(enterContainsQuarter && leaveContainsQuarter) {
					int sizeLeave = playerStats.getLeaveGame().get(quarter).size();
					int sizeEnter = playerStats.getEnterGame().get(quarter).size();	
					if(sizeLeave > sizeEnter) {
						for(int x=0; x < sizeLeave; x++) {
							int leaveTime = MinutesPlayed.timeSplit(playerStats.getLeaveGame().get(quarter).get(x));
							if(x==0) {
								timeInPlay += fullTime - leaveTime;
							}
							else {
								timeInPlay += MinutesPlayed.timeSplit(playerStats.getEnterGame().get(quarter).get(x - 1)) - leaveTime;
							}
						}
					}
					else if(sizeEnter > sizeLeave) {
						for(int x=0; x < sizeEnter; x++) {
							int enterTime = MinutesPlayed.timeSplit(playerStats.getEnterGame().get(quarter).get(x));
							if (x == sizeEnter - 1) {
								timeInPlay += enterTime;
							}
							else {
								timeInPlay += enterTime - MinutesPlayed.timeSplit(playerStats.getLeaveGame().get(quarter).get(x));
							}
						}
					}
					else {
						int enterTime = MinutesPlayed.timeSplit(playerStats.getEnterGame().get(quarter).get(0));
						int leaveTime = MinutesPlayed.timeSplit(playerStats.getLeaveGame().get(quarter).get(0));
						if (enterTime > leaveTime) {
							timeInPlay += enterTime - leaveTime;
							for(int x = 1; x < sizeEnter; x++) {
								enterTime = MinutesPlayed.timeSplit(playerStats.getEnterGame().get(quarter).get(x));
								leaveTime = MinutesPlayed.timeSplit(playerStats.getLeaveGame().get(quarter).get(x));
								timeInPlay += enterTime - leaveTime;
							}
						}
						else {
							int lastIndex = 0;
							timeInPlay += fullTime - leaveTime;
							if(sizeEnter == 1) {
								timeInPlay += enterTime;
								minutesPlayed.put(playerID, minutesPlayed.get(playerID) + timeInPlay);
								playerSum.put(playerID, sumOfQuarterPlay);
								continue;
							}
							for(int x = 1; x < sizeEnter; x++) {
								enterTime = MinutesPlayed.timeSplit(playerStats.getEnterGame().get(quarter).get(x-1));
								leaveTime = MinutesPlayed.timeSplit(playerStats.getLeaveGame().get(quarter).get(x));
								timeInPlay += enterTime - leaveTime;
								lastIndex = x;
							}
							timeInPlay += MinutesPlayed.timeSplit(playerStats.getEnterGame().get(quarter).get(lastIndex));
							minutesPlayed.put(playerID, minutesPlayed.get(playerID) + timeInPlay);
							playerSum.put(playerID, sumOfQuarterPlay);
							continue;
						}
					}
					minutesPlayed.put(playerID, minutesPlayed.get(playerID) + timeInPlay);
				}
				
				else if(enterContainsQuarter)  {
					String time = playerStats.getEnterGame().get(quarter).get(0);
					timeInPlay = MinutesPlayed.timeSplit(time); 
					minutesPlayed.put(playerID, minutesPlayed.get(playerID) + timeInPlay);
					playerSum.put(playerID, sumOfQuarterPlay);
					continue;
				}	
				
				else if(leaveContainsQuarter) {
					String time = playerStats.getLeaveGame().get(quarter).get(0);
					timeInPlay = fullTime - MinutesPlayed.timeSplit(time); 
					minutesPlayed.put(playerID, minutesPlayed.get(playerID) + timeInPlay);
					playerSum.put(playerID, sumOfQuarterPlay);
					continue;
				}
				playerSum.put(playerID, sumOfQuarterPlay);
			}
		}
		return minutesPlayed;
	}
}

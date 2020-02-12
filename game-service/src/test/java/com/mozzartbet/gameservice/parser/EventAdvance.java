package com.mozzartbet.gameservice.parser;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.mozzartbet.gameservice.domain.Event;
import com.mozzartbet.gameservice.exception.ScoreException;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class EventAdvance {
	
	ActionParser actionParser;

	@Test
	public void tiedTest() {
		actionParser = new ActionParser();
		actionParser.setInstanceJsoupDocument();
		actionParser.getInstanceJsoupDocument().setDocument("https://www.basketball-reference.com/boxscores/pbp/201906130GSW.html");
		actionParser.parseEvents("https://www.basketball-reference.com/boxscores/pbp/201906130GSW.html").forEach(event -> 
		{ 
			try {
				actionParser.ties(event);
			} catch (ScoreException e) {
				log.debug(e.toString(), e);
			} 
			actionParser.setPreviousScore(event.getScoreSoFar());
		});
	}
	
	@Test
	public void leadChangesTest() {
		actionParser = new ActionParser();
		actionParser.setInstanceJsoupDocument();
		actionParser.getInstanceJsoupDocument().setDocument("https://www.basketball-reference.com/boxscores/pbp/201906130GSW.html");
		actionParser.parseEvents("https://www.basketball-reference.com/boxscores/pbp/201906130GSW.html").forEach(event -> 
		{ 
			try {
				actionParser.leadChanges(event);
			} catch (ScoreException e) {
				log.debug(e.toString(), e);
			} 
			actionParser.setPreviousScore(event.getScoreSoFar());
		});
		
		log.debug("Lead changed " + actionParser.getLeadChange(), actionParser.getLeadChange());
		log.debug("Team One Lead " + actionParser.getTeamOneLead(), actionParser.getTeamOneLead());
		log.debug("Team Two Lead " + actionParser.getTeamTwoLead(), actionParser.getTeamTwoLead());
		
	}
	
	@Test
	public void gameTiedTest() {
		actionParser = new ActionParser();
		actionParser.setInstanceJsoupDocument();
		actionParser.getInstanceJsoupDocument().setDocument("https://www.basketball-reference.com/boxscores/pbp/201906130GSW.html");
		actionParser.parseEvents("https://www.basketball-reference.com/boxscores/pbp/201906130GSW.html").forEach(event -> 
		{ 
			try {
				actionParser.gameTied(event);
			} catch (ScoreException e) {
				log.debug(e.toString(), e);
			} 
			actionParser.setPreviousScore(event.getScoreSoFar());
		});
		int[] time = {0};
		actionParser.getGameTiedDiff().forEach((first, second) -> time[0] += first - second);
		System.out.println(time[0]);
		//log.debug("Game Tied" , actionParser.getLeadChange());
	}
}

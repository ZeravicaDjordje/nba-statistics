package com.mozzartbet.gameservice.parser;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.mozzartbet.gameservice.domain.Event;

import com.mozzartbet.gameservice.parser.GameParser;

public class GameServiceParserTestOneMatch {
	
	@Test
	public void checkIfMatchReturnsPlayByPlayObjects() {
		GameParser teamParser = new GameParser();
		teamParser.setInstanceJsoupDocument();
		teamParser.getInstanceJsoupDocument().setDocument("https://www.basketball-reference.com/boxscores/pbp/201905250TOR.html");
		for(Event playByPlay : teamParser.parseEvents()) {
			assertEquals(Event.class, playByPlay.getClass());
		}
	}
}

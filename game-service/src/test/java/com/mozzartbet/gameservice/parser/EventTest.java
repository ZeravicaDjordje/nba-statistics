package com.mozzartbet.gameservice.parser;

import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.mozzartbet.gameservice.domain.Event;

public class EventTest {

	GameParser gameParser;

	@Test
	public void eventInstanceOfTest() {
		gameParser = new GameParser();
		gameParser.setInstanceJsoupDocument();
		gameParser.getInstanceJsoupDocument().setDocument("https://www.basketball-reference.com/boxscores/pbp/201906130GSW.html");
		gameParser.parseEvents("https://www.basketball-reference.com/boxscores/pbp/201906130GSW.html").forEach(event -> assertTrue(event instanceof Event));
		
	}
	
	@Test
	public void dateEventTest() {
		gameParser = new GameParser();
		gameParser.setInstanceJsoupDocument();
		gameParser.getInstanceJsoupDocument().setDocument("https://www.basketball-reference.com/boxscores/pbp/201906130GSW.html");
		gameParser.parseEvents("https://www.basketball-reference.com/boxscores/pbp/201906130GSW.html").forEach(event -> System.out.println(event.getGameDate()));		
	}
}

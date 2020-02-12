package com.mozzartbet.gameservice.parser;

import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Test;

import com.mozzartbet.gameservice.domain.Event;

public class TeamURLTest {

	@Test
	public void teamURLTest() {
		GameParser gameParser = new GameParser();
		gameParser.setInstanceJsoupDocument();
		gameParser.getInstanceJsoupDocument().setDocument("https://www.basketball-reference.com/boxscores/pbp/201905250TOR.html");
		List<String> urls = gameParser.getTeamURLFromPBP();
		assertTrue(urls.size() == 2);
		assertTrue(urls.get(0).contains(".html"));
		assertTrue(urls.get(1).contains(".html"));
	}
}

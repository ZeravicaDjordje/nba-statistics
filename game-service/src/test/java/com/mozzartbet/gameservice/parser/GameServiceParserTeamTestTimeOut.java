package com.mozzartbet.gameservice.parser;

import org.jsoup.HttpStatusException;
import org.junit.Test;

public class GameServiceParserTeamTestTimeOut {

	@Test(timeout=3000)
	public void checkTimeOutException() {
		GameParser teamParser = new GameParser();
		teamParser.setInstanceJsoupDocument();
		teamParser.getInstanceJsoupDocument().setDocument("https://httpstat.us/524");
	}
}

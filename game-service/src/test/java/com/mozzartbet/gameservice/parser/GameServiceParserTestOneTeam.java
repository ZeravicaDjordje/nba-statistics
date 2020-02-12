package com.mozzartbet.gameservice.parser;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import com.mozzartbet.gameservice.domain.Player;

public class GameServiceParserTestOneTeam {
		
	
	@Test
	public void checkIfParserReturnsPlayerObjects() {
		GameParser teamParser = new GameParser();
		teamParser.setInstanceJsoupDocument();
		teamParser.getInstanceJsoupDocument().setFile("D:\\24May\\game-service\\src\\test\\resources\\roster.html");
		teamParser.getInstanceJsoupDocument().setDocument();
		System.out.println(teamParser.getInstanceJsoupDocument().getDocument());
		List<Player> players = teamParser.parsePlayersPage();
		for(Player player : players) {
			assertEquals(Player.class, player.getClass());
		}
	
	}
	
	@Test
	public void checkIfParserReturnsPlayerUnifromNumber() {
		GameParser teamParser = new GameParser();
		teamParser.setInstanceJsoupDocument();
		teamParser.getInstanceJsoupDocument().setFile("D:\\24May\\game-service\\src\\test\\resources\\roster.html");
		teamParser.getInstanceJsoupDocument().setDocument();
		for(Player player : teamParser.parsePlayersPage()) {
			assertTrue(!player.getUniformNumber().isEmpty());
		}
		System.out.println("Every player has a birth date");
	}
	
	//@Test
	public void checkIfParserReturnsPlayerCollege() {
		GameParser teamParser = new GameParser();
		teamParser.setInstanceJsoupDocument();
		teamParser.getInstanceJsoupDocument().setFile("D:\\24May\\game-service\\src\\test\\resources\\roster.html");
		teamParser.getInstanceJsoupDocument().setDocument();
		for(Player player : teamParser.parsePlayersPage()) {
			assertTrue(!player.getCollege().isEmpty());
		}
		System.out.println("Every player has a college");
	}
	
	@Test
	public void checkIfParserReturnsPlayerHeight() {
		GameParser teamParser = new GameParser();
		teamParser.setInstanceJsoupDocument();
		teamParser.getInstanceJsoupDocument().setFile("D:\\24May\\game-service\\src\\test\\resources\\roster.html");
		teamParser.getInstanceJsoupDocument().setDocument();
		for(Player player : teamParser.parsePlayersPage()) {
			assertTrue(!player.getHeight().isEmpty());
		}
		System.out.println("Every player has height");
	}
	
	@Test
	public void checkIfParserReturnsPlayerPlayerName() {
		GameParser teamParser = new GameParser();
		teamParser.setInstanceJsoupDocument();
		teamParser.getInstanceJsoupDocument().setFile("D:\\24May\\game-service\\src\\test\\resources\\roster.html");
		teamParser.getInstanceJsoupDocument().setDocument();
		for(Player player : teamParser.parsePlayersPage()) {
			assertTrue(!player.getPlayerName().isEmpty());
		}
		System.out.println("Every player has a Name");
	}
	
	@Test
	public void checkIfParserReturnsPlayerPosition() {
		GameParser teamParser = new GameParser();
		teamParser.setInstanceJsoupDocument();
		teamParser.getInstanceJsoupDocument().setFile("D:\\24May\\game-service\\src\\test\\resources\\roster.html");
		teamParser.getInstanceJsoupDocument().setDocument();
		for(Player player : teamParser.parsePlayersPage()) {
			assertTrue(!player.getPosition().isEmpty());
		}
		System.out.println("Every player has position");
	}
	
	@Test
	public void checkIfParserReturnsPlayerWidth() {
		GameParser teamParser = new GameParser();
		teamParser.setInstanceJsoupDocument();
		teamParser.getInstanceJsoupDocument().setFile("D:\\24May\\game-service\\src\\test\\resources\\roster.html");
		teamParser.getInstanceJsoupDocument().setDocument();
		for(Player player : teamParser.parsePlayersPage()) {
			assertTrue(!player.getWidth().isEmpty());
		}
		System.out.println("Every player has a width");
	}
	
	@Test
	public void checkIfParserReturnsPlayerYearsExperience() {
		GameParser teamParser = new GameParser();
		teamParser.setInstanceJsoupDocument();
		teamParser.getInstanceJsoupDocument().setFile("D:\\24May\\game-service\\src\\test\\resources\\roster.html");
		teamParser.getInstanceJsoupDocument().setDocument();
		for(Player player : teamParser.parsePlayersPage()) {
			assertTrue(!player.getYearsOfExp().isEmpty());
		}
		System.out.println("Every player has years of experience");
	}
	
	@Test
	public void checkIfParserReturnAllPlayers() {
		GameParser teamParser = new GameParser();
		teamParser.setInstanceJsoupDocument();
		teamParser.getInstanceJsoupDocument().setFile("D:\\24May\\game-service\\src\\test\\resources\\roster.html");
		teamParser.getInstanceJsoupDocument().setDocument();
		assertEquals(16, teamParser.parsePlayersPage().size());
		System.out.println("All players are returned");
	}
	
}

package com.mozzartbet.gameservice.parser;

import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Test;

public class SeasonUrlTest {
	
	@Test
	public void seasonURLTest() {
		TeamMatchParser teamParser = new TeamMatchParser("2016","2","1");	
		teamParser.setInstanceJsoupDocument();
		System.out.println(teamParser.getSeasonUrl());
		
	}
}
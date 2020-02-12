package com.mozzartbet.gameservice.parser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.mozzartbet.gameservice.domain.*;

public class TeamMatchParser extends GameParser {

	private String month;
	private String day;
	private String year;
	
	public TeamMatchParser() {
		;
	}
	
	public TeamMatchParser(String year, String month, String day) {
		this.month = month;
		this.day = day;
		this.year = year;
	}
	
	public void searchTeamPerSeason() {
		getInstanceJsoupDocument().setDocument("https://www.basketball-reference.com/leagues/NBA_" + this.year + ".html");
		for(String url : getTeamsURL()) {
			getInstanceJsoupDocument().setDocument(url);
			parsePlayersPage();
		}
	}
	

	public Map<String, String> serachAllMatchesPerSeason() {
		Map<String, String>  seasonUrls = new LinkedHashMap<String, String>() ;
		getInstanceJsoupDocument().setDocument("https://www.basketball-reference.com/boxscores/?month="+this.month + "&day=" + this.day + "&year=" + this.year);
		while(true) {
			String dateUrl = parseNextDate();
			System.out.println(dateUrl);
			System.out.println("CONTAINS YEAR " + dateUrl.toLowerCase().contains(String.valueOf(Integer.valueOf(this.year) + 1)));
			System.out.println("CONTAINS MONTH " + dateUrl.toLowerCase().contains("month=" + String.valueOf(Integer.valueOf(this.month))));
			if(dateUrl.toLowerCase().contains(String.valueOf(Integer.valueOf(this.year) + 1)) && dateUrl.toLowerCase().contains("month=" + String.valueOf(Integer.valueOf(this.month))) ) break;
			Map<String, String> allMatchesURL = getAllMatchesURL();
			getInstanceJsoupDocument().setDocument("https://www.basketball-reference.com" + dateUrl);
			if(allMatchesURL.size() == 0 ) {
				continue;
			}
			seasonUrls.putAll(allMatchesURL);
		}
		return seasonUrls;
	}
	
	public Map<String, String> serachAllMatchesPerSeason(String year, String month, String day) {
		Map<String, String>  seasonUrls = new LinkedHashMap<String, String>() ;
		getInstanceJsoupDocument().setDocument("https://www.basketball-reference.com/boxscores/?month="+this.month + "&day=" + this.day + "&year=" + this.year);
		while(true) {
			String dateUrl = parseNextDate();
			System.out.println(dateUrl);
			if(dateUrl.contains("&year=".concat(year)) && dateUrl.contains("month=".concat(month)) && dateUrl.contains("&day=".concat(day))) break;
			Map<String, String> allMatchesURL = getAllMatchesURL();
			getInstanceJsoupDocument().setDocument("https://www.basketball-reference.com" + dateUrl);
			if(allMatchesURL.size() == 0 ) {
				continue;
			}
			seasonUrls.putAll(allMatchesURL);
		}
		return seasonUrls;
	}
	
	public String getSeasonUrl() {
		getInstanceJsoupDocument().setDocument("https://www.basketball-reference.com/leagues/");
		Document document = getInstanceJsoupDocument().getDocument();
		Elements seasons = document.select("[id=stats] > tbody > tr > th");
		List<String> season = new ArrayList<>();
		IntStream.range(12, seasons.size()).forEach(index -> { if(seasons.get(index).text().contains(this.year)) season.add(seasons.get(index).select("a").attr("href"));});
		return "https://www.basketball-reference.com" + season.get(0);
	}
	
	public Map<String, String> getAllTeamsForSeason(String seasonUrl) {
		Map<String, String> teams = new HashMap<>();
		getInstanceJsoupDocument().setDocument(seasonUrl);
		Document document = getInstanceJsoupDocument().getDocument();
		Elements eastConf = document.select("table[id=divs_standings_E] > tbody > tr[class=full_table] > th");
		Elements westConf = document.select("table[id=divs_standings_W] > tbody > tr[class=full_table] > th");
		eastConf.forEach(team -> teams.put("https://www.basketball-reference.com" + team.select("a").attr("href"), team.select("a").text()));
		westConf.forEach(team -> teams.put("https://www.basketball-reference.com" + team.select("a").attr("href"), team.select("a").text()));
		return teams;
	}
}

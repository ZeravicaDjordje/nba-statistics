package com.mozzartbet.gameservice.parser;

import com.mozzartbet.gameservice.domain.*;
import com.mozzartbet.gameservice.domain.stats.PlayerStatistics;
import com.mozzartbet.gameservice.util.MinutesPlayed;
import com.mozzartbet.gameservice.util.SaveDateFormat;
import com.mozzartbet.gameservice.util.SaveSeconds;
import com.mozzartbet.gameservice.util.TurnDateIntoTimeStamp;
import com.mozzartbet.gameservice.util.jsoup.JsoupDocument;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class GameParser {	
	
	private JsoupDocument jsoupDocument;
	
	public JsoupDocument getInstanceJsoupDocument() {
		return jsoupDocument;
	}
	
	public void setInstanceJsoupDocument() {
		this.jsoupDocument = new JsoupDocument();
	}
	
	public List<String> getTeams() {
		List<String> urls = new ArrayList();
		Document document = getInstanceJsoupDocument().getDocument();
		String teamOne = document.select("[class=scorebox] > a").get(0).attr("href");
		String teamTwo = document.select("[class=scorebox] > a").get(1).attr("href");
		urls.add(teamOne);
		urls.add(teamTwo);
		return urls;
	}
	
	public List<Event> parseEvents() {
		List<Event> collectionOfPlayByPlay = new ArrayList<Event>(); 
		String quarter = "1 QT";
		Document document = getInstanceJsoupDocument().getDocument();
		Elements teamLink = document.select("[class=scorebox] ").select("strong");
		String teamOneLink = teamLink.get(0).select("a").attr("href");
		String teamTwoLink = teamLink.get(1).select("a").attr("href");
		Elements matchTable = document.select("table");
		Elements matchTableBody = matchTable.select("tbody");
		Elements matchTableThead = matchTableBody.select("tr[class=thead]");
		Elements matchTableHead = matchTableThead.get(1).select("th");
		Elements matchTableRows = matchTableBody.select("tr[id=q1] ~ tr");
		Element teamOneElement = matchTableHead.get(1);
		Element teamTwoElement = matchTableHead.get(5);
		Pattern re = Pattern.compile("q\\d");
		for(Element matchTableRow: matchTableRows) {
			if(re.matcher(matchTableRow.id()).find()) {
				quarter = matchTableRow.text();
			}
			Elements matchRowCell = matchTableRow.select("td");
			int sizeOfRowCell = matchRowCell.size();
			switch(sizeOfRowCell) 
			{
			case 0:
				continue;
			case 2:
				if(matchRowCell.toString().contains("Jump ball:")) {
					//System.out.println(matchRowCell.text());
					;
				}
				continue;
			case 6:
				String teamOne = teamOneElement.text();
				String teamTwo = teamTwoElement.text();
				String timestamp = matchRowCell.get(0).text();
				String teamOneAction = matchRowCell.get(1).text();
				String teamOneScore = matchRowCell.get(2).text();
				String scoreSoFar = matchRowCell.get(3).text();
				String teamTwoScore = matchRowCell.get(4).text();
				String teamTwoAction = matchRowCell.get(5).text();
				String gameUrl = document.baseUri();
				collectionOfPlayByPlay.add(Event.builder().
						gameUrl(gameUrl).
						quarter(quarter).
						timeStamp(timestamp).
						teamOneScore(teamOneScore).
						teamTwoScore(teamTwoScore).
						teamOneAction(teamOneAction).
						teamTwoAction(teamTwoAction).
						scoreSoFar(scoreSoFar).
						teamOne(teamOne).
						teamTwo(teamTwo).
						teamOneLink(teamOneLink).
						teamTwoLink(teamTwoLink).
						event(matchRowCell.toString()).build());
				continue;
			}
		}
		return collectionOfPlayByPlay;
	}
	
	public Map<String, PlayerStatistics> getMapFromTeam(int team) {
		Map<String, PlayerStatistics> playerIdStats = new HashMap<String, PlayerStatistics>();
		Document document = getInstanceJsoupDocument().getDocument();
		Elements playersTableBody = document.select("table[id^=box_]");
		Elements players = playersTableBody.get(team).select("tbody > tr > th > a");
		for(Element player : players) {
			playerIdStats.put(player.attr("href"), new PlayerStatistics());
		}
		return playerIdStats;
	}
	
	
	public List<Event> parseEvents(String gameUrl) {
		List<Event> collectionOfPlayByPlay = new ArrayList<Event>(); 
		String quarter = "1 QT";
		getInstanceJsoupDocument().setDocument(gameUrl);
		Document document = getInstanceJsoupDocument().getDocument();
		String gameDate = document.select("[class=scorebox_meta] > div").get(0).text();
		Elements teamLink = document.select("[class=scorebox] ").select("strong");
		String teamOneLink = teamLink.get(0).select("a").attr("href");
		String teamTwoLink = teamLink.get(1).select("a").attr("href");
		Elements matchTable = document.select("table");
		Elements matchTableBody = matchTable.select("tbody");
		Elements matchTableThead = matchTableBody.select("tr[class=thead]");
		Elements matchTableHead = matchTableThead.get(1).select("th");
		Elements matchTableRows = matchTableBody.select("tr[id=q1] ~ tr");
		Element teamOneElement = matchTableHead.get(1);
		Element teamTwoElement = matchTableHead.get(5);
		Pattern re = Pattern.compile("q\\d");
	    SimpleDateFormat sdf = new SimpleDateFormat("MM/d/yyyy HH:mm");
	    SaveDateFormat saveDate = new SaveDateFormat();
	    SaveSeconds saveSeconds = new SaveSeconds();
	    saveDate.setDateFormatGame("");
	    saveSeconds.setPreviousSeconds(720);
	    for(Element matchTableRow: matchTableRows) {
			if(re.matcher(matchTableRow.id()).find()) {
				quarter = matchTableRow.text();
			    saveSeconds.setPreviousSeconds(720);
			}
			Elements matchRowCell = matchTableRow.select("td");
			int sizeOfRowCell = matchRowCell.size();
			switch(sizeOfRowCell) 
			{
			case 0:
				continue;
			case 2:
				if(matchRowCell.toString().contains("Jump ball:")) {
					//System.out.println(matchRowCell.text());
					;
				}
				continue;
			case 6:
				String teamOne = teamOneElement.text();
				String teamTwo = teamTwoElement.text();
				String timestamp = matchRowCell.get(0).text();
				String teamOneAction = matchRowCell.get(1).text();
				String teamOneScore = matchRowCell.get(2).text();
				String scoreSoFar = matchRowCell.get(3).text();
				String teamTwoScore = matchRowCell.get(4).text();
				String teamTwoAction = matchRowCell.get(5).text();
				TurnDateIntoTimeStamp.clockTimeEvent(sdf, gameDate, timestamp, quarter, saveDate, saveSeconds);
				collectionOfPlayByPlay.add(Event.builder().
						gameDate(new Timestamp(sdf.getCalendar().getTime().getTime()).getTime()).
						gameUrl(gameUrl).
						quarter(quarter).
						timeStamp(timestamp).
						teamOneScore(teamOneScore).
						teamTwoScore(teamTwoScore).
						teamOneAction(teamOneAction).
						teamTwoAction(teamTwoAction).
						scoreSoFar(scoreSoFar).
						teamOne(teamOne).
						teamTwo(teamTwo).
						teamOneLink(teamOneLink).
						teamTwoLink(teamTwoLink).
						event(matchRowCell.toString()).build());
				continue;
			}
		}
		return collectionOfPlayByPlay;
	}
	
	public List<Player> parsePlayersPage() {
		List<Player> playersInTeam = new ArrayList<Player>();
		Document document = getInstanceJsoupDocument().getDocument();
		Elements playersTableBody = document.select("tbody");
		Elements playersTableRow = playersTableBody.select("tr");
		Elements team = document.select("[itemprop=name] > span");
		for(Element element : playersTableRow) {
			Elements birthDate = element.select("td[data-stat=birth_date]");
			Elements position = element.select("td[data-stat=pos]");
			Elements playerName = element.select("td[data-stat=player]");
			Elements height = element.select("td[data-stat=height]");
			Elements width = element.select("td[data-stat=weight]");
			Elements yearsOfExp = element.select("td[data-stat=years_experience]");
			Elements college = element.select("td[data-stat=college]");
			Elements uniformNumber = element.select("th[data-stat=number]");
			playersInTeam.add(Player.builder().
					teamName(team.get(1).text()).
					team(Team.builder().name(team.get(1).text()).build()).
					playerName(playerName.text()).
					playerUrl(playerName.select("a").attr("href")).
					uniformNumber(uniformNumber.get(0).text()).
					playerName(playerName.get(0).text()).
					position(position.get(0).text()).
					height(height.get(0).text()).
					width(width.get(0).text()).
					birthDate(birthDate.get(0).text()).
					yearsOfExp(yearsOfExp.get(0).text()).
					college(college.get(0).text()).build());
		}
		return playersInTeam;
		
	}
	
	public List<String> parseAllSeasons() {
		List<String> seasonsUrls = new ArrayList<String>();
		Document document = getInstanceJsoupDocument().getDocument();
		Elements seasons = document.select("[id=stats] > tbody > tr > [data-stat=season] > a");
		for(Element season : seasons) {
			String seasonUrl = "https://www.basketball-reference.com" + season.attr("href");
			seasonsUrls.add(seasonUrl);
		}
		return seasonsUrls;
	}
	
	public List<String> getTeamsURL() {
		Document document = getInstanceJsoupDocument().getDocument();
		List<String> teamUrls = new ArrayList<String>();
		Element divisionStanding = document.getElementById("divs_standings_E");
		Elements teams = divisionStanding.select("[data-stat=team_name] > a");
		for(Element team : teams) {
			String teamUrl = "https://www.basketball-reference.com".concat(team.attr("href"));
			teamUrls.add(teamUrl);
		}
		return teamUrls;
	}
	
	public List<String> getTeamURLFromPBP() {
		Document document = getInstanceJsoupDocument().getDocument();
		List<String> teamUrls = new ArrayList<>();
		document.select("strong").forEach(element -> element.getElementsByAttribute("href").forEach(url -> teamUrls.add("https://www.basketball-reference.com".concat(url.attr("href")))));
		return teamUrls;
	}
	public String parseNextDate() {
		Document document = getInstanceJsoupDocument().getDocument();	
		Elements nextDate = document.select("[class=prevnext] > [class=button2 next]");
		return nextDate.attr("href");
		
	}
	
	
	public Map<String,String> getAllMatchesURL() {
		Map<String, String> matchUrls = new LinkedHashMap<String, String>();
		Document document = getInstanceJsoupDocument().getDocument();
		Elements boxLinks = document.select("[class=game_summary expanded nohover]");
		for(Element boxLink : boxLinks) {
			Elements matchPBP = boxLink.select("p[class=links] > a[href*=pbp]");
			Elements matchBOX = boxLink.select("p[class=links] > a[href*=boxscores]");
			matchUrls.put("https://www.basketball-reference.com".concat(matchPBP.attr("href")), "https://www.basketball-reference.com".concat(matchBOX.attr("href")));
		}
		return matchUrls;
	}
	
	public void setValuesPerQuarter(String primaryKey, String seconderyKey, Integer value, Map<String, Map<String, Integer>> structureData) {
		if(structureData.containsKey(primaryKey)) {
			if(structureData.get(primaryKey).containsKey(seconderyKey)) {
				structureData.get(primaryKey).put(seconderyKey, value);
			}
		}
	}

}


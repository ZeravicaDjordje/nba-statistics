package com.mozzartbet.gameservice.mapper;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Map;
import java.util.Map.Entry;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Iterables;
import com.mozzartbet.gameservice.domain.AssistLeaders;
import com.mozzartbet.gameservice.domain.Game;
import com.mozzartbet.gameservice.domain.Player;
import com.mozzartbet.gameservice.domain.Quarter;
import com.mozzartbet.gameservice.domain.QuarterValue;
import com.mozzartbet.gameservice.domain.ReboundLeaders;
import com.mozzartbet.gameservice.domain.ScoreLeaders;
import com.mozzartbet.gameservice.domain.Team;
import com.mozzartbet.gameservice.domain.stats.PlayerStatistics;
import com.mozzartbet.gameservice.domain.stats.Statistics;
import com.mozzartbet.gameservice.domain.stats.TeamStatistics;
import com.mozzartbet.gameservice.parser.ActionParser;
import com.mozzartbet.gameservice.parser.QuarterParser;
import com.mozzartbet.gameservice.parser.TeamMatchParser;
import com.mozzartbet.gameservice.util.MinutesPlayed;

@RunWith(SpringRunner.class)
@SpringBootTest
//@ActiveProfiles("test")
//@Transactional
public class SeasonMapperTest {

	Statistics statistics;
	
	TeamMatchParser teamMatch;

	@Autowired
	private EventMapper eventMapper;
	
	@Autowired
	private PlayerMapper playerMapper;
	
	@Autowired
	private GameMapper gameMapper;
	
	@Autowired
	private QuarterValueMapper quarterValueMapper;
	
	@Autowired
	private TeamMapper teamMapper;
	
	@Autowired
	private TeamStatisticsMapper teamStatisticsMapper;
	
	@Autowired
	private ReboundLeadersMapper reboundLeadersMapper;
	
	@Autowired
	private AssistLeadersMapper assistLeadersMapper;
	
	@Autowired
	private ScoreLeadersMapper scoreLeadersMapper;
	
	@Autowired
	private PlayerStatisticsMapper playerStatisticsMapper;
	
	@Test
	public void testSeason() {
		this.teamMatch = new TeamMatchParser("2018","12","31");
		this.teamMatch.setInstanceJsoupDocument();
		String seasonUrl = this.teamMatch.getSeasonUrl();
		Map<String, String> teams = this.teamMatch.getAllTeamsForSeason(seasonUrl);
		teams.forEach((teamUrl, team) -> 
		{
			Team t = Team.builder().name(team).teamUrl(teamUrl).build();
			teamMapper.insert(t);
		}
		);
		this.teamMatch.getAllTeamsForSeason(seasonUrl).forEach((teamUrl, team) -> 
		{ 
			this.teamMatch.getInstanceJsoupDocument().setDocument(teamUrl); 
			this.teamMatch.parsePlayersPage().forEach( player -> 
			{
				System.out.println(player);
				playerMapper.insert(player);
			});
		});
		System.out.println(playerMapper.getById(Long.valueOf(1)));
		System.out.println(teamMapper.getById(Long.valueOf(1)));
		Map<String, String> allMatches = teamMatch.serachAllMatchesPerSeason("2019","1","31");		
		System.out.println(allMatches.entrySet());
		for(Map.Entry<String, String> matchUrls : allMatches.entrySet()) {
			String pbp = matchUrls.getKey();
			String box = matchUrls.getValue();
			gameMapper.insert(Game.builder().gameUrl(pbp).boxUrl(box).build());
			teamMatch.parseEvents(pbp).forEach(event -> {
			eventMapper.insert(event);
			System.out.println(event.getGameDate());
			});
			this.statistics = 
					new Statistics(
					new TeamMatchParser(), 
					new ActionParser(), 
					new QuarterParser(), 
					new MinutesPlayed(),
					new Quarter(),
					new TeamStatistics(),
					new TeamStatistics(),
					new ArrayList<Map.Entry<String, Integer>>(),
					new ArrayList<Map.Entry<String, Integer>>(),
					new ArrayList<Map.Entry<String, Integer>>());
			System.out.println(pbp + " " + box);
			this.statistics.setTeamPlayerStatistics(
					pbp,
					box);
			this.statistics.getQuarterStatistics(this.statistics.getQuarterValues().getQuarterForTeamOne()).forEach((key, value) -> {
				quarterValueMapper.insert(QuarterValue.builder().teamUrl(this.statistics.getTeamStatistics(false).getTeamUrl()).gameUrl(pbp).quarter(key).score(Long.valueOf(value)).build());
			});
			this.statistics.getQuarterStatistics(this.statistics.getQuarterValues().getQuarterForTeamTwo()).forEach((key, value) -> {
				quarterValueMapper.insert(QuarterValue.builder().teamUrl(this.statistics.getTeamStatistics(false).getTeamUrl()).gameUrl(pbp).quarter(key).score(Long.valueOf(value)).build());
			});
			this.statistics.setLeaders();
			teamStatisticsMapper.insert(this.statistics.getTeamStatistics(false));
			teamStatisticsMapper.insert(this.statistics.getTeamStatistics(true));
			this.statistics.getLeadersInRebounding().forEach(quarter -> reboundLeadersMapper.insert(
					ReboundLeaders.
					builder().
					score(Long.valueOf(quarter.getValue())).
					player(quarter.getKey()).
					gameUrl(pbp).build()));
			this.statistics.getLeadersInScoring().forEach(quarter -> scoreLeadersMapper.insert(
					ScoreLeaders.
					builder().
					score(Long.valueOf(quarter.getValue())).
					player(quarter.getKey()).
					gameUrl(pbp).build()));
			this.statistics.getLeadersInAssists().forEach(quarter -> assistLeadersMapper.insert(
					AssistLeaders.
					builder().
					score(Long.valueOf(quarter.getValue())).
					player(quarter.getKey()).
					gameUrl(pbp).build()));
			for(Map.Entry<String,Map<String,PlayerStatistics>> quarter :  this.statistics.getQuarterValues().getQuarterForTeamOne().entrySet()) {
				quarter.getValue().entrySet().forEach(player -> { playerStatisticsMapper.insert(this.statistics.getPlayerStatistics(player.getKey()));});
				break;
			}
			for(Map.Entry<String,Map<String,PlayerStatistics>> quarter :  this.statistics.getQuarterValues().getQuarterForTeamTwo().entrySet()) {
				quarter.getValue().entrySet().forEach(player -> { playerStatisticsMapper.insert(this.statistics.getPlayerStatistics(player.getKey()));});
				break;
			}
		}
	}
}

package com.mozzartbet.gameservice.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mozzartbet.gameservice.domain.Game;
import com.mozzartbet.gameservice.mapper.AssistLeadersMapper;
import com.mozzartbet.gameservice.mapper.GameMapper;
import com.mozzartbet.gameservice.mapper.PlayerStatisticsMapper;
import com.mozzartbet.gameservice.mapper.QuarterValueMapper;
import com.mozzartbet.gameservice.mapper.ReboundLeadersMapper;
import com.mozzartbet.gameservice.mapper.ScoreLeadersMapper;
import com.mozzartbet.gameservice.mapper.TeamStatisticsMapper;
import com.mozzartbet.gameservice.service.GameService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class GameServiceImpl implements GameService {


	@Autowired
	final GameMapper gameMapper;
	
	@Autowired
	final AssistLeadersMapper assitLeadersMapper;
	
	@Autowired
	final ScoreLeadersMapper scoreLeadersMapper;
	
	@Autowired
	final ReboundLeadersMapper reboundLeadersMapper;
	
	@Autowired
	final PlayerStatisticsMapper playerStatsMapper;
	
	@Autowired
	final TeamStatisticsMapper teamStatsMapper;
	
	@Autowired
	final QuarterValueMapper quarterValueMapper;
	
	public Game getGame(Long id) {
		Game game = gameMapper.getById(id);
		return game;
	}
	
	public Game findGameByUrl(String url, Long gameId) {
		Game game = gameMapper.findGameByUrl(url, gameId);
		return game;
	}
	
	public Game save(Game game) {
		game.getAssistLeaders().forEach(assist -> assitLeadersMapper.save(assist));
		game.getScoreLeaders().forEach(score -> scoreLeadersMapper.save(score));
		game.getReboundLeaders().forEach(rebound -> reboundLeadersMapper.save(rebound));
		game.getPlayerStats().forEach(playerStats -> playerStatsMapper.save(playerStats));
		game.getTeamStats().forEach(teamStats -> teamStatsMapper.save(teamStats));
		gameMapper.save(game);
		return game;
	}
		
}	

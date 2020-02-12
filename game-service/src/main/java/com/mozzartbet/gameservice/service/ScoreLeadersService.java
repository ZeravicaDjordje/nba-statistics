package com.mozzartbet.gameservice.service;

import java.util.List;


import com.mozzartbet.gameservice.domain.ScoreLeaders;

public interface ScoreLeadersService {

	public List<ScoreLeaders> serachByPlayerUrl(String playerUrl);
	
	public ScoreLeaders save(ScoreLeaders scoreLeaders);
	
	public List<ScoreLeaders> searchByGameUrl(String gameUrl);
	
	public ScoreLeaders getScoreLeadersCached(Long id);
	
	public ScoreLeaders getScoreLeaders(Long id);
}

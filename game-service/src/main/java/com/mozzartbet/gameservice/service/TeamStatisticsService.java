package com.mozzartbet.gameservice.service;

import java.util.List;
import java.util.Map;

import com.mozzartbet.gameservice.domain.stats.TeamStatistics;

public interface TeamStatisticsService {

	public List<TeamStatistics> findTeamStatsByName(String teamName, Long teamId);
	
	public TeamStatistics save(TeamStatistics team);
	
	public Map<Long, TeamStatistics> getGameTeamStatistics(Long gameId);
	
	public TeamStatistics getTeamStatisticsCached(Long teamStatsId);
	
	public TeamStatistics getTeamStatistics(Long teamStatsId);

}

package com.mozzartbet.gameservice.service.impl;

import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.mozzartbet.gameservice.domain.stats.TeamStatistics;
import com.mozzartbet.gameservice.mapper.TeamStatisticsMapper;
import com.mozzartbet.gameservice.service.TeamStatisticsService;

public class TeamStatisticsServiceImpl implements TeamStatisticsService {

	@Autowired
	TeamStatisticsMapper teamStatsMapper;
	
	
	public List<TeamStatistics> findTeamStatsByName(String teamName, Long gameId) {
		List<TeamStatistics> teamStats = teamStatsMapper.findTeamStatsByName(teamName, gameId);
		return teamStats;
	}
	
	public TeamStatistics save(TeamStatistics teamStats) {
		teamStatsMapper.save(teamStats);
		return teamStats;
	}
	
	public Map<Long, TeamStatistics> getGameTeamStatistics(Long gameId) {
		Map<Long, TeamStatistics> gameTeamStats = teamStatsMapper.getGameTeamStatistics(gameId);
		return gameTeamStats;
	}
	
	final LoadingCache<Long, TeamStatistics> teamStatsCache = CacheBuilder.newBuilder().
			initialCapacity(100).
			maximumSize(1000).
			expireAfterWrite(3, TimeUnit.SECONDS).
			recordStats().
			build(new CacheLoader<Long, TeamStatistics>() {
				@Override
				public TeamStatistics load(Long id) throws Exception {
					return teamStatsMapper.getById(id);
				}
			});
	
	public TeamStatistics getTeamStatisticsCached(Long teamStatsId) {
		TeamStatistics teamStats = teamStatsMapper.getById(teamStatsId);
		return teamStats;
	}
	
	public TeamStatistics getTeamStatistics(Long teamStatsId) {
		return teamStatsCache.getUnchecked(teamStatsId);
	}
}

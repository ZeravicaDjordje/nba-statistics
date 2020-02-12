package com.mozzartbet.gameservice.service.impl;

import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.mozzartbet.gameservice.domain.stats.PlayerStatistics;
import com.mozzartbet.gameservice.mapper.PlayerStatisticsMapper;
import com.mozzartbet.gameservice.service.PlayerStatisticsService;

@Service
public class PlayerStatisticsServiceImpl implements PlayerStatisticsService {

	@Autowired
	PlayerStatisticsMapper playerStatsMapper;
	
	
	public List<PlayerStatistics> findPlayersStatsByName(String playerName, Long gameId) {
		List<PlayerStatistics> playerStats = playerStatsMapper.findPlayersStatsByName(playerName, gameId);
		return playerStats;
	}
	
	public PlayerStatistics save(PlayerStatistics playerStats) {
		playerStatsMapper.save(playerStats);
		return playerStats;
	}
	
	public Map<Long, PlayerStatistics> getGamePlayersStatistics(Long gameId) {
		Map<Long, PlayerStatistics> gamePlayerStats = playerStatsMapper.getGamePlayersStatistics(gameId);
		return gamePlayerStats;
	}
	
	final LoadingCache<Long, PlayerStatistics> playerStatsCache = CacheBuilder.newBuilder().
			initialCapacity(100).
			maximumSize(1000).
			expireAfterWrite(3, TimeUnit.SECONDS).
			recordStats().
			build(new CacheLoader<Long, PlayerStatistics>() {
				@Override
				public PlayerStatistics load(Long id) throws Exception {
					return playerStatsMapper.getById(id);
				}
			});
	
	public PlayerStatistics getPlayerStatisticsCached(Long playerStatsId) {
		PlayerStatistics playerStats = playerStatsMapper.getById(playerStatsId);
		return playerStats;
	}
	
	public PlayerStatistics getPlayerStatistics(Long playerStatsId) {
		return playerStatsCache.getUnchecked(playerStatsId);
	}

}

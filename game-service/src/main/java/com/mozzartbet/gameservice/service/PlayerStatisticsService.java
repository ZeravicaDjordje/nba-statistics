package com.mozzartbet.gameservice.service;

import java.util.List;
import java.util.Map;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.apache.ibatis.annotations.Param;

import com.mozzartbet.gameservice.domain.Player;
import com.mozzartbet.gameservice.domain.stats.PlayerStatistics;
import com.mozzartbet.gameservice.service.dto.PlayerSearchRequest;
import com.mozzartbet.gameservice.service.dto.PlayerSearchResponse;

public interface PlayerStatisticsService {

	public List<PlayerStatistics> findPlayersStatsByName(String playerName, Long teamId);
	
	public PlayerStatistics save(PlayerStatistics player);
	
	public Map<Long, PlayerStatistics> getGamePlayersStatistics(Long gameId);
	
	public PlayerStatistics getPlayerStatisticsCached(Long playerStatsId);
	
	public PlayerStatistics getPlayerStatistics(Long playerStatsId);

}

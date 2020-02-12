package com.mozzartbet.gameservice.mapper;

import java.util.List;
import java.util.Map;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.mozzartbet.gameservice.domain.Player;
import com.mozzartbet.gameservice.domain.stats.PlayerStatistics;
import com.mozzartbet.gameservice.service.dto.PlayerSearchRequest;
import com.mozzartbet.gameservice.service.dto.PlayerSearchResponse;

@Mapper
public interface PlayerStatisticsMapper extends BaseMapper<PlayerStatistics>{
	
	public long count();
	
	public PlayerStatistics getById(Long id);
	
	public int insert(PlayerStatistics entity);
	
	public int insertWhitOutId(PlayerStatistics entity);
		
	public int update(PlayerStatistics entity);

	public int deleteById(Long id);

	public List<PlayerStatistics> findPlayersStatsByName(String playerName, Long gameId);
	
	public @NotNull @Valid PlayerSearchResponse searchPlayers(@NotNull @Valid @Param("request") PlayerSearchRequest request);
	
	public PlayerStatistics save(PlayerStatistics player);
	
	public Map<Long, PlayerStatistics> getGamePlayersStatistics(Long gameId);
	
	public PlayerStatistics getPlayerStatisticsCached(Long playerStatsId);
	
	public PlayerStatistics getPlayerStatistics(Long playerStatsId);

}

package com.mozzartbet.gameservice.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.mozzartbet.gameservice.domain.stats.TeamStatistics;

@Mapper
public interface TeamStatisticsMapper extends BaseMapper<TeamStatistics>{

	public long count();
	
	public List<Integer> getAllId();
	
	public TeamStatistics getById(Long id);

	public int insert(TeamStatistics entity);
	
	public int update(TeamStatistics entity);

	public int deleteById(Long id);

	public List<TeamStatistics> findTeamStatsByName(String teamName, Long teamId);
	
	public TeamStatistics save(TeamStatistics team);
	
	public Map<Long, TeamStatistics> getGameTeamStatistics(Long gameId);
	
	public TeamStatistics getTeamStatisticsCached(Long teamStatsId);
	
	public TeamStatistics getTeamStatistics(Long teamStatsId);

}

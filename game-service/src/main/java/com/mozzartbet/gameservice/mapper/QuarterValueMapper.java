package com.mozzartbet.gameservice.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.mozzartbet.gameservice.domain.Player;
import com.mozzartbet.gameservice.domain.QuarterValue;
import com.mozzartbet.gameservice.domain.ReboundLeaders;
import com.mozzartbet.gameservice.domain.Team;

@Mapper
public interface QuarterValueMapper extends BaseMapper<QuarterValue> {

	public int insert(QuarterValue quarterValue);

	public long count();
	
	public QuarterValue save(QuarterValue quarterValue);
	
	public List<QuarterValue> getQuarters(String quarter);
	
	public Long getGameId(QuarterValue quarterValue);
	
	public List<QuarterValue> getAllData();

	public List<QuarterValue> checkForDuplicate();
	
	public List<QuarterValue> getAllId();
	
	public List<QuarterValue> getPlayersFromTeam(String team);
	
	public QuarterValue getById(Long id);//@Param("id") Long id);
	
    public int update(QuarterValue entity);
 
	public int deleteById(@Param("id") Long id);
	
	public List<QuarterValue> getPlayersForTeam(@Param("teamId") Long teamId);
	
	public Team getTeamWithPlayers(@Param("teamId") Long teamId);
	
}

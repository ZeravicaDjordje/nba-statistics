package com.mozzartbet.gameservice.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;


import com.mozzartbet.gameservice.domain.ReboundLeaders;

@Mapper
public interface ReboundLeadersMapper extends BaseMapper<ReboundLeaders> {

	public int insert(ReboundLeaders reboundLeaders);

	public long count();
	
	public List<ReboundLeaders> getAllData();

	public List<ReboundLeaders> serachByPlayerUrl(String playerUrl);

	public long getGameId(ReboundLeaders reboundLeaders);
	
	public List<ReboundLeaders> checkForDuplicate();
	
	public ReboundLeaders save(ReboundLeaders reboundLeaders);
	
	public List<ReboundLeaders> searchByGameUrl(String gameUrl);
	
	public ReboundLeaders getReboundLeadersCached(Long id);
	
	public ReboundLeaders getReboundLeaders(Long id);
	
	public List<ReboundLeaders> getAllId();
	
	public List<ReboundLeaders> getPlayersFromTeam(String team);
	
	public ReboundLeaders getById(Long id);//@Param("id") Long id);
	
    public int update(ReboundLeaders entity);
 
	public int deleteById(@Param("id") Long id);
	
	public List<ReboundLeaders> getPlayersForTeam(@Param("teamId") Long teamId);
	
	public ReboundLeaders getTeamWithPlayers(@Param("teamId") Long teamId);
	
}

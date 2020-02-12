package com.mozzartbet.gameservice.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.mozzartbet.gameservice.domain.ReboundLeaders;
import com.mozzartbet.gameservice.domain.ScoreLeaders;

@Mapper
public interface ScoreLeadersMapper extends BaseMapper<ScoreLeaders> {

	public int insert(ScoreLeaders scoreLeaders);
	
	public ScoreLeaders save(ScoreLeaders scoreLeaders);
	
	public List<ScoreLeaders> searchByGameUrl(String gameUrl);
	
	public List<ScoreLeaders> serachByPlayerUrl(String playerUrl);

	public ScoreLeaders getScoreLeadersCached(Long id);
	
	public ScoreLeaders getScoreLeaders(Long id);
	
	public long count();
	
	public long getGameId(ScoreLeaders scoreLeaders);

	public List<ScoreLeaders> getAllData();

	public List<ScoreLeaders> checkForDuplicate();
	
	public List<ScoreLeaders> getAllId();
	
	public List<ScoreLeaders> getPlayersFromTeam(String team);
	
	public ScoreLeaders getById(Long id);//@Param("id") Long id);
	
    public int update(ScoreLeaders entity);
 
	public int deleteById(@Param("id") Long id);
	
	public List<ScoreLeaders> getPlayersForTeam(@Param("teamId") Long teamId);
	
	public ScoreLeaders getTeamWithPlayers(@Param("teamId") Long teamId);
}

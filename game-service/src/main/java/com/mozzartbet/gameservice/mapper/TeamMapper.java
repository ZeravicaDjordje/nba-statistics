package com.mozzartbet.gameservice.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Many;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectKey;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.mapping.FetchType;

import com.mozzartbet.gameservice.domain.Player;
import com.mozzartbet.gameservice.domain.Team;

@Mapper
public interface TeamMapper extends BaseMapper<Team> {

	public long count();
	
	public List<Team> getAllData();
	
	public List<Team> checkForDuplicate();
	
	public Team getById(@Param("id") Long id);

	public int insert(Team entity);

	public int update(Team entity);

	public int deleteById(@Param("id") Long id);

	public Team getWithPlayersById(@Param("id") Long id);

	public List<Team> findTeamByName(@Param("teamName") String teamName);
	
	public Team getTeamByName(String teamName);
	
	public Team searchTeamByUrl(String teamUrl);
		
	public Map<Long, Team> getTeam(Long teamId);
	
	public Team getTeamCached(Long teamId);
	
}
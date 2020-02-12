package com.mozzartbet.gameservice.mapper;

import java.sql.Timestamp;
import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.mozzartbet.gameservice.domain.Player;
import com.mozzartbet.gameservice.domain.Team;
import com.mozzartbet.gameservice.service.dto.PlayerSearchRequest;

@Mapper
public interface PlayerMapper extends BaseMapper<Player> {

	public long count();
	
	public List<Player> getAllData();

	public List<Player> checkForDuplicate();
	
	public Player getPlayerByName(String name);
	
	public List<Long> checkForTeamId(Player player);

	public List<Integer> getAllId();
	
	public List<Player> getPlayersFromTeam(String team);
	
	public Player getById(Long id);//@Param("id") Long id);
	
	public int insert(Player entity);
	
	public int update(Player entity);

	public int deleteById(@Param("id") Long id);
	
	public List<Player> getPlayersForTeam(@Param("teamId") Long teamId);
	
	public Team getTeamWithPlayers(@Param("teamId") Long teamId);
	
	public List<Player> findPlayersByName(@Param("playerName") String playerName, @Param("teamId") Long teamId);
	
	public List<Player> searchPlayers(@Param("request") PlayerSearchRequest request);
	
	public Player lockById(@Param("id")Long id);
	
	public int updateOptimistic(@Param("player") Player player,  @Param("expectedModifiedOn") Timestamp expectedModifiedOn);

}

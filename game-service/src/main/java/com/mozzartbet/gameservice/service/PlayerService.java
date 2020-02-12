package com.mozzartbet.gameservice.service;

import java.util.List;
import java.util.Map;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.apache.ibatis.annotations.Param;

import com.mozzartbet.gameservice.domain.Player;
import com.mozzartbet.gameservice.service.dto.PlayerSearchRequest;
import com.mozzartbet.gameservice.service.dto.PlayerSearchResponse;

public interface PlayerService {

	public List<Player> findPlayersByName(String playerName, Long teamId);
	
	public @NotNull @Valid PlayerSearchResponse searchPlayers(@NotNull @Valid @Param("request") PlayerSearchRequest request);
	
	public Player save(Player player);
	
	public Map<Long, Player> getTeamPlayers(Long teamId);
	
	public Player getPlayerCached(Long playerId);
	
	public Player getPlayer(Long playerId);

	// Locking
	
	public enum LockType {
		MEMORY,
		STRIPED,
		PESSIMISTIC,
		OPTIMISTIC
	}
	
	public Player save(Player player, LockType lockType);
}

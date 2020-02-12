package com.mozzartbet.gameservice.service.impl;

import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

import static java.util.function.Function.*;
import static java.util.stream.Collectors.*;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.CacheStats;
import com.google.common.cache.LoadingCache;
import com.mozzartbet.gameservice.annotation.Loggable;
import com.mozzartbet.gameservice.domain.Player;
import com.mozzartbet.gameservice.exception.PlayerException;
import com.mozzartbet.gameservice.mapper.PlayerMapper;
import com.mozzartbet.gameservice.mapper.TeamMapper;
import com.mozzartbet.gameservice.service.PlayerService;
import com.mozzartbet.gameservice.service.dto.PlayerSearchRequest;
import com.mozzartbet.gameservice.service.dto.PlayerSearchResponse;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class PlayerServiceImpl implements PlayerService {
	
	final TeamMapper teamMapper;
	final PlayerMapper playerMapper;

	@Override
	@Transactional(readOnly=true)
	public List<Player> findPlayersByName(String playerName, Long teamId) {
		return playerMapper.findPlayersByName(playerName, teamId);
	}
	
	@Override
	@Transactional(readOnly=true)
	public PlayerSearchResponse searchPlayers(PlayerSearchRequest request) {
		List<Player> players = playerMapper.searchPlayers(request);
		return PlayerSearchResponse.builder().players(players).build();
	}
	
	@Override
	@CacheEvict(cacheNames="players", key="#player.id")
	@Loggable(detail = true)
	public Player save(Player player) {
		checkNameDuplicated(player);
		try {
			playerMapper.save(player);
			return player;
		}
		catch (DuplicateKeyException e) {
			throw new PlayerException(
					 PlayerException.PlayerExceptionCode.DUPLICATED_NAME, 
					 "Name: %s is duplicated!", 
					 player.getPlayerName());
		}
	}
	
	@Transactional
	public void updateStats(Player player) {
		
	}
	
	private void checkNameDuplicated(Player player) {
		Player existing = playerMapper.getPlayerByName(player.getPlayerName());
		if(existing != null && !existing.getId().equals(player.getId())) {
			throw new PlayerException(
					PlayerException.PlayerExceptionCode.DUPLICATED_NAME,
					"Name: %s is duplicated in id=%s", player.getPlayerName(),
					existing.getId());	
		}
	}
	
	final LoadingCache<Long, Player> playerCache = CacheBuilder.newBuilder().
			initialCapacity(100).
			maximumSize(1000).
			expireAfterWrite(3, TimeUnit.SECONDS).
			recordStats().
			build(new CacheLoader<Long, Player>() {
				@Override
				public Player load(Long id) throws Exception {
					return playerMapper.getById(id);
				}
			});
			
	@Transactional(readOnly=true)
	@Override
	public Map<Long, Player> getTeamPlayers(Long teamId) {
		List<Player> players = playerMapper.getPlayersForTeam(teamId);
		return players.stream().collect(toMap(Player::getId, identity()));
	}
	
	@Override
	@Cacheable(cacheNames="players")
	public Player getPlayerCached(Long playerId) {
		return playerMapper.getById(playerId);
	}
	
	@Transactional(readOnly=true)
	public Player getPlayer(Long playerId) {
		return playerCache.getUnchecked(playerId);
	}
	
	@VisibleForTesting
	public CacheStats playerCacheStats() {
		return playerCache.stats();
	}
	
	final ReentrantLock playerLock = new ReentrantLock();
	
	public Player saveWithMemoryLock(Player player) {
		
		try {
			if(playerLock.tryLock(5, TimeUnit.SECONDS)) {
				try {
					return playerMapper.save(player);
				}
				catch(DuplicateKeyException  e) {
					throw new PlayerException(PlayerException.PlayerExceptionCode.DUPLICATED_NAME, "Name: %s is duplicated!", player.getPlayerName());
				}
				finally {
					playerLock.unlock();
				}
			}
			else {
				throw new IllegalStateException();
			}
		}
		catch(InterruptedException e) {
			throw new RuntimeException(e);
		}
	}
	
	// Database optimistic lock
	public Player saveWithOptimisticLock(Player player) {
		if(player.getId() == null) {
			playerMapper.insert(player);
		}
		
		Player existing = (player.getModifiedOn() != null) ? player : playerMapper.getById(player.getId());
		if(playerMapper.updateOptimistic(player, existing.getModifiedOn()) == 0) {
			throw new OptimisticLockingFailureException("Player id=" + player.getId() + " was changed in meantime!");
		}
		
		return playerMapper.getById(player.getId());
	}
	// Chose Lock
	public Player save(Player player, LockType lockType) {
		switch (lockType) {
		case MEMORY:
			return saveWithMemoryLock(player);
		case OPTIMISTIC:
			return saveWithOptimisticLock(player);
		default:
			throw new IllegalArgumentException("Unsupported lock type: " + lockType);
		}
	}
	
	
}

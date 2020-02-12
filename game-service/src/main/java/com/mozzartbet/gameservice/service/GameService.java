package com.mozzartbet.gameservice.service;

import java.util.List;
import java.util.Map;

import com.mozzartbet.gameservice.domain.Game;


public interface GameService {

	public Game findGameByUrl(String url, Long gameId);
	
	public Game save(Game game);
	
	public Game getGame(Long id);

}

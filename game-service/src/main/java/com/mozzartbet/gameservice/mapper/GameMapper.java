package com.mozzartbet.gameservice.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;

import com.mozzartbet.gameservice.domain.Game;
import com.mozzartbet.gameservice.domain.ReboundLeaders;

@Mapper
public interface GameMapper extends BaseMapper<Game> {

		public long count();
		
		public List<Game> getAllData();
		
		public List<Game> checkForDuplicate();
		
		public Game findGameByUrl(String url, Long gameId);

		public Game getById(Long id);
	
		public int insert(Game entity);

		public int update(Game entity);

		public int deleteById(@Param("id") Long id);


}
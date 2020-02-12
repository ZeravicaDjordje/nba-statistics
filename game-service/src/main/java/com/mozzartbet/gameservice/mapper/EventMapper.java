package com.mozzartbet.gameservice.mapper;

import java.sql.Timestamp;
import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.mozzartbet.gameservice.domain.Event;

@Mapper
public interface EventMapper extends BaseMapper<Event> {

	public long count();
	
	public List<Integer> getAllId();
		
	public Event getById(@Param("id") Long id);
	
	public int insert(Event entity);
	
	public int update(Event entity);

	public int deleteById(@Param("id") Long id);
	
	public List<Event> findEvents(@Param("fromDate") Long fromDate, @Param("toDate") Long toDate);
	
	public List<Event> getEventsByTime(Timestamp from, Timestamp to);

	public Event getEventCached(Long id);
	
	public Event getEvent(Long id);
}

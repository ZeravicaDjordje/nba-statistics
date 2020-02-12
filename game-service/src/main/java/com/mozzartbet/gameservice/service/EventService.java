package com.mozzartbet.gameservice.service;

import java.sql.Timestamp;
import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import com.mozzartbet.gameservice.domain.Event;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

public interface EventService {

	@RequiredArgsConstructor
	@Getter
	public enum EventDestination {
		STATS_PLAY_BY_PLAY("stats-play-by-play");
		private final String destinationName;
	}
	
	public @NotNull @Valid List<Event> findEvents(@NotNull Long fromDate, @NotNull Long toDate);
	
	public void sentEvents(@NotNull Long relativeTo, @NotNull @Valid Iterable<Event> event, @NotNull EventDestination destination);
	
	public Event save(Event event);
		
	public List<Event> getEventsByTime(Timestamp from, Timestamp to);
	
	public Event getEventCached(Long id);
	
	public Event getEvent(Long id);

	// 1. Producer API: sendEvents
	// 2. Queue vs topic: as a sender, this is just a destination
	
	// 3. Spring config: starter + bean for queues
	// 4. How does Spring know how to prepare and send our domain object?
	// 5. JSON & Jackson 
	// 6. Async; receive timeouts

}

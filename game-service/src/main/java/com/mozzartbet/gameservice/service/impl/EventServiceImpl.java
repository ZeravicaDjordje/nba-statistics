package com.mozzartbet.gameservice.service.impl;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.mozzartbet.gameservice.domain.AssistLeaders;
import com.mozzartbet.gameservice.domain.Event;
import com.mozzartbet.gameservice.mapper.EventMapper;
import com.mozzartbet.gameservice.service.EventService;

import com.mozzartbet.gameservice.service.EventService.EventDestination;

@Service
@Transactional(readOnly = true)
@Validated
public class EventServiceImpl implements EventService {

	@Autowired
	private EventMapper eventMapper;
	
	@Autowired
	private JmsTemplate jmsTemplate;
	
	@Override
	public Event save(Event event) {
		eventMapper.save(event);
		return event;
	}
	
	@Override
	public List<Event> getEventsByTime(Timestamp from, Timestamp to) {
		List<Event> events = eventMapper.getEventsByTime(from, to);
		return events;
	}
	
	@Override
	public Event getEventCached(Long id) {
		Event event = eventMapper.getEventCached(id);
		return event;
	}
	
	@Override
	public Event getEvent(Long id) {
		Event event = eventCache.getUnchecked(id);
		return event;
	}

	final LoadingCache<Long, Event> eventCache = CacheBuilder.newBuilder().
			initialCapacity(100).
			maximumSize(1000).
			expireAfterWrite(3, TimeUnit.SECONDS).
			recordStats().
			build(new CacheLoader<Long, Event>() {
				@Override
				public Event load(Long id) throws Exception {
					return eventMapper.getById(id);
				}
			});
	
	
	@Override
	public List<Event> findEvents(Long fromDate, Long toDate) {
		return eventMapper.findEvents(fromDate, toDate);
	}
	
	@Override
	@Async
	public void sentEvents(Long relativeTo, Iterable<Event> events, EventDestination destination) {
		LocalDate date =
			    Instant.ofEpochMilli(relativeTo).atZone(ZoneId.systemDefault()).toLocalDate();

		for (Event e : events) {
			long timeToSleep = ChronoUnit.MILLIS.between(date, Instant.ofEpochMilli(e.getGameDate()).atZone(ZoneId.systemDefault()).toLocalDate());
			// more efficient way?
			sleepMillis(timeToSleep);
				
			jmsTemplate.convertAndSend(destination.getDestinationName(), e);
				
			date = Instant.ofEpochMilli(e.getGameDate()).atZone(ZoneId.systemDefault()).toLocalDate();

		}
	}
	
	private void sleepMillis(long timeToSleep) {
	 	try {
			Thread.sleep(timeToSleep);
		}
		catch (InterruptedException e) {
			throw new IllegalStateException(e);
		}
	}
	
}

package com.mozzartbet.gameservice.service;

import static org.junit.Assert.assertTrue;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

import javax.jms.JMSException;

import org.json.JSONException;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;

import com.mozzartbet.gameservice.domain.Event;
import com.mozzartbet.gameservice.parser.GameParser;

import lombok.extern.slf4j.Slf4j;

//@Slf4j
public class EventServiceFindEventsByDate extends BaseServiceTest {

	@Autowired
	private EventService eventService;
	
	GameParser gameParser;
	
	@Test
	//@Ignore
	public void findEvents() throws JMSException, JSONException, ParseException {
		gameParser = new GameParser();
		gameParser.setInstanceJsoupDocument();
		gameParser.getInstanceJsoupDocument().setDocument("https://www.basketball-reference.com/boxscores/pbp/201812310IND.html");
		List<Event> pbp = gameParser.parseEvents("https://www.basketball-reference.com/boxscores/pbp/201812310IND.html");
		List<Event> es = eventService.findEvents(pbp.get(0).getGameDate(), pbp.get(101).getGameDate());
		assertTrue(es.size() > 0);
	}
	
}

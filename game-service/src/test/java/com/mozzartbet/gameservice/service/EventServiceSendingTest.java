package com.mozzartbet.gameservice.service;

import static org.junit.Assert.assertEquals;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.List;
import static com.mozzartbet.gameservice.service.EventService.EventDestination.*;
import javax.jms.JMSException;
import javax.jms.Message;

import org.apache.activemq.command.ActiveMQTextMessage;
import org.json.JSONException;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;

import com.mozzartbet.gameservice.domain.Event;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class EventServiceSendingTest extends BaseServiceTest {

	@Autowired
	private JmsTemplate jmsTemplate;
	
	@Autowired
	private EventService eventService;
	
	@Test
	//@Ignore
	public void sendEventsAndManuallyRecieve() throws JMSException, JSONException, ParseException {
		List<Event> es = eventService.findEvents(new Timestamp(new SimpleDateFormat("yyyy-mm-dd hh:mm:ss").parse("2019-05-01 11:12:00").getTime()).getTime(),
				new Timestamp(new SimpleDateFormat("yyyy-mm-dd hh:mm:ss").parse("2019-05-01 11:14:00").getTime()).getTime());
		eventService.sentEvents(es.get(0).getGameDate(), es, STATS_PLAY_BY_PLAY);
		System.out.println("Events " + es);
		// good practice, if bug causes no message to be dispatched
		jmsTemplate.setReceiveTimeout(1000);
		
		Message m;
			
		System.out.println("Destination Name Stats Play by Play " + STATS_PLAY_BY_PLAY.getDestinationName());
		m = jmsTemplate.receive();
		System.out.println("Message m s" + m);
		//log.info("{}", ((ActiveMQTextMessage) m).getText());
		// JSONAssert
		assertEquals("{'player':{'team':{'name':'Denver Nuggets'},'name':'Nikola Jokic'},'eventTime':'2019-05-01T11:12:13','points':2,'made':false}", 
				((ActiveMQTextMessage)m).getText(), false);
		System.out.println("Events " + es);
	
	}
	
	
	
}

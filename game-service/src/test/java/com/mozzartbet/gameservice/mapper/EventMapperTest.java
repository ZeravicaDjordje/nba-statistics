package com.mozzartbet.gameservice.mapper;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.sql.Timestamp;
import java.util.Date;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import com.mozzartbet.gameservice.domain.Event;
import com.mozzartbet.gameservice.domain.Team;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
@Transactional
public class EventMapperTest {

	@Autowired
	private EventMapper eventMapper;
	
	@Test
	public void testEvent() {
		Event event = Event.builder().
				quarter("4 QT").
				createdOn(new Timestamp(new Date().getTime())).
				modifiedOn(new Timestamp(new Date().getTime())).
				scoreSoFar("23").
				teamOne("a").
				teamTwo("b").
				teamOneAction("a").
				teamTwoAction("b").
				teamOneLink("a").
				teamTwoLink("b").
				teamOneScore("a").
				teamTwoScore("b").
				timeStamp("12:00").
				event("<html><head></head></html>").
				build();
		assertEquals(eventMapper.insert(event), 1);
		assertTrue(eventMapper.getById(Long.valueOf(1)) instanceof Event);
		assertEquals(eventMapper.update(event), 1);
		assertEquals(eventMapper.deleteById(event.getId()), 1);
	}
}
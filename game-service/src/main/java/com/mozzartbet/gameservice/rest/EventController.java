package com.mozzartbet.gameservice.rest;

import static com.mozzartbet.gameservice.domain.ReboundEvent.ReboundType.*;
import static com.mozzartbet.gameservice.service.EventService.EventDestination.*;
import static java.time.LocalDateTime.*;
import static java.util.Arrays.*;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mozzartbet.gameservice.domain.Event;
import com.mozzartbet.gameservice.domain.Player;
import com.mozzartbet.gameservice.domain.Player.Position;
import com.mozzartbet.gameservice.domain.PointEvent;
import com.mozzartbet.gameservice.domain.ReboundEvent;
import com.mozzartbet.gameservice.domain.Team;
import com.mozzartbet.gameservice.mapper.EventMapper;
import com.mozzartbet.gameservice.mapper.PlayerMapper;
import com.mozzartbet.gameservice.mapper.TeamMapper;
import com.mozzartbet.gameservice.service.EventService;
import com.mozzartbet.gameservice.service.dto.SendEventsRequest;

@RestController
public class EventController {

	@Autowired
	private TeamMapper teamMapper;

	@Autowired
	private PlayerMapper playerMapper;

	@Autowired
	private EventMapper eventMapper;

	@Autowired
	private EventService eventService;
	
	@PostMapping(path = "/team/generate")
	public void generateTeam() {
		Team t = Team.builder().name("Denver Nuggets").city("Denver").build();
		teamMapper.insert(t);

		Player p1 = Player.builder().team(t).name("Nikola Jokic").number("15").position(Position.C).build();
		playerMapper.insert(p1);

		Player p2 = Player.builder().team(t).name("Jamal Murray").number("27").position(Position.SG).build();
		playerMapper.insert(p2);
	}

	@PostMapping(path = "/events/generate")
	public void generateEvents() {
		Player p1 = playerMapper.findPlayersByName("Nikola Jokic", null).get(0);
		Player p2 = playerMapper.findPlayersByName("Jamal Murray", null).get(0);
		
		List<Event> es = asList(
				PointEvent.builder().eventTime(parse("2019-05-01T11:12:13")).player(p1).points(2)
						.made(false).build(),
				PointEvent.builder().eventTime(parse("2019-05-01T11:13:05")).player(p2).points(3)
						.made(true).build(),
				ReboundEvent.builder().eventTime(parse("2019-05-01T11:13:17")).player(p2)
						.reboundType(DEFENSIVE).teamRebound(false).build());

		es.forEach(e -> {
			eventMapper.insert(e);
			eventMapper.insertSpec(e);
		});
	}

	@PostMapping(path = "/events/send")
	public void sendEvents() {
		eventService.sendEvents(SendEventsRequest.builder()
				.destination(STATS_PLAY_BY_PLAY)
				.fromDate(LocalDateTime.parse("2019-05-01T11:12:00"))
				.toDate(LocalDateTime.parse("2019-05-01T11:14:00"))
				.relativeTo(LocalDateTime.parse("2019-05-01T11:12:00"))
				.speedFactor(0.1d)
				.build());
	}
}

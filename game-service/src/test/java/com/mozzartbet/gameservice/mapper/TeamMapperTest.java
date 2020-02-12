package com.mozzartbet.gameservice.mapper;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Date;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import com.mozzartbet.gameservice.domain.Player;
import com.mozzartbet.gameservice.domain.Team;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
//@Transactional
public class TeamMapperTest {

	@Autowired
	private TeamMapper teamMapper;
	
	@Autowired
	private PlayerMapper playerMapper;
	
	@Test
	public void testTeam() {
		Team team = Team.builder().name("Denver Nugets").build();
		Player player = Player.builder().
				teamName("Denver Nugets").
				height("3123").
				width("23").
				birthDate("a").
				college("g").
				position("va").
				uniformNumber("@3").
				yearsOfExp("123").
				playerUrl("c").
				playerName("a").
				build();
		assertEquals(teamMapper.insert(team), 1);
		assertEquals(playerMapper.insert(player), 1);
		System.out.println(teamMapper.getById(team.getId()));
		assertTrue(teamMapper.getById(team.getId()) instanceof Team);
		assertEquals(teamMapper.update(team), 1);
		assertEquals(teamMapper.deleteById(team.getId()), 1);
	}
}

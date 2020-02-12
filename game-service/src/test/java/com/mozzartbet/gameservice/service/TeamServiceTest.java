package com.mozzartbet.gameservice.service;

import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.mozzartbet.gameservice.domain.Team;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Transactional
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TeamServiceTest extends BaseServiceTest {

	
	@Autowired
	TeamService teamService;
	
	@Test
	public void findTeamByNameTest() {
		List<Team> teams = teamService.findTeamByName("Mil");
		log.debug("Log Team with all Players associated with the team", teams );
		assertTrue(teams.size() != 0);
		teams.forEach(team -> assertTrue(team != null));
	}
	
	@Test
	public void serachTeamsByUrlTest() {
		Team team = teamService.searchTeamByUrl("https://www.basketball-reference.com/teams/MIL/2019.html");
		assertTrue(team != null);
	}
	
	
	@Test
	public void saveTest() {
		Team team = Team.builder().id(Long.valueOf(1000)).teamUrl("teamUrl").createdOn(Timestamp.valueOf(LocalDateTime.now())).name("New Team").build();
		Team resultTeam = teamService.save(team);
		assertTrue(team.equals(resultTeam));
	}
	
	@Test
	public void getTeamCachedTest() {
		Team teamCached = teamService.getTeamCached(Long.valueOf(1));
		assertTrue(teamCached != null);
	}
	
	@Test
	public void getTeamTest() {
		Team team = teamService.getTeam(Long.valueOf(1));
		assertTrue(team != null);
	}
	
	@Test
	public void CteamCacheStatsTest() {
		System.out.println(teamService.teamCacheStats());
	}
}

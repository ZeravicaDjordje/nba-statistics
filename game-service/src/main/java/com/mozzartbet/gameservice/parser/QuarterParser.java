package com.mozzartbet.gameservice.parser;

import java.util.List;
import java.util.Map;

import com.mozzartbet.gameservice.domain.Event;
import com.mozzartbet.gameservice.domain.Quarter;
import com.mozzartbet.gameservice.domain.stats.PlayerStatistics;

public class QuarterParser extends GameParser{

	public Quarter getQuarterEvents(TeamMatchParser parser, ActionParser actionParser, List<Event> events, String matchBOX) {
		parser.getInstanceJsoupDocument().setDocument(matchBOX);
		Map<String,PlayerStatistics> playerMapTeamOne = parser.getMapFromTeam(0);
		parser.getInstanceJsoupDocument().setDocument(matchBOX);
		Map<String,PlayerStatistics> playerMapTeamTwo = parser.getMapFromTeam(2);
		Quarter quarterValues = actionParser.parseMatchValues(events, playerMapTeamOne, playerMapTeamTwo);
		return quarterValues;
	}

}

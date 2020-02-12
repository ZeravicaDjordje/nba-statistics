package com.mozzartbet.gameservice.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class TurnDateIntoTimeStamp {

	public static int theMonth(String month){
		Map<String, Integer> months  = new HashMap() {{	    		
			put("January",1);
			put("February",2); 
			put("March",3); 
			put("April",4); 
			put("May",5); 
			put("June",6); 
			put("July",7); 
			put("August",8); 
			put("September",9); 
			put("October",10); 
			put("November",11); 
			put("December",12);
		}};
	    return months.get(month);
	}
	
	public static void clockTimeEvent(SimpleDateFormat sdf, String gameDate, String timeStamp, String quarter, SaveDateFormat saveDate, SaveSeconds saveSeconds) {
		int quarterTime;
		if (quarter.contains("Q")) {
			quarterTime = 720;
		}
		else {
			quarterTime = 300;
		}
		int eventSeconds = MinutesPlayed.timeSplit(timeStamp);
		String[] gameDateSplit = gameDate.split(" ");
		String hourMinutes = gameDateSplit[0];
		String day = gameDateSplit[3].replace(",", "");
		String year = gameDateSplit[4];
		int seconds = quarterTime - eventSeconds;
		saveSeconds.setCurrentSeconds(eventSeconds);
		int month = theMonth(gameDateSplit[2]);
		saveDate.setDateFormatEvent(month + "/" + day + "/" + year + " " + hourMinutes);
		//System.out.println(month + "/" + day + "/" + year + " " + hourMinutes);
		checkDate(sdf, saveDate, saveSeconds);
	}
	
	public static void checkDate(SimpleDateFormat sdf, SaveDateFormat saveDate, SaveSeconds saveSeconds) {
		if(saveDate.getDateFormatGame().equals(saveDate.getDateFormatEvent())) {
			sdf.getCalendar().add(Calendar.SECOND, saveSeconds.getPreviousSeconds() - saveSeconds.getCurrentSeconds());
			saveSeconds.setPreviousSeconds(saveSeconds.getCurrentSeconds());
		}
		else {
			sdf.format(new Date(saveDate.getDateFormatEvent()));
			sdf.getCalendar().add(Calendar.SECOND, saveSeconds.getPreviousSeconds() - saveSeconds.getCurrentSeconds());
			saveDate.setDateFormatGame(saveDate.getDateFormatEvent());
			saveSeconds.setPreviousSeconds(saveSeconds.getCurrentSeconds());

		}	
	}
}

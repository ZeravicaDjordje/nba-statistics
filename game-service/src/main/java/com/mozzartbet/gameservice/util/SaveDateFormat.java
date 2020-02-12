package com.mozzartbet.gameservice.util;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SaveDateFormat {

	private String dateFormatGame;
	private String dateFormatEvent;
	
}

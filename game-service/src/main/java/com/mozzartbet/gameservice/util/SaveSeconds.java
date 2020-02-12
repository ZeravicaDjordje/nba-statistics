package com.mozzartbet.gameservice.util;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class SaveSeconds {

	private int previousSeconds;
	private int currentSeconds;
	
}

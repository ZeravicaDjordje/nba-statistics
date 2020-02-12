package com.mozzartbet.gameservice.util;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.SocketTimeoutException;
import java.net.URL;

import com.mozzartbet.gameservice.exception.TimeOutConnectionException;

public class CheckConnectionTimeOut {
		
	public void checkConnectionTime(String u) throws TimeOutConnectionException, IOException {
		URL url = new URL(u);
		HttpURLConnection connection = (HttpURLConnection) url.openConnection();
		connection.setConnectTimeout(1000);
		InputStream input = connection.getInputStream();
		if(!(input.available() > 0)) {
			throw new TimeOutConnectionException(u);
		}
	}
}

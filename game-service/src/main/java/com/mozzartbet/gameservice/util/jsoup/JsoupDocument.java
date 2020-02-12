package com.mozzartbet.gameservice.util.jsoup;

import java.io.File;
import java.io.IOException;
import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.util.concurrent.TimeoutException;

import org.jsoup.HttpStatusException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class JsoupDocument {

	private Document document;
	private File file;
	
	public void setFile(String url) {
		this.file = new File(url);	
	}
	
	public Document getDocument() {
		return document;
	}
	 
	
	public void setDocument(String url) {
		try {
			this.document = Jsoup.connect(url).get();
		}
		catch(HttpStatusException httpse) {
			System.out.println(httpse.toString());
		}
		catch(SocketTimeoutException ste) {
			System.out.println(ste.toString());
		}
		catch (IOException ioe) {
			ioe.printStackTrace();
		}
	}
	
	public void setDocument(String url, int timeOut) {
		try {
			this.document = Jsoup.connect(url).timeout(timeOut).get();
		}
		catch(HttpStatusException httpse) {
			System.out.println(httpse.toString());
		}
		catch (IOException ioe) {
			ioe.printStackTrace();
		}
	}
	
	public void setDocument(String url, int timeOut, String UserAgent) {
		try {
			this.document = Jsoup.connect(url).userAgent(UserAgent).timeout(timeOut).get();
		}
		catch(HttpStatusException httpse) {
			System.out.println(httpse.toString());
		}
		catch (IOException ioe) {
			ioe.printStackTrace();
		}
	}
	
	public void setDocument() {
		try {
			this.document = Jsoup.parse(this.file, "UTF-8", "");
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
	}
}

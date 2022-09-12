package org.openjfx.eecs3311_project;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class CommonUtils {
	
	private static final String ORDER_PATH = "LastOrderId.txt";
	private static final String MOVIE_PATH = "LastMovieId.txt";
	
	public static long generateNewOrderNumber() {
		return generateNewId(ORDER_PATH);
	}
	
	public static long generateNewMovieId() {
		return generateNewId(MOVIE_PATH);
	}
	
	private static long generateNewId(String path) {
		File database = new File(path);
		FileWriter writer;
		long orderNumber = 0L;
		try {
			BufferedReader reader = new BufferedReader(new FileReader(database));
			String str = reader.readLine();
			if (str != null) {
				orderNumber = Long.valueOf(str) + 1;
			}
			reader.close();
			
			writer = new FileWriter(database, false);
			writer.write(String.valueOf(orderNumber));
			writer.close();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return orderNumber;
		
	}

}

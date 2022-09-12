package org.openjfx.eecs3311_project;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

public class TentitiveOrderDatabase {

	//map of username -> set of movieIds
	public Map<String, HashSet<Long>> tentitiveOrders = new HashMap<String, HashSet<Long>>();
	private static final String PATH = "TentitiveOrderDatabase.txt";
	
	public void load(){
		
		File tentitiveOrderDatabase = new File(PATH);
		try {
			BufferedReader reader = new BufferedReader(new FileReader(tentitiveOrderDatabase));
			String str;
			while ((str = reader.readLine()) != null) {
				String[] orderInfo = str.split(",");
				HashSet<Long> movieIds = new HashSet<Long>();
				for (int i = 1; i < orderInfo.length; i++) {
					movieIds.add(Long.valueOf(orderInfo[i]));
				}
				tentitiveOrders.put(orderInfo[0], movieIds);
			}
			reader.close();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void addNewMovie(String username, Long movieId) {
		if (tentitiveOrders.containsKey(username)) {
			tentitiveOrders.get(username).add(movieId);
		} else {
			tentitiveOrders.put(username, new HashSet<>(Arrays.asList(movieId)));
		}
		
		List<String> lines = new ArrayList<>();
		for (Map.Entry<String,HashSet<Long>> entry : tentitiveOrders.entrySet()) {
			StringBuffer sb = new StringBuffer(entry.getKey());
			for (Long id : entry.getValue()) {
				sb.append("," + id);
			}
			sb.append("\n");
			lines.add(sb.toString());
		}
		
		File tentitiveOrderDatabase = new File(PATH);
		try {
			FileWriter writer = new FileWriter(tentitiveOrderDatabase, false); 
			for(String str: lines) {
			  writer.write(str);
			}
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void removeMovie(String username, Long removedMovieId) {
		if (tentitiveOrders.containsKey(username)) {
			tentitiveOrders.get(username).remove(removedMovieId);
		}
		
		List<String> lines = new ArrayList<>();
		for (Map.Entry<String,HashSet<Long>> entry : tentitiveOrders.entrySet()) {
			StringBuffer sb = new StringBuffer(entry.getKey());
			for (Long id : entry.getValue()) {
				sb.append("," + id);
			}
			sb.append("\n");
			lines.add(sb.toString());
		}
		
		File tentitiveOrderDatabase = new File(PATH);
		try {
			FileWriter writer = new FileWriter(tentitiveOrderDatabase, false); 
			for(String str: lines) {
			  writer.write(str);
			}
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void removeOrder(String username) {
		tentitiveOrders.remove(username);
		
		List<String> lines = new ArrayList<>();
		for (Map.Entry<String,HashSet<Long>> entry : tentitiveOrders.entrySet()) {
			StringBuffer sb = new StringBuffer(entry.getKey());
			for (Long id : entry.getValue()) {
				sb.append("," + id);
			}
			sb.append("\n");
			lines.add(sb.toString());
		}
		
		File tentitiveOrderDatabase = new File(PATH);
		try {
			FileWriter writer = new FileWriter(tentitiveOrderDatabase, false); 
			for(String str: lines) {
			  writer.write(str);
			}
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}

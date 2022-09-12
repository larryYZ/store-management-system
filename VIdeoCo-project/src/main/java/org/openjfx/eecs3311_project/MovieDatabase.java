package org.openjfx.eecs3311_project;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MovieDatabase {

	public Map<Long, Movie> movies = new HashMap<Long, Movie>();
	private static final String PATH = "MovieDatabase.txt";
	
	public void load(){
		File movieDatabase = new File(PATH);
		try {
			BufferedReader reader = new BufferedReader(new FileReader(movieDatabase));
			String str;
			while ((str = reader.readLine()) != null) {
				String[] movieInfo = str.split(";");
				Movie movie = new Movie(
						Long.valueOf(movieInfo[0]),
						movieInfo[1],
						Integer.valueOf(movieInfo[2]),
						movieInfo[3],
						movieInfo[4],
						movieInfo[5],
						movieInfo[6],
						movieInfo[7]);
				movies.put(Long.valueOf(movieInfo[0]), movie);
			}
			reader.close();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void decreaseMovieCount(List<Long> movieIds) {
		
		List<String> lines = new ArrayList<>();
		for (Map.Entry<Long, Movie> entry : movies.entrySet()) {
			if (movieIds.contains(entry.getKey())) {
				entry.getValue().decreaseQuantity();
			}
			
			lines.add(entry.getValue().toString());
		}
		
		File movieDatabase = new File(PATH);
		try {
			FileWriter writer = new FileWriter(movieDatabase, false); 
			for(String str: lines) {
			  writer.write(str);
			}
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void increaseMovieCount(List<Long> movieIds) {
		
		List<String> lines = new ArrayList<>();
		for (Map.Entry<Long, Movie> entry : movies.entrySet()) {
			if (movieIds.contains(entry.getKey())) {
				entry.getValue().increaseQuantity();
			}
			
			lines.add(entry.getValue().toString());
		}
		
		File movieDatabase = new File(PATH);
		try {
			FileWriter writer = new FileWriter(movieDatabase, false); 
			for(String str: lines) {
			  writer.write(str);
			}
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void update(Movie movie) {
		movies.put(movie.getId(), movie);
		
		List<String> lines = new ArrayList<>();
		for (Map.Entry<Long, Movie> entry : movies.entrySet()) {
			lines.add(entry.getValue().toString());
		}
		
		File movieDatabase = new File(PATH);
		try {
			FileWriter writer = new FileWriter(movieDatabase, false); 
			for(String str: lines) {
			  writer.write(str);
			}
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void remove(Movie movie) {
		movies.remove(movie.getId());
		
		List<String> lines = new ArrayList<>();
		for (Map.Entry<Long, Movie> entry : movies.entrySet()) {
			lines.add(entry.getValue().toString());
		}
		
		File movieDatabase = new File(PATH);
		try {
			FileWriter writer = new FileWriter(movieDatabase, false); 
			for(String str: lines) {
			  writer.write(str);
			}
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	// this method does not update movies map
	public static void addNewMovie(Movie movie){
		File movieDatabase = new File(PATH);
		FileWriter writer;
		try {
			writer = new FileWriter(movieDatabase, true);
			writer.write(movie.toString());
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public List<Long> getMovieIds(List<String> moviesList) {
		List<Long> movieIds = new ArrayList<>();
		for (Movie m : movies.values()) {
			if (moviesList.contains(m.getTitle())) {
				movieIds.add(m.getId());
			}
		}
		return movieIds;
	}
	
}

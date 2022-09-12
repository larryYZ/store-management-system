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

public class UserDatabase {

	public Map<String, User> users = new HashMap<String, User>();
	private static final String PATH = "UserDatabase.txt";
	
	public void load(){
		
		File userDatabase = new File(PATH);
		try {
			BufferedReader reader = new BufferedReader(new FileReader(userDatabase));
			String str;
			while ((str = reader.readLine()) != null) {
				String[] userInfo = str.split(",");
				User user = new User(userInfo[0], userInfo[1], userInfo[2], userInfo[3], Integer.valueOf(userInfo[4]), userInfo[5]);
				users.put(user.getName(), user);
			}
			reader.close();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void addNewUser(User user){
		File userDatabase = new File(PATH);
		FileWriter writer;
		try {
			writer = new FileWriter(userDatabase, true);
			writer.write(user.toString());
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		users.put(user.getName(), user);
	}
	
	public void updateUser(User user) {
		users.put(user.getName(), user);
		
		List<String> lines = new ArrayList<>();
		for (User u : users.values()) {
			lines.add(u.toString());
		}
		
		File userDatabase = new File(PATH);
		try {
			FileWriter writer = new FileWriter(userDatabase, false); 
			for(String str: lines) {
			  writer.write(str);
			}
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void removeUser(User user) {
		users.remove(user.getName());
		
		List<String> lines = new ArrayList<>();
		for (User u : users.values()) {
			lines.add(u.toString());
		}
		
		File userDatabase = new File(PATH);
		try {
			FileWriter writer = new FileWriter(userDatabase, false); 
			for(String str: lines) {
			  writer.write(str);
			}
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}

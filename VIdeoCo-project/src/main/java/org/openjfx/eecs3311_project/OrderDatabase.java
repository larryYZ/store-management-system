package org.openjfx.eecs3311_project;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OrderDatabase {

	//map of username -> list of order
	public Map<String, ArrayList<Order>> orders = new HashMap<String, ArrayList<Order>>();
	private static final String PATH = "OrderDatabase.txt";
	
	public void load(){
		
		File orderDatabase = new File(PATH);
		try {
			BufferedReader reader = new BufferedReader(new FileReader(orderDatabase));
			String str;
			while ((str = reader.readLine()) != null) {
				String[] orderInfo = str.split(";");
				Order order = new Order(orderInfo[0], Long.valueOf(orderInfo[1]), Long.valueOf(orderInfo[2]), Integer.valueOf(orderInfo[3]),
						orderInfo[4], orderInfo[5], orderInfo[6], orderInfo[7], orderInfo[8], orderInfo[9], orderInfo[10], orderInfo[11], orderInfo[12]);
				String username = order.getUsername();
				if (orders.containsKey(username)) {
					orders.get(username).add(order);
				} else {
					orders.put(order.getUsername(), new ArrayList<Order>(Arrays.asList(order)));
				}
				
			}
			reader.close();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	// this method does not update orders map
	public static void addNewOrder(Order order){
		File orderDatabase = new File(PATH);
		FileWriter writer;
		try {
			writer = new FileWriter(orderDatabase, true);
			writer.write(order.toString());
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void removeOrder(Order order) {
		orders.get(order.getUsername()).remove(order);
		
		List<String> lines = new ArrayList<>();
		for (ArrayList<Order> orderList : orders.values()) {
			for (Order o : orderList) {
				lines.add(o.toString());
			}
		}
		
		File orderDatabase = new File(PATH);
		try {
			FileWriter writer = new FileWriter(orderDatabase, false); 
			for(String str: lines) {
			  writer.write(str);
			}
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void updateOrder(Order order, Order newOrder) {
		ArrayList<Order> userOrders = orders.get(order.getUsername());
		userOrders.remove(order);
		userOrders.add(newOrder);
		
		List<String> lines = new ArrayList<>();
		for (ArrayList<Order> orderList : orders.values()) {
			for (Order o : orderList) {
				lines.add(o.toString());
			}
		}
		
		File orderDatabase = new File(PATH);
		try {
			FileWriter writer = new FileWriter(orderDatabase, false); 
			for(String str: lines) {
			  writer.write(str);
			}
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void removeUserOrders(String username) {
		orders.remove(username);
		
		List<String> lines = new ArrayList<>();
		for (ArrayList<Order> orderList : orders.values()) {
			for (Order o : orderList) {
				lines.add(o.toString());
			}
		}
		
		File orderDatabase = new File(PATH);
		try {
			FileWriter writer = new FileWriter(orderDatabase, false); 
			for(String str: lines) {
			  writer.write(str);
			}
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
}

package org.openjfx.eecs3311_project;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class UserTest {

	@Test
	void test1() {
		String name = "larry";
		String email = "larryz@my.yorku.ca";
		String password = "1234";
		String permission = "CUSTOMER";
		int loyaltyPoints = 10;
		String realName = "Larry Zhang";
		User user = new User(name, email, password, permission, loyaltyPoints, realName);
		assertEquals(name, user.getName());
		assertEquals(email, user.getEmail());
		assertEquals(password, user.getPassword());
		assertEquals(permission, user.getPermission());
		assertEquals(loyaltyPoints, user.getLoyaltyPoints());
		assertEquals(realName, user.getRealName());
	}
	
	@Test
	void test2() {
		String name = "larry";
		String email = "larryz@my.yorku.ca";
		String password = "1234";
		String permission = "CUSTOMER";
		int loyaltyPoints = 10;
		String realName = "Larry Zhang";
		User user = new User();
		user.setName(name);
		user.setEmail(email);
		user.setPassword(password);
		user.setPermission(permission);
		user.setLoyaltyPoints(loyaltyPoints);
		user.setRealName(realName);
		
		assertEquals(name, user.getName());
		assertEquals(email, user.getEmail());
		assertEquals(password, user.getPassword());
		assertEquals(permission, user.getPermission());
		assertEquals(loyaltyPoints, user.getLoyaltyPoints());
		assertEquals(realName, user.getRealName());
	}
	
	@Test
	void test3() {
		String name = "larry";
		String email = "larryz@my.yorku.ca";
		String password = "1234";
		String permission = "CUSTOMER";
		int loyaltyPoints = 10;
		String realName = "Larry Zhang";
		int diff = 3;
		
		User user = new User(name, email, password, permission, loyaltyPoints, realName);
		user.decreaseLoyaltyPoints(diff);
		
		assertEquals(name, user.getName());
		assertEquals(email, user.getEmail());
		assertEquals(password, user.getPassword());
		assertEquals(permission, user.getPermission());
		assertEquals(loyaltyPoints - diff, user.getLoyaltyPoints());
		assertEquals(realName, user.getRealName());
	}
	
	@Test
	void test4() {
		String name = "larry";
		String email = "larryz@my.yorku.ca";
		String password = "1234";
		String permission = "CUSTOMER";
		int loyaltyPoints = 10;
		String realName = "Larry Zhang";
		int diff = 3;
		
		User user = new User(name, email, password, permission, loyaltyPoints, realName);
		user.increaseLoyaltyPoints(diff);
		
		assertEquals(name, user.getName());
		assertEquals(email, user.getEmail());
		assertEquals(password, user.getPassword());
		assertEquals(permission, user.getPermission());
		assertEquals(loyaltyPoints + diff, user.getLoyaltyPoints());
		assertEquals(realName, user.getRealName());
	}
	
	@Test
	void test5() {
		String name = "larry";
		String email = "larryz@my.yorku.ca";
		String password = "1234";
		String permission = "CUSTOMER";
		int loyaltyPoints = 10;
		String realName = "Larry Zhang";
		
		User user = new User(name, email, password, permission, loyaltyPoints, realName);
		user.increaseLoyaltyPoints();
		
		assertEquals(name, user.getName());
		assertEquals(email, user.getEmail());
		assertEquals(password, user.getPassword());
		assertEquals(permission, user.getPermission());
		assertEquals(loyaltyPoints + 1, user.getLoyaltyPoints());
		assertEquals(realName, user.getRealName());
	}
	
	@Test
	void test6() {
		String name = "larry";
		String email = "larryz@my.yorku.ca";
		String password = "1234";
		String permission = "CUSTOMER";
		int loyaltyPoints = 10;
		String realName = "Larry Zhang";
		User user = new User(name, email, password, permission, loyaltyPoints, realName);
		assertEquals("larry,larryz@my.yorku.ca,1234,CUSTOMER,10,Larry Zhang\n", user.toString());
	}

}

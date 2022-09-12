package org.openjfx.eecs3311_project;


public class User {
	private String name;
	private String email;
	private String password;
	private String permission;
	private int loyaltyPoints;
	private String realName;
	
	public User() {
		
	}
	
	public User(String name, String email, String password, String permission, int loyaltyPoints, String realName) {
		this.name = name;
		this.email = email;
		this.password = password;
		this.permission = permission;
		this.loyaltyPoints = loyaltyPoints;
		this.realName = realName;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPermission() {
		return permission;
	}

	public void setPermission(String permission) {
		this.permission = permission;
	}
	
	public int getLoyaltyPoints() {
		return loyaltyPoints;
	}

	public void setLoyaltyPoints(int loyaltyPoints) {
		this.loyaltyPoints = loyaltyPoints;
	}
	
	public void increaseLoyaltyPoints() {
		this.loyaltyPoints++;
	}
	
	public void increaseLoyaltyPoints(int amount) {
		this.loyaltyPoints += amount;
	}
	
	public void decreaseLoyaltyPoints(int amount) {
		this.loyaltyPoints -= amount;
	}
	
	public String getRealName() {
		return realName;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}

	@Override
	public String toString() {
		return name + "," + email + "," + password + "," + permission + "," + loyaltyPoints + "," + realName + "\n";
	}
	
}

package org.openjfx.eecs3311_project;

public class Order {
	
	private String username;
	private long orderNumber;
	private long time;
	private int returned; // whether the customer returned the order or not
	private String address;
	private String city;
	private String province;
	private String postalCode;
	private String country;
	private String payment;
	private String paymentDetail; // cardNum or loyalty points
	private String movieIds;
	private String warehouse;
	
	public Order() {
		
	}
	
	public Order(String username, long orderNumber, long time, int returned, String address, String city,
			String province, String postalCode, String country, String payment, String paymentDetail, String movieIds, String warehouse) {
		this.username = username;
		this.orderNumber = orderNumber;
		this.time = time;
		this.returned = returned;
		this.address = address;
		this.city = city;
		this.province = province;
		this.postalCode = postalCode;
		this.country = country;
		this.payment = payment;
		this.paymentDetail = paymentDetail;
		this.movieIds = movieIds;
		this.warehouse = warehouse;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public long getOrderNumber() {
		return orderNumber;
	}

	public void setOrderNumber(long orderNumber) {
		this.orderNumber = orderNumber;
	}

	public long getTime() {
		return time;
	}

	public void setTime(long time) {
		this.time = time;
	}

	public int getReturned() {
		return returned;
	}

	public void setReturned(int returned) {
		this.returned = returned;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getPostalCode() {
		return postalCode;
	}

	public void setPostalCode(String postalCode) {
		this.postalCode = postalCode;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getPayment() {
		return payment;
	}

	public void setPayment(String payment) {
		this.payment = payment;
	}

	public String getPaymentDetail() {
		return paymentDetail;
	}

	public void setPaymentDetail(String paymentDetail) {
		this.paymentDetail = paymentDetail;
	}
	
	public String getMovieIds() {
		return movieIds;
	}

	public void setMovies(String movieIds) {
		this.movieIds = movieIds;
	}
	
	public String getWarehouse() {
		return warehouse;
	}

	public void setWarehouse(String warehouse) {
		this.warehouse = warehouse;
	}

	public void setMovieIds(String movieIds) {
		this.movieIds = movieIds;
	}

	@Override
	public String toString() {
		return username + ";" + orderNumber + ";" + time + ";" + returned + ";" + address + ";" + city + ";" + province
				+ ";" + postalCode + ";" + country + ";" + payment + ";"
				+ paymentDetail + ";" + movieIds + ";" + warehouse + "\n";
	}

//	@Override
//	public int hashCode() {
//		return Objects.hash(orderNumber, username);
//	}
//
//	@Override
//	public boolean equals(Object obj) {
//		if (this == obj)
//			return true;
//		if (obj == null)
//			return false;
//		if (getClass() != obj.getClass())
//			return false;
//		Order other = (Order) obj;
//		return orderNumber == other.orderNumber && Objects.equals(username, other.username);
//	}
	
}

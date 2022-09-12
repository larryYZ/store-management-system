package org.openjfx.eecs3311_project;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class OrderTest {

	@Test
	void test1() {
		String username = "larry";
		long orderNumber = 192048L;
		long time = 32493085930285L;
		int returned = 1;
		String address = "1 Larry St";
		String city = "North York";
		String province = "Ontario";
		String postalCode = "M3N 9F3";
		String country = "Canada";
		String payment = "creditCard";
		String paymentDetail = "39439289438025930";
		String movieIds = "10001,10002";
		String warehouse = "WH1";
		Order order = new Order(username, orderNumber, time, returned, address, city,
				province, postalCode, country, payment, paymentDetail, movieIds, warehouse);
		assertEquals(username, order.getUsername());
		assertEquals(orderNumber, order.getOrderNumber());
		assertEquals(time, order.getTime());
		assertEquals(returned, order.getReturned());
		assertEquals(address, order.getAddress());
		assertEquals(city, order.getCity());
		assertEquals(province, order.getProvince());
		assertEquals(postalCode, order.getPostalCode());
		assertEquals(country, order.getCountry());
		assertEquals(payment, order.getPayment());
		assertEquals(paymentDetail, order.getPaymentDetail());
		assertEquals(movieIds, order.getMovieIds());
		assertEquals(warehouse, order.getWarehouse());
	}
	
	@Test
	void test2() {
		String username = "larry";
		long orderNumber = 192048L;
		long time = 32493085930285L;
		int returned = 1;
		String address = "1 Larry St";
		String city = "North York";
		String province = "Ontario";
		String postalCode = "M3N 9F3";
		String country = "Canada";
		String payment = "creditCard";
		String paymentDetail = "39439289438025930";
		String movieIds = "10001,10002";
		String warehouse = "WH1";
		Order order = new Order();
		order.setUsername(username);
		order.setOrderNumber(orderNumber);
		order.setTime(time);
		order.setReturned(returned);
		order.setAddress(address);
		order.setCity(city);
		order.setProvince(province);
		order.setPostalCode(postalCode);
		order.setCountry(country);
		order.setPayment(payment);
		order.setPaymentDetail(paymentDetail);
		order.setMovieIds(movieIds);
		order.setWarehouse(warehouse);
		
		assertEquals(username, order.getUsername());
		assertEquals(orderNumber, order.getOrderNumber());
		assertEquals(time, order.getTime());
		assertEquals(returned, order.getReturned());
		assertEquals(address, order.getAddress());
		assertEquals(city, order.getCity());
		assertEquals(province, order.getProvince());
		assertEquals(postalCode, order.getPostalCode());
		assertEquals(country, order.getCountry());
		assertEquals(payment, order.getPayment());
		assertEquals(paymentDetail, order.getPaymentDetail());
		assertEquals(movieIds, order.getMovieIds());
		assertEquals(warehouse, order.getWarehouse());
	}
	
	@Test
	void test3() {
		String username = "larry";
		long orderNumber = 192048L;
		long time = 32493085930285L;
		int returned = 1;
		String address = "1 Larry St";
		String city = "North York";
		String province = "Ontario";
		String postalCode = "M3N 9F3";
		String country = "Canada";
		String payment = "creditCard";
		String paymentDetail = "39439289438025930";
		String movieIds = "10001,10002";
		String warehouse = "WH1";
		Order order = new Order(username, orderNumber, time, returned, address, city,
				province, postalCode, country, payment, paymentDetail, movieIds, warehouse);
		assertEquals("larry;192048;32493085930285;1;1 Larry St;North York;Ontario;M3N 9F3;Canada;creditCard;39439289438025930;10001,10002;WH1\n", order.toString());
	}

}

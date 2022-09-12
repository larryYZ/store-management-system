package org.openjfx.eecs3311_project;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class MovieTest {

	@Test
	void test1() {
		long id = 32985L;
		String title = "New movie";
		int quantity = 20;
		String genre = "Action";
		String releaseDate = "2021/12/06";
		String actors = "Emily";
		String directors = "Larry";
		String description = "some text";
		
		Movie movie = new Movie(id, title, quantity, genre, releaseDate, actors, directors, description);
		assertEquals(id, movie.getId());
		assertEquals(title, movie.getTitle());
		assertEquals(quantity, movie.getQuantity());
		assertEquals(genre, movie.getGenre());
		assertEquals(releaseDate, movie.getReleaseDate());
		assertEquals(actors, movie.getActors());
		assertEquals(directors, movie.getDirectors());
		assertEquals(description, movie.getDescription());
	}
	
	@Test
	void test2() {
		long id = 32985L;
		String title = "New movie";
		int quantity = 20;
		String genre = "Action";
		String releaseDate = "2021/12/06";
		String actors = "Emily";
		String directors = "Larry";
		String description = "some text";
		
		Movie movie = new Movie();
		movie.setId(id);
		movie.setTitle(title);
		movie.setQuantity(quantity);
		movie.setGenre(genre);
		movie.setReleaseDate(releaseDate);
		movie.setActors(actors);
		movie.setDirectors(directors);
		movie.setDescription(description);
		
		assertEquals(id, movie.getId());
		assertEquals(title, movie.getTitle());
		assertEquals(quantity, movie.getQuantity());
		assertEquals(genre, movie.getGenre());
		assertEquals(releaseDate, movie.getReleaseDate());
		assertEquals(actors, movie.getActors());
		assertEquals(directors, movie.getDirectors());
		assertEquals(description, movie.getDescription());
	}
	
	@Test
	void test3() {
		long id = 32985L;
		String title = "New movie";
		int quantity = 20;
		String genre = "Action";
		String releaseDate = "2021/12/06";
		String actors = "Emily";
		String directors = "Larry";
		String description = "some text";
		
		Movie movie = new Movie(id, title, quantity, genre, releaseDate, actors, directors, description);
		movie.decreaseQuantity();
		movie.increaseQuantity();
		
		assertEquals(id, movie.getId());
		assertEquals(title, movie.getTitle());
		assertEquals(quantity, movie.getQuantity());
		assertEquals(genre, movie.getGenre());
		assertEquals(releaseDate, movie.getReleaseDate());
		assertEquals(actors, movie.getActors());
		assertEquals(directors, movie.getDirectors());
		assertEquals(description, movie.getDescription());
	}
	
	@Test
	void test4() {
		long id = 32985L;
		String title = "New movie";
		int quantity = 20;
		String genre = "Action";
		String releaseDate = "2021/12/06";
		String actors = "Emily";
		String directors = "Larry";
		String description = "some text";
		
		Movie movie = new Movie(id, title, quantity, genre, releaseDate, actors, directors, description);
		assertEquals("32985;New movie;20;Action;2021/12/06;Emily;Larry;some text\n", movie.toString());
	}

}

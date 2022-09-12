package org.openjfx.eecs3311_project;

public class Movie {
	private long id;
	private String title;
	private int quantity;
	private String genre;
	private String releaseDate;
	private String actors;
	private String directors;
	private String description;
	
	public Movie() {
		
	}
	
	public Movie(long id, String title, int quantity, String genre, String releaseDate, String actors, String directors, String description) {
		this.id = id;
		this.title = title;
		this.quantity = quantity;
		this.genre = genre;
		this.releaseDate = releaseDate;
		this.actors = actors;
		this.directors = directors;
		this.description = description;
	}
	
	public long getId() {
		return id;
	}


	public void setId(long id) {
		this.id = id;
	}


	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
	
	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	
	public void decreaseQuantity() {
		this.quantity--;
	}
	
	public void increaseQuantity() {
		this.quantity++;
	}

	public String getReleaseDate() {
		return releaseDate;
	}

	public void setReleaseDate(String releaseDate) {
		this.releaseDate = releaseDate;
	}

	public String getActors() {
		return actors;
	}

	public void setActors(String actors) {
		this.actors = actors;
	}

	public String getDirectors() {
		return directors;
	}

	public void setDirectors(String directors) {
		this.directors = directors;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getGenre() {
		return genre;
	}

	public void setGenre(String genre) {
		this.genre = genre;
	}

	@Override
	public String toString() {
		return id + ";" + title + ";" + quantity + ";" + genre + ";" + releaseDate + ";" + actors + ";" + directors + ";" + description + "\n";
	}
	
}

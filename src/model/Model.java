package model;

import java.util.ArrayList;

public class Model {
	private ArrayList<Gebruiker> gebruikers = new ArrayList<>();
	private ArrayList<Movie> films = new ArrayList<>();

	public Model(){
		films.add(new Movie(0, 0, "Reservoir Dogs", null, 0, null, null));
		Movie m = new Movie(0, 0, "Fight Club", null, 0, null, null);
		m.addRating(new Rating(1, null));
		films.add(m);
		gebruikers.add(new Gebruiker("Jelle", "", "Hoffman", "1234", "peddz"));
	}
	public ArrayList<Gebruiker> getGebruikers() {
		return gebruikers;
	}

	public void addGebruiker(Gebruiker gebruiker) {
		gebruikers.add(gebruiker);
	}

	public ArrayList<Movie> getMovies() {
		return films;
	}
	
	public void addMovie(Movie m){
		films.add(m);
	}
	
	
}

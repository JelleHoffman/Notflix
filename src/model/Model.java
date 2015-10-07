package model;

import java.util.ArrayList;

public class Model {
	private ArrayList<Gebruiker> gebruikers = new ArrayList<>();
	private ArrayList<Movie> films = new ArrayList<>();

	public Model(){
		gebruikers.add(new Gebruiker("Jelle", "", "Hoffman", "1234", "peddz"));
		gebruikers.add(new Gebruiker("Luuk", "", "Wellink", "12345", "sportief pookje"));
		films.add(new Movie(0, 1, "Reservoir Dogs", null, 0, null, null));
		Movie m = new Movie(0, 2, "Fight Club", null, 0, null, null);
		m.addRating(new Rating(1, gebruikers.get(1)));
		films.add(m);
		
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

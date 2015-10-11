package model;

import java.util.ArrayList;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "model")
public class Model {
	@XmlElement private ArrayList<Gebruiker> gebruikers = new ArrayList<>();
	@XmlElement private ArrayList<Movie> films = new ArrayList<>();

	public Model(){
		gebruikers.add(new Gebruiker("Jelle", "", "Hoffman", "1234", "peddz"));
		gebruikers.add(new Gebruiker("Luuk", "", "Wellink", "12345", "sportief pookje"));
		films.add(new Movie(films.size()+1, 0105236, "Reservoir Dogs", "5-8-1993", 99, "Quentin Tarantino", "After a simple jewelery heist goes terribly wrong, the surviving criminals begin to suspect that one of them is a police informant."));
		Movie m = new Movie(films.size()+1, 0137523, "Fight Club", "4-11-1999", 139, "David Fincher", "An insomniac office worker, looking for a way to change his life, crosses paths with a devil-may-care soap maker, forming an underground fight club that evolves into something much, much more...");
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

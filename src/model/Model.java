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
		films.add(new Movie(films.size()+1, "tt0105236", "Reservoir Dogs", "5-8-1993", 99, "Quentin Tarantino", "After a simple jewelery heist goes terribly wrong, the surviving criminals begin to suspect that one of them is a police informant."));
		Movie b = (new Movie(films.size()+1, "tt0111161", "The Shawshank Redemption", "2-3-1994",142, "Frank Darabont","Two imprisoned men bond over a number of years, finding solace and eventual redemption through acts of common decency."));
		Movie d = (new Movie(films.size()+1, "tt0068646", "The Godfather", "18-1-1973",175,"Francis Ford Coppola","The aging patriarch of an organized crime dynasty transfers control of his clandestine empire to his reluctant son."));
		Movie f = (new Movie(films.size()+1, "tt0468569","The Dark Knight", "24-7-2008", 152,"Christopher Nolan","When the menace known as the Joker wreaks havoc and chaos on the people of Gotham, the caped crusader must come to terms with one of the greatest psychological tests of his ability to fight injustice."));
		films.add(new Movie(films.size()+1, "tt0050083", "Angry men","1-4-1957",96,"Sidney Lumet","A dissenting juror in a murder trial slowly manages to convince the others that the case is not as obviously clear as it seemed in court."));
		films.add(new Movie(films.size()+1, "tt0108052", "Schindler's List","3-3-1994",195,"Steven Spielberg","In Poland during World War II, Oskar Schindler gradually becomes concerned for his Jewish workforce after witnessing their persecution by the Nazis."));
		films.add(new Movie(films.size()+1, "tt0110912","Pulp Fiction","1-12-1994",154,"Quentin Tarantino","The lives of two mob hit men, a boxer, a gangster's wife, and a pair of diner bandits intertwine in four tales of violence and redemption."));
		films.add(new Movie(films.size()+1, "tt0167260","The Lord of the Rings: The Return of the King","17-12-2003",201,"Peter Jackson","Gandalf and Aragorn lead the World of Men against Sauron's army to draw his gaze from Frodo and Sam as they approach Mount Doom with the One Ring."));
		Movie n = (new Movie(films.size()+1, "tt0080684","Star Wars: Episode V - The Empire Strikes Back","18-12-1980",124,"Irvin Kershner","After the rebels have been brutally overpowered by the Empire on their newly established base, Luke Skywalker takes advanced Jedi training with Master Yoda, while his friends are pursued by Darth Vader as part of his plan to capture Luke."));
		Movie m = new Movie(films.size()+1, "tt0137523", "Fight Club", "4-11-1999", 139, "David Fincher", "An insomniac office worker, looking for a way to change his life, crosses paths with a devil-may-care soap maker, forming an underground fight club that evolves into something much, much more...");
		m.addRating(new Rating(1, gebruikers.get(1)));
		n.addRating(new Rating(2, gebruikers.get(1)));
		b.addRating(new Rating(3, gebruikers.get(1)));
		d.addRating(new Rating(4, gebruikers.get(1)));
		f.addRating(new Rating(2,gebruikers.get(1)));
		films.add(m);
		films.add(n);
		films.add(b);
		films.add(d);
		films.add(f);
		
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

package resources;

import java.util.ArrayList;

import javax.servlet.ServletContext;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import model.Model;
import model.Movie;

@Path("films")
public class FilmsPath {
	@Context ServletContext context;
	
	@GET
	@Path("ratings")
	@Produces(MediaType.APPLICATION_JSON)
	public ArrayList <Movie> getGerateFilms(){
		ArrayList<Movie> ratedMovies = new ArrayList<>();
		Model model = (Model) context.getAttribute("model");
		for(Movie m:model.getMovies()){
			if(!m.getRatings().isEmpty()){
				ratedMovies.add(m);
			}
		}
		return ratedMovies;
	}
 }

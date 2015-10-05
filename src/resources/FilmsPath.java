package resources;

import java.util.ArrayList;

import javax.servlet.ServletContext;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import model.Gebruiker;
import model.Model;
import model.Movie;

@Path("films")
public class FilmsPath {
	@Context ServletContext context;
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getFilms(@HeaderParam("Authorization") String accessToken){
		Model model = (Model) context.getAttribute("model");
		if(accessTokenExcist(accessToken)){
			return Response.ok(model.getMovies()).build();
		}
		return Response.status(401).build();
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getFilm(@QueryParam("imdb-nummer")int nummer,
			@HeaderParam("Authorization") String accessToken){
		Model model = (Model) context.getAttribute("model");
		if(accessTokenExcist(accessToken)){
			for (Movie m: model.getMovies()){
				if (m.getiMDBNummer()==nummer){
					return Response.ok(m).build();
				}
			}
			return Response.status(404).build();
		}
		return Response.status(401).build();
		
	}
	
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
	
	private boolean accessTokenExcist(String accessToken){
		Model model = (Model) context.getAttribute("model");
		for(Gebruiker g:model.getGebruikers()){
			if(g.getAccessToken().equals(accessToken)){
				return true;
			}
		}
		
		return false;
	}
 }

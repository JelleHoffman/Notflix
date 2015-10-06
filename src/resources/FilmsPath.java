package resources;

import java.util.ArrayList;

import javax.servlet.ServletContext;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import model.Gebruiker;
import model.Model;
import model.Movie;
import model.Rating;

@Path("films")
public class FilmsPath {
	@Context ServletContext context;
	
	@GET
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public Response getFilms(@HeaderParam("Authorization") String accessToken){
		Model model = (Model) context.getAttribute("model");
		if(accessTokenExcist(accessToken)){
			return Response.ok(model.getMovies()).build();
		}
		return Response.status(401).build();
	}
	
	@GET
	@Path("{imdb-nummer}")
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public Response getFilm(@PathParam("imdb-nummer")int nummer,
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
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
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
	
	@PUT
	@Path("{imdb-nummer}/rate")
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public Response rateFilm(@PathParam("imdb-nummer") int nummer,
			@HeaderParam("Authorization") String accessToken,
			@FormParam("rating") double rating){
		Model model = (Model) context.getAttribute("model");
		for(Gebruiker g:model.getGebruikers()){
			if(g.getAccessToken().equals(accessToken)){
				for (Movie m: model.getMovies()){
					if (m.getiMDBNummer()==nummer){
						m.addRating(new Rating(
								rating,
								g));
						return Response.ok(m).build();
					}
				}
				return Response.status(404).build();
			}
		}
		return Response.status(401).build();
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

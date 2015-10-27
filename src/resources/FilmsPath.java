package resources;

import java.io.Console;
import java.util.ArrayList;

import javax.servlet.ServletContext;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
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
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import model.Gebruiker;
import model.Model;
import model.Movie;
import model.Rating;

@Path("/film")
public class FilmsPath {
	@Context ServletContext context;
	
	@GET
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public Response getFilms(@HeaderParam("Authorization") String accessToken){
		Model model = (Model) context.getAttribute("model");
		if(accessTokenExcist(accessToken)){
			GenericEntity<ArrayList<Movie>> entity= new GenericEntity<ArrayList<Movie>>(model.getMovies()){};
			return Response.ok(entity).build();
		}
		
		return Response.status(401).build();
	}
	
	@GET
	@Path("{imdb-nummer}")
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public Response getFilm(@PathParam("imdb-nummer")String nummer,
			@HeaderParam("Authorization") String accessToken){
		System.out.println("imdb nummer in get film"+accessToken);
		Model model = (Model) context.getAttribute("model");
		if(accessTokenExcist(accessToken)){
			for (Movie m: model.getMovies()){
				if (m.getiMDBNummer().equals(nummer)){
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
	@Path("rate/{imdb-nummer}")
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public Response rateFilm(@PathParam("imdb-nummer") String nummer,
			@HeaderParam("Authorization") String accessToken,
			@FormParam("rating") double rating){
		Model model = (Model) context.getAttribute("model");
		if(rating>=0.5&&rating<=5){
			if(accessTokenExcist(accessToken)){
				for(Gebruiker g:model.getGebruikers()){
					if(g.getAccessToken().equals(accessToken)){
						for (Movie m: model.getMovies()){
							if (m.getiMDBNummer().equals(nummer)){
								for(Rating r:m.getRatings()){
									if(r.getGebruiker().equals(g)){
										r.setRating(rating);
										return Response.ok(m).build();
									}
								}
								m.addRating(new Rating(
										rating,
										g));
								return Response.ok(m).build();
							}
						}
						return Response.status(404).build();
					}
				}
			}
			return Response.status(401).build();
		}
		return Response.status(403).build();	
	}
	
	@DELETE
	@Path("rate/{imdb-nummer}")
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public Response deleteRate(@PathParam("imdb-nummer") String nummer,
			@HeaderParam("Authorization") String accessToken){
		Model model = (Model) context.getAttribute("model");
		if(accessTokenExcist(accessToken)){
			for(Gebruiker g:model.getGebruikers()){
				if(g.getAccessToken().equals(accessToken)){
					for (Movie m: model.getMovies()){
						if (m.getiMDBNummer().equals(nummer)){
							for(Rating r:m.getRatings()){
								if(r.getGebruiker().equals(g)){
									if(m.deleteRating(r)){
										return Response.ok(m).build();
									}else{
										return Response.status(500).build();
									}
								}
							}
							return Response.status(403).build();
						}
					}
					return Response.status(404).build();
				}
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

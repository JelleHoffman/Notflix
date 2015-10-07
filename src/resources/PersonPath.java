package resources;

import java.util.ArrayList;
import java.util.Random;

import javax.servlet.ServletContext;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
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

@Path("/gebruikers")
public class PersonPath {
	@Context ServletContext context;

	@POST
	@Path("/add-gebruiker")
	@Consumes({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public Response addGebruiker(Gebruiker gebruiker, @HeaderParam ("Wachtwoord") String wachtwoord) {
		Model model = (Model) context.getAttribute("model");
		Gebruiker addGebruiker = gebruiker;
		addGebruiker.setWachtwoord(wachtwoord);
		model.addGebruiker(addGebruiker);
		return Response.ok(addGebruiker).build();
	}

	@GET
	@Path("get-accesstoken")
	@Produces(MediaType.TEXT_PLAIN)
	public Response getAccessToken(@HeaderParam("Nickname") String nickname, 
			@HeaderParam("Wachtwoord") String wachtwoord) {
		Model model = (Model) context.getAttribute("model");
		System.out.println("hij komt hier wel");
		for (Gebruiker g : model.getGebruikers()) {
			if (g.getNickname().equals(nickname)&&g.geefWachtwoord().equals(wachtwoord)) {
				g.setAccessToken(getRandomToken());
				System.out.println(g.getAccessToken());
				return Response.ok(g.getAccessToken()).build();
			}
		}
		return Response.status(401).build();
	}

	@GET
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public Response getGebruikers(@HeaderParam("Authorization") String accessToken) {
		Model model = (Model) context.getAttribute("model");
		if (accessTokenExcist(accessToken)) {
			ArrayList<Gebruiker> gebruikers = model.getGebruikers();
			GenericEntity<ArrayList<Gebruiker>> entity= new GenericEntity<ArrayList<Gebruiker>>(model.getGebruikers()){};
			return Response.ok(entity).build();
		}
		return Response.status(401).build();

	}
	
	@GET
	@Path("get-gebruiker")
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public Response getGebruiker(@QueryParam("nickname") String nickname,
			@HeaderParam("Authorization") String accessToken) {
		Model model = (Model) context.getAttribute("model");
		if(accessTokenExcist(accessToken)){
			for(Gebruiker g : model.getGebruikers()) {
				if(g.getNickname().equals(nickname)) {
					return Response.ok(g).build();
				}
			}
			return Response.status(404).build();
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
	
	private String getRandomToken(){
		String mogelijkheden ="abcdefghijklmnopqrstuvwxyz0123456789";
		String accessToken = "";
		Random r = new Random();
		for (int i = 0;i<10;i++){
			accessToken += mogelijkheden.charAt(r.nextInt(mogelijkheden.length()));
		}
		return accessToken;
	}
}

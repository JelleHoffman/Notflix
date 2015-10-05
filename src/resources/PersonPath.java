package resources;

import javax.servlet.ServletContext;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import model.Gebruiker;
import model.Model;

@Path("/gebruikers")
public class PersonPath {
	@Context ServletContext context;
	
	@POST
	@Path("/add-gebruiker")
	@Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	@Produces(MediaType.APPLICATION_JSON)
	public Gebruiker addGebruiker(Gebruiker gebruiker){
		Model model = (Model) context.getAttribute("model");
		System.out.println(gebruiker.getWachtwoord());
		model.addGebruiker(gebruiker);
		return gebruiker;
	}
	
	@GET
	@Path("get-accesstoken")
	@Produces(MediaType.TEXT_PLAIN)
	public String getAccessToken(@QueryParam("nickname")String nickname){
		Model model = (Model) context.getAttribute("model");
		for(Gebruiker g: model.getGebruikers()){
			if(g.getNickname().equals(nickname)){
				return g.getAccessToken();
			}
		}
		return null;
	}
}

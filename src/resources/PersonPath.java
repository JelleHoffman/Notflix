package resources;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import model.Gebruiker;
import model.Model;

@Path("/gebruikers")
public class PersonPath {
	
	
	@POST
	@Path("/add-gebruiker")
	@Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	@Produces(MediaType.APPLICATION_JSON)
	public Gebruiker addGebruiker(Gebruiker gebruiker){
		Model model = new Model();
		System.out.println(gebruiker.getWachtwoord());
		model.addGebruiker(gebruiker);
		return gebruiker;
	}
}

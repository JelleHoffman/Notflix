package resources;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;





@Path("firstget")
public class FirstGet {
	
	@GET
	public Response getFunction(){
		ResponseBuilder response = Response.ok();
		return response.build();
	}
}

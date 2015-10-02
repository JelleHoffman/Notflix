import javax.ws.rs.ApplicationPath;

import org.glassfish.jersey.server.ResourceConfig;

@ApplicationPath("api")
public class Test extends ResourceConfig{
	public Test(){
		super();
		packages("resources");
		register(com.fasterxml.jackson.jaxrs.json.JacksonJsonProvider.class);
	}
}

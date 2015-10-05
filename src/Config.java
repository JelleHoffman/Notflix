import javax.ws.rs.ApplicationPath;

import org.glassfish.jersey.server.ResourceConfig;

@ApplicationPath("api")
public class Config extends ResourceConfig{
	public Config(){
		super();
		packages("resources");
		register(com.fasterxml.jackson.jaxrs.json.JacksonJsonProvider.class);
	}
}

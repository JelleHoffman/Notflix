import javax.ws.rs.ApplicationPath;

import org.glassfish.jersey.server.ResourceConfig;

@ApplicationPath("api")
public class Config extends ResourceConfig{
	public Config(){
		super();
		System.out.println("test");
		packages("resources");
		
		for(Class<?> c : this.getClasses()) {
			System.out.println(c.getName());
		}
		register(com.fasterxml.jackson.jaxrs.json.JacksonJsonProvider.class);
	}
}

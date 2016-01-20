package com.sample.patterns.runtimeconfiguration;

import java.io.IOException;
import java.net.URI;

import javax.ws.rs.core.UriBuilder;

import org.glassfish.grizzly.http.server.HttpServer;

import com.sun.jersey.api.container.grizzly2.GrizzlyServerFactory;
import com.sun.jersey.api.core.PackagesResourceConfig;
import com.sun.jersey.api.core.ResourceConfig;
import com.sun.jersey.api.json.JSONConfiguration;

public class EmbededService {

	

	private static URI getBaseURI() {
		return UriBuilder.fromUri("http://localhost/").port(9998).build();
	}

	public static final URI BASE_URI = getBaseURI();

	protected static HttpServer startServer() throws IOException {
		System.out.println("Starting grizzly...");
		ResourceConfig rc = new PackagesResourceConfig(
				"com.cisco.patterns.yaml");
		rc.getFeatures().put(JSONConfiguration.FEATURE_POJO_MAPPING, true);
		HttpServer httpServer = GrizzlyServerFactory.createHttpServer(BASE_URI,
				rc);
		return httpServer;
	}

	public static void main(String[] args) throws IOException {
		HttpServer httpServer = startServer();
		System.out
				.println(String
						.format("Jersey app started with WADL available at "
								+ "%sapplication.wadl\nTry out %shelloworld\nHit enter to stop it...",
								BASE_URI, BASE_URI));
		System.in.read();
		httpServer.stop();
	}

	
}

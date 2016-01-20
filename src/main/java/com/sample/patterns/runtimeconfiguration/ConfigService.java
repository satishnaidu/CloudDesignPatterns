package com.sample.patterns.runtimeconfiguration;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

@Path("/api")
public class ConfigService {

	@GET
	@Path("{service}")
	@Produces({ MediaType.APPLICATION_JSON })
	public Response getConfigProps(@PathParam("service") String serviceName) {

		System.out.println("getConfigProps entered");
		ConfigServiceImpl configServiceImpl = new ConfigServiceImpl();
		ConfigProps configProps = configServiceImpl
				.getConfigService(serviceName);

		return Response.ok().entity(configProps).build();
	}

	@POST
	@Path("{service}")
	@Produces({ MediaType.APPLICATION_JSON })
	public ConfigProps updateConfigProps(ConfigProps configProps) {
		ConfigServiceImpl configServiceImpl = new ConfigServiceImpl();
		configProps = configServiceImpl.updateConfigService(configProps);
		return configProps;
	}

	@POST
	@Path("/service/{serviceName}")
	@Produces({ MediaType.APPLICATION_JSON })
	public Response registerService(
			@PathParam("serviceName") String serviceName, String serviceUrl) {
		ConfigServiceImpl configServiceImpl = new ConfigServiceImpl();
		configServiceImpl.registerService(serviceName, serviceUrl);
		return Response.ok().entity(Status.OK).build();
	}

	@POST
	@Path("/service")
	@Produces({ MediaType.APPLICATION_JSON })
	public Response unRegisterService(
			@QueryParam("serviceName") String serviceName) {
		ConfigServiceImpl configServiceImpl = new ConfigServiceImpl();
		configServiceImpl.unRegisterService(serviceName);
		return Response.ok().entity(Status.OK).build();
	}
	
	
}

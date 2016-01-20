package com.sample.patterns.runtimeconfiguration;


public class JerseyEmbeddedHTTPServer {
//	public static void main(String[] args) throws IOException {
//		System.out
//				.println("Starting Crunchify's Embedded Jersey HTTPServer...\n");
//		HttpServer crunchifyHTTPServer = createHttpServer();
//		crunchifyHTTPServer.start();
//		System.out.println(String.format(
//				"\nJersey Application Server started with WADL available at "
//						+ "%sapplication.wadl\n", getCrunchifyURI()));
//		System.out
//				.println("Started Crunchify's Embedded Jersey HTTPServer Successfully !!!");
//	}
//
//	private static HttpServer createHttpServer() throws IOException {
//		ResourceConfig crunchifyResourceConfig = new PackagesResourceConfig(
//				"com.crunchify.tutorial");
//		// This tutorial required and then enable below line:
//		// http://crunfy.me/1DZIui5
//		// crunchifyResourceConfig.getContainerResponseFilters().add(CrunchifyCORSFilter.class);
//		return HttpServerFactory.create(getCrunchifyURI(),
//				crunchifyResourceConfig);
//	}
//
//	private static URI getCrunchifyURI() {
//		return UriBuilder.fromUri("http://" + crunchifyGetHostName() + "/")
//				.port(8085).build();
//	}
//
//	private static String crunchifyGetHostName() {
//		String hostName = "localhost";
//		try {
//			hostName = InetAddress.getLocalHost().getCanonicalHostName();
//		} catch (UnknownHostException e) {
//			e.printStackTrace();
//		}
//		return hostName;
//	}

}

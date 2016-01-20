package com.sample.patterns.runtimeconfiguration;

import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

import org.yaml.snakeyaml.Yaml;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;

public class ConfigServiceImpl {

	private Map<String, String> registerdServices = new HashMap<String, String>();

	public static void main(String[] args) throws IOException {

		String filePath = "C:/Cisco/Projects/CodeBase/GIT_REPORTS/Checkin/reportframework/src/main/java/com/cisco/vcs/reports/restIF/sample.yaml";
		ConfigServiceImpl service = new ConfigServiceImpl();
		service.getConfigService("reports");
	}

	public ConfigServiceImpl() {
		// ClientConfig clientConfig = new DefaultClientConfig();
		// clientConfig.getFeatures().put(JSONConfiguration.FEATURE_POJO_MAPPING,
		// Boolean.TRUE);
		// Client client = Client.create(clientConfig);
	}

	public ConfigProps getConfigService(String serviceName) {
		String filePath = "C:/Cisco/Projects/CodeBase/GIT_REPORTS/Checkin/reportframework/src/main/java/com/cisco/vcs/reports/restIF/sample.yaml";
		Yaml yaml = new Yaml();
		ConfigProps configProps = new ConfigProps();
		try (InputStream in = Files.newInputStream(Paths.get(filePath))) {

			configProps = yaml.loadAs(in, ConfigProps.class);
			System.out.println(configProps);

			Service service = configProps.getService();
			service.setServiceName("alarms");
			FileWriter fileWriter = new FileWriter(filePath);
			yaml.dump(configProps, fileWriter);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return configProps;
	}

	public ConfigProps updateConfigService(ConfigProps configProps) {
		String filePath = "C:/Cisco/Projects/CodeBase/GIT_REPORTS/Checkin/reportframework/src/main/java/com/cisco/vcs/reports/restIF/sample.yaml";
		Yaml yaml = new Yaml();
		FileWriter fileWriter = null;
		try {
			fileWriter = new FileWriter(filePath);
			yaml.dump(configProps, fileWriter);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		notifyObservers();
		return configProps;
	}

	public void registerService(String serviceName, String serviceUrl) {
		String filePath = "C:/Cisco/Projects/CodeBase/GIT_REPORTS/Checkin/reportframework/src/main/java/com/cisco/vcs/reports/restIF/sample.yaml";
		ConfigProps configProps = new ConfigProps();
		Yaml yaml = new Yaml();

		try (InputStream in = Files.newInputStream(Paths.get(filePath))) {

			configProps = yaml.loadAs(in, ConfigProps.class);
			System.out.println(configProps);

			Map<String, String> registerdServices = configProps
					.getRegisterdServices();
			registerdServices.put(serviceName, serviceUrl);
			configProps.setRegisterdServices(registerdServices);
			FileWriter fileWriter = new FileWriter(filePath);
			yaml.dump(configProps, fileWriter);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("Registered services: " + registerdServices);
	}

	public void unRegisterService(String serviceName) {

		String filePath = "C:/Cisco/Projects/CodeBase/GIT_REPORTS/Checkin/reportframework/src/main/java/com/cisco/vcs/reports/restIF/sample.yaml";
		ConfigProps configProps = new ConfigProps();
		Yaml yaml = new Yaml();

		try (InputStream in = Files.newInputStream(Paths.get(filePath))) {

			configProps = yaml.loadAs(in, ConfigProps.class);
			System.out.println(configProps);

			Map<String, String> registerdServices = configProps
					.getRegisterdServices();
			registerdServices.remove(serviceName);
			configProps.setRegisterdServices(registerdServices);
			FileWriter fileWriter = new FileWriter(filePath);
			yaml.dump(configProps, fileWriter);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("Registered services: " + registerdServices);
	}

	public void notifyObservers() {

		System.out.println("before calling notify observers");
		try {
			Client client = Client.create();
			// Get
			WebResource webResource = client
					.resource("http://localhost:9999/api/reports");
			ClientResponse response = webResource.accept("application/json")
					.get(ClientResponse.class);
			if (response.getStatus() != 200) {
				throw new RuntimeException("Failed : HTTP error code : "
						+ response.getStatus());
			}

			StringBuilder inputStringBuilder = new StringBuilder();
			BufferedReader bufferedReader;

			bufferedReader = new BufferedReader(new InputStreamReader(
					response.getEntityInputStream(), "UTF-8"));

			String line = bufferedReader.readLine();
			while (line != null) {
				inputStringBuilder.append(line);
				inputStringBuilder.append('\n');
				line = bufferedReader.readLine();
			}
			String output = inputStringBuilder.toString();

			// String output = response.getEntity(String.class);
			// Employee output = response.getEntity(Employee.class);//Get the
			// object
			// from the response
			System.out.println("Output json client .... \n");
			System.out.println(output);

			String filePath = "C:/Cisco/Projects/CodeBase/GIT_REPORTS/Checkin/reportframework/src/main/java/com/cisco/vcs/reports/restIF/sample.yaml";

			ConfigProps configProps = new ConfigProps();
			Yaml yaml = new Yaml();

			InputStream in = Files.newInputStream(Paths.get(filePath));

			configProps = yaml.loadAs(in, ConfigProps.class);
			System.out.println(configProps);

			Map<String, String> registerdServices = configProps
					.getRegisterdServices();

			for (String service : registerdServices.values()) {

				// Post
				webResource = client.resource(service);
				webResource.accept("application/json").post(
						ClientResponse.class, output);
			}
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}

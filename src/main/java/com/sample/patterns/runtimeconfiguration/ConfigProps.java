package com.sample.patterns.runtimeconfiguration;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class ConfigProps {

	private Date released;
	private String version;
	private String environment;
	private Connection connection;
	private Service service;

	private Map<String, String> registerdServices = new HashMap<>();

	public Date getReleased() {
		return released;
	}

	public void setReleased(Date released) {
		this.released = released;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getEnvironment() {
		return environment;
	}

	public void setEnvironment(String environment) {
		this.environment = environment;
	}

	public Connection getConnection() {
		return connection;
	}

	public void setConnection(Connection connection) {
		this.connection = connection;
	}

	public Service getService() {
		return service;
	}

	public void setService(Service service) {
		this.service = service;
	}

	public Map<String, String> getRegisterdServices() {
		return registerdServices;
	}

	public void setRegisterdServices(Map<String, String> registerdServices) {
		this.registerdServices = registerdServices;
	}

	@Override
	public String toString() {
		return "ConfigProps [released=" + released + ", version=" + version
				+ ", environment=" + environment + ", connection=" + connection
				+ ", service=" + service + ", registerdServices="
				+ registerdServices + "]";
	}

}

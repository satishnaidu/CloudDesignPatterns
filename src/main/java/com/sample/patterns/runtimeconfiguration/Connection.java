package com.sample.patterns.runtimeconfiguration;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="connection")
public class Connection {

	private String driverClass;
	private String url;
	private String username;
	private String password;

	public String getDriverClass() {
		return driverClass;
	}

	public void setDriverClass(String driverClass) {
		this.driverClass = driverClass;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public String toString() {
		return "Connection [driverClass=" + driverClass + ", url=" + url
				+ ", username=" + username + ", password=" + password + "]";
	}

}

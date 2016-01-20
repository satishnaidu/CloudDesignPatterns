package com.sample.patterns.runtimeconfiguration;

import java.util.HashMap;
import java.util.Map;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement()
public class Service {

	private String serviceName;
	private String servicePath;

	private Map<String, String> serviceprops = new HashMap<String,String>();

	public Map<String, String> getServiceprops() {
		return serviceprops;
	}

	public void setServiceprops(Map<String, String> serviceprops) {
		this.serviceprops = serviceprops;
	}

	public String getServiceName() {
		return serviceName;
	}

	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}

	public String getServicePath() {
		return servicePath;
	}

	public void setServicePath(String servicePath) {
		this.servicePath = servicePath;
	}

	@Override
	public String toString() {
		return "Service [serviceName=" + serviceName + ", servicePath="
				+ servicePath + ", serviceprops=" + serviceprops + "]";
	}

}

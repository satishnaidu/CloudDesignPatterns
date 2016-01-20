package com.sample.patterns.runtimeconfiguration;

import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.yaml.snakeyaml.Yaml;

public class ServicePropertiesImpl {

	public static void main(String[] args) throws IOException {

		String filePath = "C:/Cisco/Projects/CodeBase/GIT_REPORTS/Checkin/reportframework/src/main/java/com/cisco/vcs/reports/restIF/sample.yaml";
		Yaml yaml = new Yaml();
		try (InputStream in = Files.newInputStream(Paths.get(filePath))) {

			ConfigProps configService = yaml.loadAs(in, ConfigProps.class);
			System.out.println(configService);

			configService.setEnvironment("dev");
			Service service = configService.getService();
			service.setServiceName("alarms");
			FileWriter fileWriter = new FileWriter(filePath);
			yaml.dump(configService, fileWriter);
		}

	}

}

package com.otp.app.template;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Map;
import java.util.stream.Collectors;

public class EmailTemplate {

	private String template;

	public EmailTemplate(String customTemplate) {
		try {
			this.template = loadTemplate(customTemplate);
		} catch (Exception e) {
			this.template = "Empty Template";
		}
	}

	private String loadTemplate(String customTemplate) throws Exception {
		ClassLoader classLoader = getClass().getClassLoader();
		InputStream inputStream = classLoader.getResourceAsStream(customTemplate);
		if (inputStream == null) {
			throw new Exception("Could not find template with name " + customTemplate);
		}
		String content = "Empty Template";
		try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
			content = reader.lines().collect(Collectors.joining(System.lineSeparator()));
		} catch (IOException e) {
			throw new Exception("Could not read template with name " + customTemplate);
		}
		return content;
	}

	public String getTemplate(Map<String, String> replacements) {
		String cTemplate = this.template;
		// Replace the String
		for (Map.Entry<String, String> entry : replacements.entrySet()) {
			cTemplate = cTemplate.replace("{{" + entry.getKey() + "}}", entry.getValue());
		}
		return cTemplate;
	}
}
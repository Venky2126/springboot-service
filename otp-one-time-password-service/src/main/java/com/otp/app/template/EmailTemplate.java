package com.otp.app.template;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Map;

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
		File file = new File(classLoader.getResource(customTemplate).getFile());
		String content = "Empty Template";
		try {
			content = new String(Files.readAllBytes(file.toPath()));
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

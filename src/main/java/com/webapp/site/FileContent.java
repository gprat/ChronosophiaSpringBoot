package com.webapp.site;

public class FileContent {

	private String filename;
	
	private String description;
	
	private String username;

	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public FileContent(String filename, String description, String username) {
		super();
		this.filename = filename;
		this.description = description;
		this.username = username;
	}
	
	
}

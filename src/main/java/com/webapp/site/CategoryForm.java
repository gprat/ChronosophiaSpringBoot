package com.webapp.site;

import jakarta.validation.constraints.*;

public class CategoryForm {

	private String CategoryName;

	public String getCategoryName() {
		return CategoryName;
	}

	public void setCategoryName(String categoryName) {
		CategoryName = categoryName;
	}
	
	
}

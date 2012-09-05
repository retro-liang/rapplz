package com.retro.rapplz.db.dao;

import java.util.List;

import com.retro.rapplz.db.entity.Category;

public interface CategoryDao
{
	public Category getCategoryById(Long id);
	
	public Category getCategoryByName(String name);

	public List<Category> getCategories();
	
	public void save(Category category);
	
	public void remove(Long id);
}
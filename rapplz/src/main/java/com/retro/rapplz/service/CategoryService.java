package com.retro.rapplz.service;

import java.util.Set;

import com.retro.rapplz.db.entity.Category;
import com.retro.rapplz.service.exception.ApplicationServiceException;

public interface CategoryService
{
	public Set<Category> getCategories() throws ApplicationServiceException;
}
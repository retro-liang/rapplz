package com.retro.rapplz.service;

import java.util.Set;

import com.retro.rapplz.db.entity.Category;
import com.retro.rapplz.service.exception.ApplicationServiceException;
import com.retro.rapplz.web.dto.CategoryInfo;

public interface CategoryService
{
	public Category getCategoryById(Long id) throws ApplicationServiceException;
	public Set<Category> getCategories() throws ApplicationServiceException;
	public Set<CategoryInfo> getCategoryInfos() throws ApplicationServiceException;
}
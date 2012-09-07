package com.retro.rapplz.service;

import java.util.HashSet;
import java.util.Set;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.retro.rapplz.db.dao.CategoryDao;
import com.retro.rapplz.db.entity.Category;
import com.retro.rapplz.service.exception.ApplicationServiceException;
import com.retro.rapplz.web.dto.CategoryInfo;

@Service("categoryService")
@Transactional
public class CategoryServiceImpl implements CategoryService
{
	private static final Logger logger = Logger.getLogger(CategoryServiceImpl.class.getName());
	
	@Autowired
	private CategoryDao categoryDao;
	
	@Override
	@Transactional(readOnly = true)
	public Category getCategoryById(Long id) throws ApplicationServiceException
	{
		return (Category) categoryDao.loadById(Category.class, id);
	}
	
	@Override
	@Transactional(readOnly = true)
	public Set<Category> getCategories() throws ApplicationServiceException
	{
		return new HashSet<Category>(categoryDao.list(Category.class));
	}
	
	@Override
	@Transactional(readOnly = true)
	public Set<CategoryInfo> getCategoryInfos() throws ApplicationServiceException
	{
		Set<CategoryInfo> categoryInfos = new HashSet<CategoryInfo>();
		for(Object category : categoryDao.list(Category.class))
		{
			CategoryInfo categoryInfo = new CategoryInfo();
			categoryInfo.setId(((Category)category).getId().toString());
			categoryInfo.setName(((Category)category).getName());
			categoryInfos.add(categoryInfo);
		}
		return categoryInfos;
	}
}

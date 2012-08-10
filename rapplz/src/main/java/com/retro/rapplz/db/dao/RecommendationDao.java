package com.retro.rapplz.db.dao;

import java.util.List;

import com.retro.rapplz.db.entity.Recommendation;

public interface RecommendationDao
{
	public Recommendation getRecommendation(Long id);
	
	public List<Recommendation> getCategories();
	
	public void save(Recommendation recommendation);
	
	public void remove(Long id);
}
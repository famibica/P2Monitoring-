package com.sap.p2monitoring.dao;

import java.util.Collection;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.sap.p2monitoring.model.TaggingRules;

@Repository
public class TaggingRulesDAO {

	@PersistenceContext
	private EntityManager em;

	public Collection<TaggingRules> getAllTaggingRules() {
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<TaggingRules> criteria = cb.createQuery(TaggingRules.class);
		Collection<TaggingRules> resultList = em.createQuery(criteria).getResultList();
		return resultList;
	}
	
	//Just a way to find jobs that have the same specific date, time doesn't matter here
	public TaggingRules getByTagName(String tagName) {
		Query query = em.createQuery("SELECT c FROM TaggingRules c WHERE c.tagName =?1", TaggingRules.class);
		query.setParameter(1, tagName);
		query.setMaxResults(1);
			
		try {
			return (TaggingRules) query.getSingleResult();
		} catch (Exception e) {
			return null;
		}
	}
		
	/*
	 * Transactional methods
	 */

	@Transactional
	public boolean createTaggingRules(TaggingRules taggingRules) {
		try {
			em.persist(taggingRules);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	@Transactional
	public void deleteTaggingRules(String tagName) {
		TaggingRules taggingRules = getByTagName(tagName);

		if (taggingRules != null) {
			em.remove(taggingRules);
		}
	}

	@Transactional
	public TaggingRules updateTaggingRules(TaggingRules newTaggingRules) {
		TaggingRules taggingRules = getByTagName(newTaggingRules.getTagName());
		
		taggingRules.setTagName(newTaggingRules.getTagName());
		taggingRules.setSystem(newTaggingRules.getSystem());
		taggingRules.setDescription(newTaggingRules.getDescription());
		taggingRules.setRule1(newTaggingRules.getRule1());
		taggingRules.setRule2(newTaggingRules.getRule2());
		taggingRules.setRule3(newTaggingRules.getRule3());
		taggingRules.setRule4(newTaggingRules.getRule4());
		taggingRules.setRule5(newTaggingRules.getRule5());
		
		em.merge(taggingRules);
		
		return taggingRules;
	}
}

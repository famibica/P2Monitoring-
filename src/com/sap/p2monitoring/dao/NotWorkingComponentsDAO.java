package com.sap.p2monitoring.dao;

import java.util.Collection;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.sap.p2monitoring.model.NotWorkingComponents;

@Repository
public class NotWorkingComponentsDAO {

	@PersistenceContext
	private EntityManager em;

	public Collection<NotWorkingComponents> getAllComponents() {
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<NotWorkingComponents> criteria = cb.createQuery(NotWorkingComponents.class);
		Collection<NotWorkingComponents> resultList = em.createQuery(criteria).getResultList();
		return resultList;
	}
	
	//Just a way to find jobs that have the same specific date, time doesn't matter here
	public NotWorkingComponents getByComponentCode(String componentCode) {
		Query query = em.createQuery("SELECT c FROM NotWorkingComponents c WHERE c.componentCode LIKE ?1", NotWorkingComponents.class);
		query.setParameter(1, componentCode + "%");
		query.setMaxResults(1);
			
		try {
			return (NotWorkingComponents) query.getSingleResult();
		} catch (Exception e) {
			return null;
		}
	}
	
	/*
	 * Transactional methods
	 */

	@Transactional
	public boolean createComponent(NotWorkingComponents component) {
		try {
			em.persist(component);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	@Transactional
	public void deleteComponent(String componentCode) {
		NotWorkingComponents component = getByComponentCode(componentCode);

		if (component != null) {
			em.remove(component);
		}
	}

	@Transactional
	public NotWorkingComponents updateComponent(NotWorkingComponents newComponent) {
		NotWorkingComponents component = getByComponentCode(newComponent.getComponentCode());
		
		component.setComponentCode(newComponent.getComponentCode());
		em.merge(component);
		
		return component;
	}
}

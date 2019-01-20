package com.sap.p2monitoring.dao;

import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.sap.p2monitoring.model.DevComp;
import com.sap.p2monitoring.model.ids.DevCompID;

@Repository
public class DevCompDAO {

	@PersistenceContext
	private EntityManager em;

	public Collection<DevComp> getAllDevs() {
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<DevComp> criteria = cb.createQuery(DevComp.class);
		Collection<DevComp> resultList = em.createQuery(criteria).getResultList();
		return resultList;
	}
	
	//Search By Comp or ID, Like, meaning it accepts close range names or substrings
	public List<DevComp> getDevByComponentOrDeveloper(String componentOrDeveloper) {
		TypedQuery<DevComp> query = em.createQuery("SELECT c FROM DevComp c WHERE c.developerID LIKE ?1 OR c.component LIKE ?1", DevComp.class);
		query.setParameter(1, componentOrDeveloper);
		
		return query.getResultList();
	}
	
	//Search By Dev ID
	public List<DevComp> getDevByID(String developerID) {
		TypedQuery<DevComp> query = em.createQuery("SELECT c FROM DevComp c WHERE c.developerID =?1 ", DevComp.class);
		query.setParameter(1, developerID);
		
		return query.getResultList();
	}
	
	//Search By Component
	public List<DevComp> getDevByComponent(String component) {
		TypedQuery<DevComp> query = em.createQuery("SELECT c FROM DevComp c WHERE c.component =?1 ", DevComp.class);
		query.setParameter(1, component);
			
		return query.getResultList();
	}

	//Search relation between DeveloperID and Component
	public DevComp getByIDAndComponent(String developerID, String component) {
		// search if there is an developer with this developerID and Component
		// passed on the call
		// if there is, return incident
		// if there isn't, return null
		DevCompID PK = new DevCompID();
		PK.setDeveloperID(developerID);
		PK.setComponent(component);

		DevComp devComp = em.find(DevComp.class, PK);
		
		return devComp;
	}

	/*
	 * Transactional methods
	 */

	@Transactional
	public boolean createDevComp(DevComp devComp) {
		try {
			em.persist(devComp);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	@Transactional
	public void deleteDevComp(String developerID,String component) {
		DevComp devComp = getByIDAndComponent(developerID, component);

		if (devComp != null) {
			em.remove(devComp);
		}
	}
	
	@Transactional
	public DevComp updateDevComp(String developerID, String component,  DevComp newDevComp) {
		DevComp devComp = getByIDAndComponent(developerID, component);

		devComp.setManualLastUpdate(new Date());
		devComp.setManualUpdatedBy(newDevComp.getManualUpdatedBy());
		devComp.setLevel(newDevComp.getLevel());

		em.merge(devComp);
		return devComp;
	}
	
	@Transactional
	public DevComp updateDevCompTimes(String developerID, String component, DevComp newDevComp) {
		DevComp devComp = getByIDAndComponent(developerID, component);

		//All the fields that can be updated come in here
		devComp.setTimesDevComp(newDevComp.getTimesDevComp());

		em.merge(devComp);
		return devComp;
	}
	
}

package com.sap.p2monitoring.dao;

import java.util.Collection;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.sap.p2monitoring.model.DevIncident;

@Repository
public class DevIncidentDAO {

	@PersistenceContext
	private EntityManager em;

	public Collection<DevIncident> getAllDevIncidentRelations() {
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<DevIncident> criteria = cb.createQuery(DevIncident.class);
		Collection<DevIncident> resultList = em.createQuery(criteria).getResultList();
		return resultList;
	}

	public DevIncident getDevIncidentByIncidentIDandUser(String incidentID, String user) {
		Query query = em.createQuery("SELECT c FROM DevIncident c WHERE c.incidentID =?1 AND c.user =?2 ",
				DevIncident.class);
		query.setParameter(1, incidentID);
		query.setParameter(2, user);

		try {
			return (DevIncident) query.getSingleResult();
		} catch (Exception e) {
			return null;
		}
	}

	/*
	 * Transactional methods
	 */

	@Transactional
	public boolean createDevIncident(DevIncident devIncident) {

		try {
			em.persist(devIncident);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	@Transactional
	public void deleteDevIncident(String incidentID, String user) {
		DevIncident devIncident = getDevIncidentByIncidentIDandUser(incidentID, user);

		if (devIncident != null) {
			em.remove(devIncident);
		}
	}

	@Transactional
	public boolean updateDevIncident(DevIncident newDevIncident) {
		//TODO update is not used yet, because of that I didn't implemented
		try {
			em.merge(newDevIncident);
			return true;
		} catch (Exception e) {
			return false;
		}
	}
}

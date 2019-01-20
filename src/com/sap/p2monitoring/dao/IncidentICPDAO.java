package com.sap.p2monitoring.dao;

import java.util.Date;
import java.util.Collection;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
//import javax.persistence.Query; In case of Single Result on Search
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.sap.p2monitoring.model.IncidentICP;
import com.sap.p2monitoring.model.ids.IncidentICPID;

@Repository
public class IncidentICPDAO {

	@PersistenceContext
	private EntityManager em;

	public Collection<IncidentICP> getAllIncidents() {
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<IncidentICP> criteria = cb.createQuery(IncidentICP.class);
		Collection<IncidentICP> resultList = em.createQuery(criteria).getResultList();
		return resultList;
	}

	public List<IncidentICP> getIncidentById(String objectID) {
		TypedQuery<IncidentICP> query = em.createQuery("SELECT c FROM IncidentICP c WHERE c.objectID =?1 ",
				IncidentICP.class);
		query.setParameter(1, objectID);

		return query.getResultList();
		/*
		 * Example, single result
		 * 
		 * Query query = em.createQuery(
		 * "SELECT c FROM IncidentICP c WHERE c.objectID =?1 ",
		 * IncidentICP.class);
		 *
		 * query.setParameter(1, objectID);
		 *
		 * return (IncidentICP) query.getSingleResult();
		 */
	}

	public IncidentICP getIncidentIDandByUploadDateTime(String objectID, Date date) {
		// search if there is an incident with this objectID and changedDate
		// passed on the call, if there is, return incident,
		// if there isn't, return null
		IncidentICPID PK = new IncidentICPID();
		PK.setObjectID(objectID);
		PK.setUploadDateTime(date);

		IncidentICP incidentICP = em.find(IncidentICP.class, PK);
		return incidentICP;
	}
	/*
	 * Transactional methods
	 */

	@Transactional
	public boolean createIncidentAndStatus(IncidentICP object) {

		return true;
	}

	@Transactional
	public boolean createIncident(IncidentICP object) {
		try {
			em.persist(object);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	@Transactional
	public void deleteIncident(String objectID, Date uploadDateTime) {
		IncidentICP object = getIncidentIDandByUploadDateTime(objectID, uploadDateTime);

		if (object != null) {
			em.remove(object);
		}
	}

	@Transactional
	public IncidentICP updateIncident(String objectID, Date uploadDateTime, IncidentICP newIncident) {
		IncidentICP object = getIncidentIDandByUploadDateTime(objectID, uploadDateTime);

		// object.setObjectID(newIncident.getObjectID());

		em.merge(object);
		return object;
	}

}
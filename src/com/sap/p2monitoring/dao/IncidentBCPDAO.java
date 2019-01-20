package com.sap.p2monitoring.dao;

import java.util.Date;
import java.util.Collection;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.sap.p2monitoring.model.IncidentBCP;
import com.sap.p2monitoring.model.ids.IncidentBCPID;

@Repository
public class IncidentBCPDAO {

	@PersistenceContext
	private EntityManager em;

	public Collection<IncidentBCP> getAllIncidents() {
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<IncidentBCP> criteria = cb.createQuery(IncidentBCP.class);
		Collection<IncidentBCP> resultList = em.createQuery(criteria).getResultList();
		return resultList;
	}

	public List<IncidentBCP> getIncidentById(String incidentID) {
		TypedQuery<IncidentBCP> query = em.createQuery("SELECT c FROM IncidentBCP c WHERE c.incidentID =?1 ",
				IncidentBCP.class);
		query.setParameter(1, incidentID);

		return query.getResultList();
	}

	public long getIncidentByProcessorID(String processorID) {
		Query query = em.createQuery("SELECT COUNT(DISTINCT c.incidentID) FROM IncidentBCP c WHERE c.processorID =?1 ",
				IncidentBCP.class);
		query.setParameter(1, processorID);

		return (long) query.getSingleResult();
	}

	public IncidentBCP getByIncidentIDandUploadDateTime(String incidentID, Date uploadDateTime) {
		// search if there is an incident with incidentID and lastUpdateBySAPAt
		// passed on the call
		// if there is, return incident
		// if there isn't, return null
		IncidentBCPID PK = new IncidentBCPID();
		PK.setIncidentID(incidentID);
		PK.setUploadDateTime(uploadDateTime);

		IncidentBCP incident = em.find(IncidentBCP.class, PK);
		return incident;
	}

	/*
	 * Transactional methods
	 */

	@Transactional
	public boolean createIncident(IncidentBCP incident) {	
		try {
			em.persist(incident);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	@Transactional
	public void deleteIncident(String incidentID, Date uploadDateTime) {
		IncidentBCP incident = getByIncidentIDandUploadDateTime(incidentID, uploadDateTime);

		if (incident != null) {
			em.remove(incident);
		}
	}

	@Transactional
	public IncidentBCP updateIncident(String incidentID, Date uploadDateTime, IncidentBCP newIncident) {
		IncidentBCP incident = getByIncidentIDandUploadDateTime(incidentID, uploadDateTime);

		incident.setIncidentID(newIncident.getIncidentID());

		em.merge(incident);
		return incident;
	}

}
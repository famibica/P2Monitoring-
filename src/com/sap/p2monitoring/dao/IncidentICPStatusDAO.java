package com.sap.p2monitoring.dao;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.sap.p2monitoring.formatters.IncidentICPStatusResults;
import com.sap.p2monitoring.formatters.IncidentICPStatusResultsUser;
import com.sap.p2monitoring.model.IncidentICPStatus;
import com.sap.p2monitoring.model.ids.IncidentICPStatusID;

@Repository
public class IncidentICPStatusDAO {
	@PersistenceContext
	private EntityManager em;

	public Collection<IncidentICPStatus> getAllIncidents() {
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<IncidentICPStatus> criteria = cb.createQuery(IncidentICPStatus.class);
		Collection<IncidentICPStatus> resultList = em.createQuery(criteria).getResultList();
		return resultList;
	}

	public List<IncidentICPStatus> getIncidentStatusById(long id) {
		TypedQuery<IncidentICPStatus> query = em.createQuery("SELECT c FROM IncidentICPStatus c WHERE c.id =?1 ",
				IncidentICPStatus.class);
		query.setParameter(1, id);

		return query.getResultList();
	}

	public static Date addDays(Date date, int days) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.DATE, days); // minus number would decrement the days
		return cal.getTime();
	}
	
	public List<IncidentICPStatusResults> getIncidentStatusByDay(String day) throws ParseException {
		// Organize DateTime with day(user input)
		String dateFromInput = day;
		SimpleDateFormat searchDateTimeFormat = new SimpleDateFormat("dd-MM-yyyy");
		Date searchDateTime = searchDateTimeFormat.parse(dateFromInput);
		Date searchDateTimePlusOneDay = addDays(searchDateTime, 1);

		TypedQuery<IncidentICPStatus> query = em.createQuery(
				"SELECT c FROM IncidentICPStatus c WHERE c.completedDateTime BETWEEN ?1 AND ?2 ORDER BY c.completedDateTime",
				IncidentICPStatus.class);
		query.setParameter(1, searchDateTime);
		query.setParameter(2, searchDateTimePlusOneDay);

		List<IncidentICPStatus> listIncidentICPStatus = query.getResultList();
		List<IncidentICPStatusResults> resultList = new ArrayList<IncidentICPStatusResults>();

		for (int i = 0; i < listIncidentICPStatus.size(); i++) {
			resultList.add(listIncidentICPStatus.get(i).getResults());
		}
		return resultList;
	}

	public List<IncidentICPStatusResults> getIncidentStatusLastDay() throws ParseException {

		TypedQuery<IncidentICPStatus> query = em.createQuery(
				"SELECT c FROM IncidentICPStatus c ORDER BY c.completedDateTime DESC", IncidentICPStatus.class);
		query.setMaxResults(48);

		List<IncidentICPStatus> listIncidentICPStatus = query.getResultList();
		List<IncidentICPStatusResults> resultList = new ArrayList<IncidentICPStatusResults>();

		for (int i = 0; i < listIncidentICPStatus.size(); i++) {
			resultList.add(listIncidentICPStatus.get(i).getResults());
		}

		Collections.reverse(resultList);

		return resultList;
	}

	public List<IncidentICPStatusResultsUser> getIncidentStatusByUser(String userID) throws ParseException {	
		//TODO select user from table UserCIM to check his name
		//TODO select the last 48 dates that IncidentICPStatus has been uploaded
		//TODO iterate from 0 to 47 on the array with dates and   
		//TODO 4 searches-
		//	   select from incidents, where uploadedDateTime is the same as the IncidentICPStatus uploadedTime
		//	   processor is equal UserCIM name and status is Handover, In Process, New or Action Needed with a count for each
		//     create this list and return it as IncidentICPStatusResults

		//TypedQuery<IncidentICPStatusResultsUser> query = em.createQuery("SELECT c FROM USERSTATUSICP c ORDER BY c.uploadDateTime DESC", IncidentICPStatusResultsUser.class);
		@SuppressWarnings("unchecked")
		List<IncidentICPStatusResultsUser> resultList = em.createQuery("SELECT new IncidentStatusResultsUser(c.uploadDateTime, c.InProgress,c.Handover,c.ActionNeeded,c.New) FROM USERSTATUSICP WHERE c.name =?1 ORDER BY c.uploadDateTime DESC").setParameter(1, userID).setMaxResults(48).getResultList();
			
		Collections.reverse(resultList);
		
		return resultList;				
	}

	public IncidentICPStatus getIncidentByIDandScheduledDateTime(long id, Date completedDateTime) {
		// search if there is an incident with this objectID and changedDate
		// passed on the call, if there is, return incident,
		// if there isn't, return null
		IncidentICPStatusID PK = new IncidentICPStatusID();
		PK.setId(id);
		PK.setCompletedDateTime(completedDateTime);

		IncidentICPStatus incidentICP = em.find(IncidentICPStatus.class, PK);
		return incidentICP;
	}

	public IncidentICPStatus getIncidentICPStatusByScheduledDateTime(Date completedDateTime) {
		// search if there is an incident with this objectID and changedDate
		// passed on the call, if there is, return incident,
		// if there isn't, return null

		Query query = em.createQuery("SELECT c FROM IncidentICPStatus c WHERE c.completedDateTime =?1 ",
				IncidentICPStatus.class);
		query.setParameter(1, completedDateTime);
		query.setMaxResults(1);
		try {
			return (IncidentICPStatus) query.getSingleResult();
		} catch (Exception e) {
			return null;
		}
	}

	/*
	 * Transactional methods
	 */
	@Transactional
	public boolean createIncidentICPStatus(IncidentICPStatus newIncidentICPStatus) {
		try {
			em.persist(newIncidentICPStatus);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	@Transactional
	public boolean createIncidentICPStatus(Date newDate, int statusActionNeeded, int statusActionNeededT1, int statusActionNeededT2, int statusActionNeededT3, int statusActionNeededT4, int statusActionNeededT5, int statusHandover, int statusHandoverT1, int statusHandoverT2, 
			int statusHandoverT3, int statusHandoverT4, int statusHandoverT5, int statusNew, int statusNewT1, int statusNewT2, int statusNewT3, int statusNewT4, int statusNewT5, int statusInProcess) {

		IncidentICPStatus newIncidentICPStatus = new IncidentICPStatus();
		newIncidentICPStatus.setCompletedDateTime(newDate);
		newIncidentICPStatus.setStatusActionNeeded(statusActionNeeded);
		newIncidentICPStatus.setStatusActionNeededT1(statusActionNeededT1);
		newIncidentICPStatus.setStatusActionNeededT2(statusActionNeededT2);
		newIncidentICPStatus.setStatusActionNeededT3(statusActionNeededT3);
		newIncidentICPStatus.setStatusActionNeededT4(statusActionNeededT4);
		newIncidentICPStatus.setStatusActionNeededT5(statusActionNeededT5);
		newIncidentICPStatus.setStatusHandover(statusHandover);
		newIncidentICPStatus.setStatusHandoverT1(statusHandoverT1);
		newIncidentICPStatus.setStatusHandoverT2(statusHandoverT2);
		newIncidentICPStatus.setStatusHandoverT3(statusHandoverT3);
		newIncidentICPStatus.setStatusHandoverT4(statusHandoverT4);
		newIncidentICPStatus.setStatusHandoverT5(statusHandoverT5);
		newIncidentICPStatus.setStatusInProcess(statusInProcess);
		newIncidentICPStatus.setStatusNew(statusNew);
		newIncidentICPStatus.setStatusNewT1(statusNewT1);
		newIncidentICPStatus.setStatusNewT2(statusNewT2);
		newIncidentICPStatus.setStatusNewT3(statusNewT3);
		newIncidentICPStatus.setStatusNewT4(statusNewT4);
		newIncidentICPStatus.setStatusNewT5(statusNewT5);

		if (createIncidentICPStatus(newIncidentICPStatus)) {
			return true;
		}
		return false;
	}

	@Transactional
	public void deleteIncidentICPStatus(long id, Date completedDateTime) {
		IncidentICPStatus incidentICPStatus = getIncidentByIDandScheduledDateTime(id, completedDateTime);

		if (incidentICPStatus != null) {
			em.remove(incidentICPStatus);
		}
	}

	@Transactional
	public IncidentICPStatus updateIncidentICPStatus(long id, Date completedDateTime, IncidentICPStatus newIncident) {
		IncidentICPStatus incidentICPStatus = getIncidentByIDandScheduledDateTime(id, completedDateTime);

		// object.setObjectID(newIncident.getObjectID());

		em.merge(incidentICPStatus);
		return incidentICPStatus;
	}
}

package com.sap.p2monitoring.dao;

import java.util.Collection;
import java.util.Date;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.sap.p2monitoring.model.Job;

@Repository
public class JobDAO {

	@PersistenceContext
	private EntityManager em;

	public Collection<Job> getAllJobs() {
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Job> criteria = cb.createQuery(Job.class);
		Collection<Job> resultList = em.createQuery(criteria).getResultList();
		return resultList;
	}
	
	//Just a way to find jobs that have the same specific date, time doesn't matter here
	public Job getJobByDate(String date) {
		Job job = em.find(Job.class, date);
		return job;
	}

	public Job getByDateAndTime(Date jobDateTime) {
		// search if there is an Job with this date and time
		// passed on the call
		// if there is, return incident
		// if there isn't, return null
		Job job = em.find(Job.class, jobDateTime);
		return job;
	}
	
	/*
	*  Convenience method to add a specified number of minutes to a Date object
	*  From: http://stackoverflow.com/questions/9043981/how-to-add-minutes-to-my-date
	*  @param  minutes  The number of minutes to add
	*  @param  beforeTime  The time that will have minutes added to it
	*  @return  A date object with the specified number of minutes added to it 
	*/
	private static Date addMinutesToDate(int minutes, Date beforeTime){
	    final long ONE_MINUTE_IN_MILLIS = 60000;//millisecs

	    long curTimeInMs = beforeTime.getTime();
	    Date afterAddingMins = new Date(curTimeInMs + (minutes * ONE_MINUTE_IN_MILLIS));
	    return afterAddingMins;
	}
	
	public Job getNextJob(){
		// search for the next job
		TypedQuery<Job> query = em.createQuery("SELECT c FROM Job c WHERE c.jobDateTime BETWEEN ?1 AND ?2 AND c.status =?3 ORDER BY c.jobDateTime ASC", Job.class);
		Date date60minEarlier = addMinutesToDate(-60, new Date());
		Date date1minAfter = addMinutesToDate(+1, new Date());

		query.setParameter(1, date60minEarlier);
		query.setParameter(2, date1minAfter);
		query.setParameter(3, "Pending");
		query.setMaxResults(1);
		
		try{
			return query.getSingleResult();
		}catch(NoResultException e)
		{
			return null;
		}
	}

	/*
	 * Transactional methods
	 */

	@Transactional
	public boolean createJob(Job job) {
		try {
			em.persist(job);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	@Transactional
	public void deleteJob(Date jobDateTime) {
		Job job = getByDateAndTime(jobDateTime);

		if (job != null) {
			em.remove(job);
		}
	}

	@Transactional
	public Job updateJob(Job newJob) {
		Job job = getByDateAndTime(newJob.getJobDateTime());
		
		job.setCompletedDate(new Date());
		job.setStatus("Completed");
		em.merge(job);
		
		//All the fields that can be updated come in here
		if(job.getLapseInMinutes() > 0){
			// if there is Lapsed Time, create another job with the same information for the next date	
			// calculate new time
			Job newJobUpdate = new Job();
			newJobUpdate.setLapseInMinutes(job.getLapseInMinutes());
			newJobUpdate.setPersonalId(job.getPersonalId());
			newJobUpdate.setJobDateTime(addMinutesToDate(+ job.getLapseInMinutes(), job.getJobDateTime()));
			newJobUpdate.setCompletedDate(null);
			newJobUpdate.setStatus("Pending");
			newJobUpdate.setRegion(job.getRegion());
			newJobUpdate.setSearch(job.getSearch());
			newJobUpdate.setSystem(job.getSystem());
			newJobUpdate.setPersonalId(job.getPersonalId());
			
			if(createJob(newJobUpdate) == false)
			{
				//Write at log, problem creating new job
				newJobUpdate.setJobDateTime(addMinutesToDate(+ newJobUpdate.getLapseInMinutes() + 1, newJobUpdate.getJobDateTime()));
				createJob(newJobUpdate);
			}
		}
		return job;
	}

}

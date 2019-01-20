package com.sap.p2monitoring.dao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.sap.p2monitoring.controller.IncidentBCPController;
import com.sap.p2monitoring.model.IncidentBCP;
import com.sap.p2monitoring.model.IncidentICP;
import com.sap.p2monitoring.model.TagIncident;
import com.sap.p2monitoring.model.TaggingRules;

@Repository
public class TagIncidentDAO {

	@Autowired
	private TaggingRulesDAO taggingRulesDAO;

	@PersistenceContext
	private EntityManager em;

	public Collection<TagIncident> getAllTags() {
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<TagIncident> criteria = cb.createQuery(TagIncident.class);
		Collection<TagIncident> resultList = em.createQuery(criteria).getResultList();
		return resultList;
	}

	public List<TagIncident> getAllIncidentsByTagName(String tagName) {
		TypedQuery<TagIncident> query = em.createQuery("SELECT c FROM TagIncident c WHERE c.tagName =?1",
				TagIncident.class);
		query.setParameter(1, tagName);

		return query.getResultList();
	}

	// get specific tagXincident match
	public TagIncident getByIncidentIDTagName(String incidentID, String tagName) {
		Query query = em.createQuery("SELECT c FROM TagIncident c WHERE c.incidentID =?1 AND c.tagName =?2",
				TagIncident.class);
		query.setParameter(1, incidentID);
		query.setParameter(2, tagName);
		query.setMaxResults(1);

		try {
			return (TagIncident) query.getSingleResult();
		} catch (Exception e) {
			return null;
		}
	}

	// Get all tags from an specific incident
	public List<TagIncident> getAllTagsByIncidentID(String incidentID) {
		TypedQuery<TagIncident> query = em.createQuery("SELECT c FROM TagIncident c WHERE c.incidentID =?1 ",
				TagIncident.class);
		query.setParameter(1, incidentID);

		return query.getResultList();
	}

	// get specific tagXincident match
	// TODO make the search IN INCIDENTSICP by Message and client
	public IncidentICP getByMessageNumberAndCustomer(String messageNumber, String customer) {
		Query query = em.createQuery("SELECT c FROM IncidentICP c WHERE c.messageNumber LIKE ?1 AND c.customer LIKE ?2 ",
				IncidentICP.class);
		query.setParameter(1, messageNumber + '%');

		Pattern pattern = Pattern.compile("\\s");
		Matcher matcher = pattern.matcher(customer);
		boolean found = matcher.find();

		if (found == true) {
			query.setParameter(2, customer.split(" ")[0] + '%');
		} else {
			query.setParameter(2, customer + '%');
		}
		query.setMaxResults(1);

		try {
			return (IncidentICP) query.getSingleResult();
		} catch (Exception e) {
			return null;
		}
	}

	// Get SR Percentage
	// TODO get SR percentage
	public String getSRPercentage() {
		TypedQuery<TagIncident> queryA = em
				.createQuery("SELECT c FROM TagIncident c WHERE c.incidentID!=?1 AND c.tagName!=?2", TagIncident.class);
		queryA.setParameter(1, "Complete");
		queryA.setParameter(2, "GroupB");
		List<TagIncident> queryAResult = queryA.getResultList();

		// getting total of groupA worked
		int totalA = queryAResult.size();
		// starting the counting of SR created from GroupA
		int SR_A = 0;

		for (int i = 0; i < queryAResult.size(); i++) {
			if (getByMessageNumberAndCustomer(queryAResult.get(i).getIncidentNumber(),
					queryAResult.get(i).getCustomer()) != null) {
				SR_A++;
			}
		}

		TypedQuery<TagIncident> queryB = em
				.createQuery("SELECT c FROM TagIncident c WHERE c.incidentID!=?1 AND c.tagName!=?2", TagIncident.class);
		queryB.setParameter(1, "Complete");
		queryB.setParameter(2, "GroupA");
		List<TagIncident> queryBResult = queryB.getResultList();

		int totalB = queryBResult.size();
		int SR_B = 0;

		for (int i = 0; i < queryBResult.size(); i++) {
			if (getByMessageNumberAndCustomer(queryBResult.get(i).getIncidentNumber(),
					queryBResult.get(i).getCustomer()) != null) {
				SR_B++;
			}
		}

		return "GroupA: " + SR_A / totalA + ", GroupB: " + SR_B / totalB;
	}

	public boolean checkSRandTagUpdating() {
		/**
		 * Update Tags
		 * 
		 * - Check if there is new incidents that must be tagged - - Select
		 * incidents from BCP that has no tags - - Check Rules if any of them
		 * apply to the incident - - If there is any rule that apply in the
		 * incident, Tag it
		 * 
		 */
		System.out.print("Update Tag Delay Starting");
		/* 1 - Tag Delay */
		//TODO Corrigir o Except
		TypedQuery<IncidentBCP> query = em.createQuery(
				"SELECT c FROM IncidentBCP c WHERE c.incidentID NOT IN ( SELECT a.incidentID FROM TagIncident a )", IncidentBCP.class);
		List<IncidentBCP> queryResult = query.getResultList();

		Date newDate = new Date();
		
		if(queryResult.size() > 0)
		{
			for (int i = 0; i < queryResult.size(); i++) {
				/**
				 * Gets number of days between two dates. Ignoring weekends. Flag
				 * DELAY
				 */
				int days = IncidentBCPController.getDaysBetweenIgnoreWeekends(
						new DateTime(queryResult.get(i).getTimeOfLastReaction()), new DateTime(newDate));
				
				TaggingRules delay = new TaggingRules();
				delay = taggingRulesDAO.getByTagName("Delay");

				if (days > Integer.parseInt(delay.getRule1())) {
					// Search if it has not this flag yet
					// If there is, does nothing
					if (queryResult.get(i).getCimServiceRequest().equals("")
							|| queryResult.get(i).getCimServiceRequest().equals(null)) {
						if (getByIncidentIDTagName(queryResult.get(i).getIncidentID(), "Delay") == null) {
							// add in table TagIncident
							if (createTagIncidentWithIncident("Delay", "iRobot", newDate, queryResult.get(i)) == false) {
								System.out.println("Was not possible to add Tag Delay to incidentID"
										+ queryResult.get(i).getIncidentID() + "by iRobot - " + i);
							}
						}
					}
				}
			}
		}
		System.out.print("Update Tag Delay Done");
		System.out.print("Starting Future Tags");
		/* 2 - Future Tags */
		
		/**
		 * Update ServiceRequestTag
		 *
		 * - Check if there is any Tagged incident that has SR atm, checking the
		 * incidentICP table - - If there is, tag it with ServiceRequestCreated
		 * - - If not just don't tag
		 * 
		 */
		
		//TODO corrigir o except
		
		TypedQuery<IncidentBCP> querySR = em.createQuery(
				"SELECT c FROM IncidentBCP c WHERE c.incidentID NOT IN( SELECT a.incidentID FROM TagIncident a WHERE a.tagName=?1 )", IncidentBCP.class);
		querySR.setParameter(1, "ServiceRequestCreated");

		List<IncidentBCP> querySRResult = querySR.getResultList();
		
		System.out.print("Future Tags Search Done");
		
		if(querySRResult.size() > 0){
			for (int i = 0; i < querySRResult.size(); i++) {
				//If there is an SR for this BCP Incident, tag it as ServiceRequestCreated			
				if(getByMessageNumberAndCustomer(querySRResult.get(i).getIncidentNumber(), querySRResult.get(i).getCustomer()) == null) {
					createTagIncidentWithIncident("ServiceRequestCreated", "iRobot", new Date(), querySRResult.get(i));
				}
			}
		}
		System.out.print("Future Tags Done");
		
		return true;
	}

	// Get all tags from an specific incident
	public List<IncidentBCP> getAllIncidentsByCustomTag(String customTags) {
		List<String> list_add_tags = new ArrayList<String>();
		List<String> list_remove_tags = new ArrayList<String>();
		String tags[] = customTags.split(",");

		// String parsing
		for (int i = 0; i < tags.length; i++) {
			if (tags[i].substring(0, 1).equals("+")) {
				list_add_tags.add(tags[i].substring(1, tags[i].length()));
			} else {
				list_remove_tags.add(tags[i].substring(1, tags[i].length()));
			}
		}
		
		if(list_add_tags.size() == 0)
		{
			return null;
		}else
		{
			if(list_remove_tags.size() == 0)
			{
				String sql_query = "SELECT c FROM IncidentBCP c WHERE c.incidentID IN ( SELECT DISTINCT(d.incidentID) FROM TagIncident d WHERE d.tagName IN :add_tags )";
				TypedQuery<IncidentBCP> query = em.createQuery(sql_query, IncidentBCP.class);
				query.setParameter("add_tags", list_add_tags);
				return query.getResultList();
			} else
			{
				String sql_query = "SELECT c FROM IncidentBCP c WHERE c.incidentID IN ( SELECT DISTINCT(d.incidentID) FROM TagIncident d WHERE d.tagName IN :add_tags AND d.incidentID NOT IN ( SELECT a.incidentID FROM TagIncident a WHERE a.tagName IN :remove_tags))";
				TypedQuery<IncidentBCP> query = em.createQuery(sql_query, IncidentBCP.class);
				query.setParameter("add_tags", list_add_tags);
				query.setParameter("remove_tags", list_remove_tags);
				return query.getResultList();
			}
		}
	}

	/*
	 * Transactional methods
	 */

	// Insert in TagIncident passing incidentBCP and Tag info's
	@Transactional
	public boolean createTagIncidentWithIncident(String tagName, String userID, Date tagAddedDate,
			IncidentBCP incidentBCP) {

		TagIncident tagIncident = new TagIncident();

		tagIncident.setTagName(tagName);
		tagIncident.setUserID(userID);
		tagIncident.setTagAddedDate(tagAddedDate);
		tagIncident.setIncidentID(incidentBCP.getIncidentID());
		tagIncident.setTimeOfLastReaction(incidentBCP.getTimeOfLastReaction());
		tagIncident.setLastUpdateBySAPAt(incidentBCP.getLastUpdateBySAPAt());
		tagIncident.setWp(incidentBCP.getWp());
		tagIncident.setEscalation(incidentBCP.getEscalation());
		tagIncident.setContractType(incidentBCP.getContractType());
		tagIncident.setPriority(incidentBCP.getPriority());
		tagIncident.setComponent(incidentBCP.getComponent());
		tagIncident.setCustomer(incidentBCP.getCustomer());
		tagIncident.setStatus(incidentBCP.getStatus());
		tagIncident.setProcessor(incidentBCP.getProcessor());
		tagIncident.setDescription(incidentBCP.getDescription());
		tagIncident.setIrtTrafficLight(incidentBCP.getIrtTrafficLight());
		tagIncident.setIrtPlannedEndDate(incidentBCP.getIrtPlannedEndDate());
		tagIncident.setTransactionType(incidentBCP.getTransactionType());
		tagIncident.setCimServiceRequest(incidentBCP.getCimServiceRequest());
		tagIncident.setDevHelpRequest(incidentBCP.getDevHelpRequest());
		tagIncident.setIncidentUpdated(incidentBCP.getIncidentUpdated());
		tagIncident.setMptPlannedEndDate(incidentBCP.getMptPlannedEndDate());
		tagIncident.setMptTrafficLight(incidentBCP.getMptTrafficLight());
		tagIncident.setCustomerCallback(incidentBCP.getCustomerCallback());
		tagIncident.setRampUp(incidentBCP.getRampUp());
		tagIncident.setIncidentYear(incidentBCP.getIncidentYear());
		tagIncident.setIncidentNumber(incidentBCP.getIncidentNumber());
		tagIncident.setIncidentIDCustomer(incidentBCP.getIncidentIDCustomer());
		tagIncident.setCountry(incidentBCP.getCountry());
		tagIncident.setNumberCallsFromCustomer(incidentBCP.getNumberCallsFromCustomer());
		tagIncident.setProcessorID(incidentBCP.getProcessorID());
		tagIncident.setProcessingOrg(incidentBCP.getProcessingOrg());
		tagIncident.setServiceTeam(incidentBCP.getServiceTeam());
		tagIncident.setCreationDate(incidentBCP.getCreationDate());
		tagIncident.setIncidentUploadDateTime(incidentBCP.getUploadDateTime());

		try {
			em.persist(tagIncident);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	// Get All tag incidents except complete, groupA and groupB and split in
	// half for each group.
	@Transactional
	public boolean getAllTaggedAndSplit() {
		TypedQuery<TagIncident> query = em.createQuery(
				"SELECT c FROM TagIncident c WHERE (c.tagName !=?1 AND c.tagName !=?2 AND c.tagName !=?3) ",
				TagIncident.class);
		query.setParameter(1, "Complete");
		query.setParameter(2, "GroupA");
		query.setParameter(3, "GroupB");

		List<TagIncident> listTagIncident = query.getResultList();
		
		Date newDate = new Date();

		if (listTagIncident.size() > 0) {

			for (int i = 0; i < listTagIncident.size(); i++) {
				// TagIncident tagIncident = new TagIncident();
				// tagIncident = listTagIncidentCopy.get(i);
				em.detach(listTagIncident.get(i));
				// Alternate Between
				if (((i + 1) % 2) == 0) // if its Even
				{
					listTagIncident.get(i).setTagName("GroupA");
					listTagIncident.get(i).setTagAddedDate(newDate);
					listTagIncident.get(i).setUserID("iRobot");
				} else // if its not
				{
					listTagIncident.get(i).setTagName("GroupB");
					listTagIncident.get(i).setTagAddedDate(newDate);
					listTagIncident.get(i).setUserID("iRobot");
				}

				if (getByIncidentIDTagName(listTagIncident.get(i).getIncidentID(),
						listTagIncident.get(i).getTagName()) == null) {
					try {
						em.persist(listTagIncident.get(i));
					} catch (Exception e) {
						System.out.println(
								"Was not possible to create this new TagIncident when splitting in 2 groups IncidentID:"
										+ listTagIncident.get(i).getIncidentID());
					}
				}
			}
		}
	
		return true;
	}

	@Transactional
	public boolean createTagIncident(TagIncident tagIncident) {
		try {
			em.persist(tagIncident);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	@Transactional
	public void deleteTagIncident(String incidentID, String tagName) {
		TagIncident tagIncident = getByIncidentIDTagName(incidentID, tagName);

		if (tagIncident != null) {
			em.remove(tagIncident);
		}
	}

	@Transactional
	public TagIncident updateTagIncident(TagIncident newTag) {
		/*
		 * TagIncident tagIncident =
		 * getByIncidentIDTagName(newTag.getIncidentID(), newTag.getTagName());
		 * 
		 * tagIncident.setTagStatus(newTag.getTagStatus());
		 * 
		 * em.merge(tagIncident);
		 */
		return newTag;
	}
}

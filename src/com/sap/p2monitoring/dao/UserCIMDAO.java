package com.sap.p2monitoring.dao;

import java.util.Collection;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;


import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.sap.p2monitoring.model.UserCIM;

@Repository
public class UserCIMDAO {

	@PersistenceContext
	private EntityManager em;

	public Collection<UserCIM> getAllUsers() {
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<UserCIM> criteria = cb.createQuery(UserCIM.class);
		Collection<UserCIM> resultList = em.createQuery(criteria).getResultList();
		return resultList;
	}
	
	//Just a way to find jobs that have the same specific date, time doesn't matter here
	public UserCIM getUserByID(String userID) {
		UserCIM user = em.find(UserCIM.class, userID);
		return user;
	}
	
	 /*
	 * Transactional methods
	 */

	@Transactional
	public boolean createUserCIM(UserCIM userCIM) {
		try {
			em.persist(userCIM);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	@Transactional
	public void deleteUserCIM(String userID) {
		UserCIM userCIM = getUserByID(userID);

		if (userCIM != null) {
			em.remove(userCIM);
		}
	}

	@Transactional
	public UserCIM updateUserCIM(UserCIM newUserCIM) {
		UserCIM userCIM = getUserByID(newUserCIM.getUserID());
		
		userCIM.setName(newUserCIM.getName());
		userCIM.setEmail(newUserCIM.getEmail());
		userCIM.setRegion(newUserCIM.getRegion());
		em.merge(userCIM);
		
		return userCIM;
	}

}

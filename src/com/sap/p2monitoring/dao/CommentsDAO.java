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

import com.sap.p2monitoring.model.Comments;

@Repository
public class CommentsDAO {

	@PersistenceContext
	private EntityManager em;

	public Collection<Comments> getAllComments() {
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Comments> criteria = cb.createQuery(Comments.class);
		Collection<Comments> resultList = em.createQuery(criteria).getResultList();
		return resultList;
	}
	
	public Comments getCommentByID(long id) {
		Comments comment = em.find(Comments.class, id);
		return comment;
	}
	
	public List<Comments> getCommentsByIncidentID(String incidentID) {
			TypedQuery<Comments> query = em.createQuery("SELECT c FROM Comments c WHERE c.incidentID =?1 ORDER BY c.lastUpdateDateTime",
					Comments.class);
			query.setParameter(1, incidentID);

			return query.getResultList();
	}
	
	/*
	 * Transactional methods
	 */

	@Transactional
	public boolean createComment(Comments comment) {
		comment.setCommentDateTime(new Date());
		comment.setLastEditDateTime(new Date());
		
		try {
			em.persist(comment);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	@Transactional
	public void deleteComment(long id) {
		Comments comment = getCommentByID(id);

		if (comment != null) {
			em.remove(comment);
		}
	}

	@Transactional
	public boolean updateComment(Comments newComment) {
		Comments comment = getCommentByID(newComment.getId());
		
		comment.setComment(newComment.getComment());
		comment.setLastEditDateTime(new Date());
		
		try {
			em.merge(comment);
			return true;
		} catch (Exception e) {
			return false;
		}
		
	}
}

package com.sap.p2monitoring.controller;

import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.sap.p2monitoring.dao.CommentsDAO;
import com.sap.p2monitoring.model.Comments;

@Controller
@RequestMapping("/Comments")
public class CommentsController {

	@Autowired
	private CommentsDAO commentsDAO;

	@RequestMapping(method = RequestMethod.GET)
	public @ResponseBody Collection<Comments> findAll() {
		return commentsDAO.getAllComments();
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public @ResponseBody Comments findByID(@PathVariable long id) {
		return commentsDAO.getCommentByID(id); 
	}
	
	@RequestMapping(value = "/GetByIncidentID", method = RequestMethod.GET)
	public @ResponseBody List<Comments> findCommentsByIncidentID(@PathVariable String incidentID) {
		return commentsDAO.getCommentsByIncidentID(incidentID); 
	}

	@RequestMapping(method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.CREATED)
	public @ResponseBody boolean create(@RequestBody Comments comment) {
		commentsDAO.createComment(comment);		
		return true;
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	@ResponseStatus(HttpStatus.OK)
	public @ResponseBody void delete(@PathVariable long id) {
		commentsDAO.deleteComment(id);		
	}

	@RequestMapping(method = RequestMethod.PUT)
	@ResponseStatus(HttpStatus.OK)
	public @ResponseBody boolean updateComment(@RequestBody Comments comment) {
		return commentsDAO.updateComment(comment);
	}

}
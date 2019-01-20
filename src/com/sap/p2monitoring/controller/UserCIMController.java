package com.sap.p2monitoring.controller;

import java.util.Collection;

import com.sap.security.um.user.PersistenceException;
import com.sap.security.um.user.UnsupportedUserAttributeException;
import com.sap.security.um.user.UserProvider;
import com.sap.security.um.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.sap.p2monitoring.dao.UserCIMDAO;
import com.sap.p2monitoring.model.UserCIM;

@Controller
@RequestMapping("/UserCIM")
public class UserCIMController {
	
	private UserProvider userProvider;
	/*
    public User getUser(String name) {
        try {
            return transformUser(userProvider.getUser(name));
        } catch (PersistenceException e) {
        }
    }*/

    public User getCurrentUser() {
        try {
            return userProvider.getCurrentUser();
        } catch (PersistenceException e) {
        	return null;
        }
    }
	
	@Autowired
	private UserCIMDAO userCIMDAO;

	@RequestMapping(method = RequestMethod.GET)
	public @ResponseBody Collection<UserCIM> findAll() {
		return userCIMDAO.getAllUsers();
	}

	@RequestMapping(value = "/{userID}", method = RequestMethod.GET)
	public @ResponseBody UserCIM findUserByID(@PathVariable String userID) {
		return userCIMDAO.getUserByID(userID); 
	}
	
	@RequestMapping(value = "/currentUser/", method = RequestMethod.GET)
	public @ResponseBody String getCurrentUser1() throws PersistenceException, UnsupportedUserAttributeException {
		// Check for a logged in user
				
		User usuario = getCurrentUser();
		
		return usuario.getAttribute("name");	
	}
	
	@RequestMapping(method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.CREATED)
	public @ResponseBody UserCIM create(@RequestBody UserCIM userCIM) {
		userCIMDAO.createUserCIM(userCIM);
		
		return userCIM;
	}

	@RequestMapping(value = "/{userID}", method = RequestMethod.DELETE)
	@ResponseStatus(HttpStatus.OK)
	public @ResponseBody void delete(@PathVariable String userID) {
		userCIMDAO.deleteUserCIM(userID);
	}

	@RequestMapping(value = "/UpdateUserCIM/", method = RequestMethod.PUT)
	@ResponseStatus(HttpStatus.OK)
	public @ResponseBody UserCIM setOccupied(@RequestBody UserCIM userCIM) {
		return userCIMDAO.updateUserCIM(userCIM);
	}

}
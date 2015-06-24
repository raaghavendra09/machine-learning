package com.dmac.service;

import java.util.List;
import java.util.Objects;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.dmac.core.Codez;
import com.dmac.entity.user.Users;
import com.equator.common.constraints.Conditions;

@Service("UsersService")
public class UsersService {

	@PersistenceContext
	private EntityManager entityManager;
	
	
	@Transactional
	public List<Users> fetchAllUsers() {
		 
		List<Users> results = entityManager.createQuery("SELECT users FROM Users users", Users.class)
										  .getResultList();
		return results;
	}
	
	/**
	 * 
	 * @return Password
	 */
	@Transactional
	public String fetchPassword(String username) {
		
		Objects.requireNonNull(username, "username null in fetchPassword() method of UsersService");
		Conditions.checkNotEmptyString(username);
		
		Users user = (Users) entityManager
							 	.createQuery("SELECT user FROM USERS user where user.username = :value")
					 			.setParameter("value", username)
					 			.getSingleResult();
		
	
		if (user != null && !user.getCryptedPassword().isEmpty()) {
			String cryptedPassword = user.getCryptedPassword();
			return cryptedPassword;
		}
		
		return Codez.NO_VALUE.name();
	}
}

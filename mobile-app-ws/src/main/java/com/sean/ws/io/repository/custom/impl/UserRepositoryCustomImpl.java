package com.sean.ws.io.repository.custom.impl;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.beans.factory.annotation.Autowired;

import com.sean.ws.io.entity.UserEntity;
import com.sean.ws.io.repository.custom.UserRepositoryCustom;

public class UserRepositoryCustomImpl implements UserRepositoryCustom {
	
	@Autowired
	EntityManager em;
	
	/*
	@Override
	public List<UserEntity> findUserByFirstNameAndLastNameCustom(String firstName, String lastName) {
		CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<UserEntity> cq = cb.createQuery(UserEntity.class);
 
        Root<UserEntity> userEntity = cq.from(UserEntity.class);
        Predicate authorNamePredicate = cb.equal(userEntity.get("firstName"), firstName);
        Predicate titlePredicate = cb.like(userEntity.get("lastName"), "%" + lastName + "%");
        cq.where(authorNamePredicate, titlePredicate);
 
        TypedQuery<UserEntity> query = em.createQuery(cq);
        return query.getResultList();
	}
	*/
	
	@Override
	public List<UserEntity> findUserByFirstNameAndLastNameCustom(String firstName, String lastName) {
		CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<UserEntity> cq = cb.createQuery(UserEntity.class);
 
        Root<UserEntity> userEntity = cq.from(UserEntity.class);
        
        List<Predicate> predicates = new ArrayList<>();
        
        if (firstName != null) {
            predicates.add(cb.equal(userEntity.get("firstName"), firstName));
        }
        
        if (lastName != null) {
            predicates.add(cb.like(userEntity.get("lastName"), "%" + lastName + "%"));
        }
        
        cq.where(predicates.toArray(new Predicate[0]));
 
        TypedQuery<UserEntity> query = em.createQuery(cq);
        return query.getResultList();
	}

}

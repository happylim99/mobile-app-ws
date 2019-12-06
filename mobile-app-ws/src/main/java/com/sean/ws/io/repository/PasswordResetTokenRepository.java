package com.sean.ws.io.repository;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.sean.ws.io.entity.PasswordResetTokenEntity;

@Repository
public interface PasswordResetTokenRepository extends PagingAndSortingRepository<PasswordResetTokenEntity, Long>{
	PasswordResetTokenEntity findByToken(String token);
}

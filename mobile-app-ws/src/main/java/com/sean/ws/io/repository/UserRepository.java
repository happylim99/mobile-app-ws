package com.sean.ws.io.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.sean.ws.io.entity.UserEntity;
import com.sean.ws.io.repository.custom.UserRepositoryCustom;

//@Repository do not need since we extends JpaRepository
//public interface UserRepository extends CrudRepository<UserEntity, Long> {
//public interface UserRepository extends PagingAndSortingRepository<UserEntity, Long> {
public interface UserRepository extends JpaRepository<UserEntity, Long>, UserRepositoryCustom {
	UserEntity findByEmail(String email);
	UserEntity findByUserId(String userId);
	UserEntity findUserByEmailVerificationToken(String token);
	//List<UserEntity> findAllUsers(Sort sort);
	
	@Query(value="select * from Users u where u.EMAIL_VERIFICATION_STATUS = 1", 
			countQuery="select count(*) from Users u where u.EMAIL_VERIFICATION_STATUS = 1", 
			nativeQuery = true)
	Page<UserEntity> findAllUsersWithConfirmedEmailAddress( Pageable pageableRequest );
	
	@Query(value="select * from users u where u.first_name = ?1 and u.last_name = ?2", nativeQuery = true)
	List<UserEntity> findUserByFirstAndLastName(String firstName, String lastName);
	
	@Query(value="select * from users u where u.first_name = ?1", nativeQuery = true)
	List<UserEntity> findUserByFirstName(String firstName);
	
	@Query(value="select * from users u where u.last_name = :last_name", nativeQuery = true)
	List<UserEntity> findUserByLastName(@Param("last_name") String lastName);
	
	@Query(value="select * from users u where u.first_name like %:keyword% or u.last_name like %:keyword%", nativeQuery = true)
	List<UserEntity> findUserByKeyword(@Param("keyword") String keyword);
	
	@Query(value="select first_name, last_name from users u where u.first_name like %:keyword% or u.last_name like %:keyword%", nativeQuery = true)
	List<Object[]> findUserFirstNameAndLastNameByKeyword(@Param("keyword") String keyword);
	
	@Transactional
	@Modifying
	@Query(value="update users u set u.EMAIL_VERIFICATION_STATUS=:emailVerificationStatus where u.user_id=:userId", nativeQuery = true)
	void updateUserEmailVerificationStatus(@Param("emailVerificationStatus") boolean emailVerificationStatus,
			@Param("userId") String userId);
	
	//JPQL example(just use class name and class variable) case sensitive
	@Query("select u from UserEntity u where u.userId=:userId")
	UserEntity findUserEntityByUserId(@Param("userId") String userId);
	
	//JPQL
	@Query("select u.firstName, u.lastName from UserEntity u where userId =:userId")
	List<Object[]> getUserEntityFullNameById(@Param("userId") String userId);
	
	//JPQL
	@Transactional
	@Modifying
	@Query("update UserEntity u set u.emailVerificationStatus=:emailVerificationStatus where u.userId=:userId")
	void updateUserEntityEmailVerificationStatus(
			@Param("emailVerificationStatus") boolean emailVerificationStatus,
			@Param("userId") String userId);
	
	//HQL
	@Query("from UserEntity u where u.userId=:userId")
	UserEntity findUserEntityByUserIdHql(@Param("userId") String userId);
	
}

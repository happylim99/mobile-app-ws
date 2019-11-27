package com.sean.ws.io.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.sean.ws.io.entity.AddressEntity;
import com.sean.ws.io.entity.UserEntity;

@Repository
public interface AddressRepository extends PagingAndSortingRepository<AddressEntity, Long> {
	List<AddressEntity> findAllByUserDetails(UserEntity userEntity);
	Page<AddressEntity> findAllByUserDetails(Pageable pageable, UserEntity userEntity);
	AddressEntity findByAddressId(String addressId);
}

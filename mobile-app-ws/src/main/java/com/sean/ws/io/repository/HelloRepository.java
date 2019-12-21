package com.sean.ws.io.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sean.ws.io.entity.HelloEntity;

public interface HelloRepository extends JpaRepository<HelloEntity, Long> {

}

package com.francinjr.xpenses.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;	
import org.springframework.stereotype.Repository;

import com.francinjr.xpenses.domain.model.Ganho;

@Repository
public interface GanhoRepository extends JpaRepository<Ganho, Long> {

}

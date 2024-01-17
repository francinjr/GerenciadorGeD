package com.francinjr.xpenses.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.francinjr.xpenses.domain.model.Finance;

@Repository
public interface FinanceRepository extends JpaRepository<Finance, Long> {

}

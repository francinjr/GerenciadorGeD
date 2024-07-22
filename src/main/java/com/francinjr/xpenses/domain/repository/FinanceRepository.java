package com.francinjr.xpenses.domain.repository;

import com.francinjr.xpenses.domain.model.Finance;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface FinanceRepository extends JpaRepository<Finance, Long> {
	
	@Modifying
	@Query("UPDATE Finance f SET f.description =:description WHERE f.id =:id")
	void updateDescription(@Param("description") String description, @Param("id") Long id);

	@Query("SELECT f FROM Finance f WHERE f.name LIKE LOWER(CONCAT ('%',:name,'%'))")
	Page<Finance> findFinancesByName(@Param("name") String name, Pageable pageable);
}

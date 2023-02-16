package com.diary.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.diary.entity.Weight;

public interface WeightRepository extends JpaRepository<Weight, Long> {
	
	@Query("select w from Weight w where w.date like :date% and w.memberId = :memberId order by w.date desc")
	List<Weight> findWeight(@Param("memberId") String memberId, @Param("date") String date);
	Long deleteByMemberId(String memberId);
}

package com.diary.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.diary.entity.Diet;

public interface DietRepository extends JpaRepository<Diet, Long>{

	@Query("select distinct d.time from Diet d where d.memberId = :memberId and d.date = :date order by d.time")
	String[] findDistinctTime(@Param("memberId") String memberId, @Param("date") java.sql.Date date);
	List<Diet> findByMemberIdAndDate(String memberId, java.sql.Date date);
	List<Diet> findByMemberIdAndDateAndTime(String memberId, java.sql.Date date, String time);
	Long deleteByMemberIdAndDateAndTime(String memberId, java.sql.Date date, String time);
	Long deleteByMemberId(String memberId);
}

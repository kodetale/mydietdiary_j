package com.diary.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.diary.entity.Exercise;

public interface ExerciseRepository extends JpaRepository<Exercise, Long> {

	@Query("select distinct e.time from Exercise e where e.memberId = :memberId and e.date = :date order by e.time")
	String[] findDistinctTime(@Param("memberId") String memberId, @Param("date") java.sql.Date date);
	List<Exercise> findByMemberIdAndDate(String memberId, java.sql.Date date);
	List<Exercise> findByMemberIdAndDateAndTime(String memberId, java.sql.Date date, String time);
	Long deleteByMemberIdAndDateAndTime(String memberId, java.sql.Date date, String time);
	Long deleteByMemberId(String memberId);
}

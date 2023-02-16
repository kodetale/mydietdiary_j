package com.diary.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.diary.repository.ExerciseRepository;
import com.diary.entity.Exercise;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class ExerciseService {
	
	@Autowired
    private final ExerciseRepository exerciseRepository;
	
	public String[] time_arr(String memberId, java.sql.Date date) {
		return exerciseRepository.findDistinctTime(memberId, date);
    }
	
	public List<Exercise> printExercise(String memberId, java.sql.Date date) {   
    	return exerciseRepository.findByMemberIdAndDate(memberId, date);
    }
	
	public Exercise saveExercise(String memberId, java.sql.Date date, String time, String exercise, int num, String unit) {
        Exercise exer = new Exercise();
        exer.setMemberId(memberId);
		exer.setDate(date);
		exer.setTime(time);
		exer.setExercise(exercise);
		exer.setNum(num);
		exer.setUnit(unit);
        return exerciseRepository.save(exer);
    }
	
	public List<Exercise> editExercise(String memberId, java.sql.Date date, String time) {   
    	return exerciseRepository.findByMemberIdAndDateAndTime(memberId, date, time);
    }
	
	public void delExercise(String memberId, java.sql.Date date, String time) {   
    	exerciseRepository.deleteByMemberIdAndDateAndTime(memberId, date, time);
    }

}

package com.diary.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.diary.repository.DietRepository;
import com.diary.entity.Diet;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class DietService {
	
	@Autowired
    private final DietRepository dietRepository;
	
	public String[] time_arr(String memberId, java.sql.Date date) {
		return dietRepository.findDistinctTime(memberId, date);
    }
	
	public List<Diet> printDiet(String memberId, java.sql.Date date) {   
    	return dietRepository.findByMemberIdAndDate(memberId, date);
    }
	
	public Diet saveDiet(String memberId, java.sql.Date date, String time, String food, int kcal) {
        Diet diet = new Diet();
        diet.setMemberId(memberId);
        diet.setDate(date);
        diet.setTime(time);
        diet.setFood(food);
        diet.setKcal(kcal);
        return dietRepository.save(diet);
    }
	
	public List<Diet> editDiet(String memberId, java.sql.Date date, String time) {   
    	return dietRepository.findByMemberIdAndDateAndTime(memberId, date, time);
    }
	
	public void delDiet(String memberId, java.sql.Date date, String time) {   
    	dietRepository.deleteByMemberIdAndDateAndTime(memberId, date, time);
    }

}

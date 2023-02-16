package com.diary.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.diary.entity.Weight;
import com.diary.repository.WeightRepository;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class WeightService {
	
	@Autowired
	private final WeightRepository weightRepository;
	
	public Weight saveWeight(String memberId, String date, int weight) {
        Weight wei = new Weight();
        wei.setMemberId(memberId);
        wei.setDate(date);
        wei.setWeight(weight);
        return weightRepository.save(wei);
    }
	
	public List<Weight> printWeight(String memberId, String date) {
		return weightRepository.findWeight(memberId, date);
	}
	
	public void delWeight(Long id) {   
    	weightRepository.deleteById(id);
    }
}

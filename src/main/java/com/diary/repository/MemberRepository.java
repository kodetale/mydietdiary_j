package com.diary.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.diary.entity.Member;

public interface MemberRepository extends JpaRepository<Member, Long> {
	
	Member findByMemberId(String memberId);
	void deleteById(Long id);
	
}

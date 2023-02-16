package com.diary.controller;

import java.security.Principal;
import java.time.LocalDate;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.diary.entity.Member;
import com.diary.service.MemberService;
import com.diary.service.WeightService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class WeightController {
	
	private final MemberService memberService;
	private final WeightService weightService;
	
	public String name(Principal principal) {
		String name = null;
		if(principal != null) {
			String loginId = principal.getName();
			Member member = memberService.findbyMemberId(loginId);
			name = member.getName();	
		}
		
		return name;
	}
	
	@GetMapping(value="/weight")
	public String weight(@RequestParam(value = "year") int year, @RequestParam(value = "month") int month, Principal principal, Model model) {
		model.addAttribute("name", name(principal));
		model.addAttribute("today", LocalDate.now());
		model.addAttribute("today_y", LocalDate.now().getYear());
		model.addAttribute("today_m", LocalDate.now().getMonthValue());
		model.addAttribute("year", year);
		model.addAttribute("month", month);
		String date = year + "-" + String.format("%02d", month);
		model.addAttribute("weight", weightService.printWeight(principal.getName(), date));
		
		return "weight";
	}
	
	@RequestMapping(value ="/weight")
	public String new_weight(@RequestParam(value = "date") String date, @RequestParam(value = "weight") int weight, Principal principal) {
		weightService.saveWeight(principal.getName(), date, weight);
		return "redirect:/weight?year="+LocalDate.now().getYear()+"&month="+String.format("%02d", LocalDate.now().getMonthValue());
		
	}
	
	@ResponseBody
	@RequestMapping(value="/weight/delete", method = {RequestMethod.POST})
	public void del_weight(Long id, int year, int month, Principal principal) {
		weightService.delWeight(id);
		
	}

}

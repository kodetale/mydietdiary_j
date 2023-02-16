package com.diary.controller;

import java.security.Principal;
import java.time.LocalDate;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.diary.service.DietService;
import com.diary.service.MemberService;
import com.diary.entity.Member;

import lombok.RequiredArgsConstructor;

@RequestMapping("/diet")
@Controller
@RequiredArgsConstructor
public class DietController {
	
	private final MemberService memberService;
	private final DietService dietService;
	
	public String name(Principal principal) {
		String name = null;
		if(principal != null) {
			String loginId = principal.getName();
			Member member = memberService.findbyMemberId(loginId);
			name = member.getName();	
		}
		
		return name;
	}

	@GetMapping(value ="/diary")
	public String diet(@RequestParam(value = "date") java.sql.Date date, Model model, Principal principal) {
		model.addAttribute("name", name(principal));
		model.addAttribute("date", date);
		model.addAttribute("today", LocalDate.now());
		model.addAttribute("today_y", LocalDate.now().getYear());
		model.addAttribute("today_m", LocalDate.now().getMonthValue());
		model.addAttribute("time", dietService.time_arr(principal.getName(), date));	
		model.addAttribute("diet", dietService.printDiet(principal.getName(), date));
		
		return "diet/diary";
	}
	
	@GetMapping(value ="/new")
	public String new_diet(@RequestParam(value = "date") java.sql.Date date, Model model, Principal principal) {
		model.addAttribute("name", name(principal));
		model.addAttribute("date", date);
		return "diet/new";
	}
	
	@RequestMapping(value ="/new")
	public String save_diet(HttpServletRequest request, @RequestParam(value = "date") java.sql.Date date, @RequestParam(value = "time") String time, Principal principal) {
		String[] food_arr = request.getParameterValues("food");
		String[] kcal_arr = request.getParameterValues("kcal");
		int[] kcal_int = new int[kcal_arr.length];
		for (int i = 0; i < kcal_arr.length; i++) {   
		   try {
		      kcal_int[i] = Integer.parseInt(kcal_arr[i]);
		   } catch (Exception e) {
			   System.out.println(e);
		   }
		}
        for (int i = 0; i < food_arr.length; i++) {
        	dietService.saveDiet(principal.getName(), date, time, food_arr[i], kcal_int[i]);
        }
        
        return "redirect:/diet/diary?date="+date;
	}
	
	@GetMapping(value ="/edit")
	public String edit_diet(@RequestParam(value = "date") java.sql.Date date, @RequestParam(value = "time") String time, Model model, Principal principal) {
		model.addAttribute("name", name(principal));
		model.addAttribute("date", date);
		model.addAttribute("time", time);
		model.addAttribute("diet", dietService.editDiet(principal.getName(), date, time));
		return "diet/edit";
	}
	
	@RequestMapping(value ="/edit")
	public String edit_diet(HttpServletRequest request, @RequestParam(value = "date") java.sql.Date date, @RequestParam(value = "time") String time, Principal principal) {
		
		dietService.delDiet(principal.getName(), date, time);
		
		if(request.getParameterValues("food") != null) {
			String[] food_arr = request.getParameterValues("food");
			String[] kcal_arr = request.getParameterValues("kcal");
			int[] kcal_int = new int[kcal_arr.length];
			
			for (int i = 0; i < kcal_arr.length; i++) {   
			   try {
			      kcal_int[i] = Integer.parseInt(kcal_arr[i]);
			   } catch (Exception e) {
				   System.out.println(e);
			   }
			}
			
	        for (int i = 0; i < food_arr.length; i++) {
	        	dietService.saveDiet(principal.getName(), date, time, food_arr[i], kcal_int[i]);
	        }	
		}
        
        return "redirect:/diet/diary?date="+date;
	}
	
	@ResponseBody
	@RequestMapping(value="/diary", method = {RequestMethod.POST})
	public void delDiet(java.sql.Date date, String time, Principal principal) {		
		dietService.delDiet(principal.getName(), date, time);
	}
}

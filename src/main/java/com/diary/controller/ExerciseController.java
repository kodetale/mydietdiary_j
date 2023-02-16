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

import com.diary.service.ExerciseService;
import com.diary.service.MemberService;
import com.diary.entity.Member;

import lombok.RequiredArgsConstructor;

@RequestMapping("/exercise")
@Controller
@RequiredArgsConstructor
public class ExerciseController {
	
	private final MemberService memberService;
	private final ExerciseService exerciseService;
	
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
	public String exercise(@RequestParam(value = "date") java.sql.Date date, Model model, Principal principal) {
		model.addAttribute("name", name(principal));
		model.addAttribute("date", date);
		model.addAttribute("today", LocalDate.now());
		model.addAttribute("today_y", LocalDate.now().getYear());
		model.addAttribute("today_m", LocalDate.now().getMonthValue());
		model.addAttribute("time", exerciseService.time_arr(principal.getName(), date));	
		model.addAttribute("exer", exerciseService.printExercise(principal.getName(), date));
		return "exercise/diary";
	}
	
	@GetMapping(value ="/new")
	public String new_exercise(@RequestParam(value = "date") java.sql.Date date, Model model, Principal principal) {
		model.addAttribute("name", name(principal));
		model.addAttribute("date", date);
		return "exercise/new";
	}
	
	@RequestMapping(value ="/new")
	public String save_exercise(HttpServletRequest request, @RequestParam(value = "date") java.sql.Date date, @RequestParam(value = "time") String time, Principal principal) {
		String[] exer_arr = request.getParameterValues("exercise");
		String[] num_arr = request.getParameterValues("num");
		String[] unit_arr = request.getParameterValues("unit");
		int[] num_int = new int[num_arr.length];
		for (int i = 0; i < num_arr.length; i++) {   
		   try {
		      num_int[i] = Integer.parseInt(num_arr[i]);
		   } catch (Exception e) {
			   System.out.println(e);
		   }
		}
        for (int i = 0; i < exer_arr.length; i++) {
        	exerciseService.saveExercise(principal.getName(), date, time, exer_arr[i], num_int[i], unit_arr[i]);
        }
        
        return "redirect:/exercise/diary?date="+date;
	}
	
	@GetMapping(value ="/edit")
	public String edit_exercise(@RequestParam(value = "date") java.sql.Date date, @RequestParam(value = "time") String time, Model model, Principal principal) {
		model.addAttribute("name", name(principal));
		model.addAttribute("date", date);
		model.addAttribute("time", time);
		model.addAttribute("exer", exerciseService.editExercise(principal.getName(), date, time));
		return "exercise/edit";
	}
	
	@RequestMapping(value ="/edit")
	public String edit_exercise(HttpServletRequest request, @RequestParam(value = "date") java.sql.Date date, @RequestParam(value = "time") String time, Principal principal) {
		
		exerciseService.delExercise(principal.getName(), date, time);
		
		if(request.getParameterValues("exercise") != null) {
			String[] exer_arr = request.getParameterValues("exercise");
			String[] num_arr = request.getParameterValues("num");
			String[] unit_arr = request.getParameterValues("unit");
			int[] num_int = new int[num_arr.length];
			
			for (int i = 0; i < num_arr.length; i++) {   
			   try {
			      num_int[i] = Integer.parseInt(num_arr[i]);
			   } catch (Exception e) {
				   System.out.println(e);
			   }
			}
			
	        for (int i = 0; i < exer_arr.length; i++) {
	        	exerciseService.saveExercise(principal.getName(), date, time, exer_arr[i], num_int[i], unit_arr[i]);
	        }	
		}
        
        return "redirect:/exercise/diary?date="+date;
	}
	
	@ResponseBody
	@RequestMapping(value="/diary", method = {RequestMethod.POST})
	public void delDiet(java.sql.Date date, String time, Principal principal) {		
		exerciseService.delExercise(principal.getName(), date, time);
	}
	

}

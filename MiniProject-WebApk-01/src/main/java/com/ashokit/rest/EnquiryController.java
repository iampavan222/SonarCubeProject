package com.ashokit.rest;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.ashokit.binding.DashboardResponse;
import com.ashokit.binding.EnquiryForm;
import com.ashokit.service.EnquiryService;

import jakarta.servlet.http.HttpSession;

@Controller
public class EnquiryController
{
	@Autowired
	private HttpSession session;
	
	@Autowired
	private EnquiryService enquiryService;
	
	@GetMapping("/logout")
	public String logOut()
	{
		System.out.println("EnquiryController.logOut()");
		session.invalidate();
		return "index";
	}
	
	
	
	@GetMapping("/dashboard")
	public String getDashboardPage(Model model)
	{
		System.out.println("EnquiryController.getDashboardPage()");
		
		Integer userId = (Integer) session.getAttribute("userId");
		
		//set the uid to service class
		DashboardResponse dashboardData = enquiryService.getDashboardResponse(userId);
		
		//set the data to model class using attribute
		model.addAttribute("dashboardData", dashboardData);
		
		return "dashboard";
	}
	
	
	@PostMapping("/enquiry")
	public String AddEnquiryPage(@ModelAttribute("EnqFormObj") EnquiryForm EnqFormObj,Model model)
	{
		System.out.println("EnquiryController.AddEnquiryPage()");
		
		//print the data in add enquiry form to console
		System.out.println(EnqFormObj);
		
		//check the condition
		boolean status = enquiryService.saveEnquiry(EnqFormObj);
		
		if(status)
		{
			model.addAttribute("succMsg", "Enquiry Added");
		}
		else
		{
			model.addAttribute("errMsg", "Problem Occured Due to technical Issue");
		}
		
		return "add-enquiry";
	}
	
	@GetMapping("/enquiry")
	public String AddEnquiry(Model model)
	{
		System.out.println("EnquiryController.AddEnquiry()");
		
		//get courses for drop down
		List<String> courses = enquiryService.getCourses();
		
		//get enq status for drop down
		List<String> enqStatus = enquiryService.getEnquiryStatus();
		
		System.out.println(enqStatus);
		
		//create binding class obj and set data in model obj
		model.addAttribute("EnqFormObj", new EnquiryForm());
		model.addAttribute("courseName", courses);
		model.addAttribute("enqStatus", enqStatus);
		
		return "add-enquiry";
	}
	
	@GetMapping("/enquires")
	public String viewEnquires()
	{
		return "view-queries";
	}
	

}

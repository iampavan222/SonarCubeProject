package com.ashokit.service;

import java.util.List;

import com.ashokit.binding.DashboardResponse;
import com.ashokit.binding.EnquiryForm;
import com.ashokit.binding.EnquirySearchCriteria;

public interface EnquiryService 
{
	public List<String> getCourses();
	
	public List<String> getEnquiryStatus();
	
	public DashboardResponse getDashboardResponse(Integer userId);
	
	public boolean saveEnquiry(EnquiryForm enquiryForm);
	
	public List<EnquiryForm> getEnquiries(Integer userId,
			EnquirySearchCriteria searchCriteria);

}

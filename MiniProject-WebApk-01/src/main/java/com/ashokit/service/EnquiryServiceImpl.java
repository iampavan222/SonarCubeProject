package com.ashokit.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ashokit.binding.DashboardResponse;
import com.ashokit.binding.EnquiryForm;
import com.ashokit.binding.EnquirySearchCriteria;
import com.ashokit.entity.CourseEntity;
import com.ashokit.entity.EnqStatusEntity;
import com.ashokit.entity.StudentEnqEntity;
import com.ashokit.entity.UserDtlsEntity;
import com.ashokit.repo.CourseRepo;
import com.ashokit.repo.EnqStatusRepo;
import com.ashokit.repo.StudentEnqRepo;
import com.ashokit.repo.UserDtlsRepo;

import jakarta.persistence.criteria.CriteriaBuilder.In;
import jakarta.servlet.http.HttpSession;

@Service
public class EnquiryServiceImpl implements EnquiryService 
{
	@Autowired
	private UserDtlsRepo userDtlsRepo;
	
	@Autowired
	private CourseRepo courseRepo;
	
	@Autowired
	private EnqStatusRepo enqStatusRepo;
	
	@Autowired
	private StudentEnqRepo studentEnqRepo;
	
	@Autowired
	private HttpSession session;

	@Override
	public List<String> getCourses() 
	{
		List<CourseEntity> findAll = courseRepo.findAll();
		
		List<String> names= new ArrayList<String>();
		
		for(CourseEntity entity:findAll)
		{
			names.add(entity.getCourseName());
		}
		
		return names;
	}

	@Override
	public List<String> getEnquiryStatus() 
	{
		List<EnqStatusEntity> list = enqStatusRepo.findAll();
		
		List<String> status= new ArrayList<String>();
		
		for(EnqStatusEntity entity:list)
		{
			status.add(entity.getStatusName());
		}
		return status;
	}

	@Override
	public DashboardResponse getDashboardResponse(Integer userId) 
	{
		System.out.println("EnquiryServiceImpl.getDashboardResponse()");
		
		//associated the value to dashboard page using object
		DashboardResponse response = new DashboardResponse();

		Optional<UserDtlsEntity> findById = userDtlsRepo.findById(userId);
		
		if(findById.isPresent())
		{
			UserDtlsEntity userEntity = findById.get();
			
			//get all the enqueries
			List<StudentEnqEntity> enquiries = userEntity.getEnquiries();
			
			//System.out.println(enquiries);
			
			//count the enqueries list
			Integer totalCnt = enquiries.size();
			
			//write our own business logic with enrolled and 
			Integer enrolledCnt = enquiries.stream().filter(e->e.getEnqStatus().equals("Enrolled")).collect(Collectors.toList()).size();
			
			//write our business logic with lost
			Integer lostCnt = enquiries.stream().filter(e->e.getEnqStatus().equals("Lost")).collect(Collectors.toList()).size();
			
			//set the values to the object
			response.setTotalEnqueriesCnt(totalCnt);
			response.setEnrolledCnt(enrolledCnt);
			response.setLostCnt(lostCnt);
		}
		
		return response;
	}

	@Override
	public boolean saveEnquiry(EnquiryForm enquiryForm)
	{
		//object creation for student
		StudentEnqEntity studentEnqEntity = new StudentEnqEntity();
		
		//copy the form to entity class using bean utils
		BeanUtils.copyProperties(enquiryForm, studentEnqEntity);
		
		Integer userId = (Integer)session.getAttribute("userId");
		
	    UserDtlsEntity userDtlsEntity = userDtlsRepo.findById(userId).get();
	   
	    //set the userentity to student
	    studentEnqEntity.setUser(userDtlsEntity);
	    
	    //save the entity details to database
		studentEnqRepo.save(studentEnqEntity);
		
		return true;
	}

	@Override
	public List<EnquiryForm> getEnquiries(Integer userId, EnquirySearchCriteria searchCriteria) {
		// TODO Auto-generated method stub
		return null;
	}

}

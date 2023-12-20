package com.ashokit.service;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ashokit.binding.LoginForm;
import com.ashokit.binding.SignUpForm;
import com.ashokit.binding.UnlockForm;
import com.ashokit.entity.UserDtlsEntity;
import com.ashokit.repo.UserDtlsRepo;
import com.ashokit.utils.EmailUtils;
import com.ashokit.utils.PwdUtils;

import jakarta.servlet.http.HttpSession;


@Service
public class UserServiceImpl implements UserService {
	@Autowired
	private PwdUtils pwdUtils;
	
	@Autowired
	private UserDtlsRepo userRepo;

	@Autowired
	private EmailUtils emailUtils;
	
	@Autowired
	private HttpSession session;

	@Override
	public String login(LoginForm loginForm) 
	{
		UserDtlsEntity entity = 
				 userRepo.findByEmailIdAndPwd(loginForm.getEmail(), loginForm.getPwd());
		
		if(entity==null)
		{
			return "Invalid Credentials";
		}
		
		if(entity.getAccStatus().equals("LOCKED"))
		{
			return"Account is in Locked";
		}
		
		//create session and store user data in session
		session.setAttribute("userId", entity.getUserId());
	
		return "success";
	}

	@Override
	public boolean signup(SignUpForm signUpForm) 
	{
		
		UserDtlsEntity user = userRepo.findByEmailId(signUpForm.getEmailId());

		if (null != user) {
			return false;
		}
		// copy data from model class to entity class object
		UserDtlsEntity entity = new UserDtlsEntity();
		BeanUtils.copyProperties(signUpForm, entity);

		// first generate strong random password
		String tempPassword = pwdUtils.generatePwd();
		entity.setPwd(tempPassword);

		// set account status is Locked
		entity.setAccStatus("LOCKED");

		// inser record
		userRepo.save(entity);

		// set the receiver details
		String to = signUpForm.getEmailId();
		String subject = "unlock your account";

		// modify the code using stringbuffer
		StringBuffer body = new StringBuffer("");
		body.append("<h1>use temporary Password to unlock your account</h1>");
		body.append("<br>");

		// set the temparary password
		body.append("<h1><b>TempPassword::</b></h1>" + tempPassword);
		body.append("<br>");

		// sent the email to the particular id
		body.append("<a href=\"http://localhost:8080/unlock?email=" + to + "\">click Here to unlock your Account</h1>");

		// send email to unlock the account
		emailUtils.sendEmailConfig(to, subject, body.toString());

		return true;
	}

	@Override
	public boolean unlockAccount(UnlockForm unlockForm) 
	{
		System.out.println("UserServiceImpl.unlockAccount()");
		UserDtlsEntity entity = userRepo.findByEmailId(unlockForm.getEmail());
		if(entity.getPwd().equals(unlockForm.getTempPwd()))
		{
			entity.setPwd(unlockForm.getNewPwd());
			entity.setAccStatus("UNLOCKED");
			userRepo.save(entity);
			return true;
		}
		else
		{
			return false;
		}
	}

	@Override
	public boolean forgotPwd(String emaiId) 
	{
		UserDtlsEntity status = userRepo.findByEmailId(emaiId);
		if(status==null)
		{
			return false;
		}
		
		//send the password to mail 
		String subJect="Recovery Password";
		String body="Your Pwd::"+status.getPwd();
		emailUtils.sendEmailConfig(emaiId, subJect, body);
		
		return true;
	}

}

package com.ashokit.service;

import com.ashokit.binding.LoginForm;
import com.ashokit.binding.SignUpForm;
import com.ashokit.binding.UnlockForm;

public interface UserService 
{
	public String login(LoginForm loginForm);
	
	public boolean signup(SignUpForm signUpForm);
	
	public boolean unlockAccount(UnlockForm unlockForm);
	
	public boolean forgotPwd(String emaiId);

}

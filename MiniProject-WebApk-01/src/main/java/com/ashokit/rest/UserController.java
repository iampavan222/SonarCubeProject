package com.ashokit.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.ashokit.binding.LoginForm;
import com.ashokit.binding.SignUpForm;
import com.ashokit.binding.UnlockForm;
import com.ashokit.service.UserService;

@Controller
public class UserController {
	@Autowired
	private UserService userService;

	@PostMapping("/signup")
	public String getPostDataInSignUpPage(@ModelAttribute("user") SignUpForm signUpForm, Model model) {
		System.out.println("UserController.getPostDataInSignUpPage()");
		System.out.println(signUpForm);
		boolean status = userService.signup(signUpForm);
		if (status) {
			model.addAttribute("successMsg", "Account created, check your email");
		} else {
			model.addAttribute("errMsg", "choose unique email id");
		}
		return "signup";
	}

	@GetMapping("/signup")
	public String getSignUpPage(Model model) {
		System.out.println("UserController.getSignUpPage()");
		model.addAttribute("user", new SignUpForm());
		return "signup";
	}

	@GetMapping("/login")
	public String getLoginPage(Model model) 
	{
		System.out.println("UserController.getLoginPage()");
		model.addAttribute("loginForm", new LoginForm());
		return "login";
	}
	
	
	@PostMapping("/login")
	public String goForLoginPage(@ModelAttribute("loginForm") LoginForm loginForm,Model model)
	{
		System.out.println("UserController.goForLoginPage()");
		System.out.println(loginForm);
		
		//business logic 
		String status = userService.login(loginForm);
		
		if(status.equalsIgnoreCase("success"))
		{
			return "dashboard";
		}
		
		//set the error msg
		model.addAttribute("errMsg", status);
		
		return "login";
	}
	

	@GetMapping("/unlock")
	public String goUnlockPage(@RequestParam String email,Model model) 
	{
		UnlockForm unlockForm = new UnlockForm();
		unlockForm.setEmail(email);
		//set the values to model obj 
		model.addAttribute("unlock", unlockForm);
		return "unlock";
	}
	
	@PostMapping("/unlock")
	public String unlockPage(@ModelAttribute("unlock") UnlockForm unlockForm,Model model)
	{
		System.out.println(unlockForm);
		System.out.println("UserController.unlockPage()");
		/*
		 * write our own business logic
		 */
		if(unlockForm.getNewPwd().equals(unlockForm.getConfirmPwd()))
		{
			boolean status = userService.unlockAccount(unlockForm);
			if(status)
			{
				model.addAttribute("succMsg", "Your account will be unlocked");
			}
			else
			{
				model.addAttribute("errMsg", "Given Auto generatePwd is not incorrect,check your email ");
			}
		}
		else
		{
			model.addAttribute("errMsg", "ConfirmPwd and New Pwd are not same");
		}	
		return "unlock";
	}

	@GetMapping("/forgotPwd")
	public String goToForgetPwd() 
	{
		System.out.println("UserController.goToForgetPwd()");
		return "forgotPwd";
	}
	
	@PostMapping("/forgotPwd")
	public String forgotPwd(@RequestParam("email") String email,Model model)
	{
		System.out.println("UserController.forgotPwd()");
		
		System.out.println(email);
		
		boolean status = userService.forgotPwd(email);
		if(status)
		{
			model.addAttribute("succMsg", "Pwd is sent to your mail");
		}
		else
		{
			model.addAttribute("errMsg", "please provide valid emailId");
		}
		return"forgotPwd";
	}
	

}

package com.ashokit.utils;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.stereotype.Component;

@Component
public class PwdUtils 
{
	public static String generatePwd()
	{
		 // Define the characters that can be used in the password
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        String random = RandomStringUtils.random(6,characters);
        return random;
	}

}

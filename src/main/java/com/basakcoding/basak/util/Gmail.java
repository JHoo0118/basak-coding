package com.basakcoding.basak.util;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;

public class Gmail extends Authenticator{
	
	@Override
	protected PasswordAuthentication getPasswordAuthentication() {
		return new PasswordAuthentication("basakcoding@gmail.com","a123456789!");
	}
	
	
	
	
	
	
}

package com.tauriel.demo.util;

import java.net.Authenticator;
import java.net.PasswordAuthentication;

public class MyAuthenticator extends Authenticator {
	
	String userName;
	String password;

	public MyAuthenticator(String userName, String password) {
		this.userName = userName;
		this.password = password;
	}

	/**
	 * 当需要使用密码校验时自动触发
	 */
	@Override
	protected PasswordAuthentication getPasswordAuthentication() {
		return new PasswordAuthentication(userName, password.toCharArray());
	}
}

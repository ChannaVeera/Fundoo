package com.BridgeIt.FundooApp.Utility;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.BridgeIt.FundooApp.user.Dto.LoginDto;
import com.BridgeIt.FundooApp.user.Model.User;



@Component
public class EncryptUtil {
	@Autowired
	private PasswordEncoder passwordEncoder;

	public String encryptPassword(String password) {
		return passwordEncoder.encode(password);

	}

	public boolean ispassword(LoginDto loginDto, User user)

	{
		return passwordEncoder.matches(loginDto.getPassword(), user.getPassword());

	}

}

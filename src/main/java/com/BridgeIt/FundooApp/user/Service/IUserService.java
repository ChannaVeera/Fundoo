package com.BridgeIt.FundooApp.user.Service;

import javax.servlet.http.HttpServletRequest;

import com.BridgeIt.FundooApp.user.Dto.ForgetPasswordDto;
import com.BridgeIt.FundooApp.user.Dto.LoginDto;
import com.BridgeIt.FundooApp.user.Dto.UserDto;


public interface IUserService {
	String registeruser(UserDto userDto, HttpServletRequest requestUrl);

	String loginuser(LoginDto loginDto);

	String forgotpassword(String email);

	String resetpassword(String token, ForgetPasswordDto forgotPassword);

	String validateMail(String token);
}

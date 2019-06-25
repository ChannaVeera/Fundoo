package com.BridgeIt.FundooApp.user.Service;

import javax.servlet.http.HttpServletRequest;

import com.BridgeIt.FundooApp.user.Dto.ForgetPasswordDto;
import com.BridgeIt.FundooApp.user.Dto.LoginDto;
import com.BridgeIt.FundooApp.user.Dto.UserDto;
import com.BridgeIt.FundooApp.user.Model.Response;

public interface IUserService {
	Response registeruser(UserDto userDto, HttpServletRequest requestUrl);

	Response loginuser(LoginDto loginDto);

	Response forgotpassword(LoginDto loginDto);

	Response resetpassword(String token, ForgetPasswordDto forgotPassword);

}

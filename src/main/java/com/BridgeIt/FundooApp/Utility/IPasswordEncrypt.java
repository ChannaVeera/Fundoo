package com.BridgeIt.FundooApp.Utility;

import com.BridgeIt.FundooApp.user.Dto.LoginDto;
import com.BridgeIt.FundooApp.user.Model.User;

public interface IPasswordEncrypt {
	 String encryptPassword(String password);
	 boolean ispassword(LoginDto loginDto, User user);
}

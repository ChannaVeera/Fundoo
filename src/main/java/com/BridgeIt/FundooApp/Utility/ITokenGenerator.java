package com.BridgeIt.FundooApp.Utility;

public interface ITokenGenerator {

	String generateToken(String userId);
	
	String verifyToken(String token);
}

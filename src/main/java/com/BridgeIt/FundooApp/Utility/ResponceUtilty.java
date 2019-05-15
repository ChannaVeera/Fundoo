package com.BridgeIt.FundooApp.Utility;

import org.springframework.stereotype.Component;

import com.BridgeIt.FundooApp.user.Model.Response;

@Component
public class ResponceUtilty {


	public static Response getResponse(int statusCode, String token, String statusMessage) {
		Response respone = new Response();
		respone.setStatusCode(statusCode);
		respone.setToken(token);
		respone.setStatusMessage(statusMessage);
		return respone;

	}
}

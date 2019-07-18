package com.BridgeIt.FundooApp.user;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import com.BridgeIt.FundooApp.Utility.JWTToken;

public class TestNote {
	
	
	@Mock
	JWTToken token;
	
	@Test
	public void Test()
	{
		
		String userId="5ce645282e115443b891edf1";
		when(token.generateToken(userId)).thenReturn("yhvluiyhguyuyg");
//		assertEquals(Mockito.anyString(), token.ge);
	}

}

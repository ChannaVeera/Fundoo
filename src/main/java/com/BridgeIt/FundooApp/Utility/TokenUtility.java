package com.BridgeIt.FundooApp.Utility;


import java.util.Calendar;
import java.util.Locale;

import org.springframework.stereotype.Component;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.Verification;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component

public class TokenUtility {
	private static final String secretkey = "qwertyuiop123";

//	@Override
	public String generateToken(String userId) {
		Calendar cal = Calendar.getInstance(Locale.US);
		Calendar cal1 = Calendar.getInstance(Locale.US);
		cal1.setTime(cal.getTime());
		cal1.add(Calendar.HOUR_OF_DAY, 300);
		String token = Jwts.builder().setSubject("fundooNotes").setIssuedAt(cal.getTime()).setExpiration(cal1.getTime())

				.setId(userId).signWith(SignatureAlgorithm.HS256, secretkey).compact();
		return token;
	}

//	@Override
	public String verifyToken(String token) {
		Jws<Claims> claims = Jwts.parser().setSigningKey(secretkey).parseClaimsJws(token);
		String userId = claims.getBody().getId();
		return userId;
	}


}

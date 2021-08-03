package com.springboot.TransporterAPI.Util;

import org.springframework.stereotype.Component;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseToken;
import com.springboot.TransporterAPI.Exception.BusinessException;

@Component
public class FirebaseUtil {

	public void validateToken(String token){
		try {
			FirebaseToken decodedToken=FirebaseAuth.getInstance().verifyIdTokenAsync(token, true).get();
//			System.out.println(decodedToken);
		} catch (Exception e) {
			throw new BusinessException("Invalid token");
		}
	}
}

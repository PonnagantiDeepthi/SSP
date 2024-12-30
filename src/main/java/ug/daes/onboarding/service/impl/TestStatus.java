package ug.daes.onboarding.service.impl;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

public class TestStatus {
	public static void main(String[] agrs) {
		
		String uriCheckStatus = "http://10.10.1.79:8080/api/get/service-status";
		RestTemplate rest = new RestTemplate();
		
		try {
			ResponseEntity<String> resStatus = rest.getForEntity(uriCheckStatus, String.class);
			
			HttpStatus httpStatus = resStatus.getStatusCode();
			
			System.out.println("httpStatus ==>> "+httpStatus);
		} catch (HttpClientErrorException e) {
			System.out.println("in HttpClientErrorException ==> "+e.getRawStatusCode());
			
			e.printStackTrace();
		}catch (Exception e) {
			System.out.println("in Exception");
			e.printStackTrace();
		}
	}
}
 
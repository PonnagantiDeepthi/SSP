package ug.daes.onboarding.controller;

import java.security.NoSuchAlgorithmException;
import java.text.ParseException;

import javax.mail.SendFailedException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import ug.daes.onboarding.constant.ApiResponse;
import ug.daes.onboarding.dto.MobileOTPDto;
import ug.daes.onboarding.service.iface.OtpServiceIface;

@RestController
@CrossOrigin
public class OTPController {

	private static Logger logger = LoggerFactory.getLogger(OTPController.class);

	/** The Constant CLASS. */
	final static String CLASS = "OTPController";

	
	@Autowired
	OtpServiceIface otpServiceIface;
	
	@PostMapping("/api/post/register-subscriber")
	public ApiResponse sendOtpMobile(@RequestBody MobileOTPDto otpDto)
			throws NoSuchAlgorithmException, SendFailedException, ParseException {
		logger.info(CLASS + "sendOtpMobile req {} ",otpDto);
		otpDto.getSubscriberEmail().toLowerCase();
		return otpServiceIface.sendOTPMobileSms(otpDto);
	}

	
}

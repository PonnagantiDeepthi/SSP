package ug.daes.onboarding.service.iface;

import java.text.ParseException;

import ug.daes.onboarding.constant.ApiResponse;
import ug.daes.onboarding.dto.MobileOTPDto;

public interface OtpServiceIface {

	ApiResponse sendOTPMobileSms(MobileOTPDto mobileOTPDto)throws ParseException;
	
	ApiResponse sendEmail(MobileOTPDto mobileOTPDto) ;
}

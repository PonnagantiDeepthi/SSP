/**
 * 
 */
package ug.daes.onboarding.service.iface;

import java.text.ParseException;

import org.springframework.http.ResponseEntity;

import ug.daes.onboarding.constant.ApiResponse;
import ug.daes.onboarding.dto.*;

/**
 * @author Raxit Dubey
 *
 */
public interface SubscriberServiceIface {

	/**
	 * @param subscriberDTO
	 * @return
	 */
	ApiResponse saveSubscribersData(MobileOTPDto subscriberDTO) throws ParseException;

	ApiResponse addSubscriberObData(SubscriberObRequestDTO obRequestDTO) throws Exception;
	
	ApiResponse reOnboardAddSubscriberObData(SubscriberObRequestDTO obRequestDTO) throws Exception;

	ApiResponse getSubscriberObData(GetSubscriberObDataDTO subscriberUID);

	ApiResponse resetPin(GetSubscriberObDataDTO subscriberObDataDTO);

	ApiResponse getBase64String(String uri);

	ResponseEntity<Object> getVideoLiveStreaming(String subscriberUid);

	ResponseEntity<Object> getVideoLiveStreamingLocalEdms(String subscriberUid);

	ApiResponse addTrustedUsers(TrustedUserDto emails);

	ApiResponse getSubscriberDetailsReports(String startDate, String endDte);

	ApiResponse updatePhoneNumber( UpdateDto updateDto);
	ApiResponse updateEmail( UpdateDto updateDto);

	ApiResponse sendOtpEmail(UpdateOtpDto otpDto);

	ApiResponse sendOtpMobile(UpdateOtpDto otpDto);
	
	ApiResponse deleteRecord(String mobileNo,String email);
	
	

}

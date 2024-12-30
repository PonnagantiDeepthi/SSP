package ug.daes.onboarding.service.impl;

import java.security.SecureRandom;
import java.text.ParseException;
import java.util.Base64;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import org.hibernate.PessimisticLockException;
import org.hibernate.QueryTimeoutException;
import org.hibernate.exception.ConstraintViolationException;
import org.hibernate.exception.DataException;
import org.hibernate.exception.GenericJDBCException;
import org.hibernate.exception.JDBCConnectionException;
import org.hibernate.exception.LockAcquisitionException;
import org.hibernate.exception.SQLGrammarException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

import ug.daes.onboarding.constant.ApiResponse;
import ug.daes.onboarding.dto.EmailReqDto;
import ug.daes.onboarding.dto.MobileOTPDto;
import ug.daes.onboarding.dto.NotificationContextDTO;
import ug.daes.onboarding.dto.NotificationDTO;
import ug.daes.onboarding.dto.NotificationDataDTO;
import ug.daes.onboarding.dto.SmsDTO;
import ug.daes.onboarding.model.Subscriber;
import ug.daes.onboarding.model.SubscriberFcmToken;
import ug.daes.onboarding.repository.SubscriberFcmTokenRepoIface;
import ug.daes.onboarding.repository.SubscriberRepoIface;
import ug.daes.onboarding.service.iface.TestOTPServiceIface;
import ug.daes.onboarding.util.AppUtil;

@Service
public class TestOTPServiceImpl implements TestOTPServiceIface{
	
	@Value(value = "${test.mobile.otp}")
	private String mobileno;

	@Value(value = "${test.email.otp}")
	private String email;
	
	@Value(value = "${nira.username}")
	private String niraUserName;

	@Value(value = "${nira.password}")
	private String niraPassword;
	
	@Value(value = "${nira.api.sms}")
	private String niraApiSMS;
	
	@Value(value = "${nira.api.token}")
	private String niraApiToken;
	
	@Value(value = "${nira.api.timetolive}")
	private int timeToLive;
	
	@Value(value = "${email.url}")
	private String emailBaseUrl;
	
	@Value(value = "${onboarding.notificationurl}")
	private String notificationUrl;
	
//	@Value(value = "${fcm.token}")
//	private String fcmToken;
	
	@Autowired
	MessageSource messageSource;
	
	@Autowired
	OtpServiceImpl otpServiceImpl;
	
	@Autowired
	RestTemplate restTemplate;
	
	@Autowired
	SubscriberRepoIface subscriberRepoIface;
	
	@Autowired
	SubscriberFcmTokenRepoIface subscriberFcmTokenRepoIface;

	@Override
	public ApiResponse testMobileOtpService() {
		try {
			String mobileOTP = generateOtp(6);
			if (mobileno.startsWith("+256")) {
				if (mobileno.length() == 13) {
					ApiResponse response = sendSMSUGA(mobileOTP, mobileno, timeToLive);
					if(response.isSuccess()) {
						return AppUtil.createApiResponse(true,  messageSource.getMessage("api.response.mobile.otp.send.successfully", null, Locale.ENGLISH), response.getResult());
					}else {
						return AppUtil.createApiResponse(false,  messageSource.getMessage("api.error.otp.send.failed", null, Locale.ENGLISH), null);
					}
					
				} else {
					return AppUtil.createApiResponse(false, messageSource.getMessage("api.error.phone.number.is.invalid.please.enter.correct.phone.number", null, Locale.ENGLISH),null);
				}
			}else {
				return AppUtil.createApiResponse(false, messageSource.getMessage("api.error.invalid.country.code", null, Locale.ENGLISH),null);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return AppUtil.createApiResponse(false,  messageSource.getMessage("api.error.something.went.wrong.please.try.after.sometime", null, Locale.ENGLISH), null);
		}
	}

	@Override
	public ApiResponse testEmailOtpService() {
		try {
			String emailOTP = generateOtp(5);
			EmailReqDto dto = new EmailReqDto();
			dto.setEmailOtp(emailOTP);
			dto.setEmailId(email);
			//dto.setTtl(timeToLive);

			ApiResponse res = sendEmailToSubscriber(dto);
			if(res.isSuccess()) {		
				System.out.println("Email Sent Successfully");
				return AppUtil.createApiResponse(true, messageSource.getMessage("api.response.test.email.otp.sent.successfully", null, Locale.ENGLISH), null);

			}else {
				return AppUtil.createApiResponse(false,  messageSource.getMessage("api.error.otp.send.failed", null, Locale.ENGLISH), null);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return AppUtil.createApiResponse(false,  messageSource.getMessage("api.error.something.went.wrong.please.try.after.sometime", null, Locale.ENGLISH), null);
		}
	}
	
	public ApiResponse sendSMSUGA(String otp, String mobileNumber, int timeToLive) throws ParseException {
		String url = niraApiSMS;
		String basicAuth = getBasicAuth();
		SmsDTO smsDTO = new SmsDTO();
		smsDTO.setPhoneNumber(mobileNumber);
//		smsDTO.setSmsText("Dear Customer, your OTP for UgPass Registration is " + otp
//				+ ", Please use this OTP to validate your Mobile number. This OTP is valid for " + timeToLive
//				+ " Seconds - UgPass System");
		
		smsDTO.setSmsText("Dear Customer, your OTP for UgPass Registration is " + otp
				+ ", Please use this OTP to validate your Mobile number. This OTP is valid for " + timeToLive
				+ " Seconds - UgPass System");
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.set("daes-authorization", basicAuth);
		headers.set("access_token", getToken());
		HttpEntity<Object> requestEntity = new HttpEntity<>(smsDTO, headers);
		try {
			//logger.info("sendSMSUGA() >> req for restTemplate >> url {} and requestEntity {}", url,requestEntity);
			ResponseEntity<ApiResponse> res = restTemplate.exchange(url, HttpMethod.POST, requestEntity,
					ApiResponse.class);
			ApiResponse api = res.getBody();
			//logger.info("sendSMSUGA() >> res for restTemplate {}", res);
			return api;
		}
		catch (HttpClientErrorException e) {
			e.printStackTrace();
			if (e.getStatusCode() == HttpStatus.BAD_REQUEST) {
				return AppUtil.createApiResponse(false, messageSource.getMessage(
						"api.error.request.not.completed.because.of.bad.request", null, Locale.ENGLISH), null);
			} else if (e.getStatusCode() == HttpStatus.PAYLOAD_TOO_LARGE) {
				return AppUtil.createApiResponse(false, messageSource.getMessage(
						"api.error.request.not.completed.because.of.payload.is.to.large", null, Locale.ENGLISH), null);
			} else if (e.getStatusCode() == HttpStatus.REQUEST_TIMEOUT) {
				return AppUtil.createApiResponse(false, messageSource.getMessage(
						"api.error.request.not.completed.because.of.request.time.out", null, Locale.ENGLISH), null);
			} else {
				return AppUtil.createApiResponse(false,
						messageSource.getMessage("api.error.request.not.completed.because.error.code", null,
								Locale.ENGLISH) + e.getStatusCode(),
						null);
			}
		} catch (HttpServerErrorException e) {
			e.printStackTrace();
			if (e.getStatusCode() == HttpStatus.INTERNAL_SERVER_ERROR) {
				return AppUtil.createApiResponse(false,
						messageSource.getMessage("api.error.request.not.completed.because.of.internal.server.error",
								null, Locale.ENGLISH),
						null);
			} else if (e.getStatusCode() == HttpStatus.BAD_GATEWAY) {
				return AppUtil.createApiResponse(false, messageSource.getMessage(
						"api.error.request.not.completed.because.of.bad.gateway", null, Locale.ENGLISH), null);
			} else if (e.getStatusCode() == HttpStatus.SERVICE_UNAVAILABLE) {
				return AppUtil.createApiResponse(false, messageSource.getMessage(
						"api.error.request.not.completed.because.of.service.unavailable", null, Locale.ENGLISH), null);
			} else {
				return AppUtil.createApiResponse(false,
						messageSource.getMessage("api.error.request.not.completed.because.error.code", null,
								Locale.ENGLISH) + e.getStatusCode(),
						null);
			}
		}

		catch (Exception e) {
			//logger.error(CLASS + "sendSMSUGA() >> Exception {}",e.getMessage());
			e.printStackTrace();
			return AppUtil.createApiResponse(false,  messageSource.getMessage("api.error.something.went.wrong.please.try.after.sometime", null, Locale.ENGLISH), null);
		}
	}
	
	public String getBasicAuth() {
		String userCredentials = niraUserName + ":" + niraPassword;
		String basicAuth = new String(Base64.getEncoder().encode(userCredentials.getBytes()));
		return basicAuth;
	}
	
	public String getToken() {
		String url = niraApiToken;
		//logger.info("getToken() >> req >> url {} ", url);
		String basicAuth = getBasicAuth();
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.set("daes-authorization", basicAuth);
		HttpEntity<Object> requestEntity = new HttpEntity<>(headers);
		try {
			//logger.info("getToken() >> req for restTemplate {} ",requestEntity);
			ResponseEntity<String> res = restTemplate.exchange(url, HttpMethod.GET, requestEntity, String.class);
			//logger.info("getToken() >> res for restTemplate {}", res);
			return res.getBody();
		}
		catch (HttpClientErrorException e) {
			e.printStackTrace();
			if (e.getStatusCode() == HttpStatus.BAD_REQUEST) {
				return AppUtil.createApiResponse(false, messageSource.getMessage(
						"api.error.request.not.completed.because.of.bad.request", null, Locale.ENGLISH), null).toString();
			} else if (e.getStatusCode() == HttpStatus.PAYLOAD_TOO_LARGE) {
				return AppUtil.createApiResponse(false, messageSource.getMessage(
						"api.error.request.not.completed.because.of.payload.is.to.large", null, Locale.ENGLISH), null).toString();
			} else if (e.getStatusCode() == HttpStatus.REQUEST_TIMEOUT) {
				return AppUtil.createApiResponse(false, messageSource.getMessage(
						"api.error.request.not.completed.because.of.request.time.out", null, Locale.ENGLISH), null).toString();
			} else {
				return AppUtil.createApiResponse(false,
						messageSource.getMessage("api.error.request.not.completed.because.error.code", null,
								Locale.ENGLISH) + e.getStatusCode(),
						null).toString();
			}
		} catch (HttpServerErrorException e) {
			e.printStackTrace();
			if (e.getStatusCode() == HttpStatus.INTERNAL_SERVER_ERROR) {
				return AppUtil.createApiResponse(false,
						messageSource.getMessage("api.error.request.not.completed.because.of.internal.server.error",
								null, Locale.ENGLISH),
						null).toString();
			} else if (e.getStatusCode() == HttpStatus.BAD_GATEWAY) {
				return AppUtil.createApiResponse(false, messageSource.getMessage(
						"api.error.request.not.completed.because.of.bad.gateway", null, Locale.ENGLISH), null).toString();
			} else if (e.getStatusCode() == HttpStatus.SERVICE_UNAVAILABLE) {
				return AppUtil.createApiResponse(false, messageSource.getMessage(
						"api.error.request.not.completed.because.of.service.unavailable", null, Locale.ENGLISH), null).toString();
			} else {
				return AppUtil.createApiResponse(false,
						messageSource.getMessage("api.error.request.not.completed.because.error.code", null,
								Locale.ENGLISH) + e.getStatusCode(),
						null).toString();
			}
		}

		catch (Exception e) {
			//logger.error(CLASS + "getToken() >> Exception {}" ,e.getMessage());
			e.printStackTrace();
			return e.getMessage();
		}

	}
	
	public ApiResponse sendEmailToSubscriber(EmailReqDto emailReqDto) {
		try {
			String url = emailBaseUrl;
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);
			HttpEntity<Object> requestEntity = new HttpEntity<>(emailReqDto, headers);
			System.out.println("requestEntity >> " + requestEntity);
			ResponseEntity<ApiResponse> res = restTemplate.exchange(url, HttpMethod.POST, requestEntity,ApiResponse.class);
			System.out.println("res >> " + res);
			if(res.getStatusCodeValue() == 200) {
				return AppUtil.createApiResponse(true, res.getBody().getMessage(), res.getBody());
			}else if (res.getStatusCodeValue() == 400) {
				return AppUtil.createApiResponse(false, messageSource.getMessage("api.error.bad.request", null, Locale.ENGLISH), null);
			}else if (res.getStatusCodeValue() == 500) {
				return AppUtil.createApiResponse(false, messageSource.getMessage("api.error.internal.server.error", null, Locale.ENGLISH), null);
			}
			return AppUtil.createApiResponse(false, res.getBody().getMessage(), null);
		}
		catch (HttpClientErrorException e) {
			e.printStackTrace();
			if (e.getStatusCode() == HttpStatus.BAD_REQUEST) {
				return AppUtil.createApiResponse(false, messageSource.getMessage(
						"api.error.request.not.completed.because.of.bad.request", null, Locale.ENGLISH), null);
			} else if (e.getStatusCode() == HttpStatus.PAYLOAD_TOO_LARGE) {
				return AppUtil.createApiResponse(false, messageSource.getMessage(
						"api.error.request.not.completed.because.of.payload.is.to.large", null, Locale.ENGLISH), null);
			} else if (e.getStatusCode() == HttpStatus.REQUEST_TIMEOUT) {
				return AppUtil.createApiResponse(false, messageSource.getMessage(
						"api.error.request.not.completed.because.of.request.time.out", null, Locale.ENGLISH), null);
			} else {
				return AppUtil.createApiResponse(false,
						messageSource.getMessage("api.error.request.not.completed.because.error.code", null,
								Locale.ENGLISH) + e.getStatusCode(),
						null);
			}
		} catch (HttpServerErrorException e) {
			e.printStackTrace();
			if (e.getStatusCode() == HttpStatus.INTERNAL_SERVER_ERROR) {
				return AppUtil.createApiResponse(false,
						messageSource.getMessage("api.error.request.not.completed.because.of.internal.server.error",
								null, Locale.ENGLISH),
						null);
			} else if (e.getStatusCode() == HttpStatus.BAD_GATEWAY) {
				return AppUtil.createApiResponse(false, messageSource.getMessage(
						"api.error.request.not.completed.because.of.bad.gateway", null, Locale.ENGLISH), null);
			} else if (e.getStatusCode() == HttpStatus.SERVICE_UNAVAILABLE) {
				return AppUtil.createApiResponse(false, messageSource.getMessage(
						"api.error.request.not.completed.because.of.service.unavailable", null, Locale.ENGLISH), null);
			} else {
				return AppUtil.createApiResponse(false,
						messageSource.getMessage("api.error.request.not.completed.because.error.code", null,
								Locale.ENGLISH) + e.getStatusCode(),
						null);
			}
		}

		catch (Exception e) {
			e.printStackTrace();
			return AppUtil.createApiResponse(false,  messageSource.getMessage("api.error.something.went.wrong.please.try.after.sometime", null, Locale.ENGLISH), null);
		}
		
	}
	
	public String generateOtp(int maxLength) {
		try {
			SecureRandom secureRandom = SecureRandom.getInstance("SHA1PRNG");
			StringBuilder otp = new StringBuilder(maxLength);

			for (int i = 0; i < maxLength; i++) {
				otp.append(secureRandom.nextInt(9));
			}
			return otp.toString();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	
	//send notification
	@Override
	public ApiResponse testSendNotification() {	
		try {
			Map<String, String> paymentStatus = new HashMap<String, String>();
			Map<String, String> paymentTransactionId = new HashMap<String, String>();
			
			//sendNotification(fcmToken,notificationUrl);
			Subscriber subscribers = subscriberRepoIface.findFCMTokenByMobileEamil(mobileno,email);
			SubscriberFcmToken subscriberFcmToken = subscriberFcmTokenRepoIface.findBysubscriberUid(subscribers.getSubscriberUid());
			
			paymentStatus.put("PaymentStatus", "");
			paymentStatus.put("PaymentCategory", "");
			paymentTransactionId.put("PaymentTransactionId", "");
			
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);
			NotificationDTO notificationBody = new NotificationDTO();
			NotificationDataDTO dataDTO = new NotificationDataDTO();
			NotificationContextDTO contextDTO = new NotificationContextDTO();
			notificationBody.setTo(subscriberFcmToken.getFcmToken());
			notificationBody.setPriority("high");
			dataDTO.setTitle("Hi ");
			dataDTO.setBody("Test");
			contextDTO.setpREF_PAYMENT_STATUS(paymentStatus);
			contextDTO.setpREF_TRANSACTION_ID(paymentTransactionId);
			dataDTO.setNotificationContext(contextDTO);
			notificationBody.setData(dataDTO);
			//System.out.println("notificationReqBody in PaymentImpl :: " + notificationBody.getData());
			HttpEntity<Object> requestEntity = new HttpEntity<>(notificationBody, headers);
			System.out.println("RequestToken :" + requestEntity);
			
			ResponseEntity<Object> res = restTemplate.exchange(notificationUrl, HttpMethod.POST, requestEntity,
						Object.class);
			//System.out.println(res.getStatusCodeValue()+"-- res -- "+res.getBody());
			if(res.getStatusCodeValue() == 200) {
					return AppUtil.createApiResponse(true,  messageSource.getMessage("api.response.notification.send.successfully", null, Locale.ENGLISH), null);
			}else {
					return AppUtil.createApiResponse(false,  messageSource.getMessage("api.error.notification.send.failed", null, Locale.ENGLISH), null);
			}
			
			//sendNotification(paymentStatus, paymentTransactionId,subscriberFcmToken.getFcmToken(), notificationUrl);
			
		}
		catch (HttpClientErrorException e) {
			e.printStackTrace();
			if (e.getStatusCode() == HttpStatus.BAD_REQUEST) {
				return AppUtil.createApiResponse(false, messageSource.getMessage(
						"api.error.request.not.completed.because.of.bad.request", null, Locale.ENGLISH), null);
			} else if (e.getStatusCode() == HttpStatus.PAYLOAD_TOO_LARGE) {
				return AppUtil.createApiResponse(false, messageSource.getMessage(
						"api.error.request.not.completed.because.of.payload.is.to.large", null, Locale.ENGLISH), null);
			} else if (e.getStatusCode() == HttpStatus.REQUEST_TIMEOUT) {
				return AppUtil.createApiResponse(false, messageSource.getMessage(
						"api.error.request.not.completed.because.of.request.time.out", null, Locale.ENGLISH), null);
			} else {
				return AppUtil.createApiResponse(false,
						messageSource.getMessage("api.error.request.not.completed.because.error.code", null,
								Locale.ENGLISH) + e.getStatusCode(),
						null);
			}
		} catch (HttpServerErrorException e) {
			e.printStackTrace();
			if (e.getStatusCode() == HttpStatus.INTERNAL_SERVER_ERROR) {
				return AppUtil.createApiResponse(false,
						messageSource.getMessage("api.error.request.not.completed.because.of.internal.server.error",
								null, Locale.ENGLISH),
						null);
			} else if (e.getStatusCode() == HttpStatus.BAD_GATEWAY) {
				return AppUtil.createApiResponse(false, messageSource.getMessage(
						"api.error.request.not.completed.because.of.bad.gateway", null, Locale.ENGLISH), null);
			} else if (e.getStatusCode() == HttpStatus.SERVICE_UNAVAILABLE) {
				return AppUtil.createApiResponse(false, messageSource.getMessage(
						"api.error.request.not.completed.because.of.service.unavailable", null, Locale.ENGLISH), null);
			} else {
				return AppUtil.createApiResponse(false,
						messageSource.getMessage("api.error.request.not.completed.because.error.code", null,
								Locale.ENGLISH) + e.getStatusCode(),
						null);
			}
		}

		catch (JDBCConnectionException | ConstraintViolationException | DataException | LockAcquisitionException | PessimisticLockException
				| QueryTimeoutException | SQLGrammarException | GenericJDBCException e) {
			e.printStackTrace();
			return AppUtil.createApiResponse(false, messageSource.getMessage("api.error.something.went.wrong.please.try.after.sometime", null, Locale.ENGLISH), null);
		}
		catch (Exception e) {
			e.printStackTrace();
			return AppUtil.createApiResponse(false,  messageSource.getMessage("api.error.something.went.wrong.please.try.after.sometime", null, Locale.ENGLISH), null);
		}
	}

}

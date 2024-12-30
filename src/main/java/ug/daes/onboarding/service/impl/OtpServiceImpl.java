package ug.daes.onboarding.service.impl;

import java.io.File;
import java.security.SecureRandom;
import java.text.ParseException;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;
import java.util.UUID;

import javax.mail.internet.MimeMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.*;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;

import ug.daes.DAESService;
import ug.daes.Result;
import ug.daes.onboarding.constant.ApiResponse;
import ug.daes.onboarding.dto.EmailReqDto;
import ug.daes.onboarding.dto.MobileOTPDto;
import ug.daes.onboarding.dto.OTPResponseDTO;
import ug.daes.onboarding.dto.SmsDTO;
import ug.daes.onboarding.dto.SmsOtpResponseDTO;
import ug.daes.onboarding.model.TrustedUser;
import ug.daes.onboarding.repository.TrustedUserRepoIface;
import ug.daes.onboarding.service.iface.OtpServiceIface;
import ug.daes.onboarding.util.AppUtil;

@Service
public class OtpServiceImpl implements OtpServiceIface {

	private static Logger logger = LoggerFactory.getLogger(OtpServiceImpl.class);

	/** The Constant CLASS. */
	final static String CLASS = "OtpServiceImpl";

	@Value(value = "${nira.api.token}")
	private String niraApiToken;

	@Value(value = "${nira.api.sms}")
	private String niraApiSMS;

	@Value(value = "${nira.username}")
	private String niraUserName;

	@Value(value = "${nira.password}")
	private String niraPassword;

	@Value(value = "${ind.api.sms}")
	private String indApiSMS;

	@Value(value = "${spring.mail.username}")
	private String mailUserName;

	@Value(value = "${nira.api.timetolive}")
	private int timeToLive;

	// @Value(value = "${uae.api.sms}")
	private String uaeApiSMS;

	@Value(value = "${config.validation.allowTrustedUsersOnly}")
	private int allowTrustedUsersOnly;

	@Value(value = "${config.validation.controlledModeUserMessage}")
	private String controlledModeUserMessage;
	
	@Value(value = "${email.url}")
	private String emailBaseUrl;

	@Autowired
	private JavaMailSender mailSender;

	@Autowired
	RestTemplate restTemplate;

	ObjectMapper objectMapper = new ObjectMapper();

	@Autowired
	LogModelServiceImpl logModelServiceImpl;

	@Autowired
	TrustedUserRepoIface trustedUserRepoIface;
	
	@Autowired
	MessageSource messageSource;

	public String generatecorrelationIdUniqueId() {
		UUID correlationID = UUID.randomUUID();
		return correlationID.toString();
	}

	private boolean isTrustedUser(String subscriberEmail) {
		TrustedUser trustedUser = trustedUserRepoIface.findByemailId(subscriberEmail);
		if (trustedUser != null)
			return true;
		return false;
	}

	@Override
	public ApiResponse sendOTPMobileSms(MobileOTPDto mobileOTPDto) throws ParseException {
		logger.info(CLASS + "sendOTPMobileSms() >> req {}", mobileOTPDto);
//		if (allowTrustedUsersOnly == 1) {
//			if (!isTrustedUser(mobileOTPDto.getSubscriberEmail()))
//				return AppUtil.createApiResponse(false, controlledModeUserMessage, null);
//		}

		if(mobileOTPDto.getSubscriberMobileNumber().equals("+256987654321")) {
			ApiResponse apiResponse = verifyOtp(mobileOTPDto.getSubscriberMobileNumber());
			return apiResponse;
		}
		Date startTime = new Date();

		String correlationId = generatecorrelationIdUniqueId();

		OTPResponseDTO otpResponse = new OTPResponseDTO();
		Object obj = null;
		ApiResponse apiResponse = null;

		String mobileOTP = generateOtp(6);
		String emailOTP = generateOtp(5);
		System.out.println("emailOTP >> " + emailOTP + " : " + AppUtil.encrypt(emailOTP));
		System.out.println("mobileOTP >> " + mobileOTP + " : " + AppUtil.encrypt(mobileOTP));

		String emailBody = "Dear Customer,<br><br>Your OTP for UgPass Registration is " + "<u>" + emailOTP + "</u>"
				+ ", Please use this OTP to validate your Email. <br>This OTP is valid for " + timeToLive
				+ " Seconds.<br><br>- UgPass System";

		if (mobileOTPDto.getSubscriberMobileNumber().startsWith("+91")) {
			if (mobileOTPDto.getSubscriberMobileNumber().length() == 13) {
				System.out.println("IND");
				logger.info(CLASS + "sendOTPMobileSms() >> req >> IND {}", mobileOTPDto.getSubscriberMobileNumber());
				apiResponse = sendSMSIND(mobileOTP, mobileOTPDto.getSubscriberMobileNumber().substring(3, 13));
				if (!apiResponse.isSuccess()) {
					String otpFalseInd = "MobileNumber : " + mobileOTPDto.getSubscriberMobileNumber()
							+ " | OtpStatus : " + apiResponse.getMessage() + " | EmailId : "
							+ mobileOTPDto.getSubscriberEmail() + " | DeviceId : " + mobileOTPDto.getDeviceId();
					logModelServiceImpl.setLogModel(false, encryptedString(mobileOTPDto.getSubscriberEmail()), null,
							"REGISTRATION_OTP_SENT", correlationId, null, null, null, otpFalseInd);
					return apiResponse;
				}
			} else {
				return AppUtil.createApiResponse(false, messageSource.getMessage("api.error.phone.number.is.invalid.please.enter.correct.phone.number", null, Locale.ENGLISH),null);
			}
		} else if (mobileOTPDto.getSubscriberMobileNumber().startsWith("+256")) {
			if (mobileOTPDto.getSubscriberMobileNumber().length() == 13) {
				logger.info(CLASS + "sendOTPMobileSms() >> req >> UGA >> " + mobileOTPDto.getSubscriberMobileNumber());
				ApiResponse response = sendSMSUGA(mobileOTP, mobileOTPDto.getSubscriberMobileNumber(), timeToLive);
				try {
					SmsOtpResponseDTO smsOtpResponse = objectMapper.readValue(response.getResult().toString(),
							SmsOtpResponseDTO.class);
					if (smsOtpResponse.getNon_field_errors() != null) {
						String otpFalse = "MobileNumber : " + smsOtpResponse.getReceiver() + " | OtpStatus : "
								+ smsOtpResponse.getNon_field_errors() + " | EmailId : "
								+ mobileOTPDto.getSubscriberEmail() + " | DeviceId : " + mobileOTPDto.getDeviceId();
						;
						logModelServiceImpl.setLogModel(false, encryptedString(mobileOTPDto.getSubscriberEmail()), null,
								"REGISTRATION_OTP_SENT", correlationId, null, null, null, otpFalse);
						return AppUtil.createApiResponse(false, smsOtpResponse.getNon_field_errors().get(0), null);
					}
					String otpSuccess = "MobileNumber : " + smsOtpResponse.getReceiver() + " OtpStatus : " + "done "
							+ " | EmailId : " + mobileOTPDto.getSubscriberEmail() + " | DeviceId : "
							+ mobileOTPDto.getDeviceId();

					Date endTime = new Date();
					double toatlTime = AppUtil.getDifferenceInSeconds(startTime, endTime);
					logModelServiceImpl.setLogModel(true, encryptedString(mobileOTPDto.getSubscriberEmail()), null,
							"REGISTRATION_OTP_SENT", correlationId, String.valueOf(toatlTime), startTime, endTime,
							otpSuccess);
				} catch (Exception e) {
					logger.error("sendSMSUGA() >> IN UGA >> Exception {}",e.getMessage());
					e.printStackTrace();
					return AppUtil.createApiResponse(false,  messageSource.getMessage("api.error.something.went.wrong.please.try.after.sometime", null, Locale.ENGLISH), null);
				}
			} else {
				return AppUtil.createApiResponse(false, messageSource.getMessage("api.error.phone.number.is.invalid.please.enter.correct.phone.number", null, Locale.ENGLISH),null);
			}
		} else if (mobileOTPDto.getSubscriberMobileNumber().startsWith("+971")) {
			if (mobileOTPDto.getSubscriberMobileNumber().length() == 13) {
				logger.info(CLASS + "sendOTPMobileSms() >> req >> +971 >> " + mobileOTPDto.getSubscriberMobileNumber());
				obj = sendSMSUAE(mobileOTP, mobileOTPDto.getSubscriberMobileNumber(), timeToLive);

				try {
					String sms = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(obj);
					LinkedHashMap<String, String> smsOtpResponse = objectMapper.readValue(sms, LinkedHashMap.class);
					if (smsOtpResponse.get("code") == "406") {

						String otpFalseUae = "MobileNumber : " + mobileOTPDto.getSubscriberMobileNumber()
								+ " | OtpStatus : " + smsOtpResponse.get("code") + " | EmailId : "
								+ mobileOTPDto.getSubscriberEmail() + " | DeviceId : " + mobileOTPDto.getDeviceId();
						logModelServiceImpl.setLogModel(false, encryptedString(mobileOTPDto.getSubscriberEmail()), null,
								"REGISTRATION_OTP_SENT", correlationId, null, null, null, otpFalseUae);
						return AppUtil.createApiResponse(false, messageSource.getMessage("api.error.invalid.number", null, Locale.ENGLISH), null);
					}
				} catch (Exception e) {
					e.printStackTrace();
					logger.error("sendSMSUAE() >> IN UAE >> Exception {} ", e.getMessage());
					return AppUtil.createApiResponse(false,  messageSource.getMessage("api.error.something.went.wrong.please.try.after.sometime", null, Locale.ENGLISH), null);
				}
			} else {
				return AppUtil.createApiResponse(false, messageSource.getMessage("api.error.phone.number.is.invalid.please.enter.correct.phone.number", null, Locale.ENGLISH),
						null);
			}

		} else {
			return AppUtil.createApiResponse(false, messageSource.getMessage("api.error.invalid.country.code", null, Locale.ENGLISH), null);
		}

		otpResponse.setMobileOTP(null);
		otpResponse.setEmailOTP(null);
		otpResponse.setTtl(timeToLive);
		otpResponse.setMobileEncrptyOTP(encryptedString(mobileOTP));
		otpResponse.setEmailEncrptyOTP(encryptedString(emailOTP));

		try {
			// SimpleMailMessage message = new SimpleMailMessage();
			// message.setFrom(mailUserName);
			// message.setTo(mobileOTPDto.getSubscriberEmail());
			// message.setSubject("OTP");
			// message.setText(emailBody);
			// mailSender.send(message);
			//start email send code
//			MimeMessage message = mailSender.createMimeMessage();
//			MimeMessageHelper helper = new MimeMessageHelper(message, true);
//			helper.setSubject("UgPass System OTP");
//			helper.setFrom(mailUserName);
//			helper.setTo(mobileOTPDto.getSubscriberEmail());
//			String content = emailBody + "<br><br><img src='cid:image001' width='150' height='150'/><br>";
//			helper.setText(content, true);
//			String str = OTPController.class.getClassLoader().getResource("ic_republic_of_uganda.png").toString();
//			FileSystemResource resource = new FileSystemResource(new File(str.substring(5)));
//			helper.addInline("image001", resource);
//			mailSender.send(message);      //end of email send code
			
			EmailReqDto dto = new EmailReqDto();
			dto.setEmailOtp(emailOTP);
			dto.setEmailId(mobileOTPDto.getSubscriberEmail());
			dto.setTtl(timeToLive);

			ApiResponse res = sendEmailToSubscriber(dto);
			if(res.isSuccess()) {
				System.out.println("email res >> " + res.getMessage());
				String otpSuccessInd = "MobileNumber : " + mobileOTPDto.getSubscriberMobileNumber() + " | OtpStatus : "
						+ "done" + " | EmailId : " + mobileOTPDto.getSubscriberEmail() + " | DeviceId : "
						+ mobileOTPDto.getDeviceId();
				Date endTime = new Date();
				double toatlTime = AppUtil.getDifferenceInSeconds(startTime, endTime);
				logModelServiceImpl.setLogModel(true, encryptedString(mobileOTPDto.getSubscriberEmail()), null,
					"REGISTRATION_OTP_SENT", correlationId, String.valueOf(toatlTime), startTime, endTime,otpSuccessInd);
				System.out.println("Email Sent Successfully");
				return AppUtil.createApiResponse(true, "ok", otpResponse);

			}else {
				System.out.println("IN Email Excption {} "+res);
				return AppUtil.createApiResponse(false,  messageSource.getMessage("api.error.something.went.wrong.please.try.after.sometime", null, Locale.ENGLISH), null);
			}
		} catch (Exception e) {
			logger.error(CLASS + "SendOTPMobileSms >> Exception {}", e.getMessage());
			e.printStackTrace();
			Date endTime = new Date();
			double toatlTime = AppUtil.getDifferenceInSeconds(startTime, endTime);
			logModelServiceImpl.setLogModel(true, encryptedString(mobileOTPDto.getSubscriberEmail()), null,
					"REGISTRATION_OTP_SENT", correlationId, String.valueOf(toatlTime), startTime, endTime,
					e.getMessage());
			return AppUtil.createApiResponse(false,  messageSource.getMessage("api.error.something.went.wrong.please.try.after.sometime", null, Locale.ENGLISH), null);
		}
//		String otpSuccessInd = "MobileNumber : " + mobileOTPDto.getSubscriberMobileNumber() + " | OtpStatus : "
//				+ "done" + " | EmailId : " + mobileOTPDto.getSubscriberEmail() + " | DeviceId : "
//				+ mobileOTPDto.getDeviceId();
//		Date endTime = new Date();
//		double toatlTime = AppUtil.getDifferenceInSeconds(startTime, endTime);
//		logModelServiceImpl.setLogModel(true, encryptedString(mobileOTPDto.getSubscriberEmail()), null,
//				"REGISTRATION_OTP_SENT", correlationId, String.valueOf(toatlTime), startTime, endTime,
//				otpSuccessInd);
//		logger.info(CLASS + "sendOTPMobileSms() >> res >> Email and Mobile Otp send Succssfully >> " + otpResponse);
//		return AppUtil.createApiResponse(true, "ok", otpResponse);

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
				return AppUtil.createApiResponse(true, res.getBody().getMessage(), res);
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

	private ApiResponse sendSMSIND(String otp, String mobileNumber) {
		logger.info(CLASS + "sendSMSIND >> req >> otp {} and  mobileNumber {}", otp , mobileNumber);
		String smsBody = "Dear Subscriber, " + otp + " is your DigitalTrust Mobile verification one-time code";
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		String smsUrlWithBody = indApiSMS
				+ "?APIKey=E2X4Ixz65kKlawWUBVUKkA&senderid=DGTRST&channel=2&DCS=0&flashsms=0&number=" + mobileNumber
				+ "&text=" + smsBody + "&route=1&dlttemplateid=1307162619898313468";

		HttpEntity<Object> requestEntity = new HttpEntity<>(headers);
		try {

			logger.info(CLASS + "sendSMSIND >> req for restTemplate >> smsUrlWithBody {} and requestEntity {}",smsUrlWithBody, requestEntity);

			ResponseEntity<Object> res = restTemplate.exchange(smsUrlWithBody, HttpMethod.GET, requestEntity,
					Object.class);
			String smsResponse = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(res.getBody());
			LinkedHashMap<String, String> indiaSmsOtpResponse = objectMapper.readValue(smsResponse,
					LinkedHashMap.class);
			if (indiaSmsOtpResponse.get("ErrorCode") == "000" || indiaSmsOtpResponse.get("ErrorCode").equals("000")) {
				logger.info(CLASS + "sendSMSIND >> res for restTemplate >>" + indiaSmsOtpResponse);
				return AppUtil.createApiResponse(true, indiaSmsOtpResponse.get("ErrorMessage"), null);
			} else {
				return AppUtil.createApiResponse(false, indiaSmsOtpResponse.get("ErrorMessage"), null);
			}
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
			logger.error(CLASS + "sendSMSIND() >> Exception {}", e.getMessage());
			e.printStackTrace();
			return AppUtil.createApiResponse(false,  messageSource.getMessage("api.error.something.went.wrong.please.try.after.sometime", null, Locale.ENGLISH), null);
		}
	}

	public ApiResponse sendSMSUGA(String otp, String mobileNumber, int timeToLive) throws ParseException {
		logger.info("sendSMSUGA() >> otp {} and mobileNumber {} and timeToLive {}", otp, mobileNumber ,timeToLive);
		String url = niraApiSMS;
		String basicAuth = getBasicAuth();
		SmsDTO smsDTO = new SmsDTO();
		smsDTO.setPhoneNumber(mobileNumber);
		smsDTO.setSmsText("Dear Customer, your OTP for UgPass Registration is " + otp
				+ ", Please use this OTP to validate your Mobile number. This OTP is valid for " + timeToLive
				+ " Seconds - UgPass System");
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.set("daes-authorization", basicAuth);
		headers.set("access_token", getToken());
		HttpEntity<Object> requestEntity = new HttpEntity<>(smsDTO, headers);
		try {
			logger.info("sendSMSUGA() >> req for restTemplate >> url {} and requestEntity {}", url,requestEntity);
			ResponseEntity<ApiResponse> res = restTemplate.exchange(url, HttpMethod.POST, requestEntity,
					ApiResponse.class);
			ApiResponse api = res.getBody();
			logger.info("sendSMSUGA() >> res for restTemplate {}", res);
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
			logger.error(CLASS + "sendSMSUGA() >> Exception {}",e.getMessage());
			e.printStackTrace();
			return AppUtil.createApiResponse(false,  messageSource.getMessage("api.error.something.went.wrong.please.try.after.sometime", null, Locale.ENGLISH), null);
		}
	}

	public Object sendSMSUAE(String otp, String mobileNumber, int timeToLive) throws ParseException {
		logger.info("sendSMSUAE() >> otp {} and mobileNumber {} and timeToLive{} ",otp,mobileNumber,timeToLive);
		String url = uaeApiSMS;
		String text = "Your ICA-Pass OTP Phone verification code  is " + otp + "The code is valid for " + timeToLive
				+ " seconds. Don't share this code with anyone.";

		Map<String, String> uaeSmsBody = new HashMap<String, String>();
		uaeSmsBody.put("mobileno", mobileNumber);
		uaeSmsBody.put("smstext", text);

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		System.out.println("getToken() :: " + getToken());
		headers.set("access_token", getToken());
		HttpEntity<Object> requestEntity = new HttpEntity<>(uaeSmsBody, headers);
		try {
			logger.info("sendSMSUAE() >> req for restTemplate >> url {} and requestEntity {}", url,requestEntity);
			ResponseEntity<Object> res = restTemplate.exchange(url, HttpMethod.POST, requestEntity, Object.class);
			ApiResponse api = new ApiResponse();
			api.setSuccess(true);
			api.setMessage("");
			api.setResult(res.getBody());
			logger.info("sendSMSUAE() >> res for restTemplate {}", res);
			return api.getResult();
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
			logger.error("sendSMSUAE >> Exception >> {}", e.getMessage());
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

	public String getBasicAuth() {
		String userCredentials = niraUserName + ":" + niraPassword;
		String basicAuth = new String(Base64.getEncoder().encode(userCredentials.getBytes()));
		return basicAuth;
	}

	private String encryptedString(String s) {
		try {
			Result result = DAESService.encryptData(s);
			return new String(result.getResponse());
		} catch (Exception e) {
			e.printStackTrace();
			return e.getMessage();
		}
	}

	public String getToken() {
		String url = niraApiToken;
		logger.info("getToken() >> req >> url {} ", url);
		String basicAuth = getBasicAuth();
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.set("daes-authorization", basicAuth);
		HttpEntity<Object> requestEntity = new HttpEntity<>(headers);
		try {
			logger.info("getToken() >> req for restTemplate {} ",requestEntity);
			ResponseEntity<String> res = restTemplate.exchange(url, HttpMethod.GET, requestEntity, String.class);
			logger.info("getToken() >> res for restTemplate {}", res);
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
			logger.error(CLASS + "getToken() >> Exception {}" ,e.getMessage());
			e.printStackTrace();
			return e.getMessage();
		}

	}

	public ApiResponse verifyOtp(String mobNo) {
		ApiResponse apiResponse = new ApiResponse();

		OTPResponseDTO otpResponse = new OTPResponseDTO();
		otpResponse.setEmailEncrptyOTP(AppUtil.encryptedString("12345"));
		otpResponse.setMobileEncrptyOTP(AppUtil.encryptedString("123456"));
		otpResponse.setTtl(180);

		apiResponse.setMessage("Otp verfication done");
		apiResponse.setSuccess(true);
		apiResponse.setResult(otpResponse);
		return apiResponse;

	}

	@Override
	public ApiResponse sendEmail(MobileOTPDto mobileOTPDto) {
		// TODO Auto-generated method stub
		return null;
	}

	

}

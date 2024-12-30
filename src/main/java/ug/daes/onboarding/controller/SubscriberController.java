package ug.daes.onboarding.controller;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.ParseException;
import java.util.Base64;
import java.util.Locale;

import javax.imageio.ImageIO;

import org.imgscalr.Scalr;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ug.daes.onboarding.constant.ApiResponse;
import ug.daes.onboarding.dto.*;
import ug.daes.onboarding.model.Subscriber;
import ug.daes.onboarding.model.SubscriberStatus;
import ug.daes.onboarding.repository.SubscriberRepoIface;
import ug.daes.onboarding.repository.SubscriberStatusRepoIface;
import ug.daes.onboarding.service.iface.SubscriberServiceIface;
import ug.daes.onboarding.util.AppUtil;

@RestController
public class SubscriberController {

	private static Logger logger = LoggerFactory.getLogger(SubscriberController.class);

	/** The Constant CLASS. */
	final static String CLASS = "SubscriberController";

	@Autowired
	private SubscriberServiceIface subscriberServiceIface;

//	@Autowired
//	private SubscriberRepoIface subscriberRepoIface;

//	@Autowired
//	EdmsServiceImpl edmsServiceImpl;

	@Autowired
	private SubscriberRepoIface subscriberRepo;

	@Autowired
	private SubscriberStatusRepoIface statusRepo;

	@Autowired
	MessageSource messageSource;

	@PostMapping("/api/post/trusted/user")
	public ApiResponse addTrustedUsers(@RequestBody TrustedUserDto emails) {
		logger.info(CLASS + "addTrustedUsers req {}", emails);
		return subscriberServiceIface.addTrustedUsers(emails);
	}

	@PostMapping("/api/post/save-subscriber-details")
	public ApiResponse saveSubscriberData(@RequestBody MobileOTPDto subscriberDTO) throws ParseException {
		logger.info(CLASS + " saveSubscriberData req for {} ", subscriberDTO);
//		int onboardingCount = subscriberRepoIface.countOnboarding();
//		System.out.println("onboardingCount ::" + onboardingCount);
//		if(onboardingCount >10) {
//			return AppUtil.createApiResponse(true, "Onboarding Exceeded", null);
//		}
		subscriberDTO.setSubscriberEmail(subscriberDTO.getSubscriberEmail().toLowerCase());
		return subscriberServiceIface.saveSubscribersData(subscriberDTO);
	}

	@PostMapping("/api/post/add/subscriber-ob-data")
	public ApiResponse saveSubscriberObData(@RequestBody SubscriberObRequestDTO obRequestDTO) throws Exception {
//		logger.info(CLASS + ">> saveSubscriberData() >> req >> " + obRequestDTO);
		return subscriberServiceIface.addSubscriberObData(obRequestDTO);
	}

	@PostMapping("/api/post/reonboard/subscriber-ob-data")
	public ApiResponse reOnboardSaveSubacriberObData(@RequestBody SubscriberObRequestDTO obRequestDTO)
			throws Exception {
		return subscriberServiceIface.reOnboardAddSubscriberObData(obRequestDTO);
	}

	@PostMapping("/api/post/fetch/subscriber-ob-data")
	public ApiResponse getSubscriberObData(@RequestBody GetSubscriberObDataDTO subscriberUID) {

		return subscriberServiceIface.getSubscriberObData(subscriberUID);
	}

	@PostMapping("/api/post/fetch/reset-pin")
	public ApiResponse resetPin(@RequestBody GetSubscriberObDataDTO obDataDTO) {

		return subscriberServiceIface.resetPin(obDataDTO);
	}

	@GetMapping("/api/get/live/video")
	public ResponseEntity<Object> getVideoLiveStreaming(@RequestParam String subscriberUid) {

		return subscriberServiceIface.getVideoLiveStreaming(subscriberUid);
	}

	@GetMapping("/api/get/live/video/local")
	public ResponseEntity<Object> getVideoLiveStreamingLocalEdms(@RequestParam String subscriberUid) {

		return subscriberServiceIface.getVideoLiveStreamingLocalEdms(subscriberUid);
	}

	@GetMapping("/api/get/subscriber/details/report")
	public ApiResponse getSubscriberDetailsReport(@RequestParam String startDate, @RequestParam String endDate) {

		return subscriberServiceIface.getSubscriberDetailsReports(startDate, endDate);
	}

	private Path testFile;

	@PostMapping("/api/get/thum")
	public byte[] createThumbnail(@RequestBody Selfie image) throws IOException {
		try {

			byte[] img = Base64.getDecoder().decode(image.getSubscriberSelfie());
			Resource fileRes = getTestFile(img, "selfie", ".jpeg");

			ByteArrayOutputStream thumbOutput = new ByteArrayOutputStream();
			BufferedImage thumbImg = null;
			BufferedImage img2 = ImageIO.read(fileRes.getInputStream());
			thumbImg = Scalr.resize(img2, Scalr.Method.AUTOMATIC, Scalr.Mode.AUTOMATIC, 100, Scalr.OP_ANTIALIAS);
			ImageIO.write(thumbImg, "jpeg", thumbOutput);
			System.out.println("thumbOutput ::" + thumbOutput);
			byte[] data = thumbOutput.toByteArray();
			return data;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return null;

	}

	@GetMapping("/api/get/subscriber/details")
	public ApiResponse deleteSubscriber(@RequestParam String id) {
		SubscriberDetailsDto details = new SubscriberDetailsDto();
		try {
			Subscriber subscriber = subscriberRepo.findBysubscriberUid(id);
			SubscriberStatus status = statusRepo.findBysubscriberUid(id);
			if (subscriber != null) {
				details.setEmail(subscriber.getEmailId());
				details.setPhoneNo(subscriber.getMobileNumber());
				details.setSubscriberStatus(status.getSubscriberStatus());
				details.setFullName(subscriber.getFullName());
				return AppUtil.createApiResponse(true,
						messageSource.getMessage("api.response.subscriber.details", null, Locale.ENGLISH), details);
			} else {
				return AppUtil.createApiResponse(false,
						messageSource.getMessage("api.error.subscriber.not.found", null, Locale.ENGLISH), null);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return AppUtil.createApiResponse(false, messageSource.getMessage(
					"api.error.something.went.wrong.please.try.after.sometime", null, Locale.ENGLISH), null);
		}
	}

	public Resource getTestFile(byte[] bytes, String prefix, String suffix) throws IOException {
		testFile = Files.createTempFile(prefix, suffix);
		Files.write(testFile, bytes);

		return new FileSystemResource(testFile.toFile());
	}

	// Delete Record by using mobile no
	@DeleteMapping("/api/delete/subscriber-record")
	public ApiResponse deleteSubscriber(@RequestParam String mobileNo,@RequestParam String email) {
		return subscriberServiceIface.deleteRecord(mobileNo,email);
	}



}

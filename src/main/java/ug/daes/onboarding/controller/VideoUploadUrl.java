package ug.daes.onboarding.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import ug.daes.onboarding.constant.ApiResponse;
import ug.daes.onboarding.dto.FileUploadDTO;
import ug.daes.onboarding.dto.Selfie;
import ug.daes.onboarding.repository.OnboardingLivelinessRepository;
import ug.daes.onboarding.service.impl.EdmsServiceImpl;

@RestController
public class VideoUploadUrl {

	private static Logger logger = LoggerFactory.getLogger(VideoUploadUrl.class);

	/** The Constant CLASS. */
	final static String CLASS = "VideoUploadUrl";

	@Autowired
	EdmsServiceImpl edmsService;

	@Autowired
	OnboardingLivelinessRepository onboardingLivelinessRepository;

	@PostMapping("/api/post/upload-file")
	public ApiResponse uploadFileLocal(@RequestParam("model") String model, @RequestParam("file") MultipartFile file)
			throws JsonMappingException, JsonProcessingException {
		logger.info(CLASS + " uploadFileLocal req model {} and File {}",model,file.getOriginalFilename() );
		ObjectMapper mapper = new ObjectMapper();
		FileUploadDTO modelDTO = mapper.readValue(model, FileUploadDTO.class);
		return edmsService.saveVideoToEdms(file, modelDTO);
	}

	@PostMapping("/api/post/selfi-upload")
	public ApiResponse SelfiUpload(@RequestBody Selfie selfie) {
		logger.info(CLASS + " SelfiUpload req  {}",selfie);
		return edmsService.saveSelfieToEdms(selfie);

	}

}

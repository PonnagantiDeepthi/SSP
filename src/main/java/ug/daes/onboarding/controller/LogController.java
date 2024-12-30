package ug.daes.onboarding.controller;

import java.text.ParseException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import ug.daes.onboarding.dto.NiraApiLogDto;
import ug.daes.onboarding.service.iface.LogModelServiceIface;

@RestController
public class LogController {
	
	private static Logger logger = LoggerFactory.getLogger(LogController.class);

	/** The Constant CLASS. */
	final static String CLASS = "LogController";

	@Autowired
	LogModelServiceIface logModelServiceIface;
	
	
	@PostMapping("/api/save/nira/logs")
	public void saveNiraApiLogs(@RequestBody NiraApiLogDto niraApiLogDto) throws ParseException {
		logger.info(CLASS + ">> saveNiraApiLogs >> req {} ", niraApiLogDto);
		logModelServiceIface.setLogModelNiraApi(niraApiLogDto);
	}

}

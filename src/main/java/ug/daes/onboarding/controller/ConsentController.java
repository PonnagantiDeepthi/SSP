package ug.daes.onboarding.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ug.daes.onboarding.constant.ApiResponse;
import ug.daes.onboarding.model.Consent;
import ug.daes.onboarding.repository.ConsentRepoIface;
import ug.daes.onboarding.util.AppUtil;

import org.hibernate.PessimisticLockException;
import org.hibernate.QueryTimeoutException;
import org.hibernate.exception.ConstraintViolationException;
import org.hibernate.exception.DataException;
import org.hibernate.exception.GenericJDBCException;
import org.hibernate.exception.JDBCConnectionException;
import org.hibernate.exception.LockAcquisitionException;
import org.hibernate.exception.SQLGrammarException;

@RestController
@CrossOrigin
public class ConsentController {
	
	private static Logger logger = LoggerFactory.getLogger(ConsentController.class);
	
	/** The Constant CLASS. */
	final static String CLASS = "ConsentController";
	
	@Autowired
	MessageSource messageSource;


	@Autowired
	private ConsentRepoIface consentRepoIface;
	
	@GetMapping(value = "/api/activte/consent")
	public ApiResponse getActivteConsent() {
		Consent activeConsent = new Consent();
		try {
			activeConsent = consentRepoIface.getActiveConsent();
			if (activeConsent != null) {
				return AppUtil.createApiResponse(true, messageSource.getMessage("api.response.consent", null, Locale.ENGLISH), activeConsent);
			} else {
				return AppUtil.createApiResponse(false, messageSource.getMessage("api.error.no.active.consent.found", null, Locale.ENGLISH), null);
			}
		}
		catch (JDBCConnectionException | ConstraintViolationException | DataException | LockAcquisitionException | PessimisticLockException
				| QueryTimeoutException | SQLGrammarException | GenericJDBCException e) {
			e.printStackTrace();
			return AppUtil.createApiResponse(false, messageSource.getMessage("api.error.something.went.wrong.please.try.after.sometime", null, Locale.ENGLISH), null);
		}
		catch (Exception e) {
			logger.error(CLASS + "Get Active Consent Exception {}",e.getMessage());
			e.printStackTrace();
			return AppUtil.createApiResponse(false, messageSource.getMessage("api.error.something.went.wrong.please.try.after.sometime", null, Locale.ENGLISH), null);
		}
	}

	@GetMapping(value = "/api/get/list/consent")
	public ApiResponse getConsentList() {
		List<Consent> consent = new ArrayList<Consent>();
		try {
			consent = consentRepoIface.findAll();
			if (consent != null) {
				return AppUtil.createApiResponse(true, messageSource.getMessage("api.response.consent", null, Locale.ENGLISH), consent);
			} else {
				return AppUtil.createApiResponse(false, messageSource.getMessage("api.error.empty", null, Locale.ENGLISH), null);
			}
		}
		catch (JDBCConnectionException | ConstraintViolationException | DataException | LockAcquisitionException | PessimisticLockException
				| QueryTimeoutException | SQLGrammarException | GenericJDBCException e) {
			e.printStackTrace();
			return AppUtil.createApiResponse(false, messageSource.getMessage("api.error.something.went.wrong.please.try.after.sometime", null, Locale.ENGLISH), null);
		}
		catch (Exception e) {
			logger.error(CLASS + "Get Consent List {} ", e.getMessage());
			e.printStackTrace();
			return AppUtil.createApiResponse(false, messageSource.getMessage("api.error.something.went.wrong.please.try.after.sometime", null, Locale.ENGLISH), null);
		}
	}
	
	@GetMapping(value = "/api/get/consent/id")
	public ApiResponse getConsentById(@RequestParam int id) {

		Consent consent = new Consent();
		try {
			consent = consentRepoIface.findByconsentId(id);
			if (consent != null) {
				return AppUtil.createApiResponse(true, messageSource.getMessage("api.response.consent", null, Locale.ENGLISH), consent);
			} else {
				return AppUtil.createApiResponse(false, messageSource.getMessage("api.error.empty", null, Locale.ENGLISH), null);
			}
		}
		catch (JDBCConnectionException | ConstraintViolationException | DataException | LockAcquisitionException | PessimisticLockException
				| QueryTimeoutException | SQLGrammarException | GenericJDBCException e) {
			e.printStackTrace();
			return AppUtil.createApiResponse(false, messageSource.getMessage("api.error.something.went.wrong.please.try.after.sometime", null, Locale.ENGLISH), null);
		}
		catch (Exception e) {
			logger.error(CLASS + "Get Consent By-id {} ", e.getMessage());
			e.printStackTrace();
			return AppUtil.createApiResponse(false, messageSource.getMessage("api.error.something.went.wrong.please.try.after.sometime", null, Locale.ENGLISH), null);
		}

	}

	@PostMapping(value = "/api/add/consent")
	public ApiResponse addConsent(@RequestBody Consent consent) {
		logger.info(CLASS + "Add Consent :: {}",consent);
		Consent savedConsent = new Consent();
		try {
			if (consent.getConsent() == null || consent.getConsent().equals("")) {
				return AppUtil.createApiResponse(false, messageSource.getMessage("api.error.consent.is.empty", null, Locale.ENGLISH), null);
			}
			consent.setCreatedOn(AppUtil.getDate());
			consent.setUpdatedOn(AppUtil.getDate());
			consent.setStatus("INACTIVE");
			savedConsent = consentRepoIface.save(consent);
			if (savedConsent != null) {
				return AppUtil.createApiResponse(true, messageSource.getMessage("api.response.consent.saved", null, Locale.ENGLISH), null);
			} else {
				return AppUtil.createApiResponse(false, messageSource.getMessage("api.error.consent.not.usaved", null, Locale.ENGLISH), null);
			}
		}
		catch (JDBCConnectionException | ConstraintViolationException | DataException | LockAcquisitionException | PessimisticLockException
				| QueryTimeoutException | SQLGrammarException | GenericJDBCException e) {
			e.printStackTrace();
			return AppUtil.createApiResponse(false, messageSource.getMessage("api.error.something.went.wrong.please.try.after.sometime", null, Locale.ENGLISH), null);
		}
		catch (Exception e) {
			logger.error(CLASS + "Add Consent Exception :: " + e.getMessage());
			e.printStackTrace();
			return AppUtil.createApiResponse(false, messageSource.getMessage("api.error.something.went.wrong.please.try.after.sometime", null, Locale.ENGLISH), null);
		}
	}

	@GetMapping(value = "/api/update/consent/status")
	public ApiResponse updateConsentStatus(@RequestParam int consentId, @RequestParam String status) {
		logger.info(CLASS + "Update Consent Status :: consentId and status {},{}" ,consentId ,status);
		Consent savedConsent = new Consent();
		try {
			if (status.equals("Active") || status.equals("ACTIVE")) {
				savedConsent = consentRepoIface.updateConsentStatusActive(consentId, status);
			} else {
				savedConsent = consentRepoIface.updateConsentStatusInactive(consentId, status);
			}
			if (savedConsent != null) {
				return AppUtil.createApiResponse(true, messageSource.getMessage("api.response.consent.updated", null, Locale.ENGLISH), null);
			} else {
				return AppUtil.createApiResponse(false, messageSource.getMessage("api.error.consent.not.updated", null, Locale.ENGLISH), null);
			}
		} catch (JDBCConnectionException | ConstraintViolationException | DataException | LockAcquisitionException | PessimisticLockException
				| QueryTimeoutException | SQLGrammarException | GenericJDBCException e) {
			e.printStackTrace();
			return AppUtil.createApiResponse(false, messageSource.getMessage("api.error.something.went.wrong.please.try.after.sometime", null, Locale.ENGLISH), null);
		}
		
		catch (Exception e) {
			logger.error(CLASS + "Update Consent Status Exception {}", e.getMessage());
			e.printStackTrace();
			return AppUtil.createApiResponse(false, messageSource.getMessage("api.error.something.went.wrong.please.try.after.sometime", null, Locale.ENGLISH), null);
		}
	}

}

package ug.daes.onboarding.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import ug.daes.onboarding.constant.ApiResponse;
import ug.daes.onboarding.constant.Constant;
import ug.daes.onboarding.model.Subscriber;
import ug.daes.onboarding.model.SubscriberStatus;
import ug.daes.onboarding.model.TrustedUser;
import ug.daes.onboarding.repository.SubscriberRepoIface;
import ug.daes.onboarding.repository.SubscriberStatusRepoIface;
import ug.daes.onboarding.repository.TrustedUserRepoIface;
import ug.daes.onboarding.service.iface.TrustedUserIface;
import ug.daes.onboarding.util.AppUtil;

@Service
public class TrustedUserIfaceImpl implements TrustedUserIface {

	Logger logger = LoggerFactory.getLogger(TrustedUserIfaceImpl.class);

	/** The Constant CLASS. */
	final static String CLASS = "TrustedUserIfaceImpl";

	@Autowired
	TrustedUserRepoIface trustedUserRepoIface;

	@Autowired
	SubscriberRepoIface subscriberRepoIface;

	@Autowired
	SubscriberStatusRepoIface subscriberStatusRepoIface;
	
	@Autowired
	MessageSource messageSource;

	@Override
	public ApiResponse getTrustedUserByEmail(String email) {
		logger.info(CLASS + " getTrustedUserDetails req email {}",email);
		try {
			if (email != null) {
				TrustedUser trustedUser = trustedUserRepoIface.getTrustedUserDratilsByEmail(email);
				if (Objects.nonNull(trustedUser)) {
					logger.info(CLASS + " getTrustedUserDetails res Trusted User Details {}",trustedUser);
					return AppUtil.createApiResponse(true, messageSource.getMessage("api.response.trusted.user.details", null, Locale.ENGLISH), trustedUser);
				} else {
					return AppUtil.createApiResponse(false, messageSource.getMessage("api.error.trusted.user.details.not.found", null, Locale.ENGLISH), null);
				}
			}
			return AppUtil.createApiResponse(false, messageSource.getMessage("api.error.email.cant.be.null", null, Locale.ENGLISH), null);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(CLASS + "getTrustedUserDetails Exception {}",e.getMessage());
			return AppUtil.createApiResponse(false, messageSource.getMessage("api.error.something.went.wrong.please.try.after.sometime", null, Locale.ENGLISH), null);
		}
	}

	@Override
	public ApiResponse updateTrustedUser(TrustedUser trustedUser) {
		try {
			logger.info(CLASS +" updateTrustedUser req {}",trustedUser);
			if (Objects.nonNull(trustedUser)) {
				Subscriber subscriber = subscriberRepoIface.findByemailId(trustedUser.getEmailId());
				if (Objects.nonNull(subscriber)) {
					SubscriberStatus subscriberStatus = subscriberStatusRepoIface
							.findBysubscriberUid(subscriber.getSubscriberUid());
					System.out.println("subscriberStatus :: " + subscriberStatus.getSubscriberStatus());
					logger.info(CLASS +" updateTrustedUser req subscriberStatus {}",subscriberStatus);
					if (subscriberStatus.getSubscriberStatus().equals(Constant.ACTIVE)) {
						return AppUtil.createApiResponse(false,
								messageSource.getMessage("api.error.this.email.is.already.onboarded.and.active.you.cant.update", null, Locale.ENGLISH), null);
					} else {
						TrustedUser user = new TrustedUser();
						user.setTrustedUserId(trustedUser.getTrustedUserId());
						user.setFullName(trustedUser.getFullName());
						user.setEmailId(trustedUser.getEmailId());
						user.setMobileNumber(trustedUser.getMobileNumber());
						user.setTrustedUserStatus(trustedUser.getTrustedUserStatus());
						trustedUserRepoIface.save(user);
						logger.info(CLASS +" updateTrustedUser res Trusted user updated succesfully  {}",user);
						return AppUtil.createApiResponse(true, messageSource.getMessage("api.response.trusted.user.updated.succesfully", null, Locale.ENGLISH), user);
					}
				} else {
					return AppUtil.createApiResponse(false, messageSource.getMessage("api.error.cant.found.any.relation.with.ugpass", null, Locale.ENGLISH), null);
				}

			} else {
				return AppUtil.createApiResponse(false, messageSource.getMessage("api.error.trusted.user.cant.be.null.or.empty", null, Locale.ENGLISH), null);
			}

		} catch (Exception e) {
			e.printStackTrace();
			logger.error(CLASS +" updateTrustedUser Exception {}",e.getMessage());
			return AppUtil.createApiResponse(false, messageSource.getMessage("api.error.something.went.wrong.please.try.after.sometime", null, Locale.ENGLISH), null);
		}
	}

	@Override
	public ApiResponse deleteTrustedUser(String email) {
		try {
			logger.info(CLASS +" deleteTrustedUser req email {}",email);
			if (email != null) {
				TrustedUser user = trustedUserRepoIface.findByemailId(email);
				if (user != null) {
					trustedUserRepoIface.deleteById(user.getTrustedUserId());
					logger.info(CLASS +" deleteTrustedUser req rusted User Deleted Succssfully {}",email);
					return AppUtil.createApiResponse(true, messageSource.getMessage("api.response.trusted.user.deleted.successfully", null, Locale.ENGLISH), null);
				}
				return AppUtil.createApiResponse(false, messageSource.getMessage("api.error.trusted.user.not.found", null, Locale.ENGLISH), null);
			}
			return AppUtil.createApiResponse(false, messageSource.getMessage("api.error.email.cant.should.be.null.or.empty", null, Locale.ENGLISH), null);

		} catch (Exception e) {
			e.printStackTrace();
			logger.info(CLASS +" deleteTrustedUser  Exception  {}",e.getMessage());
			return AppUtil.createApiResponse(false, messageSource.getMessage("api.error.something.went.wrong.please.try.after.sometime", null, Locale.ENGLISH), null);
		}
	}

	@Override
	public ApiResponse getAllTrustedUser() {
		try {
			List<TrustedUser> trustedUser = trustedUserRepoIface.findAll();
			if (trustedUser != null || !trustedUser.isEmpty()) {
				logger.info(CLASS +" getAllTrustedUser res Trusted Users Feached Successfully {}",trustedUser);
				return AppUtil.createApiResponse(true, messageSource.getMessage("api.response.trusted.Users.fetched.successfully", null, Locale.ENGLISH), trustedUser);
			}
			return AppUtil.createApiResponse(false, messageSource.getMessage("api.error.trusted.users.not.found", null, Locale.ENGLISH), null);
		} catch (Exception e) {
			e.printStackTrace();
			logger.info(CLASS +" getAllTrustedUser Exception {}",e.getMessage());
			return AppUtil.createApiResponse(false, messageSource.getMessage("api.error.something.went.wrong.please.try.after.sometime", null, Locale.ENGLISH), null);
		}
	}

	@Override
	public ApiResponse addTrustedUser(List<TrustedUser> trustedUser) {
		try {
			if (Objects.nonNull(trustedUser) && !CollectionUtils.isEmpty(trustedUser)) {
				List<TrustedUser> userRes = new ArrayList<>();
				for (TrustedUser trustedUser2 : trustedUser) {
					TrustedUser user = new TrustedUser();
					user.setFullName(trustedUser2.getFullName());
					user.setEmailId(trustedUser2.getEmailId());
					user.setMobileNumber(trustedUser2.getMobileNumber());
					user.setTrustedUserStatus(trustedUser2.getTrustedUserStatus());
					userRes.add(user);
				}
				trustedUserRepoIface.saveAll(userRes);
				logger.info(CLASS +" addTrustedUser res Trusted User Added Succesfully {}",userRes);
				return AppUtil.createApiResponse(true, messageSource.getMessage("api.response.trusted.user.added.succesfully", null, Locale.ENGLISH), userRes);
			}
			return AppUtil.createApiResponse(false, messageSource.getMessage("api.error.trusted.user.cant.be.null.or.empty", null, Locale.ENGLISH), null);
		} catch (Exception e) {
			e.printStackTrace();
			logger.info(CLASS +" addTrustedUser Exception  {}",e.getMessage());
			return AppUtil.createApiResponse(false, messageSource.getMessage("api.error.something.went.wrong.please.try.after.sometime", null, Locale.ENGLISH), null);
		}
	}

}

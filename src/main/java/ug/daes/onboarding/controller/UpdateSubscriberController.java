package ug.daes.onboarding.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ug.daes.onboarding.constant.ApiResponse;
import ug.daes.onboarding.dto.MobileOTPDto;
import ug.daes.onboarding.dto.SubscriberDTO;
import ug.daes.onboarding.dto.UpdateDto;
import ug.daes.onboarding.dto.UpdateOtpDto;
import ug.daes.onboarding.service.iface.SubscriberServiceIface;
import ug.daes.onboarding.util.AppUtil;

import javax.mail.SendFailedException;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.util.Locale;

@RestController
public class UpdateSubscriberController {

    private static Logger logger = LoggerFactory.getLogger(UpdateSubscriberController.class);

    /** The Constant CLASS. */
    final static String CLASS = "UpdateSubscriberController";
    @Autowired
    private SubscriberServiceIface subscriberServiceIface;
    
    @Autowired
	MessageSource messageSource;

    @PostMapping("/api/post/updateSubscriberDetails")
    public ApiResponse updatePhoneNumber(@RequestBody UpdateDto updateDto) {
        logger.info(CLASS + " updatePhoneNumber req  {}", updateDto);
        if(updateDto.isUpdateMobile() && updateDto.isUpdateEmail()){
            return AppUtil.createApiResponse(false, messageSource.getMessage("api.error.cant.update.mail.and.mobile.number.ata.time", null, Locale.ENGLISH), null);
        }
        else if(updateDto.isUpdateMobile()){
            return subscriberServiceIface.updatePhoneNumber(updateDto);
        } else if (updateDto.isUpdateEmail()) {
            return subscriberServiceIface.updateEmail(updateDto);
        }
        return AppUtil.createApiResponse(false, messageSource.getMessage("api.error.did.not.select.anything.to.update", null, Locale.ENGLISH), null);
    }
    @PostMapping("/api/post/updateSubscriberOtp")
    public ApiResponse sendOtp(@RequestBody UpdateOtpDto otpDto)
            throws NoSuchAlgorithmException, SendFailedException, ParseException {
        logger.info(CLASS + "sendOtpMobile req {}",otpDto);
        if(otpDto.isOtpEmail() && otpDto.isOtpMobile()){
            return AppUtil.createApiResponse(false, messageSource.getMessage("api.error.cant.send.otp.for.email.and.mobile.at.same.time", null, Locale.ENGLISH), null);
        }
        else if(otpDto.isOtpMobile()){
            return subscriberServiceIface.sendOtpMobile(otpDto);
        } else if (otpDto.isOtpEmail()) {
            return subscriberServiceIface.sendOtpEmail(otpDto);
        }
        return AppUtil.createApiResponse(false, messageSource.getMessage("api.error.did.not.select.anything.to.update", null, Locale.ENGLISH), null);
    }

}

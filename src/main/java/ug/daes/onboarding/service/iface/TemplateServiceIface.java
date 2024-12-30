package ug.daes.onboarding.service.iface;

import ug.daes.onboarding.constant.ApiResponse;
import ug.daes.onboarding.dto.SubscriberDTO;
import ug.daes.onboarding.dto.TemplateApproveDTO;
import ug.daes.onboarding.dto.TemplateDTO;

public interface TemplateServiceIface {

	/**
	 * @return
	 */
	ApiResponse getTemplates();
	/**
	 * @param subscriberDTO
	 * @return
	 */
	ApiResponse getActiveTemplate(SubscriberDTO subscriberDTO);
	/**
	 * @param templateDTO
	 * @return
	 */
	ApiResponse saveTemplates(TemplateDTO template);
	/**
	 * @param id
	 * @return
	 */
	ApiResponse getTemplateById(int id);
	
	/**
	 * @return
	 */
	ApiResponse getMethods();
	
	/**
	 * @return
	 */
	ApiResponse getOnBoardingSteps();
	
	/**
	 * @param id
	 * @param status
	 * @return
	 */
	ApiResponse updateTemplateStatus(int id, String status);
	
	ApiResponse testTemplate(SubscriberDTO subscriberDTO);
	
	ApiResponse templateApprove(TemplateApproveDTO templateId);
	
	ApiResponse isTemplateAlreadyExixts(String templateName, String methodId);
	
//	/**
//	 * @param id
//	 * @param status
//	 * @return
//	 */
//	ApiResponse updateTemplateState(int id, String status);
	
	/**
	 * @param id
	 * @return
	 */
	ApiResponse deleteTemplateById(int id);
	
//	/**
//	 * @param docsFieldGroup
//	 * @return
//	 */
	
	ApiResponse getTemplateLatestById(int id );
	
}

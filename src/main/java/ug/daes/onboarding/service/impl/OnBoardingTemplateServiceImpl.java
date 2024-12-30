
package ug.daes.onboarding.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import org.hibernate.PessimisticLockException;
import org.hibernate.QueryTimeoutException;
import org.hibernate.exception.ConstraintViolationException;
import org.hibernate.exception.DataException;
import org.hibernate.exception.GenericJDBCException;
import org.hibernate.exception.JDBCConnectionException;
import org.hibernate.exception.LockAcquisitionException;
import org.hibernate.exception.SQLGrammarException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import ug.daes.onboarding.constant.ApiResponse;
import ug.daes.onboarding.constant.Constant;
import ug.daes.onboarding.controller.TemplateController;
import ug.daes.onboarding.dto.EditTemplateDTO;
import ug.daes.onboarding.dto.MobileTemplateDTO;
import ug.daes.onboarding.dto.SubscriberDTO;
import ug.daes.onboarding.dto.SubscriberObRequestDTO;
import ug.daes.onboarding.dto.TemplateApproveDTO;
import ug.daes.onboarding.dto.TemplateDTO;
import ug.daes.onboarding.model.MapMethodOnboardingStep;
import ug.daes.onboarding.model.OnboardingMethod;
import ug.daes.onboarding.model.OnboardingSteps;
import ug.daes.onboarding.model.SubscriberOnboardingTemplate;
import ug.daes.onboarding.repository.MapMethodObStepRepoIface;
import ug.daes.onboarding.repository.OnBoardingMethodRepoIface;
import ug.daes.onboarding.repository.OnBoardingStepRepoIface;
import ug.daes.onboarding.repository.OnBoardingTemplateRepoIface;
import ug.daes.onboarding.service.iface.TemplateServiceIface;
import ug.daes.onboarding.util.AppUtil;


@Service
public class OnBoardingTemplateServiceImpl implements TemplateServiceIface{

	private static Logger logger = LoggerFactory.getLogger(OnBoardingTemplateServiceImpl.class);
	
	/** The Constant CLASS. */
	final static String CLASS = "OnBoardingTemplateServiceImpl";
	
	@Autowired
	OnBoardingMethodRepoIface methodRepoIface;
	
	@Autowired
	OnBoardingTemplateRepoIface templateRepoIface;
	
	@Autowired
	OnBoardingStepRepoIface stepRepoIface;
	
	@Autowired
	MapMethodObStepRepoIface mapStepRepoIface;
	
	@Autowired
	MessageSource messageSource;	
	
	@Override
	public ApiResponse getTemplates() {
		List<SubscriberOnboardingTemplate> templates = new ArrayList<SubscriberOnboardingTemplate>();
		
		try {
			templates = templateRepoIface.getAllTemplate();
			
			if (templates != null) {
				logger.info(CLASS +" getTemplates res {}",templates);
				return AppUtil.createApiResponse(true, messageSource.getMessage("api.response.template.list", null, Locale.ENGLISH), templates);
			} else {
				return AppUtil.createApiResponse(true, messageSource.getMessage("api.response.template.list.is.empty", null, Locale.ENGLISH), null);
			}
		}
		catch (JDBCConnectionException | ConstraintViolationException | DataException | LockAcquisitionException | PessimisticLockException
				| QueryTimeoutException | SQLGrammarException | GenericJDBCException ex) {
			ex.printStackTrace();
			logger.error(CLASS + "getTemplates Exception {}",ex.getMessage());
			return AppUtil.createApiResponse(false, messageSource.getMessage("api.error.something.went.wrong.registration.failed", null, Locale.ENGLISH), null);
		} 
		catch (Exception e) {
			logger.error(CLASS +" getTemplates Exception {}",e.getMessage());
			e.printStackTrace();
			return AppUtil.createApiResponse(false, messageSource.getMessage("api.error.something.went.wrong.please.try.after.sometime", null, Locale.ENGLISH), null);
		}
	}

	@SuppressWarnings("unused")
	@Override
	public ApiResponse getActiveTemplate(SubscriberDTO subscriberDTO) {
		logger.info(CLASS +" getActviteTemplate req {}",subscriberDTO.getMethodName());
		SubscriberOnboardingTemplate template = new SubscriberOnboardingTemplate();
		EditTemplateDTO templateDTO = new EditTemplateDTO();
		try {
			if (subscriberDTO.getMethodName() != null) {
				template = templateRepoIface.getPublishTemplate(subscriberDTO.getMethodName(), "PUBLISHED");
				List<MapMethodOnboardingStep> stepList = mapStepRepoIface.findBytemplateId(template.getTemplateId());
			
				HashMap<String,MapMethodOnboardingStep> hm = new HashMap<String, MapMethodOnboardingStep>();
				stepList.forEach(mapMethodOnboardingStep->{
					hm.put(mapMethodOnboardingStep.getOnboardingStep(), mapMethodOnboardingStep);
				});
				templateDTO.setSteps(hm);
				templateDTO.setTemplateName(template.getTemplateName());
				templateDTO.setTemplateMethod(template.getTemplateMethod());
				templateDTO.setPublishedStatus(template.getPublishedStatus());
				templateDTO.setState(template.getState());
				templateDTO.setTemplateId(template.getTemplateId());
				
				if (templateDTO != null) {
					logger.info(CLASS +" getActviteTemplate res Template {}",templateDTO);
					return AppUtil.createApiResponse(true, messageSource.getMessage("api.response.template", null, Locale.ENGLISH), templateDTO);
				}else {
					return AppUtil.createApiResponse(false, messageSource.getMessage("api.error.template.not.found", null, Locale.ENGLISH), null);
				}
			} else {
				return AppUtil.createApiResponse(false, messageSource.getMessage("api.error.method.name.is.empty", null, Locale.ENGLISH), null);
			}
		}
		catch (JDBCConnectionException | ConstraintViolationException | DataException | LockAcquisitionException | PessimisticLockException
				| QueryTimeoutException | SQLGrammarException | GenericJDBCException ex) {
			ex.printStackTrace();
			logger.error(CLASS + "getActviteTemplate Exception {}",ex.getMessage());
			return AppUtil.createApiResponse(false, messageSource.getMessage("api.error.something.went.wrong.registration.failed", null, Locale.ENGLISH), null);
		} 
		catch (Exception e) {
			logger.error(CLASS +" getActviteTemplate Exception {}",e.getMessage());
			e.printStackTrace();
			return AppUtil.createApiResponse(false, messageSource.getMessage("api.error.something.went.wrong.please.try.after.sometime", null, Locale.ENGLISH), null);
		}
		
		
		
//		SubscriberOnboardingTemplate template = new SubscriberOnboardingTemplate();
//		MobileTemplateDTO templateDTO = new MobileTemplateDTO();
//		try {
//			if (subscriberDTO.getMethodName() != null) {
//				template = templateRepoIface.getPublishTemplate(subscriberDTO.getMethodName(), "PUBLISHED");
//				
//				List<MapMethodOnboardingStep> stepList = mapStepRepoIface.findBytemplateId(template.getTemplateId());
//				
//				templateDTO.setSteps(stepList);
//				templateDTO.setTemplateName(template.getTemplateName());
//				templateDTO.setTemplateMethod(template.getTemplateMethod());
//				templateDTO.setPublishedStatus(template.getPublishedStatus());
//				templateDTO.setState(template.getState());
//				templateDTO.setTemplateId(template.getTemplateId());
//				
//				if (templateDTO != null) {
//					return AppUtil.createApiResponse(true, "Template ", templateDTO);
//				}else {
//					return AppUtil.createApiResponse(true, "Template Not Found", null);
//				}
//			} else {
//				return AppUtil.createApiResponse(true, "Method name is empty", null);
//			}
//		} catch (Exception e) {
//			return AppUtil.createApiResponse(false, e.getMessage(), null);
//		}
		
	}

	@Override
	public ApiResponse saveTemplates(TemplateDTO templateDTO) {
		logger.info(CLASS +" saveTemplates req {}" + templateDTO.getTemplateName());
		SubscriberOnboardingTemplate template = new SubscriberOnboardingTemplate();
		
		List<MapMethodOnboardingStep> onboardingStepList = new ArrayList<MapMethodOnboardingStep>();
		
		List<MapMethodOnboardingStep> onboardingStepSavedList = new ArrayList<MapMethodOnboardingStep>();
		
		try {
			if (templateDTO != null) {
				template.setTemplateName(templateDTO.getTemplateName());
				template.setTemplateMethod(templateDTO.getTemplateMethod());
				template.setCreatedDate(AppUtil.getDate());
				template.setUpatedDate(AppUtil.getDate());
				template.setApprovedBy(templateDTO.getApprovedBy());
				if (templateDTO.getTemplateId() == 0) {
					template.setCreatedBy(templateDTO.getCreatedBy());
				}else {
					SubscriberOnboardingTemplate templateStatus = templateRepoIface.findBytemplateId(templateDTO.getTemplateId());
					template.setTemplateId(templateDTO.getTemplateId());
					if (templateStatus != null) {
						if(templateStatus.getPublishedStatus() == "PUBLISHED" || templateStatus.getPublishedStatus().equals("PUBLISHED")) {				
							return AppUtil.createApiResponse(true, messageSource.getMessage("api.response.your.template.status.is.published.please.unpublished.it.before.making.any.modifications", null, Locale.ENGLISH), null);
						}else {
							template.setUpdatedBy(templateDTO.getUpdatedBy());
							for (@SuppressWarnings("unused") OnboardingSteps steps : templateDTO.getSteps()) {
								mapStepRepoIface.deleteBytemplateId(templateDTO.getTemplateId());
							}						
						}
					} else {
						template.setUpdatedBy(templateDTO.getUpdatedBy());
						for (@SuppressWarnings("unused") OnboardingSteps steps : templateDTO.getSteps()) {
							mapStepRepoIface.deleteBytemplateId(templateDTO.getTemplateId());
						}						
					}					
				}				
				template.setPublishedStatus("UNPUBLISHED");
				template = templateRepoIface.save(template);
				
				int i = 1;
				for (OnboardingSteps steps : templateDTO.getSteps()) {
					MapMethodOnboardingStep mapOnboardingStep = new MapMethodOnboardingStep();
					mapOnboardingStep.setCreatedDate(AppUtil.getDate());
					mapOnboardingStep.setIntegrationUrl(steps.getIntegrationUrl());
					mapOnboardingStep.setMethodName(templateDTO.getTemplateMethod());
					mapOnboardingStep.setOnboardingStep(steps.getOnboardingStep());
					mapOnboardingStep.setOnboardingStepThreshold(steps.getOnboardingStepThreshold());
					mapOnboardingStep.setAndriodTFliteThreshold(steps.getAndriodTFliteThreshold());
					mapOnboardingStep.setAndriodDTTThreshold(steps.getAndriodDTTThreshold());
					mapOnboardingStep.setIosTFliteThreshold(steps.getIosTFliteThreshold());
					mapOnboardingStep.setIosDTTThreshold(steps.getIosDTTThreshold());
					mapOnboardingStep.setTemplateId(template.getTemplateId());
					mapOnboardingStep.setSequence(i);
					onboardingStepList.add(mapOnboardingStep);
					i++;
				}
				
				onboardingStepSavedList = mapStepRepoIface.saveAll(onboardingStepList);
				
				if (template != null && onboardingStepSavedList != null) {
					logger.info(CLASS +" saveTemplates  res  Template Saved  {}",template);
					return AppUtil.createApiResponse(true, messageSource.getMessage("api.response.template.saved", null, Locale.ENGLISH), template);
				} else {
					return AppUtil.createApiResponse(false, messageSource.getMessage("api.error.template.not.saved", null, Locale.ENGLISH), null);
				}
			} else {
				return AppUtil.createApiResponse(false, messageSource.getMessage("api.error.saving.template.entity.cant.be.null", null, Locale.ENGLISH), null);
			}
		}catch (DataIntegrityViolationException e) {
			logger.error(CLASS + "saveTemplates DataIntegrityViolationException  {}",e.getMessage());
			e.printStackTrace();
			return AppUtil.createApiResponse(false, messageSource.getMessage("api.error.this.template.name.is.already.there.please.used.different.template.name", null, Locale.ENGLISH), null);
		}
		catch (JDBCConnectionException | ConstraintViolationException | DataException | LockAcquisitionException | PessimisticLockException
				| QueryTimeoutException | SQLGrammarException | GenericJDBCException ex) {
			ex.printStackTrace();
			logger.error(CLASS + "saveTemplates Exception {}",ex.getMessage());
			return AppUtil.createApiResponse(false, messageSource.getMessage("api.error.something.went.wrong.registration.failed", null, Locale.ENGLISH), null);
		} 
		catch (Exception e) {
			logger.error(CLASS + " saveTemplates Exception {}",e.getMessage());
			e.printStackTrace();
			return AppUtil.createApiResponse(false, messageSource.getMessage("api.error.something.went.wrong.please.try.after.sometime", null, Locale.ENGLISH), null);
		}
	}

	@SuppressWarnings("unused")
	@Override
	public ApiResponse getTemplateById(int id) {
		logger.info(CLASS +" getTemplateById req  id {}",id);
		SubscriberOnboardingTemplate template = new SubscriberOnboardingTemplate();
		MobileTemplateDTO templateDTO = new MobileTemplateDTO();
		try {
			template = templateRepoIface.findBytemplateId(id);
			
			List<MapMethodOnboardingStep> stepList = mapStepRepoIface.findBytemplateId(template.getTemplateId());
			
			for (MapMethodOnboardingStep mapMethodOnboardingStep : stepList) {
				
				if (mapMethodOnboardingStep.getOnboardingStep().equals("SELFIE_CAPTURING")) {
					mapMethodOnboardingStep.setOnboardingStepId(1);
				}else if (mapMethodOnboardingStep.getOnboardingStep().equals("MRZ_SCANNING")) {
					mapMethodOnboardingStep.setOnboardingStepId(2);
				}else if (mapMethodOnboardingStep.getOnboardingStep().equals("PDF417_READING")) {
					mapMethodOnboardingStep.setOnboardingStepId(3);
				}else if (mapMethodOnboardingStep.getOnboardingStep().equals("NFC")) {
					mapMethodOnboardingStep.setOnboardingStepId(4);
				}else if (mapMethodOnboardingStep.getOnboardingStep().equals("UNID")) {
					mapMethodOnboardingStep.setOnboardingStepId(5);
				}
			}
			logger.info(CLASS +" getTemplateById req stepList {}",stepList);
			templateDTO.setSteps(stepList);
			templateDTO.setTemplateName(template.getTemplateName());
			templateDTO.setTemplateMethod(template.getTemplateMethod());
			templateDTO.setPublishedStatus(template.getPublishedStatus());
			templateDTO.setState(template.getState());
			templateDTO.setTemplateId(template.getTemplateId());
			
			if (template != null) {
				logger.info(CLASS +" getTemplateById res  Template by Id {}",templateDTO);
				return AppUtil.createApiResponse(true, messageSource.getMessage("api.response.template.by.id", null, Locale.ENGLISH), templateDTO);
			} else {
				return AppUtil.createApiResponse(true, messageSource.getMessage("api.error.template.not.found", null, Locale.ENGLISH), null);
			}
		}
		catch (JDBCConnectionException | ConstraintViolationException | DataException | LockAcquisitionException | PessimisticLockException
				| QueryTimeoutException | SQLGrammarException | GenericJDBCException ex) {
			ex.printStackTrace();
			logger.error(CLASS + "getTemplateById Exception {}",ex.getMessage());
			return AppUtil.createApiResponse(false, messageSource.getMessage("api.error.something.went.wrong.registration.failed", null, Locale.ENGLISH), null);
		} 
		catch (Exception e) {
			logger.error(CLASS + "getTemplateById  Exception {}",e.getMessage());
			e.printStackTrace();
			return AppUtil.createApiResponse(false, messageSource.getMessage("api.error.something.went.wrong.please.try.after.sometime", null, Locale.ENGLISH), null);
		}		
	}

	@Override
	public ApiResponse getMethods() {
		
		List<OnboardingMethod> methods = new ArrayList<OnboardingMethod>();
		
		try {
			methods = methodRepoIface.findAll();
			if (methods == null) {
				return AppUtil.createApiResponse(true, messageSource.getMessage("api.response.method.list.is.empty", null, Locale.ENGLISH), null);
			} else {
				logger.info(CLASS +" getMethod res Method List {}",methods );
				return AppUtil.createApiResponse(true, messageSource.getMessage("api.response.method.list", null, Locale.ENGLISH), methods);
			}
		}
		catch (JDBCConnectionException | ConstraintViolationException | DataException | LockAcquisitionException | PessimisticLockException
				| QueryTimeoutException | SQLGrammarException | GenericJDBCException ex) {
			ex.printStackTrace();
			logger.error(CLASS + "getMethod Exception {}",ex.getMessage());
			return AppUtil.createApiResponse(false, messageSource.getMessage("api.error.something.went.wrong.registration.failed", null, Locale.ENGLISH), null);
		} 
		catch (Exception e) {
			logger.error(CLASS + " getMethod Exception {}",e.getMessage());
			e.printStackTrace();
			return AppUtil.createApiResponse(false, messageSource.getMessage("api.error.something.went.wrong.please.try.after.sometime", null, Locale.ENGLISH), null);
		}
	}

	@Override
	public ApiResponse getOnBoardingSteps() {
		List<OnboardingSteps> steps = new ArrayList<OnboardingSteps>();
		
		try {
			steps = stepRepoIface.findAll();
			
			if (steps != null) {
				logger.info(CLASS +" getOnBoardingStep res List of Steps {}",steps);
				return AppUtil.createApiResponse(true, messageSource.getMessage("api.response.list.of.steps", null, Locale.ENGLISH), steps);
			} else {
				return AppUtil.createApiResponse(true, messageSource.getMessage("api.response.list.of.steps", null, Locale.ENGLISH), null);
			}
		}
		catch (JDBCConnectionException | ConstraintViolationException | DataException | LockAcquisitionException | PessimisticLockException
				| QueryTimeoutException | SQLGrammarException | GenericJDBCException ex) {
			ex.printStackTrace();
			logger.error(CLASS + "getOnBoardingSteps Exception {}",ex.getMessage());
			return AppUtil.createApiResponse(false, messageSource.getMessage("api.error.something.went.wrong.registration.failed", null, Locale.ENGLISH), null);
		} 
		catch (Exception e) {
			logger.error(CLASS + " getOnBoardingSteps Exception {}",e.getMessage());
			e.printStackTrace();
			return AppUtil.createApiResponse(false, messageSource.getMessage("api.error.something.went.wrong.please.try.after.sometime", null, Locale.ENGLISH), null);
		}
		
	}

	@Override
	public ApiResponse updateTemplateStatus(int id, String status) {
		logger.info(CLASS +" updateTemplateStatus req id  {} and  status {} ",id,status);
		SubscriberOnboardingTemplate template = templateRepoIface.findBytemplateId(id);
		try {
			
			if(template.getPublishedStatus() == status || template.getPublishedStatus().equals(status)) {
				return AppUtil.createApiResponse(false, messageSource.getMessage("api.response.template.is.already", null, Locale.ENGLISH)+status, null);
			}
			template.setState(Constant.ACTIVE);
			template.setPublishedStatus(status);
			template = templateRepoIface.save(template);
			if (template != null) {
				logger.info(CLASS +" updateTemplateStatus res Template has been {},  {} ",status, template);
				return AppUtil.createApiResponse(true, messageSource.getMessage("api.response.template.has.been", null, Locale.ENGLISH)+status, template);
			} else {
				return AppUtil.createApiResponse(false, messageSource.getMessage("api.error.template.status.not.updated", null, Locale.ENGLISH), null);
			}
		}
		catch (JDBCConnectionException | ConstraintViolationException | DataException | LockAcquisitionException | PessimisticLockException
				| QueryTimeoutException | SQLGrammarException | GenericJDBCException ex) {
			ex.printStackTrace();
			logger.error(CLASS + "updateTemplateStatus Exception {}",ex.getMessage());
			return AppUtil.createApiResponse(false, messageSource.getMessage("api.error.something.went.wrong.registration.failed", null, Locale.ENGLISH), null);
		} 
		catch (Exception e) {
			logger.error(CLASS + " updateTemplateStatus Exception {}",e.getMessage());
			e.printStackTrace();
			return AppUtil.createApiResponse(false, messageSource.getMessage("api.error.something.went.wrong.please.try.after.sometime", null, Locale.ENGLISH), null);
		}
		
	}
	
	@Override
	public ApiResponse testTemplate(SubscriberDTO subscriberDTO) {
		logger.info(CLASS +" testActviteTemplate req  {}",subscriberDTO);
		SubscriberOnboardingTemplate template = new SubscriberOnboardingTemplate();
		EditTemplateDTO templateDTO = new EditTemplateDTO();
		try {
			if (subscriberDTO.getMethodName() != null) {
				template = templateRepoIface.getPublishTemplate(subscriberDTO.getMethodName(), "PUBLISHED");
				
				List<MapMethodOnboardingStep> stepList = mapStepRepoIface.findBytemplateId(template.getTemplateId());
				
//				convert on-boarding steps to HashMap
				HashMap<String,MapMethodOnboardingStep> hm = new HashMap<String, MapMethodOnboardingStep>();
				stepList.forEach(mapMethodOnboardingStep->{
					hm.put(mapMethodOnboardingStep.getOnboardingStep(), mapMethodOnboardingStep);
				});
				templateDTO.setSteps(hm);
				
//				templateDTO.setSteps(stepList);
				templateDTO.setTemplateName(template.getTemplateName());
				templateDTO.setTemplateMethod(template.getTemplateMethod());
				templateDTO.setPublishedStatus(template.getPublishedStatus());
				templateDTO.setState(template.getState());
				templateDTO.setTemplateId(template.getTemplateId());
				
				if (templateDTO != null) {
					logger.info(CLASS +" testActviteTemplate res Template {}",templateDTO);
					return AppUtil.createApiResponse(true, messageSource.getMessage("api.response.Template", null, Locale.ENGLISH), templateDTO);
				}else {
					return AppUtil.createApiResponse(true, messageSource.getMessage("api.response.template.not.found", null, Locale.ENGLISH), null);
				}
			} else {
				return AppUtil.createApiResponse(true, messageSource.getMessage("api.response.method.name.is.empty", null, Locale.ENGLISH), null);
			}
		}
		catch (JDBCConnectionException | ConstraintViolationException | DataException | LockAcquisitionException | PessimisticLockException
				| QueryTimeoutException | SQLGrammarException | GenericJDBCException ex) {
			ex.printStackTrace();
			logger.error(CLASS + "testActviteTemplate Exception {}",ex.getMessage());
			return AppUtil.createApiResponse(false, messageSource.getMessage("api.error.something.went.wrong.registration.failed", null, Locale.ENGLISH), null);
		}
		catch (Exception e) {
			logger.error(CLASS + " testActviteTemplate Exception {}",e.getMessage());
			e.printStackTrace();
			return AppUtil.createApiResponse(false, messageSource.getMessage("api.error.something.went.wrong.please.try.after.sometime", null, Locale.ENGLISH), null);
		}
	}
	
	@Override
	public ApiResponse templateApprove(TemplateApproveDTO templateApproveDTO) {
		logger.info(CLASS +" approveTemplate  req  {}",templateApproveDTO);
		try {
			SubscriberOnboardingTemplate template = templateRepoIface.findBytemplateId(templateApproveDTO.getTemplateId());
			
			if(template.getPublishedStatus() == "PUBLISHED" || template.getPublishedStatus().equals("PUBLISHED")) {				
				return AppUtil.createApiResponse(true, messageSource.getMessage("api.response.your.template.status.is.published.please.unpublished.it.before.making.any.modifications", null, Locale.ENGLISH), null);
			}else {
				if (templateApproveDTO.isApprove()) {
//					template.setState(templateApproveEnum.ACTIVE.toString());
				}else {
//					template.setState(templateApproveEnum.DECLINED.toString());
				}
				template.setRemarks(templateApproveDTO.getRemarks());
			}
		
			template = templateRepoIface.save(template);
			if (template != null) {
				logger.info(CLASS +" approveTemplate res Template State Updated {}",template);
				return AppUtil.createApiResponse(true, messageSource.getMessage("api.response.template.state.updated", null, Locale.ENGLISH), template);
			} else {
				return AppUtil.createApiResponse(true, messageSource.getMessage("api.response.template.state.not.updated", null, Locale.ENGLISH), null);
			}
		}
		catch (JDBCConnectionException | ConstraintViolationException | DataException | LockAcquisitionException | PessimisticLockException
				| QueryTimeoutException | SQLGrammarException | GenericJDBCException ex) {
			ex.printStackTrace();
			logger.error(CLASS + "approveTemplate Exception {}",ex.getMessage());
			return AppUtil.createApiResponse(false, messageSource.getMessage("api.error.something.went.wrong.registration.failed", null, Locale.ENGLISH), null);
		}
		catch (Exception e) {
			logger.error(CLASS + " approveTemplate Exception {}",e.getMessage());
			e.printStackTrace();
			return AppUtil.createApiResponse(false, messageSource.getMessage("api.error.something.went.wrong.please.try.after.sometime", null, Locale.ENGLISH), null);
		}

	}	
	
	enum templateApproveEnum {
		NEW, ACTIVE, MODIFIED, DECLINED, DELETE, DELETED
	}
	
	@Override
	public ApiResponse deleteTemplateById(int id) {
		logger.info(CLASS +" deleteTemplateById req id {}",id);
		try {
			SubscriberOnboardingTemplate template = templateRepoIface.findBytemplateId(id);
			if (template == null) {
				return AppUtil.createApiResponse(false, messageSource.getMessage("api.error.template.not.found", null, Locale.ENGLISH), null);
			}
			if (template.getPublishedStatus().equals("PUBLISHED")) {
				return AppUtil.createApiResponse(false, messageSource.getMessage("api.error.template.is.in.use.cannot.deleted", null, Locale.ENGLISH), null);
			}else if(template.getPublishedStatus().equals("UNPUBLISHED") || template.getPublishedStatus() == "UNPUBLISHED") {
				template.setPublishedStatus(templateApproveEnum.DELETED.toString());
				template.setState(templateApproveEnum.MODIFIED.toString());
				template.setUpatedDate(AppUtil.getDate());
				templateRepoIface.save(template);
				logger.info(CLASS +" deleteTemplateById  res  Template Status to DELETED ");
				return AppUtil.createApiResponse(true, messageSource.getMessage("api.response.template.status.to.deleted", null, Locale.ENGLISH), null);
			}else if(template.getPublishedStatus().equals("DELETED") || template.getPublishedStatus() == "DELETED") {
				return AppUtil.createApiResponse(false, messageSource.getMessage("api.error.template.already.deleted", null, Locale.ENGLISH), null);
			}
			else{
				return AppUtil.createApiResponse(false, messageSource.getMessage("api.error.unpublished.the.template.first", null, Locale.ENGLISH), null);
			}
		}
		catch (JDBCConnectionException | ConstraintViolationException | DataException | LockAcquisitionException | PessimisticLockException
				| QueryTimeoutException | SQLGrammarException | GenericJDBCException ex) {
			ex.printStackTrace();
			logger.error(CLASS + "deleteTemplateById Exception {}",ex.getMessage());
			return AppUtil.createApiResponse(false, messageSource.getMessage("api.error.something.went.wrong.registration.failed", null, Locale.ENGLISH), null);
		}
		catch (Exception e) {
			logger.error(CLASS + " deleteTemplateById  Exception  {}",e.getMessage());
			e.printStackTrace();
			return AppUtil.createApiResponse(false, messageSource.getMessage("api.error.something.went.wrong.please.try.after.sometime", null, Locale.ENGLISH), null);			
		}
	}
	
	@Override
	public ApiResponse isTemplateAlreadyExixts(String templateName, String methodId) {
		logger.info(CLASS +" isTemplateExist req   templateName {}  and MethodName {} ",templateName,methodId);
		try {
			int a = templateRepoIface.isTemplateExist(templateName);
			if (a==0) {
				return AppUtil.createApiResponse(false, messageSource.getMessage("api.error.not.exist", null, Locale.ENGLISH), null);
			} else {
				return AppUtil.createApiResponse(true, messageSource.getMessage("api.response.exist", null, Locale.ENGLISH), null);
			}
		}catch (JDBCConnectionException | ConstraintViolationException | DataException | LockAcquisitionException | PessimisticLockException
				| QueryTimeoutException | SQLGrammarException | GenericJDBCException ex) {
			ex.printStackTrace();
			logger.error(CLASS + "saveSubscriberData Exception {}",ex.getMessage());
			return AppUtil.createApiResponse(false, messageSource.getMessage("api.error.something.went.wrong.please.try.after.sometime", null, Locale.ENGLISH), null);
		} 
		catch (Exception e) {
			logger.error(CLASS + " isTemplateAlreadyExixts Exception {}",e.getMessage());
			e.printStackTrace();
			return AppUtil.createApiResponse(false, messageSource.getMessage("api.error.something.went.wrong.please.try.after.sometime", null, Locale.ENGLISH), null);
		}
	}
	
	@Override
	public ApiResponse getTemplateLatestById(int id) {
		logger.info(CLASS +" getTemplateLatestById req id  {}",id);
		SubscriberOnboardingTemplate template = new SubscriberOnboardingTemplate();
		EditTemplateDTO templateDTO = new EditTemplateDTO();
		template = templateRepoIface.findBytemplateId(id);
		
		try {
			if (template != null) {
				List<MapMethodOnboardingStep> stepList = mapStepRepoIface.findBytemplateId(template.getTemplateId());
			
				HashMap<String,MapMethodOnboardingStep> hm = new HashMap<String, MapMethodOnboardingStep>();
				stepList.forEach(mapMethodOnboardingStep->{
					hm.put(mapMethodOnboardingStep.getOnboardingStep(), mapMethodOnboardingStep);
				});
				templateDTO.setTemplateId(id);
				templateDTO.setSteps(hm);
				templateDTO.setTemplateName(template.getTemplateName());
				templateDTO.setTemplateMethod(template.getTemplateMethod());
				templateDTO.setPublishedStatus(template.getPublishedStatus());
				templateDTO.setState(template.getState());
				templateDTO.setTemplateId(template.getTemplateId());
				
				if (templateDTO != null) {
					logger.info(CLASS +" getTemplateLatestById  res  Template  {}",templateDTO);
					return AppUtil.createApiResponse(true, "Template ", templateDTO);
				}else {
					return AppUtil.createApiResponse(false, messageSource.getMessage("api.error.template.not.found", null, Locale.ENGLISH), null);
				}
			} else {
				return AppUtil.createApiResponse(false, messageSource.getMessage("api.error.template.is.empty", null, Locale.ENGLISH), null);
			}
		}catch (JDBCConnectionException | ConstraintViolationException | DataException | LockAcquisitionException | PessimisticLockException
				| QueryTimeoutException | SQLGrammarException | GenericJDBCException ex) {
			ex.printStackTrace();
			logger.error(CLASS + "saveSubscriberData Exception {}",ex.getMessage());
			return AppUtil.createApiResponse(false, messageSource.getMessage("api.error.something.went.wrong.please.try.after.sometime", null, Locale.ENGLISH), null);
		} 
		catch (Exception e) {
			logger.error(CLASS +" getTemplateLatestById  Exception  {}",e.getMessage());
			e.printStackTrace();
			return AppUtil.createApiResponse(false, messageSource.getMessage("api.error.something.went.wrong.please.try.after.sometime", null, Locale.ENGLISH), null);
		}
	

	}

}

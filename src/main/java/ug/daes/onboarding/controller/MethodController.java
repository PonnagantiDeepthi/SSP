package ug.daes.onboarding.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
public class MethodController {

//	@Autowired
//	private OnboardingActivitiesRepoIface obarIface;
//	
//	@Autowired
//	private MethodsRepositoryIface mrIface;
//	
//	
//	@PostMapping(value = "/api/auth/save/methods")
//	public ApiResponse saveMethods(@RequestBody Methods methods) {
//		try {
//			Methods savedMethod = new Methods();
//			
//			try {
//				methods.setCreationDate(AppUtil.getDate());
//				savedMethod = mrIface.save(methods);
//			}catch (DataIntegrityViolationException e) {
//				e.printStackTrace();
//				return AppUtil.createApiResponse(false, "Method Name Should be Unique ", null);
//			}
//			
//			try {
//				if(savedMethod ==null){
//					return AppUtil.createApiResponse(true, "Method not Saved ", null);
//				}else {
//					return AppUtil.createApiResponse(true, "Method save Successfully ", null);
//				}				
//			} catch (DataIntegrityViolationException e) {
//				return AppUtil.createApiResponse(false, "Method name must be unique", null);
//			}			
//		} catch (Exception e) {
//			e.printStackTrace();
//			return AppUtil.createApiResponse(false, e.getMessage(), null);
//		}
//	}
//	
//	@GetMapping(value = "/api/auth/get/onboarding-activities")
//	public ApiResponse getOnboardingActivity() {
//		System.out.println("in onboarding controller");
//		List<OnboardingActivities> onboardingActivities = new ArrayList<OnboardingActivities>();
//		try {
//			onboardingActivities = obarIface.getAllActivity();
//			
//			if (onboardingActivities.isEmpty()) {
//				return AppUtil.createApiResponse(false, "Activity are Empty", null);
//			}else {
//				return AppUtil.createApiResponse(true, "Activity", onboardingActivities);
//			}			
//		} catch (Exception e) {
//			e.printStackTrace();
//			return AppUtil.createApiResponse(false, e.getMessage(), null);
//		}		
//	}
//	
//	@GetMapping(value = "/api/auth/get/methods")
//	public ApiResponse getMethods() {
//		System.out.println("in onboarding controller");
//		List<Methods> methods = new ArrayList<Methods>();
//		try {
//			methods = mrIface.findAll();
//			
//			if (methods.isEmpty()) {
//				return AppUtil.createApiResponse(false, "Methods are Empty", null);
//			}else {
//				return AppUtil.createApiResponse(true, "Methods", methods);
//			}			
//		} catch (Exception e) {
//			e.printStackTrace();
//			return AppUtil.createApiResponse(false, e.getMessage(), null);
//		}		
//	}
//	
//	@GetMapping(value = "/api/auth/get/method-by-id")
//	public ApiResponse getMethodsById(@RequestParam int id) {
//		System.out.println("in onboarding controller");
//		Methods method = new Methods();
//		try {
//			method = mrIface.findBymethodId(id);
//			
//			if (method == null) {
//				return AppUtil.createApiResponse(false, "Methods are Empty", null);
//			}else {
//				return AppUtil.createApiResponse(true, "Methods", method);
//			}			
//		} catch (Exception e) {
//			e.printStackTrace();
//			return AppUtil.createApiResponse(false, e.getMessage(), null);
//		}		
//	}
	
}

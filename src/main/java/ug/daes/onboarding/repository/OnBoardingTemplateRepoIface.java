/**
 * 
 */
package ug.daes.onboarding.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import ug.daes.onboarding.model.SubscriberOnboardingTemplate;

/**
 * @author Raxit Dubey
 *
 */
@Repository
public interface OnBoardingTemplateRepoIface extends JpaRepository<SubscriberOnboardingTemplate, Integer>{

	SubscriberOnboardingTemplate findBytemplateId(int id);	
	
	SubscriberOnboardingTemplate findBytemplateMethod(String methodName);
	
	@Query(value = "select * from subscriber_onboarding_templates\n" + 
			"where template_method = ?1 and published_status = ?2 and state = 'ACTIVE'", nativeQuery = true)
	SubscriberOnboardingTemplate getPublishTemplate(String methodName, String status);
	
	@Query(value = "call update_published_status(?1,?2);",nativeQuery = true)
	SubscriberOnboardingTemplate updateTemplateStatus(String status, int id);
	
	@Query(value = "call delete_map_method_onboarding_step_id(?1);",nativeQuery = true)
	void deleteTemplateById(int id);
	
	@Query(value = "select count(*) from subscriber_onboarding_templates sot where template_name =?1", nativeQuery = true)
	int isTemplateExist(String templateName);
	
	@Query(value = "select count(*) from subscriber_onboarding_templates sot where template_name =?1 and template_method = ?2", nativeQuery = true)
	int isTemplateExistWithMethod(String method);
	
	@Query(value = "select * from get_all_template gat", nativeQuery = true)
	List<SubscriberOnboardingTemplate> getAllTemplate();
	

}

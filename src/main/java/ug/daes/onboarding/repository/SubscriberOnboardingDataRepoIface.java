/**
 * 
 */
package ug.daes.onboarding.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import ug.daes.onboarding.model.SubscriberOnboardingData;

/**
 * @author Raxit Dubey
 *
 */
@Repository
public interface SubscriberOnboardingDataRepoIface extends JpaRepository<SubscriberOnboardingData, Integer>{

	SubscriberOnboardingData findBysubscriberUid(String suid);
	
	@Query(value = "select * from subscriber_onboarding_data where subscriber_uid = ?1 ORDER BY created_date DESC limit 1",nativeQuery = true)
	List<SubscriberOnboardingData> getBySubUid(String uid);
	
	@Query(value = "select * from subscriber_onboarding_data where subscriber_uid = ?1"+
			" ORDER BY created_date DESC LIMIT 1",nativeQuery = true)
	SubscriberOnboardingData findLatestSubscriber(String suid);
	
	
	@Query(value = "SELECT COUNT(optional_data1),onboarding_method='NIN' from subscriber_onboarding_data sod where optional_data1 =?", nativeQuery = true)
	int getOptionalData1(String optionalData1);
	
	@Query(value = "SELECT DISTINCT subscriber_uid from subscriber_onboarding_data sod where optional_data1 =? ", nativeQuery = true)
	String getOptionalData1Subscriber(String optionalData1);
	
	@Query(value = "SELECT * from subscriber_onboarding_data sod where id_doc_number=? ", nativeQuery = true)
	List<SubscriberOnboardingData> findSubscriberByDocId(String documentNumber);
}

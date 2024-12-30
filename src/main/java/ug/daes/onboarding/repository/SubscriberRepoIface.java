/**
 * 
 */
package ug.daes.onboarding.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestParam;

import ug.daes.onboarding.model.Subscriber;

/**
 * @author Raxit Dubey
 *
 */
@Repository
@Transactional
public interface SubscriberRepoIface extends JpaRepository<Subscriber, Integer>{
	
	@Query(value = "select count(mobile_number) from subscribers where mobile_number = ?1",nativeQuery =  true)
	int countSubscriberMobile(String mobileNo);
	
	@Query(value = "select count(email_id) from subscribers where email_id = ?1",nativeQuery =  true)
	int countSubscriberEmailId(String emailId);
	
	@Query(value = "select count(device_uid) from subscriber_devices where device_uid = ?1",nativeQuery =  true)
	int countSubscriberDevice(String deviceId);
	
	@Query(value = "select * from subscribers where mobile_number = ?1 and email_id = ?2",nativeQuery =  true)
	Subscriber findFCMTokenByMobileEamil(String mobileNo,String email);
	
	Subscriber findByemailId(String emailId);
	
	Subscriber findBysubscriberUid(String suid);
	
	Subscriber findBymobileNumber(String mobileNo);

	@Query(value = "select * from subscribers s where email_id = ?1 or mobile_number = ?2" , nativeQuery = true)
	Subscriber getSubscriberUidByEmailAndMobile(String email, String mobile);
	
	@Query(value = "select video_url from subscriber_complete_details where subscriber_uid =?1" , nativeQuery = true)
	String getSubscriberUid(String subscriberUid);
	
	@Query(value = "select count(id_doc_number) from subscriber_onboarding_data sod WHERE id_doc_number=?1 and subscriber_uid = ?2" , nativeQuery = true)
	int getSubscriberIdDocNumber(String idDocNumber, String subscriberUid);
	
	@Query(value = "select count(id_doc_number) from subscriber_onboarding_data sod WHERE id_doc_number=?1" , nativeQuery = true)
	int getIdDocCount(String idDocNumber);
	
	@Query(value = "SELECT subscriber_status from subscriber_status ss WHERE subscriber_uid = ?1" , nativeQuery = true)
	String getSubscriberStatus(String subscriberUid);
	
	@Query(value = "select certificate_status from subscriber_certificate_life_cycle sclc where subscriber_uid = ?1 and certificate_type = 'SIGN' ORDER BY created_date DESC limit 1", nativeQuery = true)
	String getCertStatus(String subscriberUid);
	
	@Query(value = "SELECT COUNT(*) from subscribers",nativeQuery = true)
	int countOnboarding();
	
	@Query(value = "SELECT payment_status from subscriber_payment_history sph \n" +
			"WHERE subscriber_suid = ?1 \n" +
			"and payment_category = 'ONE_TIME_AND_CERT_FEE_COLLECTION' and payment_status = 'Success' or payment_status = 'Failed' order by created_on DESC limit 1",nativeQuery = true)
	String subscriberPaymnetStatus(String suid);
	
	@Query(value = "SELECT DISTINCT payment_status from subscriber_payment_history sph \n"
			+ "WHERE subscriber_suid = ?1 \n"
			+ "and payment_category = 'CERT_FEE_COLLECTION' ORDER BY created_on DESC LIMIT 1 ",nativeQuery = true)
	String subscriberPaymnetCertStatus(String suid);
	
	
	@Query(value = "SELECT payment_status from subscriber_payment_history sph \n" + 
			"WHERE subscriber_suid = ?1 \n" + 
			"and payment_category = 'ONE_TIME_AND_CERT_FEE_COLLECTION' and payment_status = 'Initiated' ORDER BY created_on DESC LIMIT 1",nativeQuery = true)
	String subscriberPaymnetInitaiatedStatus(String suid);
	
	@Modifying
	@Query(value = "call ra_0_2.delete_subscriber_record (?1)", nativeQuery = true)
	int deleteRecordBySubscriberUid(String suid);
	
}

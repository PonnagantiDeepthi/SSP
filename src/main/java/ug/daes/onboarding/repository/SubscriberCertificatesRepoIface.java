package ug.daes.onboarding.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import ug.daes.onboarding.model.SubscriberCertificate;

public interface SubscriberCertificatesRepoIface extends JpaRepository<SubscriberCertificate,String>{

//	@Query(value = "select certificate_status from subscriber_certificates where subscriber_uid=?1 and certificate_type=?2 and certificate_status=?3", nativeQuery = true)
//	String getSubscriberCertificateStatus(String uid,String type,String status);
	
	
	@Query(value = "select certificate_status from subscriber_certificates where\n" + 
			"created_date =(\n" + 
			"SELECT MAX(created_date) FROM subscriber_certificates sc \n" + 
			"WHERE subscriber_uid=?1  and certificate_type=?2 )", nativeQuery = true)
	String getSubscriberCertificateStatus(String uid,String type,String status);
	
	
	@Query(value = "select max(certificate_status) as  certificate_status  from subscriber_certificate_life_cycle where subscriber_uid=?1 and certificate_type=?2 and certificate_status=?3",nativeQuery = true)
	String getSubscriberCertificateStatusLifeHistory(String uid,String type,String status);
	
}
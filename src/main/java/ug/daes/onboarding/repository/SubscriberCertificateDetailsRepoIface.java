package ug.daes.onboarding.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import ug.daes.onboarding.model.SubscriberCertificateDetails;

@Repository
public interface SubscriberCertificateDetailsRepoIface extends JpaRepository<SubscriberCertificateDetails, String>{

//	@Query(value = "SELECT * FROM subscriber_certificates_details scd where created_date >= date(?1) and  created_date <= date(?2) order by created_date DESC;",nativeQuery =  true)
//	List<SubscriberCertificateDetails> getSubscriberReports(String startDate, String endDate);
	
	@Query(value = "SELECT * FROM subscriber_certificates_details scd where date(created_date) >= date(?1) and  date(created_date) <= date(?2) order by created_date DESC;",nativeQuery =  true)
	List<SubscriberCertificateDetails> getSubscriberReports(String startDate, String endDate);
}

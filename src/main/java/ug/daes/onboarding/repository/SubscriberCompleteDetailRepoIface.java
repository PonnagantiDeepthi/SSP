package ug.daes.onboarding.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import ug.daes.onboarding.model.SubscriberCompleteDetail;

@Transactional
@Repository
public interface SubscriberCompleteDetailRepoIface extends JpaRepository<SubscriberCompleteDetail, Integer>{
	
	@Query(value = "SELECT * FROM subscriber_certificates_details scd where created_date >= ?1 and  created_date <= ?2 order by created_date DESC;",nativeQuery =  true)
	List<SubscriberCompleteDetail> getSubscriberReports(String startDate, String endDate);
	
	@Query(value = "SELECT COUNT(device_status) from subscriber_complete_details scd WHERE (device_status =?1 and email_id=?2 ) or (device_status =?1 and mobile_number=?3)" , nativeQuery = true)
	int getActiveDeviceCountStatusByEmailAndMobileNo(String status,String email, String mobileNo);

}

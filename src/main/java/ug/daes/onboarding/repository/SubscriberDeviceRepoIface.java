/**
 * 
 */
package ug.daes.onboarding.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ug.daes.onboarding.model.SubscriberDevice;

/**
 * @author Raxit Dubey
 *
 */

@Repository
public interface SubscriberDeviceRepoIface extends JpaRepository<SubscriberDevice, Integer>{
	
	@Query(value = "SELECT * FROM subscriber_devices sd where device_uid=?1 order by updated_date DESC limit 1",nativeQuery =  true)
	SubscriberDevice findBydeviceUid(String deviceId);
	
	@Query(value = "SELECT * FROM subscriber_devices sd where device_uid=?1 order by updated_date DESC limit 1",nativeQuery =  true)
	SubscriberDevice findBydeviceDetails(String deviceId);
	
	@Query(value = "SELECT * FROM subscriber_devices sd where device_uid=?1 order by updated_date DESC limit 1",nativeQuery =  true)
	SubscriberDevice findDeviceDetailsById(String deviceId);
	
	@Query(value = "SELECT * FROM subscriber_devices sd where device_uid=?1 and device_status =?2",nativeQuery =  true)
	SubscriberDevice findBydeviceUidAndStatus(String deviceId,String status);
	
	@Query(value = "SELECT * FROM subscriber_devices sd where device_uid=?1 order by device_status DESC limit 1",nativeQuery =  true)
	SubscriberDevice findBydeviceUidDetails(String deviceId,String status);
	
	@Query(value = "SELECT * FROM subscriber_devices sd where subscriber_uid = ?1 and updated_date = (SELECT max(updated_date) FROM subscriber_devices sd \n" +
			"WHERE subscriber_uid = ?1)",nativeQuery =  true)
	SubscriberDevice getSubscriber(String suid);



	@Modifying
	@Transactional
	@Query(value = "INSERT INTO subscriber_devices\n" +
			"(subscriber_uid, device_uid, device_status, created_date, updated_date)\n" +
			"VALUES(:suid, :deviceId, :status, :createdDate, :updateDate);" , nativeQuery = true)
	void insertSubscriber(@Param("suid") String suid
			, @Param("deviceId")String deviceId,@Param("status") String status
			, @Param("createdDate")String createdDate, @Param("updateDate")String updateDate);
	
	@Modifying
	@Transactional
	@Query(value = "UPDATE subscriber_devices SET device_uid=?1, device_status=?2, updated_date=?3 WHERE subscriber_device_id=?4", nativeQuery = true)
	int updateSubscriber(String deviceUid, String deviceStatus, String updatedDate, int subscriberDeviceId);
	
	@Query(value = "SELECT * FROM subscriber_devices sd where subscriber_uid = ?1",nativeQuery =  true)
	SubscriberDevice getSubscriberDeviceStatus(String suid);
	
}

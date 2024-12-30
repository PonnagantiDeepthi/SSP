package ug.daes.onboarding.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import ug.daes.onboarding.model.SubscriberDevice;
import ug.daes.onboarding.model.SubscriberDeviceHistory;

import java.util.List;

@Repository
public interface SubscriberDeviceHistoryRepoIface extends JpaRepository<SubscriberDeviceHistory, Integer>{
	
	
	@Query(value = "select * from subscriber_devices_history where device_uid=?1 order by updated_date DESC Limit 1",nativeQuery = true)
	SubscriberDeviceHistory findBydeviceUid(String deviceId);
	
    @Query(value = "select * from subscriber_devices_history where device_uid=?1 and subscriber_uid=?2",nativeQuery = true)
    List<SubscriberDeviceHistory> findByDeviceUidAndSubscriberUid(String deviceUid, String subscriberUid);
    
    @Query(value = "select * from subscriber_devices_history where device_uid=?1 and subscriber_uid=?2 order by updated_date DESC Limit 1",nativeQuery = true)
    SubscriberDeviceHistory findByDeviceUidAndSubUid(String deviceUid, String subscriberUid);
    
    @Query(value = "select * from subscriber_devices_history where subscriber_uid=?1 order by updated_date DESC Limit 1",nativeQuery = true)
	SubscriberDeviceHistory findBySubscriberUid(String subUID);
	
//	@Query(value = "select * from subscriber_devices_history where device_uid=?1 order by created_date DESC Limit 1",nativeQuery = true)
//	SubscriberDeviceHistory findBydeviceUid(String deviceId);
//	
//    @Query(value = "select * from subscriber_devices_history where device_uid=?1 and subscriber_uid=?2",nativeQuery = true)
//    List<SubscriberDeviceHistory> findByDeviceUidAndSubscriberUid(String deviceUid, String subscriberUid);
//    
//    @Query(value = "select * from subscriber_devices_history where device_uid=?1 and subscriber_uid=?2 order by created_date DESC Limit 1",nativeQuery = true)
//    SubscriberDeviceHistory findByDeviceUidAndSubUid(String deviceUid, String subscriberUid);
//    
//    @Query(value = "select * from subscriber_devices_history where subscriber_uid=?1 order by created_date DESC Limit 1",nativeQuery = true)
//	SubscriberDeviceHistory findBySubscriberUid(String subUID);
    
}

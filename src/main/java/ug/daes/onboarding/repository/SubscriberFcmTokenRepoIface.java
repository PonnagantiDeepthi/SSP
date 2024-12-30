/**
 * 
 */
package ug.daes.onboarding.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import ug.daes.onboarding.model.SubscriberFcmToken;

/**
 * @author Raxit Dubey
 *
 */
public interface SubscriberFcmTokenRepoIface extends JpaRepository<SubscriberFcmToken, Integer>{

	SubscriberFcmToken findBysubscriberUid(String suid);
	
}

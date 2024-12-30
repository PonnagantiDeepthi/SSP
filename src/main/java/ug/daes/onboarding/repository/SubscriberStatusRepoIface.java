/**
 * 
 */
package ug.daes.onboarding.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ug.daes.onboarding.model.SubscriberStatus;

/**
 * @author Raxit Dubey
 *
 */
@Repository
public interface SubscriberStatusRepoIface extends JpaRepository<SubscriberStatus, Integer>{

	SubscriberStatus findBysubscriberUid(String suid);
	
}

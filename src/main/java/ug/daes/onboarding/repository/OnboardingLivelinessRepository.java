package ug.daes.onboarding.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import ug.daes.onboarding.model.OnboardingLiveliness;

@Repository
public interface OnboardingLivelinessRepository extends JpaRepository<OnboardingLiveliness, Integer>  {

	@Query(value = "select url from onboarding_liveliness where subscriber_uid =?1" , nativeQuery = true)
	String getSubscriberUid(String subscriberUid);
}

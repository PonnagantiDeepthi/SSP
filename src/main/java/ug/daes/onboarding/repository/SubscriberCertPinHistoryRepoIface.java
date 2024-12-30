package ug.daes.onboarding.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import ug.daes.onboarding.model.SubscriberCertificatePinHistory;

public interface SubscriberCertPinHistoryRepoIface extends JpaRepository<SubscriberCertificatePinHistory,String>{
	SubscriberCertificatePinHistory findBysubscriberUid(String uid);
}
package ug.daes.onboarding.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import ug.daes.onboarding.model.Consent;

@Repository
public interface ConsentRepoIface extends JpaRepository<Consent, Integer> {

	@Query(value = "select * from consent", nativeQuery = true)
	Consent getConsent();

	@Query(value = "call get_active_consent()", nativeQuery = true)
	Consent getActiveConsent();

	Consent findByconsentId(int id);

	@Query(value = "Exec Update_Consent @ConsentId=?1, @Consent=?2, @ConsentType=?3, @Status=?4", nativeQuery = true)
	Consent upDateConsent(Integer consentId, String consent, String consentType, Integer status);

	@Query(value = "call update_consent_status_active (?1,?2)", nativeQuery = true)
	Consent updateConsentStatusActive(int id, String status);

	@Query(value = "call update_consent_status_inactive (?1,?2)", nativeQuery = true)
	Consent updateConsentStatusInactive(int id, String status);
}

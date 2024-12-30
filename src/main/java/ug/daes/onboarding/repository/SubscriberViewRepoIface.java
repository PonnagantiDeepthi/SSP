package ug.daes.onboarding.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import ug.daes.onboarding.model.SubscriberView;

@Repository
@Transactional
public interface SubscriberViewRepoIface extends JpaRepository<SubscriberView, String> {

	
	@Query(value ="SELECT * FROM subscriber_view sv WHERE subscriber_uid=?1", nativeQuery = true)
	SubscriberView findSubscriberDetails(String suid);
	
	@Query(value ="select * from subscriber_view sv where id_doc_number=?1 AND NOT subscriber_status = 'CERT_REVOKED' AND NOT subscriber_status = 'CERT_EXPIRED' AND NOT subscriber_status = 'INACTIVE'", nativeQuery = true)
	List<SubscriberView> findSubscriberByDocId(String documeentId);
	
	@Query(value ="select * from subscriber_view sv where id_doc_number=?1 AND NOT subscriber_status = 'CERT_REVOKED' AND NOT subscriber_status = 'CERT_EXPIRED' AND NOT subscriber_status = 'INACTIVE'", nativeQuery = true)
	SubscriberView findSubscriberByDocIdCertRevoked(String documeentId);
}

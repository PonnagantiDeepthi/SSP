package ug.daes.onboarding.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ug.daes.onboarding.model.TrustedUser;

import java.util.List;

import javax.transaction.Transactional;

@Repository
@Transactional
public interface TrustedUserRepoIface extends JpaRepository<TrustedUser, Integer> {
	TrustedUser findByemailId(String emailId);

	@Query(value = "SELECT email from trusted_users ", nativeQuery = true)
	List<String> getTrustedEmails();

	@Query(value = "SELECT * from trusted_users WHERE email=?1", nativeQuery = true)
	TrustedUser getTrustedUserDratilsByEmail(String email);
}

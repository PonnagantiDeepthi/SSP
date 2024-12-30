/**
 * 
 */
package ug.daes.onboarding.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import ug.daes.onboarding.model.MapMethodOnboardingStep;

/**
 * @author Raxit Dubey
 *
 */
public interface MapMethodObStepRepoIface extends JpaRepository<MapMethodOnboardingStep, Integer>{

	List<MapMethodOnboardingStep> findBytemplateId(int id);
	
	@Modifying
	@Query(value = "call delete_map_template_step(?1)", nativeQuery = true)
	@Transactional(rollbackFor=Exception.class)
	public int deleteBytemplateId(int id);
	
}

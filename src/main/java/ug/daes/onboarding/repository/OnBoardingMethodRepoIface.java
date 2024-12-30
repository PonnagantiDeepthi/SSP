/**
 * 
 */
package ug.daes.onboarding.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ug.daes.onboarding.model.OnboardingMethod;

/**
 * @author Raxit Dubey
 *
 */
@Repository
public interface OnBoardingMethodRepoIface extends JpaRepository<OnboardingMethod, Integer>{	
	
	OnboardingMethod findByonboardingMethod(String methodName);
	
}

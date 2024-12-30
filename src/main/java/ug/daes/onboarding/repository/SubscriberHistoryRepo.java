package ug.daes.onboarding.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ug.daes.onboarding.model.SubscriberContactHistory;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;

@Repository
@Transactional
public interface SubscriberHistoryRepo extends JpaRepository<SubscriberContactHistory, Integer> {

    @Query(value = "select max(created_date) from subscriber_contact_history where subscriber_uid=?1 and email_id is not null",nativeQuery = true)
    Date getLatestForEmail(String suid);

    @Query(value = "select max(created_date) from subscriber_contact_history where subscriber_uid=?1 and mobile_number is not null",nativeQuery = true)
    Date getLatestForMobile(String suid);

}

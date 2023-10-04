package zw.org.nmr.limsehr.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import zw.org.nmr.limsehr.domain.JobScheduler;
import zw.org.nmr.limsehr.domain.Test;

/**
 * Spring Data JPA repository for the {@link Test} entity.
 */
@Repository
public interface JobSchedulerRepository extends JpaRepository<JobScheduler, String> {
    Optional<JobScheduler> findByJobName(String jobName);
}

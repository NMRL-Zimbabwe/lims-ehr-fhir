package zw.org.nmr.limsehr.service;

import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import zw.org.nmr.limsehr.domain.JobScheduler;
import zw.org.nmr.limsehr.repository.JobSchedulerRepository;

/**
 * Service class for managing labs.
 */
@Service
public class JobSchedulerService {

    private final Logger log = LoggerFactory.getLogger(JobSchedulerService.class);

    @Autowired
    JobSchedulerRepository jobSchedulerRepository;

    public Optional<JobScheduler> resolverScheduled(String jobName) {
        return jobSchedulerRepository.findByJobName(jobName);
    }
}

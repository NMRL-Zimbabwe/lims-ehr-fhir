package zw.org.nmr.limsehr.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.io.Serializable;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A user.
 */
@Entity
@Table(name = "job_scheduler")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class JobScheduler extends AbstractAuditingEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "id", unique = true, nullable = false)
    private String id;

    @JsonIgnore
    @Column(name = "job_name")
    private String jobName;

    @JsonIgnore
    @Column(name = "run_job")
    private int runJob;

    public boolean isActive() {
        return runJob > 0;
    }

    public boolean isInActive() {
        return !isActive();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getRunJob() {
        return runJob;
    }

    public void setRunJob(int runJob) {
        this.runJob = runJob;
    }

    public String getJobName() {
        return jobName;
    }

    public void setJobName(String jobName) {
        this.jobName = jobName;
    }

    @Override
    public String toString() {
        return "JobScheduler{" + "id='" + id + '\'' + ", jobName='" + jobName + '\'' + ", runJob='" + runJob + '\'' + '}';
    }
}

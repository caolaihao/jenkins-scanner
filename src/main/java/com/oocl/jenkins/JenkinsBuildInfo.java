package com.oocl.jenkins;

import com.offbytwo.jenkins.model.BuildResult;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "JENKINS_BUILD")
public class JenkinsBuildInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private final String jobName;
    private final int buildNumber;
    private final long timestamp;
    private final String triggerDescription;
    private JenkinsBuildStatus status;

    public JenkinsBuildInfo(String jobName, int buildNumber, long timestamp, BuildResult buildResult, String triggerDescription) {
        this.jobName = jobName;
        this.buildNumber = buildNumber;
        this.timestamp = timestamp;
        this.triggerDescription = triggerDescription;
        this.status = buildResult == BuildResult.SUCCESS ? JenkinsBuildStatus.SUCCESS : JenkinsBuildStatus.FAILED;
    }

    public JenkinsBuildStatus getStatus() {
        return status;
    }

    public String getJobName() {
        return jobName;
    }

    public int getBuildNumber() {
        return buildNumber;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public String getTriggerDescription() {
        return triggerDescription;
    }

    public Long getId() {
        return id;
    }
}

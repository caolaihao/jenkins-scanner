package com.sample.jenkins;

import com.offbytwo.jenkins.model.BuildResult;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

public class JenkinsBuildRepositoryTest extends IntegrationTest{
    @Autowired
    private JenkinsBuildRepository repository;

    @Test
    public void should_save_build_detail_when_given_a_build_number() throws Exception {
        String jobName = "JOB";
        int buildNumber = 10;
        long timestamp = 1506239332999L;
        JenkinsBuildInfo buildInfo = new JenkinsBuildInfo(jobName, buildNumber, timestamp, BuildResult.SUCCESS, "trigger");

        repository.save(buildInfo);

        Optional<JenkinsBuildInfo> actualInfo = repository.findByJobNameAndBuildNumber(jobName, buildNumber);
        assertThat(actualInfo).isPresent().contains(buildInfo);
    }

    @Test
    public void should_get_last_build_number_by_job_name() throws Exception {
        String jobName = "JOB";
        long timestamp = 1506239332999L;
        int lastBuildNumber = 10;
        for (int buildNumber = 0; buildNumber <= lastBuildNumber; buildNumber++) {
            repository.save(new JenkinsBuildInfo(jobName, buildNumber, timestamp, BuildResult.SUCCESS, "trigger"));
        }

        Optional<JenkinsBuildInfo> lastBuild = repository.findTopByJobNameOrderByBuildNumberDesc(jobName);
        int actualLastBuildNumber = lastBuild.isPresent()? lastBuild.get().getBuildNumber(): 0;

        assertThat(actualLastBuildNumber).isEqualTo(lastBuildNumber);
    }
}

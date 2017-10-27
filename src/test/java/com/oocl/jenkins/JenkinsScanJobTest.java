package com.oocl.jenkins;

import com.offbytwo.jenkins.model.BuildResult;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
@ActiveProfiles("test")
public class JenkinsScanJobTest {

    @Autowired
    private JenkinsBuildRepository repository;

    @Test
    public void should_refresh_job_build_info_given_a_job_name() throws Exception {
        String jobName = "JOB";
        int startBuildNumber = 10;
        int jenkinsLastBuildNumber = 12;
        JenkinsScanner scanner = getMockedJenkinsScanner(jobName, startBuildNumber, jenkinsLastBuildNumber);

        JenkinsScanJob job = new JenkinsScanJob(scanner, repository);
        job.run(jobName);

        Optional<JenkinsBuildInfo> lastBuild = repository.findTopByJobNameOrderByBuildNumberDesc(jobName);
        int actualLastBuildNumber = lastBuild.isPresent()? lastBuild.get().getBuildNumber(): 0;
        assertThat(actualLastBuildNumber).isEqualTo(12);
    }

    private JenkinsScanner getMockedJenkinsScanner(String jobName, int startBuildNumber, int jenkinsLastBuildNumber) {
        long timestamp = 1506239332999L;

        List<JenkinsBuild> list = new ArrayList<>();
        for (int i = startBuildNumber + 1; i <= jenkinsLastBuildNumber; i++) {
            list.add(new JenkinsBuild(jobName, i));
        }
        JenkinsScanner scanner = mock(JenkinsScanner.class);
        when(scanner.getBuildsAfterNumber(anyString(), anyInt())).thenReturn(list);
        when(scanner.getBuildInfo(anyString(), anyInt())).thenAnswer(invocation ->
                new JenkinsBuildInfo((String) invocation.getArguments()[0], (Integer) invocation.getArguments()[1], timestamp, BuildResult.SUCCESS, ""));
        return scanner;
    }
}

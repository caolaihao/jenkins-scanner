package com.oocl.jenkins;

import com.github.tomakehurst.wiremock.junit.WireMockRule;
import org.junit.Rule;
import org.junit.Test;

import java.util.List;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class JenkinsScannerTest {
    public static final String JOBS_JSON_FILE = "jobs.json";
    public static final int JENKINS_PORT = 8080;
    public static final String JENKINS_URL = "http://localhost:" + JENKINS_PORT + "/";
    public static final String ERROR_JENKINS_URL = "http://localhost:12399/";
    public static final String JOB_ALL_BUILDS_JSON = "job-allbuilds.json";
    public static final String JOB_JSON = "job.json";
    public static final String JOB_NAME = "pipeline-demo";

    @Rule
    public WireMockRule wireMockRule = new WireMockRule(JENKINS_PORT);

    @Test
    public void should_get_all_job_names() throws Exception {
        stubFor(get(urlEqualTo("/api/json"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withBodyFile(JOBS_JSON_FILE)));

        JenkinsScanner scanner = new JenkinsScanner(JENKINS_URL, "", "");

        List<String> jobNames = scanner.getAllJobNames();

        assertThat(jobNames.size()).isGreaterThan(0);
    }

    @Test(expected = JenkinsScanException.class)
    public void should_fail_when_scan_jenkins_given_jenkins_not_running() throws Exception {
        JenkinsScanner scanner = new JenkinsScanner(ERROR_JENKINS_URL, "", "");

        scanner.getAllJobNames();
    }

    @Test
    public void should_get_all_build_list_of_a_job() throws Exception {
        stubFor(get(urlEqualTo("/job/" + JOB_NAME + "/api/json"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withBodyFile(JOB_JSON)));

        stubFor(get(urlEqualTo("/job/" + JOB_NAME + "/api/json?tree=allBuilds[number[*],url[*],queueId[*]]"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withBodyFile(JOB_ALL_BUILDS_JSON)));

        JenkinsScanner scanner = new JenkinsScanner(JENKINS_URL, "", "");

        List<JenkinsBuild> builds = scanner.getBuildsByJob(JOB_NAME);

        assertThat(builds.size()).isGreaterThan(0);
    }

}

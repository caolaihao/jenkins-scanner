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
}

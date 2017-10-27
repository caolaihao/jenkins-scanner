package com.sample.jenkins;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Optional;

public class JenkinsScanJob {
    private static final Logger log = LoggerFactory.getLogger(JenkinsScanJob.class);

    private final JenkinsScanner scanner;
    private final JenkinsBuildRepository repository;

    public JenkinsScanJob(JenkinsScanner scanner, JenkinsBuildRepository repository) {
        this.scanner = scanner;
        this.repository = repository;
    }

    public void run(List<String> jobs) {
        for (String job : jobs) {
            run(job);
        }
    }

    public void run(String jobName) {
        log.info("Scanning Jenkins builds of {}", jobName);

        int lastBuildNumber = getLastBuildNumber(jobName);
        log.info("Last build: {}", lastBuildNumber);

        List<JenkinsBuild> builds = getLatestJenkinsBuilds(jobName, lastBuildNumber);
        log.info("Found {} new builds", builds.size());

        saveJenkinsBuildInfo(jobName, builds);
        log.info("{} new builds of {} saved.", builds.size(), jobName);
    }

    private void saveJenkinsBuildInfo(String jobName, List<JenkinsBuild> builds) {
        for (JenkinsBuild build : builds) {
            JenkinsBuildInfo buildInfo = scanner.getBuildInfo(jobName, build.getNumber());
            repository.save(buildInfo);
        }
    }

    private List<JenkinsBuild> getLatestJenkinsBuilds(String jobName, int lastBuildNumber) {

        return scanner.getBuildsAfterNumber(jobName, lastBuildNumber);
    }

    private int getLastBuildNumber(String jobName) {
        Optional<JenkinsBuildInfo> lastBuild = repository.findTopByJobNameOrderByBuildNumberDesc(jobName);

        return lastBuild.isPresent() ? lastBuild.get().getBuildNumber() : 0;
    }
}

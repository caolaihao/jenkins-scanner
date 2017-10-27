package com.oocl.jenkins;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface JenkinsBuildRepository extends JpaRepository<JenkinsBuildInfo, Long> {
    Optional<JenkinsBuildInfo> findByJobNameAndBuildNumber(String jobName, Integer buildNumber);

    Optional<JenkinsBuildInfo> findTopByJobNameOrderByBuildNumberDesc(String jobName);
}

package com.oocl.jenkins;

import org.springframework.data.jpa.repository.JpaRepository;

public interface JenkinsBuildRepository extends JpaRepository<JenkinsBuildInfo, Long> {
    JenkinsBuildInfo findByJobNameAndBuildNumber(String jobName, Integer buildNumber);
}

package com.oocl.jenkins;

public class JenkinsBuild {
    private final String jobName;
    private final int number;

    public JenkinsBuild(String jobName, int number) {

        this.jobName = jobName;
        this.number = number;
    }
}

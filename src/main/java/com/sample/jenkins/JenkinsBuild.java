package com.sample.jenkins;

public class JenkinsBuild {
    private final String jobName;
    private final int number;

    public JenkinsBuild(String jobName, int number) {

        this.jobName = jobName;
        this.number = number;
    }

    public String getJobName() {
        return jobName;
    }

    public int getNumber() {
        return number;
    }
}

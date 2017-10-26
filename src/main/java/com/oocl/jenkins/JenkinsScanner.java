package com.oocl.jenkins;

import com.offbytwo.jenkins.JenkinsServer;
import com.offbytwo.jenkins.model.Build;
import com.offbytwo.jenkins.model.Job;
import com.offbytwo.jenkins.model.JobWithDetails;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class JenkinsScanner {

    private String jenkinsUrl;
    private String userName;
    private String userPassword;
    private JenkinsServer jenkinsServer;

    public JenkinsScanner(String jenkinsUrl, String userName, String userPassword) {
        this.jenkinsUrl = jenkinsUrl;
        this.userName = userName;
        this.userPassword = userPassword;
    }

    public List<String> getAllJobNames() {
        try {
            Map<String, Job> jobs = getJenkinsServer().getJobs();

            return new ArrayList<>(jobs.keySet());
        } catch (IOException e) {
            e.printStackTrace();
            throw new JenkinsScanException();
        }
    }

    public List<JenkinsBuild> getBuildsByJob(String jobName) {
        List<JenkinsBuild> results = new ArrayList<>();
        try {
            JobWithDetails job = getJenkinsServer().getJob(jobName);
            List<Build> allBuilds = job.getAllBuilds();
            for (Build build : allBuilds) {
                JenkinsBuild jenkinsBuild = new JenkinsBuild(jobName, build.getNumber());

                results.add(jenkinsBuild);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return results;
    }

    private JenkinsServer getJenkinsServer() {
        if (jenkinsServer == null) {
            try {
                jenkinsServer = new JenkinsServer(new URI(jenkinsUrl), userName, userPassword);
            } catch (URISyntaxException e) {
                e.printStackTrace();
                throw new JenkinsScanException();
            }
        }

        return jenkinsServer;
    }
}

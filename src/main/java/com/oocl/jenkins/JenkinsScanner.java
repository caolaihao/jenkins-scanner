package com.oocl.jenkins;

import com.offbytwo.jenkins.JenkinsServer;
import com.offbytwo.jenkins.model.Job;

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

    public JenkinsScanner(String jenkinsUrl, String userName, String userPassword) {
        this.jenkinsUrl = jenkinsUrl;
        this.userName = userName;
        this.userPassword = userPassword;
    }

    public List<String> getAllJobNames() {
        try {
            Map<String, Job> jobs = new JenkinsServer(new URI(jenkinsUrl), userName, userPassword).getJobs();

            return new ArrayList<>(jobs.keySet());
        } catch (URISyntaxException e) {
            e.printStackTrace();
            throw new JenkinsScanException();
        } catch (IOException e) {
            e.printStackTrace();
            throw new JenkinsScanException();
        }
    }
}

package com.sample.jenkins;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.Banner;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.core.env.Environment;

import java.util.Arrays;
import java.util.List;

@SpringBootApplication
public class JenkinsGatherApplication implements CommandLineRunner {
    @Autowired
    private Environment environment;
    @Autowired
    private ApplicationContext context;

	public static void main(String[] args) {
        SpringApplication app = new SpringApplication(JenkinsGatherApplication.class);
        app.setBannerMode(Banner.Mode.OFF);
        app.run(args);
	}

    @Override
    public void run(String... args) throws Exception {
        String[] activeProfiles = environment.getActiveProfiles();
        if (activeProfiles.length > 0) {
            if (Arrays.asList(activeProfiles).contains("test")) {
                return;
            }
        }

        JenkinsScanner scanner = getJenkinsScanner();
        List<String> allJobNames = scanner.getAllJobNames();

        new JenkinsScanJob(scanner, getRepository()).run(allJobNames);
    }

    private JenkinsScanner getJenkinsScanner() {
        String jenkinsUrl = context.getEnvironment().getProperty("jenkins.url");
        String userName = context.getEnvironment().getProperty("jenkins.user");
        String password = context.getEnvironment().getProperty("jenkins.password");
        return new JenkinsScanner(jenkinsUrl, userName, password);
    }

    private JenkinsBuildRepository getRepository() {
        return context.getBean(JenkinsBuildRepository.class);
    }
}

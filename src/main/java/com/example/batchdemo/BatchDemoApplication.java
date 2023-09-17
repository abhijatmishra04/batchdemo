package com.example.batchdemo;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class BatchDemoApplication {

	public static void main(String[] args) {
		ApplicationContext ctx = SpringApplication.run(BatchDemoApplication.class, args);

		JobLauncher jobLauncher = ctx.getBean(JobLauncher.class);
		Job job = ctx.getBean(Job.class);

		try {
			jobLauncher.run(job, new JobParameters());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}


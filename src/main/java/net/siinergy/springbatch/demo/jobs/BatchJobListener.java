package net.siinergy.springbatch.demo.jobs;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.stereotype.Component;

@Component
public class BatchJobListener implements JobExecutionListener {

    private static final Logger LOGGER = LoggerFactory.getLogger(BatchJobListener.class);

    @Override
    public void beforeJob(JobExecution jobExecution) {
        LOGGER.info("Begining of job  {} at {}", jobExecution.getJobInstance().getJobName(), jobExecution.getStartTime());
    }

    @Override
    public void afterJob(JobExecution jobExecution) {
        String exitCode = jobExecution.getExitStatus().getExitCode();
        LOGGER.info("End of job  {} at {}  with status {}", jobExecution.getJobInstance().getJobName(), jobExecution.getEndTime(), exitCode);
    }

}

package net.siinergy.springbatch.demo.jobs.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepExecutionListener;
import org.springframework.stereotype.Component;

@Component
public class BatchStepListener implements StepExecutionListener {

    private static final Logger LOGGER = LoggerFactory.getLogger(BatchStepListener.class);

    @Override
    public void beforeStep(StepExecution stepExecution) {
        LOGGER.info("Begining of step  {} at {}", stepExecution.getStepName(), stepExecution.getStartTime());
    }

    @Override
    public ExitStatus afterStep(StepExecution stepExecution) {
        String exitCode = stepExecution.getExitStatus().getExitCode();
        LOGGER.info("End of step  {} at {} with status {}", stepExecution.getStepName(), stepExecution.getEndTime(), exitCode);
        return stepExecution.getExitStatus();
    }
}

package net.siinergy.springbatch.demo.cucumber;

import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.stereotype.Component;

@Component
public class World {

    public JobExecution jobExecution;

    public String inputFileName;
    public JobParametersBuilder jobParametter;
}

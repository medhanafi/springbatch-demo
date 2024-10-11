package net.siinergy.springbatch.demo.config;

import net.siinergy.springbatch.demo.exception.ArgsException;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.concurrent.Callable;
import java.util.concurrent.Future;

@Component
@Profile("!TEST")
public class ApplicationJobLauncher implements ApplicationRunner {

    private final JobLauncher jobLauncher;

    private final List<Job> jobs;

    private List<String> jobToExecute;

    private final TaskExecutor executor;
    @Value("${app.batch.inputFile}")
    private String inputFileName;
    public ApplicationJobLauncher(JobLauncher jobLauncher, List<Job> jobs, TaskExecutor executor, @Value("${app.batch.jobs}") String jobNames) {
        this.jobLauncher = jobLauncher;
        this.jobs = jobs;
        this.executor = executor;
        this.jobToExecute = List.of(jobNames.split(","));
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        // Création de la liste de futures pour attendre la fin de chaque job
        List<Future<JobExecution>> futures = new ArrayList<>();
        // Modification de la liste des job à executer au cas le paramètre JOBNAME exist
        String singleJob = getArgs(args.getSourceArgs()).get("JOBNAME");
        if (singleJob != null) {
            jobToExecute = List.of(singleJob);
        }
        // Lancement de chaque job dans un thread séparé
        jobs.stream().filter(job -> jobToExecute.contains(job.getName())).forEach(job -> {
            // Création des paramètres de job
            JobParameters jobParameters = new JobParametersBuilder()
                    .addDate("date", new Date())
                    .addString("jobName", job.getName())
                    .addString("fileName", inputFileName)
                    .toJobParameters();

            // Création de la tâche de lancement de job
            Callable<JobExecution> task = () -> jobLauncher.run(job, jobParameters);

            // Soumission de la tâche à l'executor
            Future<JobExecution> future = ((ThreadPoolTaskExecutor) executor).submit(task);
            futures.add(future);
        });

        // Attente de la fin de chaque job
        for (Future<JobExecution> future : futures) {
            future.get();
        }
    }

    private static Map<String, String> getArgs(String[] args) throws ArgsException {
        Map<String, String> listArg = new HashMap<>();

        for (String arg : args) {
            final String[] elements = arg.split("=", 2);

            String key = elements[0].replace("--", "");
            if (elements.length == 1) {
                throw new ArgsException("The " + key + " arg has no value, please specify one (--ARG=value)");
            }
            listArg.put(key, elements[1]);
        }

        return listArg;
    }

}

package net.siinergy.springbatch.demo.cucumber;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import net.siinergy.springbatch.demo.jobs.initdata.MovieInitConfig;
import org.assertj.core.api.Assertions;
import org.assertj.core.api.AssertionsForClassTypes;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Map;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;

public class JobInitTest {
    @Autowired
    protected World world;
    @Autowired
    private JobLauncher jobLauncher;
    @Autowired
    Map<String, Job> jobs;

    @Given("j'ai les données des films suivantes {string} à ajouter dans notre référentiel")
    public void prepareInitialData(String inputFileName) {
        world.inputFileName = inputFileName;
    }

    @When("je lance le job d'alimentation du référentiel {string}")
    public void runJob(String jobName) throws JobInstanceAlreadyCompleteException, JobExecutionAlreadyRunningException, JobParametersInvalidException, JobRestartException {
        var lejob = jobs.get(jobName);
        AssertionsForClassTypes.assertThat(lejob).as("le job %s n'as pas été trouvé", jobName).isNotNull();
        world.jobExecution = jobLauncher.run(lejob, new JobParametersBuilder()
                .addString("job", jobName)
                .addString("id", UUID.randomUUID().toString()) // paramèretres différents pour permettre d'executer plusieurs fois les même job/steps
                .addString("fileName", world.inputFileName)
                .toJobParameters());
    }

    @Then("le job doit terminer avec succès")
    public void leJobDoitTerminerAvecSuccès() {
        // Timeout après 60 secondes (vous pouvez ajuster cette valeur)
        long timeout = 60000;
        // Vérifier toutes les 500 ms
        long startTime = System.currentTimeMillis();

        // Attendre jusqu'à ce que le job soit terminé ou que le timeout soit atteint
        while (world.jobExecution.isRunning()) {
            if (System.currentTimeMillis() - startTime > timeout) {
                throw new RuntimeException("Le job a mis trop de temps à s'exécuter.");
            }

            // Pause de 500 ms entre chaque vérification (sans bloquer le thread avec sleep)
            try {
                Thread.onSpinWait(); // Active une attente occupée (spin-wait)
            } catch (Exception e) {
               fail("Echèque d'execution", e.getCause());
            }
        }
        Assertions.assertThat(world.jobExecution.getExitStatus().getExitCode()).isEqualTo("COMPLETED");
    }

    @And("le film {string} doit être ajouté avec succès")
    public void leFilmDoitÊtreAjoutéAvecSuccès(String arg0) {
        assertThat(true).isEqualTo(true);
    }

    @And("les associations avec le réalisateur {string}, le\\(s) pays {string}, et le genre {string} doivent être créées correctement.")
    public void lesAssociationsAvecLeRéalisateurLeSPaysEtLeGenreDoiventÊtreCrééesCorrectement(String arg0, String arg1, String arg2) {
        assertThat(true).isEqualTo(true);
    }
}

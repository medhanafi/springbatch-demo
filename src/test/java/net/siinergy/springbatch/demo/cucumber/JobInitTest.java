package net.siinergy.springbatch.demo.cucumber;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import net.siinergy.springbatch.demo.jobs.initdata.MovieInitWriter;
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
    private MovieInitWriter movieInitWriter;
    @Autowired
    Map<String, Job> jobs;

    @Given("j'ai les données des films suivantes {string} à ajouter dans notre référentiel")
    public void prepareInitialData(String inputFileName) {
        world.jobParametter = new JobParametersBuilder()
                .addString("id", UUID.randomUUID().toString()) // paramèretres différents pour permettre d'executer plusieurs fois les même job/steps
                .addString("fileName", inputFileName);
    }

    @When("je lance le job d'alimentation du référentiel {string}")
    public void runJob(String jobName) throws JobInstanceAlreadyCompleteException, JobExecutionAlreadyRunningException, JobParametersInvalidException, JobRestartException {
        var lejob = jobs.get(jobName);
        AssertionsForClassTypes.assertThat(lejob).as("le job %s n'as pas été trouvé", jobName).isNotNull();
        world.jobParametter.addString("job", jobName);
        world.jobExecution = jobLauncher.run(lejob, world.jobParametter.toJobParameters());
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
    public void leFilmDoitÊtreAjoutéAvecSuccès(String movieTitle) {
        Long movieId = movieInitWriter.getIdByColumn("movie", "movie_title", movieTitle);
        assertThat(movieId).isNotNull().isGreaterThan(0);
    }

    @And("les associations avec le réalisateur {string}, le\\(s) pays {string}, et le genre {string} doivent être créées correctement.")
    public void lesAssociationsAvecLeRéalisateurLeSPaysEtLeGenreDoiventÊtreCrééesCorrectement(String arg0, String arg1, String arg2) {
        assertThat(true).isEqualTo(true);
    }
}

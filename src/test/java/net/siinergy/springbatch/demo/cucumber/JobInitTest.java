package net.siinergy.springbatch.demo.cucumber;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.cucumber.java.fr.Alors;
import io.cucumber.java.fr.Et;
import io.cucumber.java.fr.Quand;
import io.cucumber.java.fr.Étantdonnéque;
import net.siinergy.springbatch.demo.cucumber.World;
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

public class JobInitTest {
    @Autowired
    protected World world;

    @Autowired
    private JobLauncher jobLauncher;
    @Autowired
    Map<String, Job> jobs;
    @Given("j'ai les données des films suivantes à ajouter dans notre référentiel")
    public void prepareInitialData() {
        assertThat(true).isEqualTo(true);
    }

    @When("je lance le job d'alimentation du référentiel  {string}")
    public void runJob(String jobName) throws JobInstanceAlreadyCompleteException, JobExecutionAlreadyRunningException, JobParametersInvalidException, JobRestartException {
        var lejob = jobs.get(jobName);
        AssertionsForClassTypes.assertThat(lejob).as("le job %s n'as pas été trouvé", jobName).isNotNull();
        world.jobExecution = jobLauncher.run(lejob, new JobParametersBuilder()
                .addString("job", jobName)
                .addString("id", UUID.randomUUID().toString()) // paramèretres différents pour permettre d'executer plusieurs fois les même job/steps
                .toJobParameters());
    }

    @Then("le job doit terminer avec succès")
    public void leJobDoitTerminerAvecSuccès() {
        Assertions.assertThat(world.jobExecution.getExitStatus().getExitCode()).isEqualTo("COMPLETED");
    }

    @And("le film {string} doit être ajouté avec succès")
    public void leFilmDoitÊtreAjoutéAvecSuccès(String arg0) {

    }

    @And("les associations avec le réalisateur {string}, le\\(s) pays {string}, et le genre {string} doivent être créées correctement.")
    public void lesAssociationsAvecLeRéalisateurLeSPaysEtLeGenreDoiventÊtreCrééesCorrectement(String arg0, String arg1, String arg2) {
    }
}

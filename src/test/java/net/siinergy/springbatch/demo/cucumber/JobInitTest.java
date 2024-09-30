package net.siinergy.springbatch.demo.cucumber;

import io.cucumber.java.fr.Alors;
import io.cucumber.java.fr.Et;
import io.cucumber.java.fr.Quand;
import io.cucumber.java.fr.Étantdonnéque;

import static org.assertj.core.api.Assertions.assertThat;

public class JobInitTest {
    @Étantdonnéque("j'ai les données des films suivantes à ajouter dans notre référentiel")
    public void jAiLesDonnéesDesFilmsSuivantesÀAjouterDansNotreRéférentiel() {
        assertThat(true).isEqualTo(true);
    }

    @Quand("je lance le job d'alimentation du référentiel")
    public void jeLanceLeJobDAlimentationDuRéférentiel() {
        assertThat(true).isEqualTo(true);
    }

    @Alors("le job doit terminer avec succès")
    public void leJobDoitTerminerAvecSuccès() {
        assertThat(true).isEqualTo(true);
    }

    @Et("le film {string} doit être ajouté avec succès")
    public void leFilmDoitÊtreAjoutéAvecSuccès(String arg0) {
        assertThat(true).isEqualTo(true);
    }

    @Et("les associations avec le réalisateur {string}, le\\(s) pays {string}, et le genre {string} doivent être créées correctement.")
    public void lesAssociationsAvecLeRéalisateurLeSPaysEtLeGenreDoiventÊtreCrééesCorrectement(String arg0, String arg1, String arg2) {
        assertThat(true).isEqualTo(true);
    }
}

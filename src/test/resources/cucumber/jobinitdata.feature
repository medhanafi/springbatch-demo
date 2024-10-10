
Feature: Test du chargement des films dans la base

  Scenario:  Chargement réussi d’un film avec des informations complètes
    Given j'ai les données des films suivantes à ajouter dans notre référentiel
      | Titre     | Année de sortie | Genre           | Durée | Pays       | Réalisateur       | Note |
      | Inception | 2010            | Science Fiction | 148   | USA, Canada | Christopher Nolan | 8.8  |
    When je lance le job d'alimentation du référentiel "<jobMovie>"
    Then le job doit terminer avec succès
    And le film "Inception" doit être ajouté avec succès
    And les associations avec le réalisateur "Christopher Nolan", le(s) pays "USA, Canada", et le genre "Science Fiction" doivent être créées correctement.
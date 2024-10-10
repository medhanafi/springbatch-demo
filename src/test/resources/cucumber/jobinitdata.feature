
Feature: Test du chargement des films dans la base

  Scenario:  Chargement réussi d’un film avec des informations complètes
    Given j'ai les données des films suivantes "movie.csv" à ajouter dans notre référentiel
    When je lance le job d'alimentation du référentiel "jobMovie"
    Then le job doit terminer avec succès
    And le film "Inception" doit être ajouté avec succès
    And les associations avec le réalisateur "Christopher Nolan", le(s) pays "USA, Canada", et le genre "Science Fiction" doivent être créées correctement.
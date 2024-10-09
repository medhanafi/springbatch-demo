#language: fr

  #Mettre l'ID de l'US ici
Fonctionnalité: Test du chargement des films dans la base

  Scénario : Chargement réussi d’un film avec des informations complètes
    Étant donné que j'ai les données des films suivantes à ajouter dans notre référentiel
      | Titre     | Année de sortie | Genre           | Durée | Pays       | Réalisateur       | Note |
      | Inception | 2010            | Science Fiction | 148   | USA, Canada | Christopher Nolan | 8.8  |
    Quand je lance le job d'alimentation du référentiel "<jobMovie>"
    Alors le job doit terminer avec succès
    Et le film "Inception" doit être ajouté avec succès
    Et les associations avec le réalisateur "Christopher Nolan", le(s) pays "USA, Canada", et le genre "Science Fiction" doivent être créées correctement.
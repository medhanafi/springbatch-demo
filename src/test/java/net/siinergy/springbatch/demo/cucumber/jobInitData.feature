#language: fr

  #US-GEDFACTO_14582
Fonctionnalité: Test du chargement des films dans la base

  Scénario : Chargement réussi d’un film avec des informations complètes
    Étant donné que j'ai les données des films suivantes à ajouter dans notre référentiel :
      | Titre     | Année de sortie | Genre           | Durée | Pays       | Réalisateur       | Note |
      | Inception | 2010            | Science Fiction | 148   | USA, Canada | Christopher Nolan | 8.8  |
    Quand je lance le job d'alimentation du référentiel
    Alors le job doit terminer avec succès
    Et le film "Inception" doit être ajouté avec succès
    Et les associations avec le réalisateur "Christopher Nolan", le(s) pays "USA, Canada", et le genre "Science Fiction" doivent être créées correctement.

  Scénario : Chargement échoué en raison d’un réalisateur inexistant
    Étant donné que j'ai les données des films suivantes à ajouter dans notre référentiel :
      | Titre  | Année de sortie | Genre | Durée | Pays   | Réalisateur | Note |
      | Amélie | 2001            | Drame | 122   | France | Inconnu     | 8.3  |
    Quand je lance le job d'alimentation du référentiel
    Alors le job doit échouer
    Et un message d’erreur "Réalisateur inexistant" doit être affiché
    Et le film "Amélie" ne doit pas être ajouté.

  Scénario : Chargement échoué en raison d’un titre manquant
    Étant donné que j'ai les données des films suivantes à ajouter dans notre référentiel :
      | Titre | Année de sortie | Genre  | Durée | Pays | Réalisateur       | Note |
      |       | 1994            | Action | 154   | USA  | Quentin Tarantino | 8.9  |
    Quand je lance le job d'alimentation du référentiel
    Alors le job doit échouer
    Et un message d’erreur "Le titre est obligatoire" doit être affiché
    Et le film ne doit pas être ajouté.

  Scénario : Chargement échoué en raison d’une année de sortie future
    Étant donné que j'ai les données des films suivantes à ajouter dans notre référentiel :
      | Titre          | Année de sortie | Genre    | Durée | Pays | Réalisateur    | Note |
      | Shutter Island | 2100            | Thriller | 180   | UK   | Martin Scorsese | 9.2  |
    Quand je lance le job d'alimentation du référentiel
    Alors le job doit échouer
    Et un message d’erreur "Année de sortie invalide" doit être affiché
    Et le film "Shutter Island" ne doit pas être ajouté.

  Scénario : Chargement réussi d’un film sans pays associé
    Étant donné que j'ai les données des films suivantes à ajouter dans notre référentiel :
      | Titre          | Année de sortie | Genre     | Durée | Pays | Réalisateur     | Note |
      | Indiana Jones  | 1981            | Aventure  | 115   |      | Steven Spielberg | 8.5  |
    Quand je lance le job d'alimentation du référentiel
    Alors le job doit terminer avec succès
    Et le film "Indiana Jones" doit être ajouté avec succès
    Et aucune association avec un pays ne doit être créée.
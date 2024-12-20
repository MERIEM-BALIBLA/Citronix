# Citrons
## Présentation
Citronix est une application de gestion destinée aux fermiers, permettant de suivre la production, la récolte et la vente de citrons dans une ferme. L'objectif principal est de faciliter la gestion des fermes, des champs, des arbres, des récoltes et des ventes, tout en optimisant le suivi de la productivité des arbres en fonction de leur âge. Le système permet de calculer l'âge des arbres et leur productivité selon trois catégories : jeunes, matures et vieux arbres.

## Fonctionnalités Principales
### 1. Gestion des Fermes
Créer, modifier et consulter les informations d'une ferme (nom, localisation, superficie, date de création).
Assigner des champs à une ferme avec des informations de superficie.
### 2. Gestion des Champs
Associer des champs à une ferme existante.
Gérer la superficie et les attributs associés à chaque champ.
### 3. Gestion des Arbres
Créer, modifier et consulter les informations d'un arbre (date de plantation, âge, champ d'appartenance).
Calculer l'âge des arbres et leur productivité selon leur âge :
Jeunes (moins de 5 ans) : 2,5 kg de citrons par arbre.
Matures (5 à 15 ans) : 12 kg de citrons par arbre.
Vieux (plus de 15 ans) : 20 kg de citrons par arbre.
### 4. Gestion des Récoltes
Suivre les récoltes par saison (hiver, printemps, été, automne).
Enregistrer la date de récolte et la quantité récoltée pour chaque arbre.
### 5. Détail des Récoltes
Suivre la quantité récoltée par arbre pour chaque récolte.
Associer chaque récolte à un arbre spécifique.
### 6. Gestion des Ventes
Enregistrer les ventes avec la date, le prix unitaire, le client et la récolte associée.
Calculer le revenu de chaque vente en fonction de la quantité récoltée et du prix unitaire.
## Technologies Utilisées
Java 8 : Utilisation de Java 8 pour sa richesse en fonctionnalités (Stream API, LocalDate, etc.).

PostgreSQL : Système de gestion de base de données relationnelle pour stocker les informations relatives aux fermes, champs, arbres, récoltes et ventes.

Spring Framework (optionnel) : Pour une gestion plus avancée de l'application (injection de dépendances, gestion de transactions, etc.).

Postman : Outil pour tester les API REST et effectuer des appels HTTP vers l'application backend. Il facilite la vérification des endpoints et des réponses, permettant ainsi de tester l'intégration des services de l'application.

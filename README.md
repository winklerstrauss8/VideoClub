# VideoClub

## Description

VideoClub est une application de bureau développée en Java pour la gestion complète d'un vidéoclub. Elle permet aux utilisateurs de gérer les abonnés, les cassettes, les catégories de films, les titres, et les locations de manière intuitive via une interface graphique Swing. L'application utilise une base de données pour stocker les informations persistantes et offre des fonctionnalités CRUD (Créer, Lire, Mettre à jour, Supprimer) pour chaque entité.

## Fonctionnalités principales

- **Gestion des abonnés** : Ajouter, modifier, supprimer et consulter les informations des abonnés (nom, prénom, adresse, etc.).
- **Gestion des cassettes** : Gérer l'inventaire des cassettes, y compris leur état et disponibilité.
- **Gestion des catégories** : Organiser les films par catégories (action, comédie, drame, etc.).
- **Gestion des titres** : Maintenir une base de données des titres de films disponibles.
- **Gestion des locations** : Enregistrer les emprunts et retours de cassettes, avec suivi des dates.
- **Interface utilisateur** : Interface graphique conviviale avec des panels dédiés pour chaque section (Accueil, Abonnés, Cassettes, etc.).
- **Authentification** : Système de connexion pour sécuriser l'accès à l'application.

## Prérequis

- Java Development Kit (JDK) 8 ou supérieur installé sur le système.
- Une base de données compatible (par exemple, MySQL ou SQLite) pour le stockage des données. Le schéma de base de données est fourni dans `sql/schema.sql`.

## Installation

1. **Cloner ou télécharger le projet** :
   - Placez le dossier du projet dans un répertoire de votre choix.

2. **Configurer la base de données** :
   - Exécutez le script SQL `sql/schema.sql` dans votre base de données pour créer les tables nécessaires.
   - Mettez à jour les informations de connexion à la base de données dans `src/dao/DatabaseConnection.java` si nécessaire.

3. **Compiler l'application** :
   - Ouvrez une invite de commandes dans le répertoire racine du projet.
   - Exécutez le script `compile.bat` pour compiler les fichiers sources Java.

4. **Exécuter l'application** :
   - Après compilation, exécutez `run.bat` pour lancer l'application.

## Utilisation

1. **Connexion** : Lancez l'application et connectez-vous avec les identifiants appropriés via le panneau de connexion.
2. **Navigation** : Utilisez les différents panels pour naviguer entre les sections :
   - **Accueil** : Vue d'ensemble et statistiques.
   - **Abonnés** : Gestion des membres du vidéoclub.
   - **Cassettes** : Gestion de l'inventaire.
   - **Catégories** : Définition des catégories de films.
   - **Titres** : Catalogue des films.
   - **Locations** : Gestion des emprunts et retours.
   - **Retour** : Traitement des retours de cassettes.
   - **Contact** : Informations de contact.
3. **Opérations CRUD** : Dans chaque panel, utilisez les boutons pour ajouter, modifier, supprimer ou rechercher des éléments.

## Structure du projet

```
VideoClub/
├── src/                    # Code source Java
│   ├── Main.java          # Point d'entrée de l'application
│   ├── dao/               # Couche d'accès aux données (DAO)
│   │   ├── DatabaseConnection.java
│   │   ├── AbonneDAO.java
│   │   ├── CassetteDAO.java
│   │   ├── CategorieDAO.java
│   │   ├── LocationDAO.java
│   │   └── TitreDAO.java
│   ├── model/             # Classes de modèle de données
│   │   ├── Abonne.java
│   │   ├── Cassette.java
│   │   ├── Categorie.java
│   │   ├── Location.java
│   │   └── Titre.java
│   └── ui/                # Interface utilisateur (Swing)
│       ├── MainFrame.java
│       ├── LoginFrame.java
│       ├── HomePanel.java
│       ├── AbonnePanel.java
│       ├── CassettePanel.java
│       ├── CategoriePanel.java
│       ├── TitrePanel.java
│       ├── LocationPanel.java
│       ├── RetourPanel.java
│       └── ContactPanel.java
├── bin/                   # Fichiers compilés (générés)
├── lib/                   # Bibliothèques externes (JAR)
├── sql/                   # Scripts SQL
│   └── schema.sql        # Schéma de la base de données
├── icons/                 # Icônes et ressources graphiques
├── compile.bat            # Script de compilation
├── run.bat                # Script d'exécution
└── README.md              # Ce fichier
```

## Technologies utilisées

- **Langage** : Java
- **Interface graphique** : Swing
- **Base de données** : JDBC pour la connexion et les requêtes SQL
- **Architecture** : Modèle-Vue-Contrôleur (MVC) implicite avec séparation DAO/Modèle/UI
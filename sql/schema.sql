-- =============================================================
-- Script de création de la base de données VideoClub
-- Club de location de cassettes vidéo
-- =============================================================

-- Suppression de la base si elle existe déjà (pour réinitialiser)
DROP DATABASE IF EXISTS videoclub;

-- Création de la base de données
CREATE DATABASE videoclub CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci;

-- Utilisation de la base
USE videoclub;

-- =============================================================
-- Table UTILISATEUR : pour l'authentification
-- =============================================================
CREATE TABLE utilisateur (
    id_utilisateur INT AUTO_INCREMENT PRIMARY KEY,
    nom_utilisateur VARCHAR(50) NOT NULL UNIQUE,
    mot_de_passe VARCHAR(255) NOT NULL,
    role VARCHAR(20) DEFAULT 'admin'
) ENGINE=InnoDB;

-- =============================================================
-- Table CATEGORIE : les catégories de films
-- Exemple : Action, Comédie, Horreur, etc.
-- =============================================================
CREATE TABLE categorie (
    id_categorie INT AUTO_INCREMENT PRIMARY KEY,
    libelle_categorie VARCHAR(100) NOT NULL
) ENGINE=InnoDB;

-- =============================================================
-- Table TITRE : les titres de films
-- Un titre appartient à une seule catégorie
-- =============================================================
CREATE TABLE titre (
    id_titre INT AUTO_INCREMENT PRIMARY KEY,
    nom_titre VARCHAR(200) NOT NULL,
    auteur VARCHAR(150),
    duree INT,                          -- durée en minutes
    id_categorie INT NOT NULL,
    FOREIGN KEY (id_categorie) REFERENCES categorie(id_categorie)
        ON DELETE RESTRICT ON UPDATE CASCADE
) ENGINE=InnoDB;

-- =============================================================
-- Table CASSETTE : les cassettes physiques
-- Plusieurs cassettes peuvent exister pour un même titre
-- =============================================================
CREATE TABLE cassette (
    num_cassette INT AUTO_INCREMENT PRIMARY KEY,
    date_achat DATE,
    prix DECIMAL(10, 2),
    id_titre INT NOT NULL,
    FOREIGN KEY (id_titre) REFERENCES titre(id_titre)
        ON DELETE RESTRICT ON UPDATE CASCADE
) ENGINE=InnoDB;

-- =============================================================
-- Table ABONNE : les abonnés du club
-- nb_location : nombre de cassettes actuellement louées (max 3)
-- =============================================================
CREATE TABLE abonne (
    num_abonne INT AUTO_INCREMENT PRIMARY KEY,
    nom_abonne VARCHAR(100) NOT NULL,
    adresse_abonne VARCHAR(255),
    date_abonnement DATE,
    date_entree DATE,
    nb_location INT DEFAULT 0          -- nombre de locations en cours
) ENGINE=InnoDB;

-- =============================================================
-- Table LOCATION : enregistre les locations en cours
-- Clé primaire composite (num_abonne, num_cassette)
-- On ne garde que la dernière date de location
-- =============================================================
CREATE TABLE location_cassette (
    num_abonne INT NOT NULL,
    num_cassette INT NOT NULL,
    date_location DATE NOT NULL,
    PRIMARY KEY (num_abonne, num_cassette),
    FOREIGN KEY (num_abonne) REFERENCES abonne(num_abonne)
        ON DELETE CASCADE ON UPDATE CASCADE,
    FOREIGN KEY (num_cassette) REFERENCES cassette(num_cassette)
        ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB;

-- =============================================================
-- Insertion des données de test
-- =============================================================

-- Utilisateur admin par défaut
INSERT INTO utilisateur (nom_utilisateur, mot_de_passe, role)
VALUES ('admin', 'admin123', 'admin');

-- Catégories
INSERT INTO categorie (libelle_categorie) VALUES
('Action'),
('Comédie'),
('Drame'),
('Horreur'),
('Science-Fiction'),
('Aventure'),
('Romance'),
('Documentaire');

-- Titres de films
INSERT INTO titre (nom_titre, auteur, duree, id_categorie) VALUES
('Terminator 2',            'James Cameron',      137, 1),
('Die Hard',                'John McTiernan',     132, 1),
('Le Dîner de Cons',        'Francis Veber',       80, 2),
('Les Visiteurs',           'Jean-Marie Poiré',    107, 2),
('Le Parrain',              'Francis Ford Coppola', 175, 3),
('Shining',                 'Stanley Kubrick',     146, 4),
('Alien',                   'Ridley Scott',        117, 5),
('Blade Runner',            'Ridley Scott',        117, 5),
('Indiana Jones',           'Steven Spielberg',    115, 6),
('Titanic',                 'James Cameron',       195, 7),
('La Planète Bleue',        'Alastair Fothergill',  90, 8);

-- Cassettes (plusieurs exemplaires pour certains titres)
INSERT INTO cassette (date_achat, prix, id_titre) VALUES
('2024-01-15', 15.00, 1),   -- Terminator 2, exemplaire 1
('2024-01-15', 15.00, 1),   -- Terminator 2, exemplaire 2
('2024-02-10', 12.50, 2),   -- Die Hard
('2024-02-20', 10.00, 3),   -- Le Dîner de Cons
('2024-03-01', 11.00, 4),   -- Les Visiteurs
('2024-03-05', 18.00, 5),   -- Le Parrain
('2024-03-10', 14.00, 6),   -- Shining
('2024-04-01', 13.50, 7),   -- Alien
('2024-04-15', 13.50, 8),   -- Blade Runner
('2024-05-01', 12.00, 9),   -- Indiana Jones
('2024-05-10', 16.00, 10),  -- Titanic
('2024-06-01',  9.50, 11);  -- La Planète Bleue

-- Abonnés
INSERT INTO abonne (nom_abonne, adresse_abonne, date_abonnement, date_entree, nb_location) VALUES
('Dupont Jean',     '12 Rue de Paris, 75001 Paris',       '2024-01-10', '2024-01-10', 0),
('Martin Sophie',   '45 Avenue des Champs, 69000 Lyon',   '2024-02-15', '2024-02-15', 0),
('Durand Pierre',   '8 Boulevard Victor Hugo, 13000 Marseille', '2024-03-20', '2024-03-20', 0),
('Leroy Marie',     '23 Rue de la Liberté, 31000 Toulouse','2024-04-05', '2024-04-05', 0),
('Bernard Lucas',   '67 Rue Pasteur, 33000 Bordeaux',     '2024-05-12', '2024-05-12', 0);

-- Quelques locations en cours
INSERT INTO location_cassette (num_abonne, num_cassette, date_location) VALUES
(1, 1, '2025-03-01'),   -- Dupont loue Terminator 2
(1, 3, '2025-03-05'),   -- Dupont loue Die Hard
(2, 6, '2025-03-02');   -- Martin loue Le Parrain

-- Mise à jour du nombre de locations
UPDATE abonne SET nb_location = 2 WHERE num_abonne = 1;
UPDATE abonne SET nb_location = 1 WHERE num_abonne = 2;

-- Fin du script

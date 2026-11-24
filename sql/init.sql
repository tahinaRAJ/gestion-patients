-- A executer une fois :  psql -U postgres -f sql/init.sql
-- Les donnees de test (medecins / patients avec mots de passe haches) sont creees
-- par l'application au premier demarrage (gestionpatients.config.DonneesInitiales).
--
-- Nommage en camelCase. PostgreSQL met en minuscules les identifiants non quotes
-- (dateNaissance -> datenaissance), Hibernate fait de meme : les deux restent coherents.

CREATE DATABASE "gestionPatients";

\c "gestionPatients"

CREATE TABLE IF NOT EXISTS role (
    id      SERIAL PRIMARY KEY,
    libelle VARCHAR(20) NOT NULL UNIQUE
);

CREATE TABLE IF NOT EXISTS sexe (
    id      SERIAL PRIMARY KEY,
    libelle VARCHAR(20) NOT NULL UNIQUE
);

CREATE TABLE IF NOT EXISTS personne (
    id               BIGSERIAL PRIMARY KEY,
    nom              VARCHAR(100) NOT NULL,
    prenom           VARCHAR(150),
    dateNaissance    DATE,
    lieuNaissance    VARCHAR(100),
    idSexe           INTEGER      REFERENCES sexe (id),
    adresse          VARCHAR(255),
    telephone        VARCHAR(30),
    email            VARCHAR(150) NOT NULL UNIQUE,
    motDePasse       VARCHAR(100) NOT NULL,
    idRole           INTEGER      NOT NULL REFERENCES role (id),
    dateModification TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP
);

INSERT INTO role (libelle) VALUES ('PATIENT'), ('MEDECIN') ON CONFLICT (libelle) DO NOTHING;
INSERT INTO sexe (libelle) VALUES ('Masculin'), ('Feminin') ON CONFLICT (libelle) DO NOTHING;

-- À exécuter une fois :  psql -U postgres -f sql/init.sql
-- Les données de test (médecins / patients avec mots de passe hachés) sont créées
-- par l'application au premier démarrage (gestionpatients.config.DonneesInitiales).

CREATE DATABASE gestion_patients;

\c gestion_patients

CREATE TABLE IF NOT EXISTS personne (
    id              BIGSERIAL PRIMARY KEY,
    nom             VARCHAR(100) NOT NULL,
    prenom          VARCHAR(150),
    date_naissance  DATE,
    lieu_naissance  VARCHAR(100),
    sexe            VARCHAR(1)   CHECK (sexe IN ('M', 'F')),
    adresse         VARCHAR(255),
    telephone       VARCHAR(30),
    email           VARCHAR(150) NOT NULL UNIQUE,
    mot_de_passe    VARCHAR(100) NOT NULL,
    role            VARCHAR(20)  NOT NULL CHECK (role IN ('PATIENT', 'MEDECIN'))
);

CREATE DATABASE crud;
USE crud;

CREATE TABLE Evenements (
    id INT AUTO_INCREMENT PRIMARY KEY,
    Titre VARCHAR(250) NOT NULL,
    Localisation VARCHAR(250) NOT NULL,
    nb_participant INT,
    Date DATE NOT NULL,
    Image VARCHAR(250) NOT NULL
);

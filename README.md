# PROJET UA3 – Gestion des résultats d'étudiants

##  Membres du projet
- AGNES
- BERTHOLD

---

##  Description du projet
Cette application Java permet de lire une liste d’étudiants depuis un fichier CSV, calculer leurs moyennes, générer un classement et exporter les résultats dans des fichiers de sortie.

---

##  Fonctionnalités développées

- Lecture des étudiants depuis un fichier CSV
- Calcul de la moyenne pour chaque étudiant
- Tri des étudiants par moyenne décroissante
- Affichage du classement dans la console
- Export du classement dans un fichier CSV
- Génération de statistiques (min, max, moyenne générale)
- Gestion des erreurs (fichier invalide, données incorrectes, etc.)

---

##  Instructions d'exécution

### 1. Compiler le projet

Depuis le dossier `src` :

```bash
javac *.java

### 2. execution du projet
java Main

### 3. fichier d'entrée

Le fichier etudiants.csv doit être placé dans le dossier data/.

Nom;Prenom;Note1;Note2;Note3
Dupont;Jean;15;14;13
Martin;Sophie;18;17;19

## 4. Instructions d'exécution

Après exécution, le programme génère :

- output/resultats.csv : classement des étudiants
- output/statistiques.txt : statistiques globales

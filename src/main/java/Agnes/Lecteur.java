package Agnes;
import Agnes.Etudiant;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;


public class Lecteur implements ILecteurEtudiants {

    private static final String SEPARATEUR = ",";

    @Override
    public List<Etudiant> lire(String source) throws IOException, csvInvalide {
//créer une liste étudiant
        List<Etudiant> etudiants = new ArrayList<>();

        File fichier = new File(source);
        //vérifier si le fichier existe vraiment sur l'ordinateur
        if (!fichier.exists())
            throw new FileNotFoundException("Fichier introuvable : " + source);
//Une fois que nous savons que le fichier existe, nous passons à l'étape de lecture technique
        try (BufferedReader reader = new BufferedReader(//lire le fichier ligne par ligne
                new InputStreamReader// se charge de convertir les données en texte
                        (new FileInputStream(fichier)//récupére les données brute
                        , StandardCharsets.UTF_8))) {//gérer les accents

            // Lecture de l'en-tête
            String ligneEntete = reader.readLine(); //lire la premiere ligne
            if (ligneEntete == null || ligneEntete.isBlank()) // si la ligne est vide ou elle contient des espaces
                throw new csvInvalide("Le fichier CSV est vide ou sans en-têtes."); //déclenche l'alarme csvInvalide

            ligneEntete = ligneEntete.replace("\uFEFF", "").trim();//pour supprimer le marqueur invisible (le BOM)
            // que certains logiciels comme Excel insèrent au début des fichiers
            //trim : pour éliminer les éspaces
            String[] entetes = ligneEntete.split(SEPARATEUR);// transformer notre ligne de texte en un tableau

            if (entetes.length < 3)// parce que le programme a besoin du nom,prenom,matiére
                throw new csvInvalide(
                        "Format invalide : il faut au moins Nom, Prenom et une matière.");

            // Les 2 premières colonnes sont Nom et Prenom, le reste sont des matières
            String[] matieres = new String[entetes.length - 2];//on a creer un tableau matieres pour séparer nom, prenom et les notes
            for (int i = 2; i < entetes.length; i++)
                matieres[i - 2] = entetes[i].trim();

            // Lecture des lignes de données
            String ligne;
            int numeroLigne = 1;// on commence a 1 car on a deja l'en tete'

            while ((ligne = reader.readLine()) != null) {
                numeroLigne++;
                ligne = ligne.trim();
                if (ligne.isEmpty()) continue;

                String[] colonnes = ligne.split(SEPARATEUR, -1);//decouper la ligne
                if (colonnes.length < entetes.length) {//on vérifie si la ligne contient assez de colonnes S'il manque pas des informations
                    System.err.printf("ATTENTION: Ligne %d ignorée : colonnes insuffisantes.%n", numeroLigne);
                    continue;
                }

                String nom    = colonnes[0].trim();// on récupere le nom dans la premiere colonne
                String prenom = colonnes[1].trim();
                if (nom.isEmpty() || prenom.isEmpty()) {
                    System.err.printf("ATTENTION: Ligne %d ignorée : nom ou prénom vide.%n", numeroLigne);
                    continue;
                }

                Etudiant etudiant = new Etudiant(nom, prenom);

                // Lire les notes pour chaque matière
                for (int i = 0; i < matieres.length; i++) {
                    String valeur = colonnes[i + 2].trim();
                    try {
                        double note = Double.parseDouble(valeur); //convertir le texte en nombre
                        if (note < 0 || note > 20)
                            System.err.printf("ATTENTION: Ligne %d, %s : note %.1f hors [0-20].%n",
                                    numeroLigne, matieres[i], note);
                        etudiant.addNote(matieres[i], note);
                    } catch (NumberFormatException e) {// gérer le probleme par faire afficher un avertissemnt
                        System.err.printf("ATTENTION: Ligne %d, %s : '%s' non numérique, ignoré.%n",
                                numeroLigne, matieres[i], valeur);
                    }
                }

                etudiants.add(etudiant);
            }
        }

        System.out.printf("Les %d étudiant(s) chargé(s) depuis '%s'.%n", etudiants.size(), source);
        return etudiants;
    }
}

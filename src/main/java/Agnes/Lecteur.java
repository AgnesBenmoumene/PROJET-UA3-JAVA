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

        List<Etudiant> etudiants = new ArrayList<>();

        File fichier = new File(source);
        if (!fichier.exists())
            throw new FileNotFoundException("Fichier introuvable : " + source);

        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(new FileInputStream(fichier), StandardCharsets.UTF_8))) {

            // ── Lecture de l'en-tête ──
            String ligneEntete = reader.readLine();
            if (ligneEntete == null || ligneEntete.isBlank())
                throw new csvInvalide("Le fichier CSV est vide ou sans en-têtes.");

            ligneEntete = ligneEntete.replace("\uFEFF", "").trim();
            String[] entetes = ligneEntete.split(SEPARATEUR);

            if (entetes.length < 3)
                throw new csvInvalide(
                        "Format invalide : il faut au moins Nom, Prenom et une matière.");

            // Les 2 premières colonnes sont Nom et Prenom, le reste sont des matières
            String[] matieres = new String[entetes.length - 2];
            for (int i = 2; i < entetes.length; i++)
                matieres[i - 2] = entetes[i].trim();

            // ── Lecture des lignes de données ──
            String ligne;
            int numeroLigne = 1;

            while ((ligne = reader.readLine()) != null) {
                numeroLigne++;
                ligne = ligne.trim();
                if (ligne.isEmpty()) continue;

                String[] colonnes = ligne.split(SEPARATEUR, -1);
                if (colonnes.length < entetes.length) {
                    System.err.printf("ATTENTION: Ligne %d ignorée : colonnes insuffisantes.%n", numeroLigne);
                    continue;
                }

                String nom    = colonnes[0].trim();
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
                        double note = Double.parseDouble(valeur);
                        if (note < 0 || note > 20)
                            System.err.printf("ATTENTION: Ligne %d, %s : note %.1f hors [0-20].%n",
                                    numeroLigne, matieres[i], note);
                        etudiant.addNote(matieres[i], note);
                    } catch (NumberFormatException e) {
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

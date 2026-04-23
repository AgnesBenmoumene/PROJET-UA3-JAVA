package example;
import Agnes.csvInvalide;
import Agnes.Lecteur;
import Berthold.Affichage;
import Berthold.Gestionnaire;
import Berthold.Resultats;
import Berthold.Statistiques;

import java.io.FileNotFoundException;
import java.io.IOException;



public class Main {

    private static final String FICHIER_ENTREE = "data/etudiants.csv";
    private static final String FICHIER_CSV    = "output/resultats.csv";
    private static final String FICHIER_STATS  = "output/statistiques.txt";

    public static void main(String[] args) {

        Gestionnaire gestion = new Gestionnaire(
                new Lecteur(),
                new Resultats(),
                new Statistiques(),
                new Affichage()
        );

        new Affichage().afficherBanniere();

        try {
            gestion.afficherEtape(1, "Chargement des données : " + FICHIER_ENTREE);
            gestion.chargerDonnees(FICHIER_ENTREE);

            gestion.afficherEtape(2, "Tri par moyenne décroissante");
            gestion.trierParMoyenne();

            gestion.afficherEtape(3, "Affichage du classement");
            gestion.afficherClassement();

            gestion.afficherEtape(4, "Export CSV : " + FICHIER_CSV);
            gestion.exporterCSV(FICHIER_CSV);

            gestion.afficherEtape(5, "Export rapport : " + FICHIER_STATS);
            gestion.exporterRapport(FICHIER_STATS);

            System.out.println("\n Programme terminé avec succès !");
            System.out.println("   → " + FICHIER_CSV);
            System.out.println("   → " + FICHIER_STATS);

        } catch (FileNotFoundException e) {
            System.err.println("\n ERREUR : Fichier introuvable — " + e.getMessage());

        } catch (csvInvalide e) {
            System.err.println("\n ERREUR : Format CSV invalide — " + e.getMessage());

        } catch (IllegalStateException e) {
            System.err.println("\n ERREUR : Données insuffisantes — " + e.getMessage());

        } catch (IOException e) {
            System.err.println("\n ERREUR I/O : " + e.getMessage());

        } catch (Exception e) {
            System.err.println("\n ERREUR INATTENDUE : " + e.getClass().getSimpleName());
            e.printStackTrace();
        }
    }
}

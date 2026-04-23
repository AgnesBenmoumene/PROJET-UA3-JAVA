package Berthold;
import Agnes.Etudiant;
import Agnes.IExporteurResultats;
import Agnes.ILecteurEtudiants;
import Agnes.IAfficheur;
import Agnes.csvInvalide;


import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;


public class Gestionnaire {

    // ── Dépendances injectées (toujours des interfaces, jamais des classes concrètes) ──
    private final ILecteurEtudiants   lecteur;
    private final IExporteurResultats exporteurCSV;
    private final IExporteurResultats exporteurRapport;
    private final IAfficheur          afficheur;

    private List<Etudiant> etudiants;

    // ── Constructeur avec injection de dépendances (DIP — cours SOLID p.13) ──

    public Gestionnaire(ILecteurEtudiants lecteur,
                        IExporteurResultats exporteurCSV,
                        IExporteurResultats exporteurRapport,
                        IAfficheur afficheur) {
        this.lecteur          = lecteur;
        this.exporteurCSV     = exporteurCSV;
        this.exporteurRapport = exporteurRapport;
        this.afficheur        = afficheur;
        this.etudiants        = new ArrayList<>();
    }

    // ── Méthodes de service ───────────────────────────────────────────────────

    public void chargerDonnees(String source) throws IOException, csvInvalide {
        this.etudiants = lecteur.lire(source);
        if (etudiants == null || etudiants.isEmpty())
            throw new IllegalStateException("Aucun étudiant chargé. Vérifiez : " + source);
    }


     // Trie la liste par moyenne décroissante.
     // En cas d'égalité : ordre alphabétique par nom.

    public void trierParMoyenne() {
        Collections.sort(etudiants,
                Comparator.comparingDouble(Etudiant::calculerMoyenne)
                        .reversed()
                        .thenComparing(Etudiant::getNom));
    }

    public void afficherClassement() {
        afficheur.afficherClassement(etudiants);
    }

    public void exporterCSV(String destination) throws IOException {
        exporteurCSV.exporter(etudiants, destination);
    }

    public void exporterRapport(String destination) throws IOException {
        exporteurRapport.exporter(etudiants, destination);
    }

    public void afficherEtape(int numero, String message) {
        afficheur.afficherEtape(numero, message);
    }
}
package Berthold;
import Agnes.Etudiant;
import Agnes.ExporteurBase;

import java.io.BufferedWriter;
import java.io.IOException;
import java.util.List;


public class Statistiques extends ExporteurBase {

    private void ecrireLigne(BufferedWriter w, String texte) throws IOException {
        w.write(texte); w.newLine();
    }

    private double moyennePromotion(List<Etudiant> etudiants) {
        double somme = 0;
        for (Etudiant e : etudiants) somme += e.calculerMoyenne();
        return Math.round((somme / etudiants.size()) * 100.0) / 100.0;
    }

    private Etudiant meilleur(List<Etudiant> list) {
        Etudiant m = null;
        for (Etudiant e : list)
            if (m == null || e.calculerMoyenne() > m.calculerMoyenne()) m = e;
        return m;
    }

    private Etudiant moinsBon(List<Etudiant> list) {
        Etudiant m = null;
        for (Etudiant e : list)
            if (m == null || e.calculerMoyenne() < m.calculerMoyenne()) m = e;
        return m;
    }

    @Override
    protected void ecrireContenu(BufferedWriter writer, List<Etudiant> etudiants) throws IOException {
        if (etudiants.isEmpty()) return;

        String[] matieres = etudiants.get(0).getNotes().keySet().toArray(new String[0]);

        ecrireLigne(writer, "".repeat(58));
        ecrireLigne(writer, "   RAPPORT STATISTIQUE -- GESTION DES NOTES D'ETUDIANTS");
        ecrireLigne(writer, "".repeat(58));
        writer.newLine();

        ecrireLigne(writer, "INFORMATIONS GENERALES");
        ecrireLigne(writer, "-".repeat(40));
        ecrireLigne(writer, String.format("  Nombre d'etudiants  : %d", etudiants.size()));
        ecrireLigne(writer, String.format("  Nombre de matieres  : %d", matieres.length));
        ecrireLigne(writer, String.format("  Moyenne de la promo : %.2f / 20", moyennePromotion(etudiants)));
        Etudiant best  = meilleur(etudiants);
        Etudiant worst = moinsBon(etudiants);
        if (best  != null) ecrireLigne(writer, String.format("  Meilleur etudiant   : %s (%.2f)", best.getNomComplet(),  best.calculerMoyenne()));
        if (worst != null) ecrireLigne(writer, String.format("  Plus faible         : %s (%.2f)", worst.getNomComplet(), worst.calculerMoyenne()));
        writer.newLine();

        ecrireLigne(writer, "STATISTIQUES PAR MATIERE");
        ecrireLigne(writer, "-".repeat(58));
        ecrireLigne(writer, String.format("  %-20s | %-8s | %-8s | %-8s", "Matiere", "Moyenne", "Minimum", "Maximum"));
        ecrireLigne(writer, "  " + "-".repeat(54));
        for (String matiere : matieres) {
            double min = Double.MAX_VALUE, max = -Double.MAX_VALUE, somme = 0;
            for (Etudiant e : etudiants) {
                Double note = e.getNotes().get(matiere);
                if (note == null) continue;
                somme += note;
                if (note < min) min = note;
                if (note > max) max = note;
            }
            double moy = Math.round((somme / etudiants.size()) * 100.0) / 100.0;
            ecrireLigne(writer, String.format("  %-20s | %-8.2f | %-8.2f | %-8.2f", matiere, moy, min, max));
        }
        writer.newLine();

        ecrireLigne(writer, "CLASSEMENT FINAL");
        ecrireLigne(writer, "-".repeat(58));
        ecrireLigne(writer, String.format("  %-4s | %-26s | %-7s | %s", "Rang", "Etudiant", "Moyenne", "Mention"));
        ecrireLigne(writer, "  " + "-".repeat(54));
        int rang = 1;
        for (Etudiant e : etudiants) {
            ecrireLigne(writer, String.format("  %-4d | %-26s | %-7.2f | %s",
                    rang, e.getNomComplet(), e.calculerMoyenne(), getMention(e.calculerMoyenne())));
            rang++;
        }
        writer.newLine();

    }


}

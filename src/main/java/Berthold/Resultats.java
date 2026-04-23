package Berthold;
import Agnes.Etudiant;
import Agnes.ExporteurBase;

import java.io.BufferedWriter;
import java.io.IOException;
import java.util.List;


public class Resultats extends ExporteurBase {

    private static final String SEP = ",";

    @Override
    protected void ecrireContenu(BufferedWriter writer, List<Etudiant> etudiants) throws IOException {
        if (etudiants.isEmpty()) return;

        // En-tête
        StringBuilder entete = new StringBuilder("Rang,Nom,Prenom");
        for (String matiere : etudiants.get(0).getNotes().keySet())
            entete.append(SEP).append(matiere);
        entete.append(SEP).append("Moyenne").append(SEP).append("Mention");
        writer.write(entete.toString());
        writer.newLine();

        // Données
        int rang = 1;
        for (Etudiant e : etudiants) {
            StringBuilder ligne = new StringBuilder();
            ligne.append(rang).append(SEP).append(e.getNom()).append(SEP).append(e.getPrenom());
            for (double note : e.getNotes().values())
                ligne.append(SEP).append(String.format("%.2f", note));
            ligne.append(SEP).append(String.format("%.2f", e.calculerMoyenne()));
            ligne.append(SEP).append(getMention(e.calculerMoyenne()));
            writer.write(ligne.toString());
            writer.newLine();
            rang++;
        }
    }
}

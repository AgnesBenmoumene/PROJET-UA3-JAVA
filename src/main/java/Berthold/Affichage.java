package Berthold;
import Agnes.Etudiant;
import Agnes.IAfficheur;


import java.util.List;


public class Affichage implements IAfficheur {

    @Override
    public void afficherBanniere() {

        System.out.println("  GESTION DES ÉTUDIANTS v3.0 — Architecture SOLID");
        System.out.println("                                                   ");
        System.out.println();
    }

    @Override
    public void afficherEtape(int numero, String message) {
        System.out.printf("%n[ÉTAPE %d] %s%n", numero, message);
        System.out.println("          " + "─".repeat(48));
    }

    @Override
    public void afficherClassement(List<Etudiant> etudiants) {
        if (etudiants == null || etudiants.isEmpty()) {
            System.out.println(" Aucun étudiant à afficher.");
            return;
        }

        String sep = "═".repeat(62);
        System.out.println(sep);
        System.out.println("   CLASSEMENT PAR MOYENNE DÉCROISSANTE");
        System.out.println(sep);
        System.out.printf("  %-4s | %-26s | %-7s | %s%n",
                "Rang", "Étudiant", "Moyenne", "Mention");
        System.out.println("  " + "─".repeat(58));

        int rang = 1;
        for (Etudiant e : etudiants) {
            double moy = e.calculerMoyenne();
            System.out.printf("  %-4d | %-26s | %-7.2f | %s%n",
                    rang, e.getNomComplet(), moy, getMention(moy));
            rang++;
        }

        System.out.println(sep);
        System.out.printf("  Promotion : %d étudiant(s)%n", etudiants.size());
        System.out.println(sep);
        System.out.println();
    }

    private String getMention(double moy) {
        if (moy >= 16) return "Très Bien";
        if (moy >= 14) return "Bien";
        if (moy >= 12) return "Assez Bien";
        if (moy >= 10) return "Passable";
        return "Insuffisant";
    }
}

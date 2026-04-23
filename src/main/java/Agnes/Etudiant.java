package Agnes;

import java.util.LinkedHashMap;
import java.util.Map;


public class Etudiant {

    //  Attributs privés
    private String nom;
    private String prenom;
    private Map<String, Double> notes; // matière → note sur 20

    //  Constructeur

    public Etudiant(String nom, String prenom) {
        this.nom    = nom;
        this.prenom = prenom;
        this.notes  = new LinkedHashMap<>(); // ordre d'insertion conservé
    }

    //  Méthodes métier


    public void addNote(String matiere, double note) {
        this.notes.put(matiere, note);
    }


    public double calculerMoyenne() {
        if (notes.isEmpty()) return 0.0;
        double somme = 0.0;
        for (double note : notes.values()) somme += note;
        return Math.round((somme / notes.size()) * 100.0) / 100.0;
    }


    public String getNomComplet() {
        return prenom + " " + nom.toUpperCase();
    }

    //  Redéfinition de Object

    @Override
    public String toString() {
        return String.format("[Etudiant] %-25s | Moyenne : %.2f/20",
                getNomComplet(), calculerMoyenne());
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Etudiant)) return false;
        Etudiant autre = (Etudiant) obj;
        return this.nom.equalsIgnoreCase(autre.nom)
                && this.prenom.equalsIgnoreCase(autre.prenom);
    }

    //  Getters / Setters

    public String getNom()                 { return nom; }
    public void   setNom(String nom)       { this.nom = nom; }

    public String getPrenom()              { return prenom; }
    public void   setPrenom(String prenom) { this.prenom = prenom; }

    public Map<String, Double> getNotes()  { return notes; }
}

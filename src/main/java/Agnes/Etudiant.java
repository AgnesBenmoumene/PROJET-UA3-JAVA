package Agnes;

import java.util.LinkedHashMap;
import java.util.Map;


public class Etudiant {

    //  Attributs privés
    private String nom;
    private String prenom;
    private Map<String, Double> notes; //  associer chaque note a une matiere

    //  Constructeur

    public Etudiant(String nom, String prenom) {
        this.nom    = nom;
        this.prenom = prenom;
        this.notes  = new LinkedHashMap<>(); //  conserver l'ordre d'insertion
    }

    //  Méthodes métier


    public void addNote(String matiere, double note) {//pour alimenter ce dictionnaire au fur et à mesure
        this.notes.put(matiere, note);
    }


    public double calculerMoyenne() {
        if (notes.isEmpty()) return 0.0;
        double somme = 0.0;
        for (double note : notes.values()) somme += note;
        return Math.round((somme / notes.size()) * 100.0) / 100.0;
    }


    public String getNomComplet()//combiner le nom et prenom
     {
        return prenom + " " + nom.toUpperCase();//transformer toutes les lettres du nom en capitales
    }

    //  Redéfinition de Object

    @Override
    public String toString() {
        return String.format("[Etudiant] %-25s | Moyenne : %.2f/20",
                getNomComplet(), calculerMoyenne());
    }

    @Override //éviter les dublons
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

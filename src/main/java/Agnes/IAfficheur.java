package Agnes;

import java.util.List;


public interface IAfficheur {


    void afficherClassement(List<Etudiant> etudiants);


    void afficherBanniere();


    void afficherEtape(int numero, String message);
}


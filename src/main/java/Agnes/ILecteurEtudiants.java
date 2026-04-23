package Agnes;

import java.io.IOException;
import java.util.List;


public interface ILecteurEtudiants {


    List<Etudiant> lire(String source) throws IOException, csvInvalide;
    //mettre une alarme en cas d'inexistence su fichier ou un probleme de format
}
package Agnes;

import java.io.IOException;
import java.util.List;


public interface ILecteurEtudiants {


    List<Etudiant> lire(String source) throws IOException, csvInvalide;
}
package Agnes;

import java.io.IOException;
import java.util.List;


public interface IExporteurResultats {

    void exporter(List<Etudiant> etudiants, String destination) throws IOException;
}
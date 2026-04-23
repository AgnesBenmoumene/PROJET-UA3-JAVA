package Agnes;

import Agnes.IExporteurResultats;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.List;



public abstract class ExporteurBase implements IExporteurResultats {


    @Override
    public final void exporter(List<Etudiant> etudiants, String destination) throws IOException {
        File fichier = new File(destination);
        if (fichier.getParentFile() != null) fichier.getParentFile().mkdirs();

        try (BufferedWriter writer = new BufferedWriter(
                new OutputStreamWriter(new FileOutputStream(fichier), StandardCharsets.UTF_8))) {
            ecrireContenu(writer, etudiants);
        }
        System.out.printf("Le fichier a été exporté vers '%s'%n", destination);
    }


    protected abstract void ecrireContenu(BufferedWriter writer, List<Etudiant> etudiants)
            throws IOException;


    protected String getMention(double moyenne) {
        if (moyenne >= 16) return "Très Bien";
        if (moyenne >= 14) return "Bien";
        if (moyenne >= 12) return "Assez Bien";
        if (moyenne >= 10) return "Passable";
        return "Insuffisant";
    }
}
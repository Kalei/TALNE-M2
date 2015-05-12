package tp1indexation.functions;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author uapv9906226
 */
public class Fichier {

    public static void ecrire(String path, String text) {
        PrintWriter ecri;
        try {
            ecri = new PrintWriter(new FileWriter(path));
            ecri.print(text);
            ecri.flush();
            ecri.close();
        }
        catch (NullPointerException a) {
            System.out.println("Erreur : pointeur null."+ path +" : fichier non trouv�");
        } catch (IOException a) {
            System.out.println("Probl�me d'IO");
        }
    }

    public static List<String> lire(String path) {
        BufferedReader lect;
        List<String> tmp = new ArrayList<>();
        try {
            lect = new BufferedReader(new FileReader(path));
            while (lect.ready() == true) {
                tmp.add(lect.readLine());
            }
        }
        catch (NullPointerException a) {
            System.err.println("Erreur : Repertoir non trouv�. path: "+path);
        } catch (IOException a) {
            System.err.println("Probl�me d'IO");
        }
        return tmp;
    }

    public static String[] fileList(String path) {
        String[] listefichiers;
        File repertoire = new File(path);
        listefichiers = repertoire.list();
   
        return listefichiers;
    }

    static void ecrire(String savetxt) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}

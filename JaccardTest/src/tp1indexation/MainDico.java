/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tp1indexation;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import tp1indexation.functions.Dico;
import tp1indexation.functions.Fichier;
import tp1indexation.functions.JaccardQuery;
import tp1indexation.functions.CritDistance;
import tp1indexation.functions.User;

/**
 *
 * @author uapv9906226
 */
public class MainDico {

    public static void main(String[] args) throws FileNotFoundException, NullPointerException {

        Scanner reader = new Scanner(System.in);
        String repertoire = "";
        System.out.println("Veuillez indiquer le chemin vers le fichier ou tapper load pour charger (\'default\' ./test/corpus (corpus simpliste)) :");
        repertoire = reader.nextLine().trim();

        if (repertoire.equals("default")) {
            repertoire = "./test/corpus";
        }

        try {
            String choice = "";
            List<String> result = null;
            Dico currentDico = null;

            if (!repertoire.equals("load")) {
                repertoire = repertoire.replace("~", System.getProperty("user.home"));
                String[] fileList = Fichier.fileList(repertoire);

                for (int i = 0; i < fileList.length; i++) {
                    //System.err.println(fileList[i]);
                    List<String> txt = Fichier.lire(repertoire + File.separator + fileList[i]);
                    currentDico = new Dico(txt, repertoire + File.separator + fileList[i]);
                }

            } else {
                choice = "load";
            }

            while (!choice.equals("exit")) {
                String req = "";
                if (choice.equals("")) {
                    System.out.println("Liste des action..... ");
                    System.out.println("-----------------------------------");
                    System.out.println("[1] display");
                    System.out.println("[2] save ");
                    System.out.println("[3] load ");
                    System.out.println("[4] query ");
                    System.out.println("[5] tfidf (sur un mot sans Jaccard)");
                    System.out.println("[6] Jaccard Tf Idf");
                    System.out.println("[7] critDistances");
                    System.out.println("[9] exit");
                    System.out.println("-----------------------------------");
                    System.out.print("Tappez votre choix : ");
                    choice = reader.nextLine().trim();
                }

                switch (choice) {
                    case "display":
                    case "1":
                        currentDico.display();
                        choice = "";
                        break;

                    case "save":
                    case "2":
                        currentDico.serialize();
                        choice = "";
                        break;

                    case "load":
                    case "3":
                        Dico toLoadDico = Dico.unSerialize();
                        //toLoadDico.display();
                        System.out.println("Votre corpus a été chargé.");
                        currentDico = toLoadDico;

                        choice = "";
                        break;
                    case "tfidf":
                    case "5":
                        System.out.println("Entrez un mot à évaluer : ");
                        req = reader.nextLine();
                        System.out.println("-------------------------------------------");
                        System.out.println("Résultat requête :");

                        System.out.println("Tfidf du mot " + req + " : " + currentDico.getPositivTfIdf(req));
                        System.out.println("-------------------------------------------");
                        choice = "";
                        break;

                    case "Jaccard_tfidf":
                    case "6":
                        System.out.println("Entrez votre requête phrase (ex: a b): ");
                        req = reader.nextLine();
                        System.out.println("-------------------------------------------");
                        System.out.println("Résultat requête :");

                        Map<Integer, Double> resultMap = new JaccardQuery(null, currentDico).getResult();

                        if (!resultMap.isEmpty()) {
                            int i = 0;
                            for (Map.Entry<Integer, Double> entry : resultMap.entrySet()) {
                                i++;
                                Integer key = entry.getKey();
                                Double value = entry.getValue();
                                if (value < (double) 1 && value > (double) 0) {
                                    System.out.println("MicroCritiqueID : " + key + " Jaccard: " + value);
                                }

                                if (i == 10) {
                                    break;
                                }
                            }
                        } else {
                            System.out.println("Aucun résultat n'a été trouvé.");
                        }

                        System.out.println("-------------------------------------------");
                        choice = "";
                        break;
                    case "7":
                        System.out.println("Entrez votre requête phrase (ex: a b): ");
                        req = reader.nextLine();
                        System.out.println("----------------------------------------------");
                        System.out.println("Résultat requête Utilisateur: " + req);

                        Map<String, Double> resultMap2 = new CritDistance(currentDico, req).getResult();

                        if (!resultMap2.isEmpty()) {
                            int i = 0;
                            for (Map.Entry<String, Double> entry : resultMap2.entrySet()) {
                                i++;
                                String key = entry.getKey();
                                Double value = entry.getValue();
                                if (value < (double) 1 && value > (double) 0) {
                                    System.out.println("Titre : " + key + " | Jaccard: " + value + " | Moyenne globale: " + currentDico.getFilms().get(key).getNoteMoyenne() + " | Note émise par l'utilisateur: " + currentDico.getUsers().get(req).getNotes().get(key));
                                }

                                if (i == 10) {
                                    break;
                                }
                            }
                        } else {
                            System.out.println("Aucun résultat n'a été trouvé.");
                        }

                        System.out.println("-------------------------------------------");
                        choice = "";
                        break;

                    case "9":
                        System.out.println("Entrez votre requête phrase (ex: a b): ");
                        req = reader.nextLine();
                        System.out.println("-------------------------------------------");

                        for (Map.Entry<String, User> entry2 : currentDico.getUsers().entrySet()) {
                            String string = entry2.getKey();

                            System.out.println("----------------------------------------------");
                            System.out.println("Résultat requête Utilisateur: " + string);

                            Map<String, Double> resultMap3 = new CritDistance(currentDico, string).getResult();

                            if (!resultMap3.isEmpty()) {
                                int i = 0;
                                for (Map.Entry<String, Double> entry : resultMap3.entrySet()) {
                                    i++;
                                    String key = entry.getKey();
                                    Double value = entry.getValue();
                                    if (value < (double) 1 && value > (double) 0) {
                                        System.out.println("Titre: " + key + " | Jaccard: " + value + " | Moyenne globale: " + currentDico.getFilms().get(key).getNoteMoyenne());
                                    }

                                    if (i == 10) {
                                        break;
                                    }
                                }
                            } else {
                                System.out.println("Aucun résultat n'a été trouvé.");
                            }
                        }
                        System.out.println("-------------------------------------------");
                        choice = "";
                        break;
                    case "exit":
                    case "10":
                        break;

                    default:
                        choice = "";
                        break;
                }
            }
        } catch (NullPointerException e) {
        }
    }
}

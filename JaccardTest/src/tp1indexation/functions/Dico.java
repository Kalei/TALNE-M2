/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tp1indexation.functions;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 *
 * @author uapv9906226
 */
public final class Dico implements Serializable {

    private Map<String, Word> words = new HashMap();
    private Map<String, Film> films = new HashMap();
    private Map<String, User> users = new HashMap();
    private Map<Integer, MicroCritique> microCritiques = new HashMap<>();
    private List<String> allDocs = new ArrayList<>();
    private int nbCrit = 0;
    private Map<String, Word> positivTerms = new HashMap<>();
    private Map<String, Word> negativTerms = new HashMap<>();
    private int nbCritPos = 0;
    private int nbCritNeg = 0;
    private List<Integer> positivCrit = new ArrayList<>();
    private List<Integer> negeativCrit = new ArrayList<>();

    public Dico(List<String> critiques, String path) {

        for (String critLine : critiques) {

            List<String> critValues = Arrays.asList(critLine.split("\\t"));

            if (!films.containsKey(critValues.get(3))) {
                films.put(critValues.get(3), new Film(critValues.get(3), Double.parseDouble(critValues.get(4))));
            } else {
                films.get(critValues.get(3)).addNote(Double.parseDouble(critValues.get(4)));
            }

            microCritiques.put(Integer.parseInt(critValues.get(0)), new MicroCritique(critValues.get(5), Double.parseDouble(critValues.get(4)), nbCrit, Integer.parseInt(critValues.get(0)), critValues.get(3), critValues.get(1)));

            if (!users.containsKey(critValues.get(1))) {
                users.put(critValues.get(1), new User(critValues.get(1), critValues.get(3), Double.parseDouble(critValues.get(4)), microCritiques.get(Integer.parseInt(critValues.get(0))), critiques.size()));
            } else {
                users.get(critValues.get(1)).addCrit(critValues.get(3), Double.parseDouble(critValues.get(4)), microCritiques.get(Integer.parseInt(critValues.get(0))), critiques.size()
                        );
            }
        }

        for (Map.Entry<Integer, MicroCritique> entry : microCritiques.entrySet()) {
            MicroCritique microCritique = entry.getValue();

            if (microCritique.note >= 4) {
                positivCrit.add(microCritique.id);
                nbCritPos++;
            } else if (microCritique.note <= 2.5) {
                negeativCrit.add(microCritique.id);
                nbCritNeg++;
            }
        }

        for (Map.Entry<Integer, MicroCritique> entry : microCritiques.entrySet()) {
            MicroCritique microCritique = entry.getValue();


            if (microCritique.note >= 4) {

                Map<String, Integer> tmpGrams = microCritique.containsTerms;

                for (Map.Entry<String, Integer> entry2 : tmpGrams.entrySet()) {

                    String term = entry2.getKey();
                    Integer occurence = entry2.getValue();

                    if (!positivTerms.containsKey(term)) {
                        positivTerms.put(term, new Word(term, microCritique, nbCritPos));
                    } else {
                        positivTerms.get(term).addFreq(microCritique, nbCritPos);
                    }

                }
            } else if (microCritique.note <= 2.5) {

                Map<String, Integer> tmpGrams = microCritique.containsTerms;

                for (Map.Entry<String, Integer> entry2 : tmpGrams.entrySet()) {
                    String term = entry2.getKey();
                    Integer occurence = entry2.getValue();
                    if (!negativTerms.containsKey(term)) {
                        negativTerms.put(term, new Word(term, microCritique, nbCritPos));
                    } else {
                        negativTerms.get(term).addFreq(microCritique, nbCritNeg);

                    }
                }
            }
        }

        //System.err.println(microCritiques);
    }

    public List<String> getPositivTerms() {
        List<String> positivKey = new ArrayList<>();

        for (Map.Entry<String, Word> entry : positivTerms.entrySet()) {
            String string = entry.getKey();

            positivKey.add(string);
        }

        return positivKey;
    }

    public Map<String, Word> getNegativTerms() {
        return negativTerms;
    }

    public void setNbDoc(int nbDoc) {
        this.nbCrit = nbDoc;
    }

    protected String cleanText(String txt) {
        txt = txt.replaceAll("[\\d\\p{Punct}]", " ").toLowerCase().trim();
        return txt;
    }

    public Map<Integer, Double> getPositivTfIdf(String term) {
        if (positivTerms.containsKey(term)) {
            return positivTerms.get(term).getAllTfIdf(nbCritPos);
        } else {

            return null;
        }
    }

    private static Map sortMap(Map unsortMap) {

        List list = new LinkedList(unsortMap.entrySet());

        Collections.sort(list, new Comparator() {
            @Override
            public int compare(Object o1, Object o2) {
                return ((Comparable) ((Map.Entry) (o1)).getValue())
                        .compareTo(((Map.Entry) (o2)).getValue());
            }
        });

        Map sortedMap = new LinkedHashMap();
        for (Iterator it = list.iterator(); it.hasNext();) {
            Map.Entry entry = (Map.Entry) it.next();
            sortedMap.put(entry.getKey(), entry.getValue());
        }
        return sortedMap;
    }

    public void display() {
        System.out.println("--------------------------");
        for (Map.Entry<String, Word> entry : words.entrySet()) {
            String mot = entry.getKey();
            Integer occurence = entry.getValue().total_occurence;
            System.out.println("--------------------------");
            System.out.println(mot + " : " + occurence);

            for (Map.Entry<Integer, LinkedCrit> file : entry.getValue().containFile.entrySet()) {
                System.out.println(file.getKey() + " : " + file.getValue().occurence);
            }
            System.out.println("--------------------------");
        }
    }

    public void serialize() {
        try {
            FileOutputStream fichier = new FileOutputStream("advTreatment.ser");
            try (ObjectOutputStream oos = new ObjectOutputStream(fichier)) {
                oos.writeObject(this);
                oos.flush();
            }
        } catch (java.io.IOException e) {
        }
    }

    public static Dico unSerialize() {
        try {
            FileInputStream fichier = new FileInputStream("advTreatment.ser");
            ObjectInputStream ois = new ObjectInputStream(fichier);
            Dico toLoadDico = (Dico) ois.readObject();
            return toLoadDico;
        } catch (java.io.IOException | ClassNotFoundException e) {
        }
        return null;
    }

    protected List<Integer> orPositivReq(List<String> wordsReq) {
        List<Integer> result = new ArrayList<>();

        for (String word : wordsReq) {
            if (this.words.containsKey(word)) {
                result.addAll(this.positivTerms.get(word).getFilesName());
            } else {
                System.out.println("Le mot " + word + " n'existe pas!");
            }
        }

        return result;
    }

    protected List<Integer> orReq(String word) {
        List<Integer> result = new ArrayList<>();

        if (this.words.containsKey(word)) {
            result.addAll(this.positivTerms.get(word).getFilesName());
        } else {
            System.out.println("Le mot " + word + " n'existe pas!");
        }

        return result;
    }

    protected List<Integer> andReq(List<String> wordsReq) {
        List<Integer> result = new ArrayList<>();
        List<Integer> tmp_result = new ArrayList<>();
        if (this.words.containsKey(wordsReq.get(0))) {
            result.addAll(this.positivTerms.get(wordsReq.get(0)).getFilesName());
        } else {
            System.out.println("Le mot " + wordsReq.get(0) + " n'existe pas!");
        }

        for (int i = 1; i < wordsReq.size(); i++) {
            tmp_result = result;
            if (this.words.containsKey(wordsReq.get(i))) {
                for (int j = 0; j < result.size(); j++) {
                    if (!this.words.get(wordsReq.get(i)).existFile(result.get(j))) {
                        tmp_result.remove(result.get(j));

                        //Décrémentation de j car le dernier maillon a changé
                        j--;
                    }
                }
            } else {
                System.out.println("Le mot " + wordsReq.get(i) + " n'existe pas!");
            }
        }
        return result;
    }

    public List<Integer> getAllPositivCrit() {
        return positivCrit;
    }

    public Map<Integer, Double> andReqLinkedFilePositivTfIdf(List<String> wordsReq) {
        Map<Integer, Double> resultMap = new HashMap<>();

        for (Map.Entry<Integer, MicroCritique> entry : microCritiques.entrySet()) {
            MicroCritique microCritique = entry.getValue();

            for (String wordReq : wordsReq) {
                if (!resultMap.containsKey(microCritique.id)) {
                    //Pour chaque document on récupére le tfidf du mot
                    resultMap.put(microCritique.id, (double) positivTerms.get(wordReq).getTfIdf(microCritique.id, nbCritPos));
                } else {
                    resultMap.put(microCritique.id, (double) resultMap.get(microCritique.id) + (double) positivTerms.get(wordReq).getTfIdf(microCritique.id, nbCritPos));
                }
            }
        }

        return resultMap;
    }

    public Double orReqTfIdf(List<String> req) {
        double result = (double) 0;

        for (String word : req) {
            if (positivTerms.containsKey(word)) {
                result += positivTerms.get(word).getAllTfIdfSum(nbCritPos);
            }
        }

        return (double) result;
    }

    public Map<String, User> getUsers() {
        return users;
    }

    public Map<Integer, MicroCritique> getMicroCritiques() {
        return microCritiques;
    }

    public Map<String, Film> getFilms() {
        return films;
    }
}

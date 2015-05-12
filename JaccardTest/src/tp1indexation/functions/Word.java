/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tp1indexation.functions;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author uapv9906226
 */
class Word implements Serializable {

    protected String word;
    protected Map<Integer, LinkedCrit> containFile = new HashMap<>();
    protected int total_occurence;

    public Word(String word, MicroCritique microCritique, int nbCritPositiv) {
        this.word = word;
        total_occurence = 1;

        containFile.put(microCritique.id, new LinkedCrit(microCritique.id, microCritique.nbMots, word, microCritique.containsTerms.get(word)));
    }

    protected void addFreq(MicroCritique microCritique, int nbCritPositiv) {
        total_occurence += microCritique.containsTerms.get(word);

        if (containFile.containsKey(microCritique.id)) {
            containFile.get(microCritique.id).occurence = microCritique.containsTerms.get(word);
        } else {
            containFile.put(microCritique.id, new LinkedCrit(microCritique.id, microCritique.nbMots, word, microCritique.containsTerms.get(word)));
        }
        total_occurence++;
    }

    protected Map<Integer, LinkedCrit> getLinkedFiles() {
        return containFile;
    }

    protected LinkedCrit getLinkedFile(String fileName) {
        if (containFile.containsKey(fileName)) {
            return containFile.get(fileName);
        } else {
            return null;
        }
    }

    protected List<Integer> getFilesName() {
        List<Integer> filesList = new ArrayList<>();
        for (Map.Entry<Integer, LinkedCrit> entry : containFile.entrySet()) {
            filesList.add(entry.getKey());
        }
        return filesList;
    }

    protected boolean existFile(Integer word) {
        return containFile.containsKey(word);
    }

    protected double getGlobalFrequency() {
        int nbLinkedFiles = containFile.size();
        double tmpResult = (double) 0;

        for (Map.Entry<Integer, LinkedCrit> entry : containFile.entrySet()) {
            Integer id = entry.getKey();
            LinkedCrit linkedFile = entry.getValue();

            tmpResult += linkedFile.getFrequency();

        }

        return tmpResult / nbLinkedFiles;
    }

    public double getTfIdf(Integer id, int nbDocs) {

        if (containFile.containsKey(id)) {

            //System.err.println(word + " " + nbDocs + " " + containFile.size());

            double idf = Math.log((double) nbDocs / (double) containFile.size());

            return (double) containFile.get(id).getFrequency() * (double) idf;
        }


        return 0.0;
    }

    public Double getAllTfIdfSum(int nbDocs) {

        double result = (double) 0;

        for (Map.Entry<Integer, LinkedCrit> entry : containFile.entrySet()) {
            Integer id = entry.getKey();
            result += getTfIdf(id, nbDocs);
        }
        return result;
    }

    public Map<Integer, Double> getAllTfIdf(int nbDocs) {
        Map<Integer, Double> result = new HashMap<>();

        for (Map.Entry<Integer, LinkedCrit> entry : containFile.entrySet()) {
            Integer id = entry.getKey();

            result.put(id, getTfIdf(id, nbDocs));
        }

        return result;
    }

    public Map<Integer, Integer> getOccurence(int nbDocs) {
        Map<Integer, Integer> result = new HashMap<>();

        for (Map.Entry<Integer, LinkedCrit> entry : containFile.entrySet()) {
            Integer id = entry.getKey();
            result.put(id, containFile.get(id).occurence);
        }

        return result;
    }
}

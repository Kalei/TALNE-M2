/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tp1indexation.functions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import static tp1indexation.functions.JaccardQuery.sortMapByValues;

/**
 *
 * @author Kalei
 */
public class CritDistance {

    private Dico db = null;
    private Map<String, Double> result = new HashMap<>();
    private User user;
    private LinkedCrit fileList;

    public CritDistance(Dico db, String user) {
        this.db = db;

        this.user = this.db.getUsers().get(user);

        List<MicroCritique> bestMicroList = this.user.getBestsMicroCrits();
        HashSet<String> termsList = new HashSet<>();

        for (MicroCritique microCritique : bestMicroList) {
            for (Map.Entry<String, Integer> entry : microCritique.containsTerms.entrySet()) {
                String term = entry.getKey();

                termsList.add(term);
            }
        }

        Map<Integer, Double> tmpResult = new JaccardQuery(new ArrayList(termsList), db).getResult();
        List<Integer> tmpToRemove = new ArrayList<>();


        for (Map.Entry<Integer, Double> entry : tmpResult.entrySet()) {
            Integer id = entry.getKey();
            Double double1 = entry.getValue();

            for (MicroCritique microCritique : bestMicroList) {
                if (microCritique.id == id) {
                    tmpToRemove.add(id);
                }
            }

            if (!this.user.microCrit.containsKey(db.getMicroCritiques().get(id).titre)) {
                tmpToRemove.add(id);
            }

        }

        //System.err.println(tmpToRemove);

        for (Integer integer : tmpToRemove) {
            tmpResult.remove(integer);
        }

        Map<String, Double> tmpResult2 = new HashMap<>();

        for (Map.Entry<Integer, Double> entry : tmpResult.entrySet()) {
            Integer id = entry.getKey();
            Double tfidf = entry.getValue();
            //System.err.println("MicroCritiqueID: " + id + " | Jaccard :" + tfidf + " | User: " + db.getMicroCritiques().get(id).user+ " | User: " + db.getMicroCritiques().get(id).titre);
            if (!tmpResult2.containsKey(db.getMicroCritiques().get(id).titre)) {
                tmpResult2.put(db.getMicroCritiques().get(id).titre, tfidf);
            } else {
                tmpResult2.put(db.getMicroCritiques().get(id).titre, tmpResult2.get(db.getMicroCritiques().get(id).titre) + tfidf);
            }

        }

        result = sortMapByValues(tmpResult2);
    }

    public Map<String, Double> getResult() {
        return result;
    }
}

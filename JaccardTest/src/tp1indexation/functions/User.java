/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tp1indexation.functions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Kalei
 */
public class User {

    protected String pseudo;
    protected Map<String, Double> notes = new HashMap<>();
    protected Map<String, MicroCritique> microCrit = new HashMap<>();

    public User(String pseudo, String titre, double note, MicroCritique microCritique, int nbCritiquesTotal) {
        this.pseudo = pseudo;
        this.notes.put(titre, note);
        this.microCrit.put(titre, microCritique);
    }

    public void addCrit(String titre, double note, MicroCritique microCritique, int nbCritiquesTotal) {
        this.notes.put(titre, note);
        this.microCrit.put(titre, microCritique);
    }

    public Double getGlobalMoyenne() {

        double totalNotes = 0.0;

        for (Map.Entry<String, Double> noteByFilm : notes.entrySet()) {
            totalNotes += noteByFilm.getValue();

        }

        return totalNotes / notes.size();
    }

    public Map<String, MicroCritique> getMicroCrit() {
        return microCrit;
    }

    public List<MicroCritique> getBestsMicroCrits() {
        List<MicroCritique> tmpResult = new ArrayList<>();
        for (Map.Entry<String, MicroCritique> entry : microCrit.entrySet()) {
            String string = entry.getKey();
            MicroCritique microCrit = entry.getValue();

            if (microCrit.note > 3.5) {
                tmpResult.add(microCrit);
            }

        }

        return tmpResult;
    }

    public Map<String, Double> getNotes() {
        return notes;
    }
    
    
}

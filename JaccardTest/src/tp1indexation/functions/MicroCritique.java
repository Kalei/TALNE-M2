/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tp1indexation.functions;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Kalei
 */
final class MicroCritique {

    protected double tfidf;
    protected String microCritique;
    protected Double note;
    protected Map<String, Integer> containsTerms = new HashMap<>();
    protected int nbMots = 0;
    protected int nbCrit = 0;
    protected int id = 0;
    protected String user;
    protected String titre;

    public MicroCritique(String microCritique, double note, int nbCrit, int id, String titre, String user) {
        this.user = user;
        this.titre = titre;
        this.id = id;
        this.microCritique = cleanText(microCritique);
        List<String> critiqueWords = new ArrayList<>(Arrays.asList(this.microCritique.trim().split(" ")));
        this.nbCrit = nbCrit;
        nbMots = critiqueWords.size();
        this.note = note;

        HashSet<String> tmpToDelet = new HashSet<>();

        for (String critiqueWord : critiqueWords) {
            if (isStopWord(critiqueWord) || critiqueWord.length() < 3) {
                tmpToDelet.add(critiqueWord);
            }
        }

        critiqueWords.removeAll(tmpToDelet);

        for (int i = 0; i < critiqueWords.size(); i++) {
            int j = i + 1;

            containsTerms.put(critiqueWords.get(i), 1);
            String gram = critiqueWords.get(i);
            nbMots++;
            while (j < critiqueWords.size() && j < 7) {
                gram = gram + " " + critiqueWords.get(j);
                if (!containsTerms.containsKey(gram)) {
                    containsTerms.put(gram, 1);
                } else {
                    containsTerms.put(gram, containsTerms.get(gram) + 1);
                }

                nbMots++;

                j++;
            }
        }

        //System.err.println(containsTerms);
        containsTerms.remove("");
    }

    public double doTfIdf(String term) {
        return 0.0;
    }

    protected String cleanText(String txt) {
        txt = txt.replaceAll("[\\d\\p{Punct}]", " ").replaceAll("( ){2,}", " ").replaceAll("<[^>]*>", " ").toLowerCase().trim();
        return txt;
    }

    public boolean isStopWord(String term) {
        switch (term) {
            case "un":
                return true;
            case "une":
                return true;
            case "le":
                return true;
            case "tel":
                return true;
            case "tes":
                return true;
            case "ta":
                return true;
            case "la":
                return true;
            case "suis":
                return true;
            case "les":
                return true;
            case "de":
                return true;
            case "des":
                return true;
            case "son":
                return true;
            case "sont":
                return true;
            case "soit":
                return true;
            case "a":
                return true;
            case "à":
                return true;
            case "est":
                return true;
            case "quoi":
                return true;
            case "au":
                return true;
            case "aux":
                return true;
            case "l":
                return true;
            case "j":
                return true;
            case "qu":
                return true;
            case "que":
                return true;
            case "n":
                return true;
            case "fait":
                return true;
            case "fais":
                return true;
            case "ne":
                return true;
            case "donc":
                return true;
            case "ces":
                return true;
            case "cet":
                return true;
            case "cette":
                return true;
            case "il":
                return true;
            case "elle":
                return true;
            case "ils":
                return true;
            case "elles":
                return true;
            case "nous":
                return true;
            case "vous":
                return true;
            case "plus":
                return true;
            case "moins":
                return true;
            case "qui":
                return true;
            case "mon":
                return true;
            case "moi":
                return true;
            case "mes":
                return true;
            case "pour":
                return true;
            case "par":
                return true;
            default:
                return false;
        }
    }
}
/*   
 * if (j < critiqueWords.size()) {
                String jGram = critiqueWords.get(i) + " " + critiqueWords.get(j);
                containsTerms.put(jGram, doTfIdf(jGram));

                int k = j + 1;
                if (k < critiqueWords.size()) {
                    String kGram = jGram + critiqueWords.get(k);
                    containsTerms.put(kGram, doTfIdf(kGram));

                    int l = k + 1;

                    if (l < critiqueWords.size()) {
                        String lGram = kGram + critiqueWords.get(l);
                        containsTerms.put(lGram, doTfIdf(lGram));

                        int m = l + 1;

                        if (m < critiqueWords.size()) {
                            String mGram = lGram + critiqueWords.get(l);
                            containsTerms.put(mGram, doTfIdf(mGram));

                            int n = m + 1;

                            if (n < critiqueWords.size()) {
                                String nGram = mGram + critiqueWords.get(n);
                                containsTerms.put(nGram, doTfIdf(nGram));

                                int o = l + 1;

                                if (o < critiqueWords.size()) {
                                    String oGram = nGram + critiqueWords.get(o);
                                    containsTerms.put(oGram, doTfIdf(oGram));
                                }
                            }
                        }
                    }
                }
            }
*/
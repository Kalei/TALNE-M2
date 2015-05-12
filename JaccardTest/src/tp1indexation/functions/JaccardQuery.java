/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tp1indexation.functions;

import java.util.ArrayList;
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
 * @author Kalei
 */
public class JaccardQuery {

    Dico db = null;
    Map<Integer, Double> result = new HashMap<>();
    LinkedCrit fileList;

    public JaccardQuery(List<String> req, Dico db) {
        List<String> reqList = new ArrayList<>();

        if (req == null) {
            reqList = db.getPositivTerms();
        } else {
            reqList = req;
        }
        this.db = db;

        /*System.err.println(db.getPositivTfIdf("très"));
        System.err.println(db.getPositivTfIdf("bon"));
        System.err.println(db.getPositivTfIdf("très bon"));*/
        
        try {
            double orResult = db.orReqTfIdf(reqList);
            Map<Integer, Double> andResult = db.andReqLinkedFilePositivTfIdf(reqList);
            for (Integer k : andResult.keySet()) {
                andResult.put(k, andResult.get(k) / orResult);
            }

            result = sortMapByValues(andResult);
        } catch (Exception e) {
            System.err.println(e);
        }
    }

    public Map<Integer, Double> getResult() {
        return result;
    }

    public static < K, V extends Comparable< ? super V>> Map< K, V> sortMapByValues(final Map< K, V> mapToSort) {
        List list = new LinkedList(mapToSort.entrySet());

        Collections.sort(list, new Comparator() {
            @Override
            public int compare(Object o1, Object o2) {
                return ((Comparable) ((Map.Entry) (o2)).getValue()).compareTo(((Map.Entry) (o1)).getValue());
            }
        });

        Map result = new LinkedHashMap();

        for (Iterator it = list.iterator(); it.hasNext();) {
            Map.Entry entry = (Map.Entry) it.next();
            result.put(entry.getKey(), entry.getValue());
        }

        return result;
    }
}

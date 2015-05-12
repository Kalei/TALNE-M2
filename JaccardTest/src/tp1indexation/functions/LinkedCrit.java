/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tp1indexation.functions;

/**
 *
 * @author Kalei
 */
public class LinkedCrit {

    protected Integer id;
    protected int occurence = 0;
    protected int nbTermes;
    protected String wordCommingFrom;

    public LinkedCrit(Integer id, int nbTermes, String word, int occurence) {
        this.id=id;
        this.nbTermes=nbTermes;
        this.wordCommingFrom=word;
        this.occurence = occurence;
    }

    public int getOccurence() {
        return occurence;
    }

    public Integer getName() {
        return id;
    }

    public void setName(Integer id) {
        this.id = id;
    }

    public void setOccurence(int occurence) {
        this.occurence = occurence;
    }

    
    public double getFrequency(){
        return (double) occurence/(double) nbTermes;
    }
}

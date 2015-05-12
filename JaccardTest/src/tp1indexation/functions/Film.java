/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tp1indexation.functions;

/**
 *
 * @author Kalei
 */
public class Film {

    protected String titre; 
    protected double totalNote = 0.0;
    protected int nbNotes=0;
    
    public Film(String titre, double note) {
        this.titre = titre;
        this.totalNote += note;
        this.nbNotes++;
    }
    
    
    public void addNote(double note){
        nbNotes++;
        totalNote+=note;
    }
    
    public Double getNoteMoyenne(){
        return (double)totalNote/(double)nbNotes;
    }
    
}

/*
 * Student: Mustata Alexandru-Ionut
 * Grupa: 331CB
 * Tema 2 APD
 */
package tema2apd;

import java.util.ArrayList;

//Implementarea evenimentului de tip Fact.
public class Fact implements Event{
    
    //Lista de rezultate a evenimentelor de tip Fact.
    public static ArrayList<Integer> results = new ArrayList<Integer>();
    
    //Numarul continut de eveniment.
    private int number = 0;
    
    public Fact(int number){
        this.number = number;
    }

    /*Metoda realizeaza initializarea calculului evenimentului si salvarea
    rezultatului obtinut.*/
    @Override
    public void computeEvent() {
        int result = computeResult();
        synchronized(results){
            results.add(result);
        }
    }

    //Metoda intoarce rezultatul obtinut din calcularea evenimentului.
    @Override
    public int computeResult() {
        int p = 1;
        int t = 1;
        while(p <= number){
            t += 1;
            p *= t;
        }
        return t-1;
    }

    
}

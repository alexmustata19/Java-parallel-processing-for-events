/*
 * Student: Mustata Alexandru-Ionut
 * Grupa: 331CB
 * Tema 2 APD
 */
package tema2apd;

import java.util.ArrayList;

//Implementarea evenimentului de tip Fib.
public class Fib implements Event{
    
    //Lista de rezultate a evenimentelor de tip Fib.
    public static ArrayList<Integer> results = new ArrayList<Integer>();
    
    //Numarul continut de eveniment.
    private int number = 0;
    
    public Fib(int number){
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
        int f0 = 0;
        int f1 = 1;
        int index = 0;
        while(f1 <= number){
            int aux = f1;
            f1 = f1 + f0;
            f0 = aux;
            index++;
        }
        return index;
    }
 
    
}

/*
 * Student: Mustata Alexandru-Ionut
 * Grupa: 331CB
 * Tema 2 APD
 */
package tema2apd;

import java.util.ArrayList;

//Implementarea evenimentului de tip Square.
public class Square implements Event{
    
    //Lista de rezultate a evenimentelor de tip Square.
    public static ArrayList<Integer> results = new ArrayList<Integer>();
    
    //Numarul continut de eveniment.
    private int number = 0;
    
    public Square(int number){
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
        for(int i=(int)(Math.sqrt(number))+1; i>=1; i--){
            if(i * i <= number){
                return i;
            }
        }
        return 1;
    }

}

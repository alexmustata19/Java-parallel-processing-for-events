/*
 * Student: Mustata Alexandru-Ionut
 * Grupa: 331CB
 * Tema 2 APD
 */
package tema2apd;

import java.util.ArrayList;

//Implementarea evenimentului de tip Prime.
public class Prime implements Event{
    
    //Lista de rezultate a evenimentelor de tip Prime.
    public static ArrayList<Integer> results = new ArrayList<Integer>();
    
    //Numarul continut de eveniment.
    private int number = 0;
    
    public Prime(int number){
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
        for(int i=number; i>=2; i--){
            if(isPrime(i)){
                return i;
            }
        }
        return 2;
    }
    
    //Metoda verifica daca un numar este prim.
    private boolean isPrime(int n){
        for(int i=2; i<=Math.sqrt(n); i++){
            if(n % i == 0){
                return false;
            }
        }
        return true;
    }
}

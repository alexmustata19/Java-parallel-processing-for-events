/*
 * Student: Mustata Alexandru-Ionut
 * Grupa: 331CB
 * Tema 2 APD
 */
package tema2apd;

/*Interfata ce descrie comportamentul necesar pe care trebuie sa il aibe
un eveniment indiferent de tipul concret al acestuia.*/
public interface Event{
    
    /*Metoda ce trebuie implementata pentru a initia calculul evenimentului si 
    pentru a salva rezultatul intors de acesta.*/
    public abstract void computeEvent();
    
    //Metoda ce trebuie implementata pentru a calcula evenimentul.
    public abstract int computeResult();
}

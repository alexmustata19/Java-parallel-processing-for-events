/*
 * Student: Mustata Alexandru-Ionut
 * Grupa: 331CB
 * Tema 2 APD
 */
package tema2apd;

//Clasa pentru realizarea mecanismului de creare de evenimente. 
public class EventFactory {
    
    /*Metoda creeaza un eveniment ce va contine numarul trimis ca parametru si
    va avea tipul specificat.*/
    public static Event createEvent(String type, int number){
        if(type.equals("PRIME")){
            return new Prime(number);
        }
        if(type.equals("FACT")){
            return new Fact(number);
        }
        if(type.equals("SQUARE")){
            return new Square(number);
        }
        if(type.equals("FIB")){
            return new Fib(number);
        }
        return null;
    }
    
}

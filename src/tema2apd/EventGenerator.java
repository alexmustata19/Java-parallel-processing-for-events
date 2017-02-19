/*
 * Student: Mustata Alexandru-Ionut
 * Grupa: 331CB
 * Tema 2 APD
 */
package tema2apd;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.StringTokenizer;
import java.util.concurrent.Semaphore;
import java.util.logging.Level;
import java.util.logging.Logger;

//Implementarea generatorului de evenimente.
public class EventGenerator implements Runnable{
    
    //Numele fisierului asociat generatorului.
    private String filename = null;
    
    //Numarul de evenimente ce trebuie citite.
    private int numberOfEvents = 0;
    
    //WorkPool-ul in care se vor introduce evenimentele generate.
    private WorkPool wp = null;
    
    /*Semaforul ce va limita ca doar un EventGenerator sa aiba acces la un 
    moment dat la WorkPool-ul in care va introduce un task.*/
    private static Semaphore producer = new Semaphore(1);
    
    public EventGenerator(String filename, int numberOfEvents, WorkPool wp){
        this.filename = filename;
        this.numberOfEvents = numberOfEvents;
        this.wp = wp;
    }

    @Override
    public void run() {
        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader(new File(filename)));
            String line = "";
            int count = 0;
            while(count < numberOfEvents && ((line = br.readLine()) != null)){
                //Se parseaza continutul citit.
                StringTokenizer st = new StringTokenizer(line, ",");
                
                //Se asteapta timpul precizat.
                long timeToSleep = Long.parseLong(st.nextToken());
                Thread.currentThread().sleep(timeToSleep);
                
                /*Se creaza un eveniment de tipul specificat ce va contine
                numarul citit.*/
                String typeOfEvent = st.nextToken();
                int number = Integer.parseInt(st.nextToken());
                Event e = EventFactory.createEvent(typeOfEvent, number);
                
                /*Se asteapta obtinerea accesului la WorkPool pentru introducerea
                unui eveniment.*/
                producer.acquireUninterruptibly();
                
                //Se introduce un eveniment in WorkPool.
                wp.putEvent(e);
                
                /*Se elibereaza accesul la WorkPool pentru celelalte obiecte de 
                tip EventGenerator.*/
                producer.release();
                
                count++;
            }
            br.close();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(EventGenerator.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(EventGenerator.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InterruptedException ex) {
            Logger.getLogger(EventGenerator.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}

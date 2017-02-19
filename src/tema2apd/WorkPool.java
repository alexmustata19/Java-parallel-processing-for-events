/*
 * Student: Mustata Alexandru-Ionut
 * Grupa: 331CB
 * Tema 2 APD
 */
package tema2apd;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.logging.Level;
import java.util.logging.Logger;

//Implementarea WorkPool-ului ce va stoca si imparti task-uri.
public class WorkPool {

    //Coada de dimensiune fixa ce va contine evenimentele.
    private ArrayBlockingQueue<Event> queue = null;

    //Numarul de thread-uri Worker ce vor accesa WorkPool-ul.
    private int numberOfThreads = 0;

    //Dimensiunea maxima admisa a cozii.
    private int queueMaxSize = 0;

    //Numarul anticipat de task-uri care vor fi trecute prin coada din WorkPool.
    private int anticipatedTasksNumber = 0;

    //Numarul de task-uri procesate.
    public int numberOfProcessedTasks = 0;

    //Boolean care anunta ca s-au consumat toate evenimentele planuite.
    public boolean shutdown = false;

    public WorkPool(int numberOfThreads, int queueMaxSize, int anticipatedTaskNumber) {
        this.numberOfThreads = numberOfThreads;
        this.queueMaxSize = queueMaxSize;
        this.queue = new ArrayBlockingQueue<Event>(queueMaxSize);
        this.anticipatedTasksNumber = anticipatedTaskNumber;
    }
    
    /*Metoda asigura extragerea unui eveniment din coada. Daca coada este goala
    se asteapta pana cand va exista cel putin un element disponibil. Daca coada
    este goala si toate task-urile prevazute au fost procesate atunci se returneaza
    null Worker-ului pentru a ii semnala acest fapt in vedererea terminarii rularii.
    */
    public Event takeEvent() {
        Event e = null;
        
        synchronized (queue) {
            /*Daca coada este goala si procesarea tuturor task-urilor nu s-a
            incheiat se asteapta pana la urmatoarea schimbare de status a cozii
            (s-a introdus un eveniment sau s-a terminat procesarea).*/
            while (queue.size() == 0 && !shutdown) {
                try {
                    queue.wait();
                } catch (InterruptedException ex) {
                    Logger.getLogger(WorkPool.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            
            if (shutdown) {
                /*Daca procesarea s-a terminat se notifica acest lucru si se 
                returneaza null Worker-ului pentru a ii fi semnalat acest lucru.*/
                queue.notifyAll();
                return null;
            } else {
                //Daca procesarea nu s-a terminat se extrage un element din coada.
                try {
                    e = queue.take();
                } catch (InterruptedException ex) {
                    Logger.getLogger(WorkPool.class.getName()).log(Level.SEVERE, null, ex);
                }
                
                /*Se incrementeaza numarul de task-uri procesate si daca acesta a
                atins numarul de task-uri anticipate se seteaza semnalul de shutdown.*/
                numberOfProcessedTasks++;
                if (numberOfProcessedTasks == anticipatedTasksNumber) {
                    shutdown = true;
                }
                
                //Se notifica schimbarea de status a cozii.
                queue.notifyAll();
            }
        }
        
        return e;
    }
    
    /*Metoda asigura introducerea unui eveniment in coada din WorkPool. Executia
    metodei se termina dupa adaugarea evenimentului (daca coada este plina se 
    asteapta pana cand se elibereaza cel putin un loc).
    */
    public void putEvent(Event e) {
        synchronized (queue) {
            /*Daca coada este plina se asteapta pana se elibereaza cel putin
            un loc in care sa se introduca noul eveniment.*/
            while (queue.size() == queueMaxSize) {
                try {
                    queue.wait();
                } catch (InterruptedException ex) {
                    Logger.getLogger(WorkPool.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            
            /*Se introduce noul eveniment in coada si se notifica acest lucru.*/
            try {
                queue.put(e);
            } catch (InterruptedException ex) {
                Logger.getLogger(WorkPool.class.getName()).log(Level.SEVERE, null, ex);
            }
            queue.notifyAll();
        }
    }
}

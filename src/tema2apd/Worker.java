/*
 * Student: Mustata Alexandru-Ionut
 * Grupa: 331CB
 * Tema 2 APD
 */
package tema2apd;

import java.util.concurrent.Semaphore;

//Implementarea Worker-ului ce va rezolva task-uri.
public class Worker implements Runnable {
    
    //WorkPool-ul din care Worker-ul va extrage task-uri pe care le va rezolva.
    private WorkPool wp = null;
    
    /*Semaforul ce va limita ca doar un Worker sa aiba acces la un moment dat
    la WorkPool-ul de unde va extrage un task.*/
    private static Semaphore consumer = new Semaphore(1);

    public Worker(WorkPool wp) {
        this.wp = wp;
    }

    @Override
    public void run() {
        while(true) {
            Event e = null;
            
            /*Se asteapta obtinerea accesului la WorkPool pentru extragerea
            unui eveniment.*/
            consumer.acquireUninterruptibly();
            
            //Se extrage un eveniment din WorkPool.
            e = wp.takeEvent();
            
            //Se elibereaza accesul la WorkPool pentru celelalte obiecte Worker.
            consumer.release();
            
            /*Daca evenimentul este null inseamna ca nu mai exista task-uri de
            procesat in WorkPool si thread-ul Worker se poate termina.*/
            if(e == null){
                break;
            }
            
            //Se declanseaza calcularea evenimentului.
            e.computeEvent();
        }
    }

}

/*
 * Student: Mustata Alexandru-Ionut
 * Grupa: 331CB
 * Tema 2 APD
 */
package tema2apd;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Main {
    
    public static void main(String[] args) {  
        //Determinarea dimensiunilor problemei.
        int queueSize = Integer.parseInt(args[0]);
        int numberOfEventsInEachFile = Integer.parseInt(args[1]);
        int numberOfFiles = args.length-2;
        int numberOfCores = Runtime.getRuntime().availableProcessors();
        
        /*Crearea WorkPool-ului prin intermediul caruia vor fi impartite 
        evenimentele obiectelor de tip Worker.*/
        WorkPool wp = new WorkPool(numberOfCores, queueSize, numberOfEventsInEachFile * numberOfFiles);
        
        //Crearea thread-urilor asociate generatorilor de evenimente.
        Thread[] eg = new Thread[numberOfFiles];
        for(int i=2; i<args.length; i++){
            eg[i-2] = new Thread(new EventGenerator(args[i], numberOfEventsInEachFile, wp));
            eg[i-2].start();
        }
        
        //Crearea thread-urilor asociate obiectelor de tip Worker.
        Thread[] workers = new Thread[numberOfCores];
        for(int i=0; i<workers.length; i++){
            workers[i] = new Thread(new Worker(wp));
            workers[i].start();
        }
        
        //Asteptarea terminarii thread-urilor asociate generatorilor de evenimente.
        for(int i=0; i<eg.length; i++){
            try {
                eg[i].join();
            } catch (InterruptedException ex) {
                Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        //Asteptarea terminarii thread-urilor asociate obiectelor de tip Worker.
        for(int i=0; i<workers.length; i++){
            try {
                workers[i].join();
            } catch (InterruptedException ex) {
                Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        //Scrierea rezultatelor in fisierele de output.
        writeResultToFile("FACT.out", Fact.results);
        writeResultToFile("FIB.out", Fib.results);
        writeResultToFile("PRIME.out", Prime.results);
        writeResultToFile("SQUARE.out", Square.results);
        
    }
    
    /*Metoda primeste o lista pe care o sorteaza si o scrie (cate un element pe
    linie) in fisierul ce va avea numele primit ca parametru.*/
    public static void writeResultToFile(String filename, ArrayList<Integer> list){
        Collections.sort(list);
        try {
            PrintWriter pw = new PrintWriter(new File(filename));
            for(Integer i : list){
                pw.println(i);
            }
            pw.close();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}

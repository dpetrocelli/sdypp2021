package org.example;

import java.util.ArrayList;

public class App {
    ArrayList<Thread> al;
    public App(){
        al = new ArrayList<Thread>();

        int max = 10000;
        int min = 1000;
        long sleepTime;

        sleepTime = (long) Math.floor(Math.random()*(max-min+1)+min);
        AppThread ap1 = new AppThread(sleepTime);
        Thread ap1Thread = new Thread (ap1);
        System.out.println(" STATE: "+ ap1Thread.getState());
        
        al.add(ap1Thread);
       
       
        sleepTime = (long) Math.floor(Math.random()*(max-min+1)+min);
        AppThread ap2 = new AppThread(sleepTime);
        Thread ap2Thread = new Thread (ap2);
        al.add(ap2Thread);

        sleepTime = (long) Math.floor(Math.random()*(max-min+1)+min);
        AppThread ap3 = new AppThread(sleepTime);
        Thread ap3Thread = new Thread (ap3);
        al.add(ap3Thread);
        
        ap1Thread.start();
        ap2Thread.start();
        ap3Thread.start();

        // hasta que todos los threads no terminen, no mostrar este mensaje
        /*
        try {
            for (Thread thread : al) {
                thread.join();
                
            }
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        */
  
        ArrayList<Integer> posicion = new ArrayList<Integer>();
        while (al.size()>0){

            for (Thread thread : al) {
                
                //System.out.println(" state: "+thread.getState().toString());
                if (thread.getState().toString().equals("TERMINATED")){
                    posicion.add(al.indexOf(thread));
                    
                }
            }
            for (Integer idObject : posicion) {
                al.remove((int) idObject);
                System.out.println(" eliminamos el item de la lista "+idObject);
            }
            
            posicion = new ArrayList<Integer>();
            
            try {
                Thread.sleep(1000);
                System.out.println("una vuelta mas");
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        System.out.println(" main termino" );
        
    }
    public static void main( String[] args ){
        App app = new App();
    }
}

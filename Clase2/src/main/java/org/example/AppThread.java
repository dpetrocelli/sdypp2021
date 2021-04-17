package org.example;

public class AppThread implements Runnable {
    long sleepTime; 
    public AppThread (long sleepTimeParent){
        this.sleepTime = sleepTimeParent;
    }
    @Override
    public void run() {
        // TODO Auto-generated method stub
        System.out.println(" Hola desde thread "+Thread.currentThread().getId());
        try {
            Thread.sleep(sleepTime);
            System.out.println(" termino el thread "+Thread.currentThread().getId());
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
    }
    
}

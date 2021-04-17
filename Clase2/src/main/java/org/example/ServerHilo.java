package org.example;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ServerHilo implements Runnable {
    BufferedReader canalEntrada;
    PrintWriter canalSalida;
    Socket client;

    public ServerHilo(Socket client) {

        this.client = client;
    }

    @Override
    public void run() {
        // imaginando un proceso que lleva tiempo
        try {
            long id =Thread.currentThread().getId(); 
            System.out.println(" Soy el Thread: "+id+" al cliente: "+client.getInetAddress().getCanonicalHostName());
                
            BufferedReader canalEntrada = new BufferedReader(new InputStreamReader(this.client.getInputStream()));
            PrintWriter canalSalida = new PrintWriter(this.client.getOutputStream(), true);
            canalSalida.println("el_server_responde del thread: "+id);
            Thread.sleep(10000);
            client.close();
        }catch (Exception e){

        }


    }
}

package org.example;


import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Server
{
    private final Logger log = LoggerFactory.getLogger(Server.class);
    private boolean flag;
    public Server(int port){
        flag = false;
        int requests = 0;

        try{
            ServerSocket ss = new ServerSocket(port);
            log.info("Server has started on port "+port);

            Socket client = null;
            
            while (!flag){
                client = ss.accept();
                requests++;
                // por cada cliente que acepto 
                //log.info(" recibimos al cliente: "+client.getInetAddress().getAddress().toString());
                                
                // simulación de tarea
                
                ServerHilo sh = new ServerHilo(client);
                Thread serverThread = new Thread(sh);
                serverThread.start();
                log.info ("RE: "+requests);
                if (requests>=3){
                    flag = true;
                }
            }

            log.info ("El servidor atendió mas de 4 peticiones");
        }catch (Exception e){
            e.printStackTrace();
        }


    }
    public static void main( String[] args )
    {
        // parametros de consola
        int threadId = (int) Thread.currentThread().getId();
        String logName = Server.class.getSimpleName()+"-"+threadId;
        System.setProperty("log.name", logName);
        
        int port = 9091;
        Server server = new Server(port);
    }
}

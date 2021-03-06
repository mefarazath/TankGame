/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Communication;

import AI.AICore;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Observable;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Farazath Ahamed
 */
public class Communicator extends Observable{
 
public final int clientPort = 7000;
public final int serverPort = 6000;
public final String IPaddress = "127.0.0.1";

private BufferedReader input;
private BufferedWriter output;

private Socket serverSocket;
private Socket clientSocket;
private ServerSocket ServerSocketForClient;
//private static Communicator comm = new Communicator();

//private Communicator(){}
//
//public static Communicator GetInstance(){
//        return comm;
//}
    public Communicator() {
       // this.addObserver(i);
    }

    public void setAICore(AICore warrior){
        this.addObserver(warrior);
    }

 public String reciveData() throws IOException {
        
        boolean error = false;
        clientSocket = null;
        String readLine = "#";

        try {
            ServerSocketForClient = new ServerSocket(clientPort);

            //connect
            clientSocket = ServerSocketForClient.accept();
            if (ServerSocketForClient.isBound()) {
                input = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                
                while (!input.ready()) {
               // System.out.println("input isn't ready");
                    Thread.sleep(500);
                }
                readLine = input.readLine();
               // this.hasChanged();
                this.setChanged();
                this.notifyObservers(readLine.split("#")[0]);
              //  System.out.println("Server Reply : "+readLine);
                input.close();
                clientSocket.close();
                ServerSocketForClient.close();
                error = false;
            }


        } catch (IOException | InterruptedException e) {
            System.out.println("Server Communication(recieving) Failed " + e.getMessage());
            error = true;
            clientSocket.close();
            ServerSocketForClient.close();
        } finally {
           
            if (clientSocket != null) {
                if (clientSocket.isConnected()) {
                    try {
                        clientSocket.close();
                    } catch (IOException ex) {
                        Logger.getLogger(Communicator.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
            if (error) {

                this.reciveData();
            }
          //  System.out.println("message : "+readLine.split("#")[0]);
           
        }
        return readLine.split("#")[0];
    }

    public void sendData(String msg) {
        try {
            serverSocket = new Socket(IPaddress, serverPort);
            if (this.serverSocket.isConnected()) {
                //To write to the socket
                this.output = new BufferedWriter(new OutputStreamWriter(this.serverSocket.getOutputStream()));
                output.write(msg);
                output.close();
                serverSocket.close();
                System.out.println("msg: " + msg + " sent to server");
            }

        } catch (Exception e) {
            System.out.println("Server Communication(sending) Failed for " + msg + " " + e.getMessage());
        } finally {
        }

    }
}

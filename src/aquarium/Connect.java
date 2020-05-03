/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aquarium;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Knytik
 */
public class Connect extends Thread{
    
    Socket socket;
    ObjectOutputStream ostream;
    boolean isActive = true;
    
    public Connect() {        
        
        try {
            
            socket = new Socket("localhost", 6100);
        } catch (IOException ex) {
            Logger.getLogger(Connect.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        start();
    }
    
    @Override
    public void run() {
        try {
            while (isActive) {
                int request = socket.getInputStream().read();
                switch (request) {
                    case 0:
                        try {
                            System.out.println("запрос от сервера 0");
                        } catch (Exception ex) {
                            System.out.println("запрос от сервера 0 провал");
                        }
                        break;   
                }
            }
        } 
        catch (IOException ex) {
            System.out.println("Exception at ClientListener 58");
            System.err.println(ex);
        }
    }

}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

/**
 *
 * @author Knytik
 */
public class Server extends Thread {

    public static ArrayList<Connect> sockets = new ArrayList<>();    
    ServerSocket serverSocket;
    final static int port = 6100;
    final static String ip = "localhost";

    boolean isActive = true;

    Server() throws IOException {

        serverSocket = new ServerSocket(port);
        start();
    }

    @Override
    public void run() {
        try {
            Main.area.append("Сервер активен\n");
            while (isActive) {
                Socket socket = serverSocket.accept();
                sockets.add(new Connect(socket));
                Main.area.append("Подключение нового клиента: " + socket.getPort() +"\n");
                
            }
        } catch (IOException ex) {
            Main.area.append("Не удалось запустить сервер\n");
        }
    }
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server;

import java.io.*;
import java.net.Socket;
import java.util.Iterator;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.omg.CORBA.TIMEOUT;

public class Connect extends Thread {

    Socket socket;
    DataOutputStream out;
    DataInputStream in;

    public Connect(Socket socket) {
        try {
            this.socket = socket;
            start();

            out = new DataOutputStream(socket.getOutputStream());
        } catch (IOException ex) {
            Logger.getLogger(Connect.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void run() {
        try {
            in = new DataInputStream(socket.getInputStream());
            while (!socket.isClosed()) {
                String request = in.readUTF();
                Main.area.append("Запрос от клиента: " + socket.getPort() + ",команда: " + request + "\n");
                switch (request.toLowerCase()) {                    
                    case "config": {
                        String buf = in.readUTF();
                        Main.area.append("Запрос от:" + socket.getPort() + " к " + buf + "\n");
                        for(Connect f : Server.sockets){
                            System.out.println(f.socket.getPort() + " " + buf );
                            if(f.socket.getPort() == Integer.valueOf(buf)){
                                f.out.writeUTF("get config");
                                f.out.writeUTF(String.valueOf(socket.getPort()));
                                Main.area.append("Запрос выполнен\n");
                                break;
                            }
                        }
                        
                        break;
                    }
                    case "disconnect":
                        Main.area.append(socket.getPort() + " Отключается\n");
                        in.close();
                        out.close();
                        Iterator<Connect> socketIterator = Server.sockets.iterator();
                        while(socketIterator.hasNext()){
                            Connect nextConnect = socketIterator.next();
                            if(nextConnect.socket == socket){
                                socketIterator.remove();
                            }
                        }
//                        Server.sockets.remove(MIN_PRIORITY);
                        break;
                    case "new connect":
                        Main.area.append("Активация пользователя\n");
                        out.writeUTF("set list");
                        out.writeUTF(String.valueOf(Server.sockets.size()));
                        Main.area.append("Колличество соединений: " + Server.sockets.size() + "\n");
                        for (Connect f : Server.sockets) {
                            if (f.socket == socket) {
                                continue;
                            }
                            out.writeUTF(String.valueOf(f.socket.getPort()));
                            f.out.writeUTF("new connect");
                            f.out.writeUTF(String.valueOf(socket.getPort()));   
                        }                        
                        Main.area.append("Активация выполнена\n");                        
                        break; 
                    case "file":
                       
                        int buf = Integer.valueOf(in.readUTF());
                        //String buf1 = in.readUTF();
                        String [] buf1 = new String[6];
                        for(int i = 0; i<6;i++)
                            buf1[i] = in.readUTF();
                        for(Connect f : Server.sockets){
                            System.out.println(f.socket.getPort() + " " + buf );
                            if(f.socket.getPort() == buf){
                               // System.out.println(buf1);
                                f.out.writeUTF("set config");
                                for(int i = 0; i<6;i++)
                                f.out.writeUTF(buf1[i]);
                                Main.area.append("Запрос от:" + f.socket.getPort() + " к " + buf + "выполнен\n");
                            }
                        }              
                        break;
                    default:
                        Main.area.append("Неизвестная команда: " + request);
                        break;
                        
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}

package Server;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class ServerMain {

    static private int serverPort;
    static private InetAddress inetAddress;
    static private ArrayList<ServerConnection> connectionsList = new ArrayList<>();

    public static void main(String[] args) {

        serverPort = 7777;

        try {
            ServerSocket serverSocket = new ServerSocket(serverPort, 50, inetAddress);
            System.out.println("Server started.");

            while (true){
                Socket socket = serverSocket.accept();
                ServerConnection serverConnection = new ServerConnection(socket, connectionsList);
                connectionsList.add(serverConnection);
                connectionsList.get((connectionsList.size()-1)).getThread().start();

                System.out.println("Client connected");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
package Server;

import com.google.gson.Gson;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.UUID;

public class ServerConnection implements Runnable{
    private Thread thread;
    private Socket socket;
    private Scanner scanner;
    private PrintWriter printWriter;
    private Gson gson;
    private UUID connectionId;
    private boolean running;
    private ArrayList<ServerConnection> connectionsList;

    ServerConnection(Socket socket, ArrayList<ServerConnection> connectionsList){
        this.connectionsList = connectionsList;
        this.thread = new Thread(this);
        this.socket = socket;
        this.gson = new Gson();
        this.connectionId = UUID.randomUUID();
        this.running = true;

        try{
            this.scanner = new Scanner(this.socket.getInputStream());
            this.printWriter = new PrintWriter(this.socket.getOutputStream(), true);

            sendUuid();
            sendThisToClients();
            sendClientsToThis();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void sendUuid(){
        DataAction dataAction = new DataAction("generatedID");
        dataAction.setUuid(connectionId);
        printWriter.println(gson.toJson(dataAction, DataAction.class));
    }

    private void sendThisToClients(){
        for (int i = 0; i < connectionsList.size(); i++){
            if(connectionsList.get(i).getConnectionId() != this.connectionId){
                DataAction dataAction = new DataAction("newConnected");
                dataAction.setUuid(connectionId);
                connectionsList.get(i).getPrintWriter().println(gson.toJson(dataAction, DataAction.class));
            }
        }
    }

    private void sendClientsToThis(){
        if(connectionsList.size() >= 1){
            ArrayList<UUID> uuids = new ArrayList<>();
            for (int i = 0; i < connectionsList.size(); i++){
                if(connectionsList.get(i).getConnectionId() != this.connectionId){
                    uuids.add(connectionsList.get(i).getConnectionId());
                }
            }
            DataAction dataAction = new DataAction("allConnected");
            dataAction.setUuids(uuids);
            printWriter.println(gson.toJson(dataAction, DataAction.class));
        }
    }

    private void disconnectClient(){
        for(int i = 0; i < connectionsList.size(); i++){
            if(connectionsList.get(i).getConnectionId() != this.connectionId){
                DataAction dataAction = new DataAction("oldDisconnected");
                dataAction.setUuid(connectionId);
                connectionsList.get(i).getPrintWriter().println(gson.toJson(dataAction, DataAction.class));
            }
        }

        for(int i = 0; i < connectionsList.size(); i++){
            if(connectionsList.get(i).getConnectionId() == this.connectionId){
                connectionsList.remove(i);
            }
        }

        running = false;
    }

    private void askSettingsFrom(UUID sender, UUID asker){
        for(int i = 0; i < connectionsList.size(); i++){
            if(connectionsList.get(i).getConnectionId().equals(sender)){
                DataAction dataAction = new DataAction("giveSettings");
                dataAction.setAsker(asker);
                connectionsList.get(i).getPrintWriter().println(gson.toJson(dataAction, DataAction.class));
                System.out.println("OPP");
            }
        }

        System.out.println("Get from: " + sender + "   To: " + asker);
    }

    private void sendSettingsTo(DataAction dataAction){
        for(int i = 0; i < connectionsList.size(); i++){
            if(connectionsList.get(i).getConnectionId().equals(dataAction.getAsker())){
                dataAction.setCommand("finalSettings");
                connectionsList.get(i).getPrintWriter().println(gson.toJson(dataAction, DataAction.class));

                System.out.println("Sended To: " + dataAction.getAsker());

                System.out.println(dataAction.getGuppyInterval());
                System.out.println(dataAction.getGuppyLifeTime());
                System.out.println(dataAction.getGuppyProbability());
            }
        }
    }

    @Override
    public void run() {
        while(scanner.hasNextLine() && running){
            while (scanner.hasNext()) {
                String gsonTransaction = scanner.next();
                DataAction dataAction = gson.fromJson(gsonTransaction, DataAction.class);

                switch (dataAction.getCommand()) { //считывает строку и переделывает в команду
                    case "disconnectClient" :
                        disconnectClient();
                        break;
                    case "getSettingsFrom" :
                        askSettingsFrom(dataAction.getSender(), dataAction.getAsker());
                        break;
                    case "sendSettingsTo" :
                        System.out.println("sendSettingsTo");
                        sendSettingsTo(dataAction);
                        break;
                    default:
                        break;
                }
                scanner.nextLine();
                break;
            }
        }
    }

    public Thread getThread() {
        return thread;
    }

    public UUID getConnectionId() {
        return connectionId;
    }

    public PrintWriter getPrintWriter() {
        return printWriter;
    }
}
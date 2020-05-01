package Client3;

import Server.DataAction;
import com.google.gson.Gson;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.Scanner;
import java.util.UUID;

public class ClientSocket implements Runnable {

    private Thread thread;
    private Socket socket;
    private Scanner scanner;
    private PrintWriter printWriter;
    private Gson gson;
    private UUID clientID;
    private boolean running;

    private String ip;
    private int port;

    private MainForm mainForm;

    public ClientSocket(MainForm mainForm){
        try {
            this.mainForm = mainForm;

            ip = "localhost";
            port = 7777;
            socket = new Socket();
            socket.connect(new InetSocketAddress(ip, port), 2000);

            thread = new Thread(this);
            this.scanner = new Scanner(this.socket.getInputStream());
            this.printWriter = new PrintWriter(this.socket.getOutputStream(), true);

            gson = new Gson();
            running = true;
            thread.start();

            mainForm.getConnectButton().setEnabled(false);
            mainForm.getDisconnectButton().setEnabled(true);
            mainForm.getImportButton().setEnabled(true);
        } catch (
            IOException e){
            e.printStackTrace();
        }
    }

    public void disconnect(){
        DataAction dataAction = new DataAction("disconnectClient");
        printWriter.println(gson.toJson(dataAction, DataAction.class));
        running = false;
    }

    public void importFrom(UUID uuidSender){
        DataAction dataAction = new DataAction("getSettingsFrom");
        dataAction.setAsker(clientID);
        dataAction.setSender(uuidSender);
        printWriter.println(gson.toJson(dataAction, DataAction.class));
    }

    public void giveSettingsTo(UUID asker){
        DataAction dataAction = new DataAction("sendSettingsTo");
        dataAction.setAsker(asker);

        dataAction.setGuppyInterval(mainForm.getSettingsPanel().getGuppyIntervalTextField().getText());
        dataAction.setGuppyProbability(mainForm.getSettingsPanel().getGuppyProbabilitySlider().getValue());
        dataAction.setGuppyLifeTime(mainForm.getSettingsPanel().getGuppyLifeTimeTextField().getText());

        dataAction.setGoldenInterval(mainForm.getSettingsPanel().getGoldenIntervalTextField().getText());
        dataAction.setGoldenProbability(mainForm.getSettingsPanel().getGoldenProbabilityComboBox().getSelectedIndex());
        dataAction.setGoldenLifeTime(mainForm.getSettingsPanel().getGoldenLifeTimeTextField().getText());

        printWriter.println(gson.toJson(dataAction, DataAction.class));

        System.out.println("Otpravil");
    }

    @Override
    public void run() {
        while(scanner.hasNextLine() && running){
            while (scanner.hasNext()) {
                String gsonTransaction = scanner.next();
                DataAction dataAction = gson.fromJson(gsonTransaction, DataAction.class);

                switch (dataAction.getCommand()) {
                    case "generatedID" :
                        setClientID(dataAction.getUuid());
                        mainForm.setTitle("Aquarium. CurrentID: " + clientID.toString());
                        //System.out.println(clientID);
                        break;
                    case "newConnected" :
                        //System.out.println("newConnected");
                        mainForm.addConnected(dataAction.getUuid());
                        break;
                    case "allConnected" :
                        //System.out.println("allConnected");
                        mainForm.addAllConnected(dataAction.getUuids());
                        break;
                    case "oldDisconnected" :
                        mainForm.deleteDisconnected(dataAction.getUuid());
                        break;
                    case "giveSettings" :
                        //System.out.println("giveSettings");
                        giveSettingsTo(dataAction.getAsker());
                        break;
                    case "finalSettings" :
                        mainForm.setSettings(dataAction);
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

    public PrintWriter getPrintWriter() {
        return printWriter;
    }

    public UUID getClientID() {
        return clientID;
    }

    public void setClientID(UUID clientID) {
        this.clientID = clientID;
    }
}

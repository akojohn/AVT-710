/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aquarium;

import java.io.*;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;

/**
 *
 * @author Knytik
 */
public class Client extends Thread {

    Socket socket;
    boolean isActive = true;
    JFrame jclient;  
    static JComboBox area;
    JButton button_get_config, button_disconnect, button_xz;

    DataInputStream ois;
    DataOutputStream oos;

    public Client(String host, int port) {
        try {
            jclient = new JFrame("Client");
            jclient.setSize(300, 300);
            jclient.setLocationRelativeTo(null);
            jclient.setResizable(false);
            jclient.setVisible(true);
            jclient.setLayout(null);

            area = new JComboBox();
            area.setBounds(10, 10, 150, 25);
            area.setEditable(false);
            
            button_get_config = new JButton("get config");
            button_get_config.setBounds(190, 10, 100, 25);

            button_disconnect = new JButton("disconnect");
            button_disconnect.setBounds(190, 40, 100, 25);
            //button_xz = new JButton("Проверка");
            //button_xz.setBounds(190, 70, 100, 25);

            //jclient.add(button_xz);
            //button_xz.addActionListener(e -> new_connect());
            jclient.add(button_get_config);
            button_get_config.addActionListener(e -> get_config());
            jclient.add(button_disconnect);
            button_disconnect.addActionListener(e -> disconect());
            jclient.add(area);
            
            jclient.update(jclient.getGraphics());
            socket = new Socket(host, port);
            oos = new DataOutputStream(socket.getOutputStream());
            
            oos.writeUTF("new connect");
            
            Main.button_Server.setEnabled(false);
            Main.button_get_connect.setEnabled(true);

        } catch (IOException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }
        start();
        //System.out.println(Server.serverSocket);        
    }

    @Override
    public void run() {
        try {
            ois = new DataInputStream(socket.getInputStream());
            while (isActive) {
                String comand = ois.readUTF();
                switch (comand) {
                    case "set list":
                        area.removeAll();
                        int i = Integer.valueOf(ois.readUTF()) - 1;
                        System.out.println("Получение списка соединений\nЧисло доступных соединений: " + i);
                        for(; i != 0; i--){
                            area.addItem(ois.readUTF());                            
                        }
                        break;
                    case "new connect":
                            area.addItem(ois.readUTF());
                        break;
                    case "get config":
                        oos.writeUTF("file");
                        String port_1 = ois.readUTF();
                        oos.writeUTF(port_1);
                        {
                        oos.writeUTF(String.valueOf(Gold.P1));
                        oos.writeUTF(String.valueOf(Gold.N1));
                        oos.writeUTF(String.valueOf(Gold.life_time));
                        oos.writeUTF(String.valueOf(Guppy.P2));
                        oos.writeUTF(String.valueOf(Guppy.N2));
                        oos.writeUTF(String.valueOf(Guppy.life_time));
                        
                        }
                        System.out.println("Ответ отправлен");
                        break;
                    case "set config":     
                        {
                        Gold.P1 = Float.parseFloat(ois.readUTF());
                        Gold.N1 = Float.parseFloat(ois.readUTF());
                        Gold.life_time = Float.parseFloat(ois.readUTF());
                        Guppy.P2 = Float.parseFloat(ois.readUTF());
                        Guppy.N2 = Float.parseFloat(ois.readUTF());
                        Guppy.life_time = Float.parseFloat(ois.readUTF());
                        
                        Main.txt_spawn_time_Gold.setText(String.valueOf(Gold.N1));
                        Main.txt_spawn_time_Guppy.setText(String.valueOf(Guppy.N2));
                        Main.txt_life_time_Gold.setText(String.valueOf(Gold.life_time));
                        Main.txt_life_time_Guppy.setText(String.valueOf(Guppy.life_time));
                        Main.slider_chance_Gold.setValue( (int)(Gold.P1*100));
                        Main.slider_chance_Guppy.setValue((int)(Guppy.P2*100));
                        }
                        System.out.println("Данные от " + area.getSelectedItem().toString() + " получены: ");
                        break;
                    default:
                        System.out.println("Неизвестная команда" + comand);
                        break;
                }

            }
        } catch (IOException ex) {
            System.out.println("Соединение разорвано");
            Main.button_Server.setEnabled(true);
        }
    }

    public void get_config() {
        try {
            oos.writeUTF("config");
            oos.writeUTF(area.getSelectedItem().toString());
        } catch (IOException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void disconect() {
        try {
            isActive = false;
            oos.writeUTF("disconnect");
            jclient.dispose();
        } catch (IOException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }
    }  
}

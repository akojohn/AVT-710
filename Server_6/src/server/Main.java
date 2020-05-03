/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server;

import java.io.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;

/**
 *
 * @author Knytik
 */
public class Main extends JFrame{

    static JFrame frame;
    static JTextArea area;
    static Server server;
    JScrollPane scrollPane;
    Main(){
        super("Server");
        setSize(300, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);          
        setVisible(true);
        setResizable(false);
        area = new JTextArea();
        area.setEditable(false);
        scrollPane = new JScrollPane(area);
        add(scrollPane);
        add(area);
       
    }
    
    
    public static void main(String args[])
    {
        frame = new Main();
        
        try {        
            server = new Server();
        } catch (IOException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }    
}

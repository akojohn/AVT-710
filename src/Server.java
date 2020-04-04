import java.awt.*;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.LinkedList;
import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Server extends javax.swing.JFrame {
    final static int port = 6100;
    final static String ip = "127.0.0.1";

    //region ServerSideConnect
    class ServerSideConnect extends Thread {        // Класс - поток, управляющий соединением
        Socket sk = null;                 // Сокет соединения
        ObjectInputStream ois;            // Двоичные потоки данных на сокете
        ObjectOutputStream oos;

        Pattern commandPattern = Pattern.compile("^ *?s([0-9]{4,5})$", Pattern.MULTILINE);

        ServerSideConnect(Socket sk0) {
            try {                         // Запомнить полученный сокет
                sk = sk0;                 // и открыть на нем потоки даннных
                oos = new ObjectOutputStream(sk.getOutputStream());
                ois = new ObjectInputStream(sk.getInputStream());
                synchronized (serverSideConnectList) {    // Добавить СЕБЯ в вектор
                    serverSideConnectList.add(this);
                }
                start();                // Стартануть собственный поток - run
                out.append("Порт " + sk.getPort() + ", соединение установлено\n");
            } catch (Exception ex) {
                out.append("1:" + ex.toString() + "\n");
            }
        }

        LinkedList<Employee> tmpList = null;
        public void run() {                     // Функционал потока - прием и рассылка сообщений
            try {
                while (true) {
                    String serverMessage = (String) ois.readObject();   // Читать строку из входного потока данных

                    if (serverMessage.length() == 0) {                  // Пустая строка - сообщение о разрыве соединения
                        close();
                        break;
                    }

                    out.append("Полученна команда " + serverMessage + "\n");

                    Matcher match = commandPattern.matcher(serverMessage);

                    /** Первый этап передачи **/
                    if (match.find()) {
                        ServerSideConnect secondConnect = null;
                        int portSecondClient = Integer.valueOf(match.group(1));
                        for (ServerSideConnect con : serverSideConnectList) {
                            if (con.sk.getPort() == portSecondClient) {
                                secondConnect = con;
                                break;
                            }
                        }
                        out.append(String.format("Передача от %d к %d\n", sk.getPort(), secondConnect.sk.getPort()));

                        this.oos.writeObject("out");
                        this.oos.writeObject("\nПередача данных к: " + secondConnect.sk.getPort() + "\n");

                        secondConnect.oos.writeObject("in");
                        secondConnect.oos.writeObject("\nПрием данных от: " + this.sk.getPort() + "\n");

                        // Данные от первого клиента. По сообщению out
                        tmpList = (LinkedList<Employee>)ois.readObject();
                        out.append(String.format("Получил данные от %d, размер %d\n", this.sk.getPort(), tmpList.size()));

                        // Данные ко второму клиенту. По сообщению in
                        secondConnect.oos.writeObject(tmpList);
                        secondConnect.oos.flush();
                        out.append(String.format("Отправил данные к %d,  размер %d\n", secondConnect.sk.getPort(), tmpList.size()));
                    }

                    /** Второй этап передачи **/
                    if (serverMessage.equals("swap")) {
                        int portFirstClient = (int) ois.readObject();
                        ServerSideConnect firstConnect = null;
                        for (ServerSideConnect con : serverSideConnectList) {
                            if (con.sk.getPort() == portFirstClient) {
                                firstConnect = con;
                                break;
                            }
                        }
                        out.append(String.format("Передача от %d к %d\n", sk.getPort(), firstConnect.sk.getPort()));

                        tmpList = (LinkedList<Employee>)ois.readObject();
                        out.append(String.format("Получил данные от %d, размер %d\n", this.sk.getPort(), tmpList.size()));

                        firstConnect.oos.writeObject(tmpList);
                        firstConnect.oos.flush();
                        out.append(String.format("Отправил данные к %d,  размер %d\n", firstConnect.sk.getPort(), tmpList.size()));

                        firstConnect.oos.writeObject("К 2 переданы все данные\n");
                        oos.writeObject("К 1 переданны все данные\n");
                    }
                    tmpList = null;
                }
            } catch (Exception ex) {
                ex.printStackTrace();
                out.append("Клиент разорвал соединение\n");
                close();
            }
        }

        public void close() {            // Закрыть соединение
            synchronized (serverSideConnectList) {
                try {
                    serverSideConnectList.remove(this);  // Удалить себя из вектора
                    out.append("Порт " + sk.getPort() + ", соединение закрыто\n");
                    sk.close();         // Закрыть сокет и потоки данных
                    sk = null;
                    ois = null;
                    oos = null;

                    printServerList(serverSideConnectList);// Для всех объектов - соединений
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        }


    }
    //endregion

    static void printServerList(Vector<ServerSideConnect> serverSideConnectList) {
        synchronized (serverSideConnectList) {
            try {
                for (ServerSideConnect connect : serverSideConnectList) {
                    connect.oos.writeObject("List:\n");
                    for (ServerSideConnect item : serverSideConnectList)
                        connect.oos.writeObject("\tПорт: " + item.sk.getPort() + "\n");
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private Vector<ServerSideConnect> serverSideConnectList = new Vector();      // Вектор объектов - соединений

    Thread listen = new Thread() {                                     // Поток для ожидания соедиенений
        public void run() {
            try {
                ServerSocket srv = new ServerSocket(Server.port);   // Объект для ожидания соединений
                while (true) {                                      // Цикл ожидания
                    Socket socket = srv.accept();                   // Получить очередное соединение
                    new ServerSideConnect(socket);                  // Создать объект-соединение
                    printServerList(serverSideConnectList);         // Для всех объектов - соединений
                }
            } catch (Exception ex) {
                ex.printStackTrace();
                out.append("Не удалось установить соединение\n");
            }
        }
    };

    /**** Создание новой формы Server ****/
    private java.awt.TextArea out;

    private Server() {
        initComponents();
        this.setBounds(400, 800, 450, 300);
        this.setTitle("Сервер");
        listen.start();
    }

    private void initComponents() {
        out = new java.awt.TextArea();
        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });
        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(out);

        pack();
    }

    private void formWindowClosing(java.awt.event.WindowEvent evt) {
        synchronized (serverSideConnectList) {       // Для всех объектов - соединений
            while (serverSideConnectList.size() != 0) {
                ServerSideConnect cc = serverSideConnectList.get(0);
                try {               //
                    cc.oos.writeObject("");
                    cc.oos.flush();
                } catch (Exception ee) {
                    cc.stop();          // Остановить поток
                    cc.close();         // Закрыть в объекте сокет и потоки данных
                }                       // и удалить из вектора
            }
            listen.stop();
        }
    }


    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> new Server().setVisible(true));
    }
}

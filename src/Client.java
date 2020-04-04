import javax.swing.*;
import java.awt.*;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Client extends javax.swing.JFrame {

    class Listen extends Thread {
        String serverMessage;

        public void run() {
            try {
                while(true) {
                    serverMessage = (String)ois.readObject();
                    out.append(serverMessage);

                    if (serverMessage.length() == 0) {
                        close();
                        break;
                    }
                    view.pauseSim();

                    /** Второй клиент сначала принимает данные, а потом кидает **/
                    if(serverMessage.equals("in")) {
                        serverMessage = (String)ois.readObject();
                        out.append(serverMessage);

                        synchronized (_objectCollection) {
                            LinkedList<Employee> workerList = new LinkedList<>();
                            for (Employee emp : _objectCollection)
                                if (emp instanceof Worker)
                                    workerList.add(emp);
                            for (Iterator<Employee> iterator = _objectCollection.iterator(); iterator.hasNext(); ) {
                                Employee temp = iterator.next();
                                if (temp instanceof Worker)
                                    iterator.remove();
                                Employee.Amount--;
                                Worker.Amount--;
                            }



                        Pattern re = Pattern.compile("(\\b\\d{1,7}\\b)", Pattern.MULTILINE);
                        Matcher match = re.matcher(serverMessage);
                        match.find();
                        int port = Integer.valueOf(match.group(1));

                        _objectCollection.addAll((LinkedList<Employee>)ois.readObject());
                        out.append("Принял коллекцию от 1, размер " + _objectCollection.size() + "\n");

                        out.append("Закончил прием, теперь передаю свои объекты\n");

                        oos.writeObject("swap");
                        oos.writeObject(port);

                        oos.writeObject(workerList);
                        oos.flush();
                        out.append("Отправил коллекцию к 1, размер " + workerList.size() + "\n");

                        out.append("Обмен закончен, in\n");
                        correctBornTime();
                        }
                    }

                    /** Первый клиент сначала кидает данные, а потом принимает **/
                    if(serverMessage.equals("out")) {
                        serverMessage = (String)ois.readObject();
                        out.append(serverMessage);

                        synchronized (_objectCollection) {
                            LinkedList<Employee> managersList = new LinkedList<>();
                            for (Employee emp : _objectCollection)
                                if (emp instanceof Manager)
                                    managersList.add(emp);
                            for (Iterator<Employee> iterator = _objectCollection.iterator(); iterator.hasNext(); ) {
                                Employee temp = iterator.next();
                                if (temp instanceof Manager)
                                    iterator.remove();
                                Employee.Amount--;
                                Manager.Amount--;
                            }

                            oos.writeObject(managersList);         // Первый отдал свою коллекцию
                            oos.flush();
                            out.append("Отправил коллекцию к 2, размер " + _objectCollection.size() + "\n");

                            _objectCollection.addAll((LinkedList<Employee>) ois.readObject());       // Получил коллекцию от второго
                            out.append("Принял коллекцию от 2, размер " + _objectCollection.size() + "\n");

                            out.append("Обмен закончен, out\n");
                            correctBornTime();
                        }
                    }

                    view.continueSim();
                }
            } catch (Exception ex) {
                ex.printStackTrace();
                out.append("Соединение с сервером было разорвано\n");
                close();
            }
        }
    }
    //---------------------------------------------------------



    private LinkedList<Employee> _objectCollection;
    private HabitatView view;
    private Listen listen = null;
    private Socket sk = null;
    private ObjectInputStream ois;
    private ObjectOutputStream oos;

    public void correctBornTime() {
        Employee.Amount = _objectCollection.size();
        Worker.Amount = 0;
        Manager.Amount = 0;

        for (Employee t : _objectCollection) {
            t.setBornTime(HabitatView.ElapsedTime);
            if (t instanceof Worker)
                Worker.Amount++;
            else
                Manager.Amount++;
        }
    }

    public void connect(){
        try {
            sk = new Socket(Server.ip, Server.port);
            oos = new ObjectOutputStream(sk.getOutputStream());
            ois = new ObjectInputStream(sk.getInputStream());
            listen = new Listen();
            listen.start();
            out.append("Порт " + sk.getLocalPort() + ", соединение установлено\n");
            this.setTitle("Client " + sk.getLocalPort());
        } catch (Exception ex) {
            ex.printStackTrace();
            out.append("Не удалось установить соединение с сервером\n");
            close();
        }
    }

    public void close(){            // Закрыть соединение
        try {
            if (sk == null) return;
            out.append("Порт " + sk.getLocalPort() + ", соединение закрыто\n");
            sk.close();             // Закрыть сокет и потоки
            sk = null;
            ois = null;
            oos = null;
        } catch(Exception ex) {
            ex.printStackTrace();
        }
    }

    public Client(LinkedList<Employee> objectCollection, HabitatView view) {
        this._objectCollection = objectCollection;
        this.view = view;
        initComponents();
        this.setBounds(this.view.getX(), view.getY(), 450, 300);
        this.setTitle("Client");
        connect();
    }

    private java.awt.Button btnSend = new java.awt.Button("Send");
    private java.awt.Button btnConnect = new java.awt.Button("Connect");
    private java.awt.TextField in= new java.awt.TextField();
    private java.awt.TextArea out = new java.awt.TextArea();

    private void initComponents() {
        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });
        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(out, BorderLayout.CENTER);

        JPanel panelUI = new JPanel(new BorderLayout());
        getContentPane().add(panelUI, BorderLayout.SOUTH);
        panelUI.add(in);
        JPanel panelButtons = new JPanel(new FlowLayout());
        btnConnect.addActionListener(this::btnConnectActionPerformed);
        btnSend.addActionListener(this::btnSendActionPerformed);
        panelButtons.add(btnSend);
        panelButtons.add(btnConnect);
        panelUI.add(panelButtons, BorderLayout.EAST);

        pack();
    }

    private void formWindowClosing(java.awt.event.WindowEvent evt) {
        try {
            listen.stop();
            synchronized (out) {
                if (sk != null) {
                    oos.writeObject("");
                    oos.flush();
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            out.append("3:" + ex.toString()+"\n");
        } finally {
            close();
        }
    }

    private void btnSendActionPerformed(java.awt.event.ActionEvent evt) {
        try {
            String commandMessage = in.getText();
            if (commandMessage.length() == 0 || oos == null)
                return;
            out.append("Отправленна команда " + commandMessage + "\n");
            oos.writeObject(commandMessage);
            oos.flush();
            view.pauseSim();
        } catch (Exception ex) {
            ex.printStackTrace();
            out.append("Ошибка при передачи команды\n");
            close();
        }
    }

    private void btnConnectActionPerformed(java.awt.event.ActionEvent evt) {
        synchronized (out){
            if (sk != null) return;
            connect();
        }
    }
}

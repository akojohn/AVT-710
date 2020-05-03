
package aquarium;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Timer;
import java.util.TimerTask;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSlider;
import javax.swing.JTextArea;
import javax.swing.JTextField;

/**
 *
 * @author Dexp
 */
public class HabitatView extends JPanel {

    static Habitat habitat;
    public static int widthImg = 50;
    public static int heigthImg = 70;

    private double startTime;
    private double pauseTime;
    public static double ElapsedTime;
    static Timer timer;
    private double deltaTime;
    static boolean show = true;
    static boolean simulating = false;
    static boolean firstRun = true;
    Image img1;
    Image img2;
    Image imgBG;
    int show_info;
    int show_obj = 1;
    public static int Width;
    public static int Height;
    private Frame frame_list;
    private float _refTime = 5F;
    private float _refTimer = _refTime;
    Image imgGuppy_up;
    Image imgGuppy_down;
    Image imgGold_right;
    Image imgGold_left;

    //JPanel panel;
    public HabitatView() {

        try {
            img1 = ImageIO.read(new File("Gold.png"));
            img2 = ImageIO.read(new File("Guppy.png"));
            imgBG = ImageIO.read(new File("BG.jpg"));
            imgGuppy_up = ImageIO.read(new File("Guppy_up.png"));
            imgGuppy_down = ImageIO.read(new File("Guppy_down.png"));
            imgGold_left = ImageIO.read(new File("Gold_left.png"));
            imgGold_right = ImageIO.read(new File("Gold_right.png"));
        } catch (IOException ex) {
            Logger.getLogger(HabitatView.class.getName()).log(Level.SEVERE, null, ex);
        }
        imgGuppy_up = imgGuppy_up.getScaledInstance(widthImg, heigthImg, Image.SCALE_DEFAULT);
        imgGuppy_down = imgGuppy_down.getScaledInstance(widthImg, heigthImg, Image.SCALE_DEFAULT);
        imgGold_left = imgGold_left.getScaledInstance(widthImg, heigthImg, Image.SCALE_DEFAULT);
        imgGold_right = imgGold_right.getScaledInstance(widthImg, heigthImg, Image.SCALE_DEFAULT);
        img1 = img1.getScaledInstance(widthImg, heigthImg, Image.SCALE_DEFAULT);
        img2 = img2.getScaledInstance(widthImg, heigthImg, Image.SCALE_DEFAULT);
        initComponents();
        repaint();
        HabitatView.Width = this.getWidth();
        HabitatView.Height = this.getHeight();
        System.out.println(Height);
        System.out.println(Width);
        addMouseListener(new PanelMouseListener());
        addKeyListener((KeyListener) new processKeyEvent());
      
    }

    private void initComponents() {
        Main.rbutton_show_time.setSelected(true);
        Main.slider_chance_Gold.setValue((int) (Gold.P1 * 100));
        Main.slider_chance_Guppy.setValue((int) (Guppy.P2 * 100));
        Main.txt_spawn_time_Gold.setText(String.valueOf(Gold.N1));
        Main.txt_spawn_time_Guppy.setText(String.valueOf(Guppy.N2));
        Main.txt_life_time_Gold.setText(String.valueOf(Gold.life_time));
        Main.txt_life_time_Guppy.setText(String.valueOf(Guppy.life_time));

        Main.button_start.addActionListener(e -> startSim());
        Main.panel.add(Main.button_start);
        Main.button_stop.addActionListener(e -> stopSim());
        Main.panel.add(Main.button_stop);

        Main.show_list.addActionListener(e -> show_list_object());
        Main.panel.add(Main.show_list);
        Main.rbutton_show_time.addActionListener(e -> show = true);
        Main.rbutton_hide_time.addActionListener(e -> show = false);
        Main.button_group.add(Main.rbutton_show_time);
        Main.button_group.add(Main.rbutton_hide_time);
        Main.panel.add(Main.rbutton_show_time);
        Main.panel.add(Main.rbutton_hide_time);

        Main.slider_chance_Gold.setBorder(BorderFactory.createTitledBorder("Шанс \"Gold\""));
        Main.slider_chance_Gold.setPreferredSize(new Dimension(200, 80));
        Main.slider_chance_Gold.setMajorTickSpacing(10);
        Main.slider_chance_Gold.setPaintTicks(true);
        Main.slider_chance_Gold.setPaintLabels(true);
        Main.slider_chance_Gold.addChangeListener(e -> Gold.P1 = (((JSlider) e.getSource()).getValue()) / 100f);
        Main.panel.add(Main.slider_chance_Gold);

        Main.slider_show_info.addChangeListener(e -> show_info = (((JSlider) e.getSource()).getValue()));

        Main.slider_chance_Guppy.setBorder(BorderFactory.createTitledBorder("Шанс \"Guppy\""));
        Main.slider_chance_Guppy.setPreferredSize(new Dimension(200, 80));
        Main.slider_chance_Guppy.setMajorTickSpacing(10);
        Main.slider_chance_Guppy.setPaintTicks(true);
        Main.slider_chance_Guppy.setPaintLabels(true);
        Main.slider_chance_Guppy.addChangeListener(e -> Guppy.P2 = (((JSlider) e.getSource()).getValue()) / 100f);
        Main.panel.add(Main.slider_chance_Guppy);

        Main.txt_spawn_time_Gold.setName("Spawn_Gold");
        Main.txt_spawn_time_Gold.setBorder(BorderFactory.createTitledBorder("Время создания \"Gold\""));
        Main.txt_spawn_time_Gold.setPreferredSize(new Dimension(200, 40));
        Main.txt_spawn_time_Gold.addActionListener(new Txt_name_ActionListener());
        Main.panel.add(Main.txt_spawn_time_Gold);

        Main.txt_spawn_time_Guppy.setName("Spawn_Guppy");
        Main.txt_spawn_time_Guppy.setBorder(BorderFactory.createTitledBorder("Время создания \"Guppy\""));
        Main.txt_spawn_time_Guppy.setPreferredSize(new Dimension(200, 40));
        Main.txt_spawn_time_Guppy.addActionListener(new Txt_name_ActionListener());
        Main.panel.add(Main.txt_spawn_time_Guppy);

        Main.txt_life_time_Gold.setName("life_time_Gold");
        Main.txt_life_time_Gold.setBorder(BorderFactory.createTitledBorder("Время жизни \"Gold\""));
        Main.txt_life_time_Gold.setPreferredSize(new Dimension(200, 40));
        Main.txt_life_time_Gold.addActionListener(new Txt_name_ActionListener());
        Main.panel.add(Main.txt_life_time_Gold);

        Main.txt_life_time_Guppy.setName("life_time_Guppy");
        Main.txt_life_time_Guppy.setBorder(BorderFactory.createTitledBorder("Время жизни \"Guppy\""));
        Main.txt_life_time_Guppy.setPreferredSize(new Dimension(200, 40));
        Main.txt_life_time_Guppy.addActionListener(new Txt_name_ActionListener());
        Main.panel.add(Main.txt_life_time_Guppy);

        Main.menu.add(Main.start_menu);
        Main.menu.add(Main.stop_menu);
        Main.start_menu.addActionListener(e -> startSim());
        Main.stop_menu.addActionListener(e -> stopSim());
        Main.menuBar.add(Main.menu);

        Main.start_menu.setEnabled(true);
        Main.button_start.setEnabled(true);
        Main.stop_menu.setEnabled(false);
        Main.button_stop.setEnabled(false);

        Main.load_files.addActionListener(e -> load_file());
        Main.panel.add(Main.load_files);
        Main.save_files.addActionListener(e -> save_file());
        Main.panel.add(Main.save_files);
        Main.console.addActionListener(e-> new Console(Main.frame));
        Main.panel.add( Main.console);

        Main.switchGoldAI.addActionListener(e -> {
            if (Main.switchGoldAI.isSelected() == true) {
                synchronized (habitat.ThreadGoldAI.locker) {
                    habitat.ThreadGoldAI.sleeping = false;
                    habitat.ThreadGoldAI.locker.notify();
                }
            } else {
                habitat.ThreadGoldAI.sleeping = true;
            }
        });
        Main.panel.add(Main.switchGoldAI);
        
        Main.switchGuppyAI.addActionListener(e -> {
            if (Main.switchGuppyAI.isSelected() == true) {
                synchronized (habitat.ThreadGuppyAI.locker) {
                    habitat.ThreadGuppyAI.sleeping = false;
                    habitat.ThreadGuppyAI.locker.notify();
                }
            } else {
                habitat.ThreadGuppyAI.sleeping = true;
            }
        });
        Main.panel.add(Main.switchGuppyAI);

        //Выпадающие списки для здания приоритетов потоков
        Main.priorityGoldAI.setBorder(BorderFactory.createTitledBorder("Gold Priority"));
        Main.priorityGoldAI.setPreferredSize(new Dimension(90, 40));
        for (int i = 0; i < 10; i++) {
            Main.priorityGoldAI.addItem(i + 1);
        }
        Main.priorityGoldAI.addActionListener(e -> habitat.ThreadGoldAI.setPriority(Main.priorityGoldAI.getSelectedIndex() + 1));
        Main.panel.add(Main.priorityGoldAI);

        Main.priorityGuppyAI.setBorder(BorderFactory.createTitledBorder("Guppy Priority"));
        Main.priorityGuppyAI.setPreferredSize(new Dimension(90, 40));
        for (int i = 0; i < 10; i++) {
            Main.priorityGuppyAI.addItem(i + 1);
        }
        Main.priorityGuppyAI.addActionListener(e -> habitat.ThreadGuppyAI.setPriority(Main.priorityGuppyAI.getSelectedIndex() + 1));
        Main.panel.add(Main.priorityGuppyAI);

    }

    class processKeyEvent extends KeyAdapter {

        public void keyReleased(KeyEvent e) {
            super.keyReleased(e);
            if (e.getKeyCode() == KeyEvent.VK_T) {

                show = !show;
            }
            if (e.getKeyCode() == KeyEvent.VK_B) {
                if (!simulating) {
                    startSim();
                }
            }
            if (e.getKeyCode() == KeyEvent.VK_E) {
                stopSim();

            }
        }
    }

    class PanelMouseListener extends MouseAdapter {

        public void mousePressed(MouseEvent e) {
            super.mouseClicked(e);
            requestFocusInWindow();
        }
    }

    public class Txt_name_ActionListener implements ActionListener {

        public void actionPerformed(ActionEvent e) {

            JTextField tmp = (JTextField) e.getSource();
            float value;
            try {
                value = Float.parseFloat(tmp.getText());
            } catch (NumberFormatException ee) {
                value = 0;
                JOptionPane.showMessageDialog(HabitatView.this, "Неверный ввод. Возвращено значение по умолчанию.", "ERROR404", JOptionPane.ERROR_MESSAGE);
            }
            if (value <= 0) {
                value = 1;
            }
            System.out.println(value);
            switch (tmp.getName()) {
                case ("Spawn_Gold"):
                    Gold.N1 = value;
                    break;
                case ("Spawn_Guppy"):
                    Guppy.N2 = value;
                    break;
                case ("life_time_Gold"):
                    Gold.life_time = value;
                    break;
                case ("life_time_Guppy"):
                    Guppy.life_time = value;
                    break;

            }
            tmp.setText(String.valueOf(value));
        }
    }

    //Слушатель нажатия клавиш
    private void update() {
        habitat.Update(deltaTime);
        _refTimer -= deltaTime;
        if (_refTimer <= 0) {
            _refTimer = _refTime;
            synchronized (habitat.ObjCollection) {
                for (Iterator<Fish> iterator = habitat.ObjCollection.iterator(); iterator.hasNext();) {
                    Fish temp = iterator.next();

                    if (temp instanceof Gold && (ElapsedTime - temp.getBornTime() > Gold.life_time)) {
                        --Gold.Sum1;
                    } else if (temp instanceof Guppy && (ElapsedTime - temp.getBornTime() > Guppy.life_time)) {
                        --Guppy.Sum2;
                    }

                    if (Fish.Sum != Gold.Sum1 + Guppy.Sum2) {
                        UUID tmpID = temp.getID();
                        habitat.idSet.remove(tmpID);
                        habitat.birthdayMap.remove(tmpID);
                        iterator.remove();
                        Fish.Sum--;
                    }
                }
            }
        }
        repaint();
    }

    
    //Отрисовка наших объектов и таймера при помощи буфера
    public void paint(Graphics g) {
        super.paint(g);
        clearViewScreen(g);
        if (simulating) {
            g.drawImage(imgBG, 0, 0, this);
            //Отрисовывыаем объекты
            for (Fish temp : habitat.ObjCollection) {

                if (temp instanceof Gold && (ElapsedTime - temp.getBornTime() < Gold.life_time)) {
                    if (!temp.get_going()) {
                        g.drawImage(imgGold_left, temp.getX(), temp.getY(), this);
                    } else {
                        g.drawImage(imgGold_right, temp.getX(), temp.getY(), this);
                    }
                } else if (temp instanceof Guppy && (ElapsedTime - temp.getBornTime() < Guppy.life_time)) {
                    if (temp.get_going()) {
                        g.drawImage(imgGuppy_down, temp.getX(), temp.getY(), this);
                    } else {
                        g.drawImage(imgGuppy_up, temp.getX(), temp.getY(), this);
                    }
                }
            }
            //Отрисовывваем таймер
            if (show) {
                g.setColor(new Color(222, 222, 30, 255));
                g.drawString(String.format("%.2f", ElapsedTime), 0, 16);
            }
            //освобождаем ресуры
            g.dispose();
        } else {
            if (firstRun) {

                g.setColor(Color.WHITE);
                g.fillRect(0, 0, getWidth(), getHeight());
                g.setFont(new Font("Consolas", Font.PLAIN, 20));
                g.setColor(Color.GRAY);
                g.drawString("Нажмите клавишу \"B\" для запуска", (getWidth() - 31 * 10) / 2, getHeight() / 2);
            }
        }

    }

    private void clearViewScreen(Graphics g) {
        g.setColor(Color.GRAY);
        g.fillRect(0, 0, getWidth(), getHeight());
    }

    //вспмогательяные методы пауза. старт, стоп, продолжить
    void startSim() {
        firstRun = false;
        simulating = true;
        try {
            loadConfig();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(HabitatView.class.getName()).log(Level.SEVERE, null, ex);
        }
        Main.start_menu.setEnabled(false);
        Main.button_start.setEnabled(false);
        Main.stop_menu.setEnabled(true);
        Main.button_stop.setEnabled(true);
        habitat = new Habitat();
        habitat.start();
        timer = new Timer();
        timer.schedule(new SimulationLoop(), 0, 10);

        Fish.Sum = 0;
        Gold.Sum1 = 0;
        Guppy.Sum2 = 0;
    }

    void stopSim() {
        simulating = false;
        if (show_info == 1) {
            int result = JOptionPane.showConfirmDialog(HabitatView.this, String.format("Рыбок всего: %d\nЗолотых рыбок: %d\nРыбок - гуппи: %d\n %.2f\n\n Остановить?", Fish.Sum, Gold.Sum1, Guppy.Sum2, ElapsedTime), "Result", JOptionPane.OK_CANCEL_OPTION);
            if (result == JOptionPane.OK_OPTION) {
                firstRun = true;
                Main.start_menu.setEnabled(true);
                Main.button_start.setEnabled(true);
                Main.stop_menu.setEnabled(false);
                Main.button_stop.setEnabled(false);
                ElapsedTime = 0;
                repaint();
                habitat.stop();
                return;

            }
            simulating = true;
            return;
        }
        Main.start_menu.setEnabled(true);
        Main.button_start.setEnabled(true);
        Main.stop_menu.setEnabled(false);
        Main.button_stop.setEnabled(false);
        firstRun = true;
        ElapsedTime = 0;
        habitat.stop();
        repaint();
    }

    public void show_list_object() {
        simulating = false;
        // if (show_obj==1){
        JDialog container = new JDialog(frame_list, "Current object", true);
        JTextArea textArea = new JTextArea();
        JScrollPane scrollPane = new JScrollPane(textArea);

        container.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                simulating = true;
                container.dispose();
            }
        });

        StringBuilder str = new StringBuilder();
        for (Fish tmp : habitat.ObjCollection) {
            if (tmp instanceof Gold && (ElapsedTime - tmp.getBornTime() < Gold.life_time)) {
                str.append(String.format("Золотая рыбка, с ID: \"%s\", родилась в %.2f\n", tmp.getID(), tmp.getBornTime()));
            } else if (tmp instanceof Guppy && (ElapsedTime - tmp.getBornTime() < Guppy.life_time)) {
                str.append(String.format("Гуппи - рыбка, с ID: \"%s\", родилась в %.2f\n", tmp.getID(), tmp.getBornTime()));
            }
        }

        textArea.setText(str.toString());

        textArea.setDisabledTextColor(Color.BLACK);
        container.setPreferredSize(new Dimension(600, 200));
        container.add(scrollPane);
        textArea.setEnabled(false);
        container.pack();
        container.setVisible(true);
        //   }

    }

    void load_file() {
        synchronized (habitat.ObjCollection) {
            habitat.ObjCollection.clear();
            habitat.idSet.clear();
            habitat.birthdayMap.clear();

            Fish.Sum = 0;
            Gold.Sum1 = 0;
            Guppy.Sum2 = 0;
            LinkedList<Fish> tmp = null;
            try {
                ObjectInputStream oin = new ObjectInputStream(new FileInputStream("data.dat"));
                ElapsedTime = (double) oin.readObject();
                tmp = (LinkedList<Fish>) oin.readObject();
                double currentTime = System.currentTimeMillis();
                startTime = currentTime - ElapsedTime * 1000;
                System.out.println(ElapsedTime);
                System.out.println(currentTime);
                System.out.println(startTime);

                for (Fish temp : tmp) {
                    temp.setBornTime(ElapsedTime);
                    Fish.Sum++;
                    habitat.idSet.add(temp.getID());
                    habitat.birthdayMap.put(temp.getID(), temp.getBornTime());
                    if (temp instanceof Gold) {
                        Gold.Sum1++;
                    } else {
                        Guppy.Sum2++;
                    }
                }
                habitat.ObjCollection = tmp;
                habitat.ThreadGoldAI.set_list(habitat.ObjCollection);
                habitat.ThreadGuppyAI.set_list(habitat.ObjCollection);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    void save_file() {
        synchronized (habitat.ObjCollection) {
            ObjectOutputStream oos;
            try {
                oos = new ObjectOutputStream(new FileOutputStream("data.dat"));
                oos.writeObject(ElapsedTime);
                oos.writeObject(habitat.ObjCollection);

                oos.close();
            } catch (IOException ex) {
                Logger.getLogger(HabitatView.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
       static void saveConfig() {
            
        BufferedWriter out;
        try {
            out = new BufferedWriter(new FileWriter("Config.txt"));
             out.write(String.valueOf((Gold.P1)));
        out.write('\n');
        out.write(String.valueOf((Gold.N1)));
        out.write('\n');
        out.write(String.valueOf((Gold.life_time)));
        out.write('\n');
        out.write(String.valueOf((Guppy.P2)));
        out.write('\n');
        out.write(String.valueOf((Guppy.N2)));
        out.write('\n');
        out.write(String.valueOf((Guppy.life_time)));

        out.close();
        } catch (IOException ex) {
            Logger.getLogger(HabitatView.class.getName()).log(Level.SEVERE, null, ex);
        }

       
    }

    static void loadConfig() throws FileNotFoundException {
        BufferedReader in;
       
            in = new BufferedReader(new FileReader("Config.txt"));
        try {
            Gold.P1 = Float.parseFloat(in.readLine());
            Gold.N1 = Float.parseFloat(in.readLine());
            Gold.life_time = Float.parseFloat(in.readLine());
            Guppy.P2 = Float.parseFloat(in.readLine());
            Guppy.N2 = Float.parseFloat(in.readLine());
            Guppy.life_time = Float.parseFloat(in.readLine());

            Main.txt_spawn_time_Gold.setText(String.valueOf(Gold.N1));
            Main.txt_spawn_time_Guppy.setText(String.valueOf(Guppy.N2));

            Main.txt_life_time_Gold.setText(String.valueOf(Gold.life_time));
            Main.txt_life_time_Guppy.setText(String.valueOf(Guppy.life_time));

            Main.slider_chance_Gold.setValue( (int)(Gold.P1*100));
            Main.slider_chance_Guppy.setValue((int)(Guppy.P2*100));
            in.close();
        } catch (IOException ex) {
            Logger.getLogger(HabitatView.class.getName()).log(Level.SEVERE, null, ex);
        }
            
           

        
    }


    //класс таймера
    private class SimulationLoop extends TimerTask {

        private double lastTime;

        public SimulationLoop() {
            startTime = System.currentTimeMillis();
            lastTime = startTime;
            pauseTime = 0;
        }

        @Override
        public void run() {
            if (simulating) {
                double currentTime = System.currentTimeMillis();
                // Время, прошедшее от начала, в секундах
                ElapsedTime = (currentTime - startTime - pauseTime) / 1000;
                // Время, прошедшее с последнего обновления, в секундах
                deltaTime = (currentTime - lastTime) / 1000;
                lastTime = currentTime;
                // Вызываем обновление
                update();
            } else {
                double currentTime = System.currentTimeMillis();
                pauseTime = (currentTime - startTime - ElapsedTime * 1000);
                lastTime = currentTime;
            }
        }
    }
}

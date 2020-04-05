/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package aquarium;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
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
import java.io.File;
import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JTextField;


/**
 *
 * @author Dexp
 */
public class HabitatView extends JPanel {
        static Habitat habitat;
	public static int widthImg = 50;
	public static int heigthImg = 70;
	
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
	public static int Width;
	public static int Height;

        
       //JPanel panel;
       
        
	public HabitatView() {
         
            try {
                img1 = ImageIO.read(new File("Gold.png"));
                img2 = ImageIO.read(new File("Guppy.png"));
                imgBG = ImageIO.read(new File("BG.jpg"));
        } catch (IOException ex) {
            Logger.getLogger(HabitatView.class.getName()).log(Level.SEVERE, null, ex);
        }
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
            Main.slider_chance_Gold.setValue((int)(Gold.P1 * 100));
            Main.slider_chance_Guppy.setValue((int)(Guppy.P2 * 100));
            Main.txt_spawn_time_Gold.setText(String.valueOf(Gold.N1));
            Main.txt_spawn_time_Guppy.setText(String.valueOf(Guppy.N2));
            
                
                Main.button_start.addActionListener(e-> startSim());
                Main.panel.add( Main.button_start);
                Main.button_stop.addActionListener(e-> stopSim());
                Main.panel.add( Main.button_stop);
               
                Main.rbutton_show_time.addActionListener(e-> show = true);
                Main.rbutton_hide_time.addActionListener(e-> show = false);
                Main.button_group.add(Main.rbutton_show_time);
                Main.button_group.add(Main.rbutton_hide_time);
                Main.panel.add(Main.rbutton_show_time);
                Main.panel.add(Main.rbutton_hide_time);
                
                Main.slider_chance_Gold.setBorder(BorderFactory.createTitledBorder("Шанс \"Gold\""));
                Main.slider_chance_Gold.setPreferredSize(new Dimension(200, 80));
                Main.slider_chance_Gold.setMajorTickSpacing(10);
                Main.slider_chance_Gold.setPaintTicks(true);
                Main.slider_chance_Gold.setPaintLabels(true);
                Main.slider_chance_Gold.addChangeListener(e -> Gold.P1 = (((JSlider)e.getSource()).getValue()) / 100f);
                Main.panel.add(Main.slider_chance_Gold);
                
                Main.slider_show_info.addChangeListener(e -> show_info = (((JSlider)e.getSource()).getValue()));
                
                
                
                Main.slider_chance_Guppy.setBorder(BorderFactory.createTitledBorder("Шанс \"Guppy\""));
                Main.slider_chance_Guppy.setPreferredSize(new Dimension(200, 80));
                Main.slider_chance_Guppy.setMajorTickSpacing(10);
                Main.slider_chance_Guppy.setPaintTicks(true);
                Main.slider_chance_Guppy.setPaintLabels(true);
                Main.slider_chance_Guppy.addChangeListener(e -> Guppy.P2 = (((JSlider)e.getSource()).getValue()) / 100f);
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
                Main.menu.add(Main.start_menu);
                Main.menu.add(Main.stop_menu);
                Main.start_menu.addActionListener(e -> startSim());
                Main.stop_menu.addActionListener(e -> stopSim());
                Main.menuBar.add(Main.menu);
               
                Main.start_menu.setEnabled(true);
                Main.button_start.setEnabled(true);
                Main.stop_menu.setEnabled(false);
                Main.button_stop.setEnabled(false);
              
	}
            class processKeyEvent extends KeyAdapter {
		 public void keyReleased(KeyEvent e) {
            super.keyReleased(e);
            if (e.getKeyCode() == KeyEvent.VK_T) {

               show=!show;
            }
            if (e.getKeyCode() == KeyEvent.VK_B) {
                if (!simulating)
		startSim();
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
    } }   
         public class Txt_name_ActionListener implements ActionListener {
                    public void actionPerformed(ActionEvent e) {
                         System.out.println("жопа");
                        JTextField tmp = (JTextField) e.getSource();
                        float value;
                        try {
                            value = Float.parseFloat(tmp.getText());
                        } catch (NumberFormatException ee) {
                            value = 0;
                            JOptionPane.showMessageDialog(HabitatView.this,"Неверный ввод. Возвращено значение по умолчанию.","ERROR404",  JOptionPane.ERROR_MESSAGE);
                        }
                        if (value <= 0) value = 1;
                        System.out.println(value);
                        switch (tmp.getName()) {
                            case ("Spawn_Gold"):
                                Gold.N1= value;
                                break;
                            case ("Spawn_Guppy"):
                                Guppy.N2 = value;
                                break;
                            
                        }
                        tmp.setText(String.valueOf(value));
                    }
                }
        
        //Слушатель нажатия клавиш
	
        private void update(){
		habitat.Update(deltaTime);
		repaint();
	}
        //Отрисовка наших объектов и таймера при помощи буфера
	public void paint(Graphics g) {
                super.paint(g);
		clearViewScreen(g);
                if(simulating){
		g.drawImage(imgBG, 0, 0, this);
		//Отрисовывыаем объекты
		for (Fish temp : habitat.ObjCollection)
			if (temp instanceof Gold) g.drawImage(img1, temp.getX(), temp.getY(), this);
			else if (temp instanceof Guppy) g.drawImage(img2, temp.getX(), temp.getY(), this);
			//Отрисовывваем таймер
			if (show){
				g.setColor(new Color(222, 222, 30, 255));
				g.drawString(String.format("%.2f", ElapsedTime), 0, 16);
			}
			//освобождаем ресуры
			g.dispose();
                }
                else{
                    if (firstRun) {
			
                        g.setColor(Color.WHITE);
                        g.fillRect(0, 0, getWidth(), getHeight());
                        g.setFont(new Font("Consolas", Font.PLAIN, 20));
                        g.setColor(Color.GRAY);
			g.drawString("Нажмите клавишу \"B\" для запуска", (getWidth()-31*10) / 2 , getHeight() / 2);
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
                Main.start_menu.setEnabled(false);
                Main.button_start.setEnabled(false);
                Main.stop_menu.setEnabled(true);
                Main.button_stop.setEnabled(true);
		habitat = new Habitat();
		timer = new Timer();
		timer.schedule(new SimulationLoop(), 0, 10);
                
		Fish.Sum = 0;
		Gold.Sum1 = 0;
		Guppy.Sum2 = 0;
	}
        
        void stopSim() {
		simulating = false;
                if(show_info==1){
                    int result = JOptionPane.showConfirmDialog(HabitatView.this,String.format("Рыбок всего: %d\nЗолотых рыбок: %d\nРыбок - гуппи: %d\n %.2f\n\n Продолжить?", Fish.Sum, Gold.Sum1, Guppy.Sum2, ElapsedTime),"Result", JOptionPane.YES_NO_OPTION);
                    if (result != JOptionPane.YES_OPTION) {
                    firstRun=true;
                     Main.start_menu.setEnabled(true);
                    Main.button_start.setEnabled(true);
                    Main.stop_menu.setEnabled(false);
                    Main.button_stop.setEnabled(false);
                    ElapsedTime=0;
                    repaint();        
                    return;
                     }
                    simulating = true;
                    return;
                }
                Main.start_menu.setEnabled(true);
                Main.button_start.setEnabled(true);
                Main.stop_menu.setEnabled(false);
                Main.button_stop.setEnabled(false);
                firstRun=true;
                ElapsedTime=0;
                repaint();                
	}


	//класс таймера
	private class SimulationLoop extends TimerTask { 
		private double pauseTime;
		private double startTime;
		private double lastTime;

		public SimulationLoop()
		{
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
			}
			else {
				double currentTime = System.currentTimeMillis();
				pauseTime = (currentTime - startTime - ElapsedTime * 1000);
				lastTime = currentTime;
			}
		}
	}
}

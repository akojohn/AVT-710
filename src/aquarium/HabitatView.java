/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package aquarium;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.JPanel;


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
       
	public static int Width;
	public static int Height;

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
                //startSim();
		//stopSim();
                repaint();
		HabitatView.Width = this.getWidth();
		HabitatView.Height = this.getHeight();
                
	}
        private void initComponents() {
	
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
                    if (!firstRun) {
			int midWidth = Width / 2 - 100;
			int midHeight = Height / 2;
			g.setFont(new Font("Consolas", Font.PLAIN, 20));
			g.setColor(new Color(255, 255, 0, 255));
			g.drawString(String.format("Рыбок всего: %d", Fish.Sum), midWidth, midHeight);
			g.setFont(new Font("Arial", Font.ITALIC, 18));
			g.setColor(new Color(255, 255, 255, 255));
			g.drawString(String.format("Золотых рыбок: %d", Gold.Sum1), midWidth, midHeight + 50);
			g.setFont(new Font("Consolas", Font.BOLD, 22));
			g.setColor(new Color(255, 0, 255, 255));
			g.drawString(String.format("Рыбок - гуппи: %d", Guppy.Sum2), midWidth, midHeight + 100);
			g.setFont(new Font("Arial", Font.BOLD, 30));
			g.setColor(new Color(200, 170, 30, 255));
			g.drawString(String.format("%.2f", ElapsedTime), midWidth, midHeight + 150);
			timer.cancel();
		}
		else {
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

		habitat = new Habitat();
		timer = new Timer();
		timer.schedule(new SimulationLoop(), 0, 10);
                
		Fish.Sum = 0;
		Gold.Sum1 = 0;
		Guppy.Sum2 = 0;
	}
        
        void stopSim() {
		simulating = false;
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

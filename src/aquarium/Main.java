/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package aquarium;

import java.awt.Dimension;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.JFrame;

/**
 *
 * @author Dexp
 */
public class Main extends JFrame{
    HabitatView view;
     static Main frame;
    /**
     * @param args the command line arguments
     */
    Main(String name){
        super(name);
        view = new HabitatView();
        setPreferredSize(new Dimension(1000, 700));
        setMinimumSize(new Dimension(800, 700));
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        add(view);
        addComponentListener(new ComponentAdapter(){
			public void componentResized(ComponentEvent e) {
				HabitatView.Width = getWidth();
				HabitatView.Height = getHeight();
			}
		});
        addKeyListener((KeyListener) new processKeyEvent());
        
    }
    public static void main(String[] args) {
        
       frame = new Main("Aquarium");
        // TODO code application logic here
    }
    class processKeyEvent extends KeyAdapter {
		 public void keyReleased(KeyEvent e) {
            super.keyReleased(e);
            if (e.getKeyCode() == KeyEvent.VK_T) {

               view.show=!view.show;
            }
            if (e.getKeyCode() == KeyEvent.VK_B) {
                if (!view.simulating)
		view.startSim();
            }
            if (e.getKeyCode() == KeyEvent.VK_E) {
               view.stopSim();

            }
        }
	}
}

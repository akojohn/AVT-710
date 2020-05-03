/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package aquarium;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JSlider;
import javax.swing.JTextField;

/**
 *
 * @author Dexp
 */
public class Main extends JFrame{
        HabitatView view;
        static Main frame;
        static JPanel panel;
        static JButton button_start = new JButton("Старт");
        static JButton button_stop = new JButton("Стоп");
        static JButton show_list = new JButton("Список объектов");
        static JRadioButton rbutton_show_time = new JRadioButton("Показывать время");
        static JRadioButton rbutton_hide_time = new JRadioButton("Скрывать время");

        static JSlider slider_chance_Gold = new JSlider(JSlider.HORIZONTAL,0, 100, 0);
        static JSlider slider_chance_Guppy = new JSlider(JSlider.HORIZONTAL,0, 100, 0);
        static JTextField txt_spawn_time_Gold = new JTextField();
        static JTextField txt_spawn_time_Guppy = new JTextField();
        
        static JTextField txt_life_time_Gold = new JTextField();
        static JTextField txt_life_time_Guppy = new JTextField();
         
        static JMenuBar menuBar = new JMenuBar();
        static JMenu menu = new JMenu("Файл");
        static JMenuItem stop_menu = new JMenuItem("Стоп");
        static JMenuItem start_menu = new JMenuItem("Старт");
        static ButtonGroup button_group = new ButtonGroup();  
        public static int Width;
	public static int Height;
        static JSlider slider_show_info = new JSlider(JSlider.HORIZONTAL,0, 1, 0);
        
        static JRadioButton switchGoldAI = new JRadioButton("GoldAI");
        static JRadioButton switchGuppyAI = new JRadioButton("GuppyAI");

        static JComboBox priorityGoldAI = new JComboBox();
        static JComboBox priorityGuppyAI = new JComboBox();
        static JButton save_files = new JButton("Сохранить");
        static JButton load_files = new JButton("Загрузить");
        static JButton console = new JButton("   Console application    ");
        
    /**
     * @param args the command line arguments
     */
    Main(String name){
        super(name);
        panel = new JPanel(new FlowLayout());
        panel.setPreferredSize(new Dimension(220,730));
        view = new HabitatView();
        setPreferredSize(new Dimension(1000, 750));
        setMinimumSize(new Dimension(800, 750));
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        add(view);
        
        setJMenuBar(menuBar);
        slider_show_info.setBorder(BorderFactory.createTitledBorder("Show off/on"));
        slider_show_info.setPreferredSize(new Dimension(90,80));
        slider_show_info.setMajorTickSpacing(1);
        slider_show_info.setMinorTickSpacing(1);
        slider_show_info.setPaintTicks(true);
        slider_show_info.setPaintLabels(true);
        panel.add(slider_show_info);
        slider_show_info.setVisible(true);
        add(panel, BorderLayout.EAST);
        
       
        
        addComponentListener(new ComponentAdapter(){
			public void componentResized(ComponentEvent e) {
				HabitatView.Width = getWidth()-235;
				HabitatView.Height = getHeight()-50;
			}
		});
                addWindowListener(new WindowAdapter() {
                public void windowClosing(WindowEvent e) {
                try {
                    HabitatView.saveConfig();
                } catch (Exception ee) {
                    System.out.println("Ошибка сохранения");
                }
                System.exit(0);
            }
        });
        
        
    }
    public static void main(String[] args) {
        
       frame = new Main("Aquarium");
        // TODO code application logic here
    }

}

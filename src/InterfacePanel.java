import javax.swing.*;
import java.awt.*;

 class InterfacePanel extends JPanel {

     JButton startSimulating = new JButton("Запуск");
     JButton stopSimulating = new JButton("Остановка");
     JRadioButton showTimeSimulating = new JRadioButton("Показать таймер");
     JRadioButton hideTimeSimulating = new JRadioButton("Скрыть таймер");
     JCheckBox isShowResultTable = new JCheckBox("Таблица с результатами");

     TextField automobileDelayTextField = new TextField("Задержка появления авто", 16);
     TextField motorcycleDelayTextField = new TextField("Задержка появления мото", 16);
     JSlider motorcycleProbabilitySlider = new JSlider(JSlider.HORIZONTAL, 10, 90, 70);
     private Double[] automobileProbabilityBoxItems = {0.8, 0.6, 0.4, 0.2};
     JComboBox automobileProbabilityBox = new JComboBox(automobileProbabilityBoxItems);

     InterfacePanel() {
         setBounds(600, 0, 180, 600);
         setBackground(Color.lightGray);
         setLayout(new FlowLayout(FlowLayout.CENTER));

         JPanel firstPartPanel = new JPanel();
         firstPartPanel.setPreferredSize(new Dimension(170,200));
         firstPartPanel.setBackground(Color.GRAY);
         firstPartPanel.setLayout(new GridLayout(5,1,10,1));

         JPanel secondPartPanel = new JPanel();
         secondPartPanel.setPreferredSize(new Dimension(170,150));
         secondPartPanel.setBackground(Color.GRAY);
         secondPartPanel.setLayout(new GridBagLayout());

         firstPartPanel.add(startSimulating);
         firstPartPanel.add(stopSimulating);

         ButtonGroup visibleTimeSimulating = new ButtonGroup();
         visibleTimeSimulating.add(showTimeSimulating);
         visibleTimeSimulating.add(hideTimeSimulating);
         firstPartPanel.add(showTimeSimulating);
         firstPartPanel.add(hideTimeSimulating);
         visibleTimeSimulating.setSelected(showTimeSimulating.getModel(), true);
         firstPartPanel.add(isShowResultTable);

         motorcycleProbabilitySlider.setPaintLabels(true);
         motorcycleProbabilitySlider.setSnapToTicks(true);
         motorcycleProbabilitySlider.setMajorTickSpacing(20);
         motorcycleProbabilitySlider.setMinorTickSpacing(10);
         motorcycleProbabilitySlider.setPaintTicks(true);

         GridBagConstraints gbcItems = new GridBagConstraints();
         gbcItems.weightx = 1.0;
         gbcItems.weighty = 1.0;
         gbcItems.fill = GridBagConstraints.HORIZONTAL;
         gbcItems.gridwidth = GridBagConstraints.REMAINDER;
         gbcItems.insets = new Insets(0,0,1,0);

         JLabel automobileProbabilityLabel = new JLabel("Коэф. появления автомобилей:");
         secondPartPanel.add(automobileProbabilityLabel,gbcItems);
         secondPartPanel.add(automobileProbabilityBox, gbcItems);
         JLabel motorcycleProbabilityLabel = new JLabel("Коэф. появления мотоциклов:");
         secondPartPanel.add(motorcycleProbabilityLabel, gbcItems);
         secondPartPanel.add(motorcycleProbabilitySlider, gbcItems);
         secondPartPanel.add(automobileDelayTextField, gbcItems);
         secondPartPanel.add(motorcycleDelayTextField, gbcItems);

         add(firstPartPanel);
         add(secondPartPanel);
         setVisible(true);
     }
 }
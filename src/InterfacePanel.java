import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyListener;

class InterfacePanel extends JPanel {

     JButton startSimulating = new JButton("Запуск");
     JButton stopSimulating = new JButton("Остановка");
     ButtonGroup visibleTimeSimulating = new ButtonGroup();
     JRadioButton showTimeSimulating = new JRadioButton("Показать таймер");
     JRadioButton hideTimeSimulating = new JRadioButton("Скрыть таймер");
     JCheckBox isShowResultTable = new JCheckBox("Таблица с результатами");

     TextField automobileDelayTextField = new TextField("Задержка появления авто", 16);
     TextField motorcycleDelayTextField = new TextField("Задержка появления мото", 16);

     JSlider motorcycleProbabilitySlider = new JSlider(JSlider.HORIZONTAL, 10, 90, 70);
     private Double[] automobileProbabilityBoxItems = {0.8, 0.6, 0.4, 0.2};
     JComboBox automobileProbabilityBox = new JComboBox(automobileProbabilityBoxItems);

     TextField automobileLifeTimeTextField = new TextField("Время жизни авто", 16);
     TextField motorcycleLifeTimeTextField = new TextField("Время жизни мото", 16);
     JButton showExistingObjects = new JButton("Показать транспорт");

     InterfacePanel() {
         setBounds(600, 0, 180, 600);
         setBackground(Color.lightGray);
         setLayout(new FlowLayout(FlowLayout.CENTER));

         GridBagConstraints gbcItems = new GridBagConstraints();
         gbcItems.weightx = 1.0;
         gbcItems.weighty = 1.0;
         gbcItems.fill = GridBagConstraints.HORIZONTAL;
         gbcItems.gridwidth = GridBagConstraints.REMAINDER;
         gbcItems.insets = new Insets(0,0,1,0);

         JPanel firstPartPanel = new JPanel();
         firstPartPanel.setPreferredSize(new Dimension(170,200));
         firstPartPanel.setBackground(Color.GRAY);
         firstPartPanel.setLayout(new GridLayout(5,1,10,1));

         visibleTimeSimulating.add(showTimeSimulating);
         visibleTimeSimulating.add(hideTimeSimulating);
         visibleTimeSimulating.setSelected(showTimeSimulating.getModel(), true);

         firstPartPanel.add(startSimulating);
         firstPartPanel.add(stopSimulating);
         firstPartPanel.add(showTimeSimulating);
         firstPartPanel.add(hideTimeSimulating);
         firstPartPanel.add(isShowResultTable);

         JPanel secondPartPanel = new JPanel();
         secondPartPanel.setPreferredSize(new Dimension(170,150));
         secondPartPanel.setBackground(Color.GRAY);
         secondPartPanel.setLayout(new GridBagLayout());

         JLabel automobileProbabilityLabel = new JLabel("Коэф. появления автомобилей:");

         motorcycleProbabilitySlider.setPaintLabels(true);
         motorcycleProbabilitySlider.setSnapToTicks(true);
         motorcycleProbabilitySlider.setMajorTickSpacing(20);
         motorcycleProbabilitySlider.setMinorTickSpacing(10);
         motorcycleProbabilitySlider.setPaintTicks(true);

         JLabel motorcycleProbabilityLabel = new JLabel("Коэф. появления мотоциклов:");

         secondPartPanel.add(automobileProbabilityLabel,gbcItems);
         secondPartPanel.add(automobileProbabilityBox, gbcItems);
         secondPartPanel.add(motorcycleProbabilityLabel, gbcItems);
         secondPartPanel.add(motorcycleProbabilitySlider, gbcItems);
         secondPartPanel.add(automobileDelayTextField, gbcItems);
         secondPartPanel.add(motorcycleDelayTextField, gbcItems);

         JPanel thirdPanel = new JPanel();
         thirdPanel.setPreferredSize(new Dimension(170,80));
         thirdPanel.setBackground(Color.GRAY);
         thirdPanel.setLayout(new GridLayout(3,1,1,1));

         thirdPanel.add(automobileLifeTimeTextField);
         thirdPanel.add(motorcycleLifeTimeTextField);
         thirdPanel.add(showExistingObjects);

         add(firstPartPanel);
         add(secondPartPanel);
         add(thirdPanel);
         setVisible(true);
     }

    @Override
    public synchronized void addKeyListener(KeyListener l) {
        super.addKeyListener(l);
        startSimulating.addKeyListener(l);
        stopSimulating.addKeyListener(l);
        showTimeSimulating.addKeyListener(l);
        hideTimeSimulating.addKeyListener(l);
        isShowResultTable.addKeyListener(l);
    }
}
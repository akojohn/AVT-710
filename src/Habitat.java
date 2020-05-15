import javax.swing.*;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.TreeMap;

class Habitat extends JFrame {

    long theUniverseStartTime = 0;
    long theUniverseTime = 0;
    private int amountAutomobiles = 0;
    private int amountVehicles = 0;
    private ArrayList<JImage> universeVehicles = new ArrayList<>();
    private HashSet vehiclesIdentificationNumbers = new HashSet();
    private TreeMap vehiclesIdentifyBornNumbers = new TreeMap();


    private double probabilityAutomobile = 1;
    private double probabilityMotorcycle = 1;

    private final int defaultDelay = 2000;
    private int automobileDelay = defaultDelay;
    private int motorcycleDelay = defaultDelay;

    private final int defaultLifeTime = 1000;
    private int automobileLifeTime = defaultLifeTime;
    private int motorcycleLifeTime = defaultLifeTime;

    final InterfacePanel userPanel = new InterfacePanel();

    private final int frameWidth = 800;
    private final int frameHeight = 660;

    JLabel simulationTimer = new JLabel();
    boolean show = true;

    public Timer helpTimer = new Timer(50, e -> timerUpdate());
    Timer timerForAutomobile = new Timer(automobileDelay, e -> {
        if (Math.random() <= probabilityAutomobile) {
            update(true);
        }
    });
    Timer timerForMotorcycle = new Timer(motorcycleDelay, e -> {
        if (Math.random() <= probabilityMotorcycle) {
            update(false);
        }
    });

    boolean isEndFlag = true;
    boolean isStartFlag = false;
    boolean isShowResultTableFlag = true;

    MenuPanelBar panelMenuBar = new MenuPanelBar();
    Habitat() throws Exception {
        UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        setBounds(283, 84, frameWidth, frameHeight);
        add(new Kostil(new Terrain()));
        setLayout(null);

        simulationTimer.setBounds(10, 3, 50, 12);
        add(simulationTimer, 0);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

//---------------------------------------------------------------------------------------------------

        add(userPanel);
        add(panelMenuBar, 0);
        setJMenuBar(panelMenuBar);
        userPanel.stopSimulating.setEnabled(false);
        userPanel.isShowResultTable.setSelected(true);
        panelMenuBar.stopSimulating.setEnabled(false);
        panelMenuBar.isShowResultTable.setSelected(true);

        userPanel.startSimulating.addActionListener(e -> {
            if (!isStartFlag && isEndFlag) {
                isStartFlag = true;
                simulationTimer.setVisible(true);
                theUniverseStartTime = new Date().getTime();
                helpTimer.start();
                timerForAutomobile.start();
                timerForMotorcycle.start();
                isEndFlag = false;
                userPanel.startSimulating.setEnabled(false);
                userPanel.stopSimulating.setEnabled(true);
                panelMenuBar.startSimulating.setEnabled(false);
                panelMenuBar.stopSimulating.setEnabled(true);
            }
        });
        userPanel.stopSimulating.addActionListener(e -> {
            if (!isEndFlag && isStartFlag) {
                isEndFlag = true;
                theUniverseTime = new Date().getTime() - theUniverseStartTime + theUniverseTime;
                timerForAutomobile.stop();
                timerForMotorcycle.stop();
                helpTimer.stop();
                isStartFlag = false;
                if (isShowResultTableFlag) statistics();
                theUniverseStartTime = new Date().getTime();
                userPanel.startSimulating.setEnabled(true);
                userPanel.stopSimulating.setEnabled(false);
                panelMenuBar.stopSimulating.setEnabled(false);
                panelMenuBar.startSimulating.setEnabled(true);
            }
        });
        userPanel.isShowResultTable.addActionListener(e -> {
            if  (userPanel.isShowResultTable.isSelected()) {
                isShowResultTableFlag = true;
                panelMenuBar.isShowResultTable.setSelected(true);
            }
            else{
                isShowResultTableFlag = false;
                panelMenuBar.isShowResultTable.setSelected(false);
            }
        });
        userPanel.showTimeSimulating.addActionListener(e -> simulationTimer.setVisible(true));
        userPanel.hideTimeSimulating.addActionListener(e -> simulationTimer.setVisible(false));

        userPanel.automobileDelayTextField.addActionListener(e-> {
            try {
                automobileDelay = Integer.parseInt(userPanel.automobileDelayTextField.getText());
                timerForAutomobile.setInitialDelay(automobileDelay);
                timerForAutomobile.setDelay(automobileDelay);
            }
            catch (IllegalArgumentException ex) {
                new MessageFrame(this,"Wrong value! The default value is set.");
                automobileDelay = defaultDelay;
            }
        });
        userPanel.motorcycleDelayTextField.addActionListener(e-> {
            try {
                motorcycleDelay = Integer.parseInt(userPanel.motorcycleDelayTextField.getText());
                timerForMotorcycle.setInitialDelay(motorcycleDelay);
                timerForMotorcycle.setDelay(motorcycleDelay);
            }
            catch (IllegalArgumentException ex) {
                new MessageFrame(this,"Wrong value! The default value is set.");
                motorcycleDelay = defaultDelay;
            }

        });
        userPanel.motorcycleProbabilitySlider.addChangeListener(e -> probabilityMotorcycle =
                (double)userPanel.motorcycleProbabilitySlider.getValue()/100);
        userPanel.automobileProbabilityBox.addActionListener(e -> probabilityAutomobile =
                (Double)userPanel.automobileProbabilityBox.getSelectedItem());

        userPanel.automobileLifeTimeTextField.addActionListener(e ->{
            try {
                automobileLifeTime = Integer.parseInt(userPanel.automobileLifeTimeTextField.getText());
            }
            catch (IllegalArgumentException ex) {
                new MessageFrame(this,"Wrong value! The default value is set.");
                automobileLifeTime = defaultLifeTime;
            }
        });
        userPanel.motorcycleLifeTimeTextField.addActionListener(e ->{
            try{
                motorcycleLifeTime = Integer.parseInt(userPanel.motorcycleLifeTimeTextField.getText());
            }
            catch (IllegalArgumentException ex){
                new MessageFrame(this,"Wrong value! The default value is set.");
                motorcycleLifeTime = defaultLifeTime;
            }
        });
        userPanel.showExistingObjects.addActionListener(e ->{
            new InformationFrame(this, vehiclesIdentifyBornNumbers);
        });

        panelMenuBar.simulationItem.addActionListener(e ->{});
        panelMenuBar.exitItem.addActionListener(e -> {
                frameClearing();
                dateClearing();
                System.exit(1);
        });

        panelMenuBar.startSimulating.addActionListener(e -> {
            if (!isStartFlag && isEndFlag) {
                isStartFlag = true;
                simulationTimer.setVisible(true);
                theUniverseStartTime = new Date().getTime();
                helpTimer.start();
                timerForAutomobile.start();
                timerForMotorcycle.start();
                isEndFlag = false;
                userPanel.startSimulating.setEnabled(false);
                userPanel.stopSimulating.setEnabled(true);
                panelMenuBar.startSimulating.setEnabled(false);
                panelMenuBar.stopSimulating.setEnabled(true);
            }
        });
        panelMenuBar.stopSimulating.addActionListener(e -> {
            if (!isEndFlag && isStartFlag) {
                isEndFlag = true;
                theUniverseTime = new Date().getTime() - theUniverseStartTime + theUniverseTime;
                timerForAutomobile.stop();
                timerForMotorcycle.stop();
                helpTimer.stop();
                isStartFlag = false;
                if (isShowResultTableFlag) statistics();
                theUniverseStartTime = new Date().getTime();
                userPanel.startSimulating.setEnabled(true);
                userPanel.stopSimulating.setEnabled(false);
                panelMenuBar.stopSimulating.setEnabled(false);
                panelMenuBar.startSimulating.setEnabled(true);
            }
        });
        panelMenuBar.isShowResultTable.addActionListener(e ->{
            if  (panelMenuBar.isShowResultTable.isSelected()) {
                isShowResultTableFlag = true;
                userPanel.isShowResultTable.setSelected(true);
            }
            else{
                isShowResultTableFlag = false;
                userPanel.isShowResultTable.setSelected(false);
            }
        });

        setVisible(true);
    }

    @Override
    public synchronized void addKeyListener(KeyListener l) {
        super.addKeyListener(l);
        userPanel.addKeyListener(l);
    }

    private void addVehicle(JImage Vehicle) {
        add(new Kostil(Vehicle), 0);                            // дополительная перегрузка (0) для отображения следующего
        universeVehicles.add(Vehicle);                     // над прдыдущим (друг на друга)
        amountVehicles++;
    }

    private void timerUpdate (){
        long currentTime = new Date().getTime() - theUniverseStartTime + theUniverseTime;
        simulationTimer.setText(String.valueOf(currentTime));
        for (int i = 0; i < universeVehicles.size(); i++) {
            if (universeVehicles.get(i).isDeterminate(currentTime)) {
                //remove(universeVehicles.get(i));
                vehiclesIdentificationNumbers.remove(universeVehicles.get(i).identificationNumber);
                vehiclesIdentifyBornNumbers.remove(universeVehicles.get(i).identificationNumber);
                universeVehicles.remove(i);
                repaint();
            }
        }
    }
    private void update(boolean is_automobile) {
        long identify = (long)Math.floor(Math.random()*(Math.pow(10,10)
                - Math.pow(10,9))+Math.pow(10,9));
        long currentTime = new Date().getTime() - theUniverseStartTime + theUniverseTime;
        vehiclesIdentificationNumbers.add(identify);
        vehiclesIdentifyBornNumbers.put(identify,currentTime);
        simulationTimer.setText(String.valueOf(currentTime));
        if (is_automobile) {
            int autoWidthSize = 192;
            int autoWidth = frameWidth - autoWidthSize - 250;
            int tempX = (int) (autoWidth * Math.random());
            int autoHeightSize = 64;
            int autoHeight = frameHeight - autoHeightSize - 50;
            int tempY = (int) (autoHeight * Math.random());
            addVehicle(new Automobile(identify, currentTime, automobileLifeTime, tempX, tempY));
            amountAutomobiles++;
        }
        else {
            int motorcycleWidthSize = 121;
            int motorcycleWidth = frameWidth - motorcycleWidthSize - 250;
            int tempX = (int) (motorcycleWidth * Math.random());
            int motorcycleHeightSize = 64;
            int motorcycleHeight = frameHeight - motorcycleHeightSize - 50;
            int tempY = (int) (motorcycleHeight * Math.random());
            addVehicle(new Motorcycle(identify,currentTime, motorcycleLifeTime, tempX, tempY));
        }
        repaint();
    }

    void frameClearing() {
        getContentPane().removeAll();
        universeVehicles.clear();
        getContentPane().repaint();
    }

    void dateClearing() {
        amountAutomobiles = 0;
        amountVehicles = 0;
        theUniverseTime = 0;
        int i =0;
        while (i < universeVehicles.size())
        {
           // remove(universeVehicles.get(i));
            i++;
        }
            vehiclesIdentifyBornNumbers.clear();
        vehiclesIdentificationNumbers.clear();
        while (universeVehicles.size() > 0)
            universeVehicles.remove(0);
    }

    void statistics() {
        StatisticsFrame resultFrame = new StatisticsFrame(amountAutomobiles, amountVehicles, theUniverseTime, this);
        resultFrame.okButton.addActionListener(e -> {
            resultFrame.dispose();
            dateClearing();
            simulationTimer.setVisible(false);
            repaint();
        });
        resultFrame.cancelButton.addActionListener(e -> resultFrame.dispose());
        resultFrame.setVisible(true);
    }

}

import javax.swing.*;
import java.util.ArrayList;
import java.util.Date;

class Habitat extends JFrame {

    long theUniverseStartTime = 0;
    long theUniverseTime = 0;
    private int amountAutomobiles = 0;
    private int amountVehicles = 0;
    private ArrayList<JImage> universeVehicles = new ArrayList<>(); //

    private double probabilityAutomobile = 0.7;
    private double probabilityMotorcycle = 0.8;
    private final int defaultDelay = 500;
    private int delayForAutomobile = defaultDelay;
    private int delayForMotorcycle = defaultDelay;

    private final int frameWidth = 800;
    private final int frameHeight = 660;

    JLabel simulationTimer = new JLabel();
    private boolean show = true;

    private Timer helpTimer = new Timer(50, e -> timerUpdate());
    Timer timerForAutomobile = new Timer(delayForAutomobile, e -> {
        if (Math.random() <= probabilityAutomobile) {
            update(true);
        }
    });
    Timer timerForMotorcycle = new Timer(delayForMotorcycle, e -> {
        if (Math.random() <= probabilityMotorcycle) {
            update(false);
        }
    });

    boolean isEndFlag = true;
    boolean isStartFlag = false;
    private boolean isShowResultTableFlag = true;

    Habitat() throws Exception {
        UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        setBounds(283, 84, frameWidth, frameHeight);
        add(new Terrain());
        setLayout(null);

        simulationTimer.setBounds(10, 3, 50, 12);
        add(simulationTimer, 0);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

//---------------------------------------------------------------------------------------------------
        InterfacePanel userPanel = new InterfacePanel();
        add(userPanel);
        MenuPanelBar panelMenuBar = new MenuPanelBar();
        add(panelMenuBar, 0);
        setJMenuBar(panelMenuBar);

        userPanel.stopSimulating.setEnabled(false);
        userPanel.isShowResultTable.setSelected(true);
        panelMenuBar.stopSimulating.setEnabled(false);
        panelMenuBar.isShowResultTable.setSelected(true);

        userPanel.startSimulating.addActionListener(e -> {
            if (!isStartFlag && isEndFlag) {
                isStartFlag = true;
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
                if (isShowResultTableFlag) Statistics();
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
                delayForAutomobile = Integer.parseInt(userPanel.automobileDelayTextField.getText());
                timerForAutomobile.setInitialDelay(delayForAutomobile);
                timerForAutomobile.setDelay(delayForAutomobile);
            }
            catch (IllegalArgumentException ex) {
                ErrorFrame errorDataTypeDelayForAutomobile = new ErrorFrame(this);
                errorDataTypeDelayForAutomobile.setVisible(true);
                delayForAutomobile = defaultDelay;
            }
        });
        userPanel.motorcycleDelayTextField.addActionListener(e-> {
            try {
                delayForMotorcycle = Integer.parseInt(userPanel.motorcycleDelayTextField.getText());
                timerForMotorcycle.setInitialDelay(delayForMotorcycle);
                timerForMotorcycle.setDelay(delayForMotorcycle);
            }
            catch (IllegalArgumentException ex) {
                ErrorFrame errorDataTypeDelayForMotorcycle = new ErrorFrame(this);
                errorDataTypeDelayForMotorcycle.setVisible(true);
                delayForMotorcycle = defaultDelay;
            }

        });
        userPanel.motorcycleProbabilitySlider.addChangeListener(e -> probabilityMotorcycle =
                (double)userPanel.motorcycleProbabilitySlider.getValue()/100);
        userPanel.automobileProbabilityBox.addActionListener(e -> probabilityAutomobile =
                (Double)userPanel.automobileProbabilityBox.getSelectedItem());
        panelMenuBar.simulationItem.addActionListener(e ->{});
        panelMenuBar.exitItem.addActionListener(e -> {
                frameClearing();
                dateClearing();
                System.exit(1);
        });

        panelMenuBar.startSimulating.addActionListener(e -> {
            if (!isStartFlag && isEndFlag) {
                isStartFlag = true;
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
                if (isShowResultTableFlag) Statistics();
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

    private void addVehicle(JImage Vehicle) {
        add(Vehicle, 0);                            // дополительная перегрузка (0) для отображения следующего
        universeVehicles.add(Vehicle);                     // над прдыдущим (друг на друга)
        amountVehicles++;
    }

    private void timerUpdate (){
        simulationTimer.setText(String.valueOf(new Date().getTime() - theUniverseStartTime + theUniverseTime));
        repaint();
    }
    private void update(boolean is_automobile) {
        simulationTimer.setText(String.valueOf(new Date().getTime() - theUniverseStartTime + theUniverseTime));
        if (is_automobile) {
            int autoWidthSize = 192;
            int autoWidth = frameWidth - autoWidthSize - 250;
            int tempX = (int) (autoWidth * Math.random());
            int autoHeightSize = 64;
            int autoHeight = frameHeight - autoHeightSize - 50;
            int tempY = (int) (autoHeight * Math.random());
            addVehicle(new Automobile(tempX, tempY));
            amountAutomobiles++;
        }
        else {
            int motorcycleWidthSize = 121;
            int motorcycleWidth = frameWidth - motorcycleWidthSize - 250;
            int tempX = (int) (motorcycleWidth * Math.random());
            int motorcycleHeightSize = 64;
            int motorcycleHeight = frameHeight - motorcycleHeightSize - 50;
            int tempY = (int) (motorcycleHeight * Math.random());
            addVehicle(new Motorcycle(tempX, tempY));
        }
        repaint();
    }

    boolean isShow() {
        show = !show;
        return show;
    }

    void frameClearing() {
        getContentPane().removeAll();
        getContentPane().repaint();
    }

    void dateClearing() {
        while (universeVehicles.size() > 0)
            universeVehicles.remove(0);
        universeVehicles.clear();
        amountAutomobiles = 0;
        amountVehicles = 0;
        theUniverseTime = 0;
    }

    void Statistics() {
        MessagePanel resultFrame = new MessagePanel(amountAutomobiles, amountVehicles, theUniverseTime, this);
        resultFrame.okButton.addActionListener(e -> {
            resultFrame.dispose();
            dateClearing();
            frameClearing();
            System.exit(1);
        });
        resultFrame.cancelButton.addActionListener(e -> resultFrame.dispose());
        resultFrame.setVisible(true);
    }
}

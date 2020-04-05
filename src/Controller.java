import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Timer;
import java.util.TimerTask;
//класс-контроллер. Отвечает за обработку нажатий на клавиатуру, вызов таймера, перерисовку формы.
class Controller implements Info.OnDialogListener, StatisticForm.OnDialogListener {
    private Habitat habitat;
    private MainForm mainForm;
    private Timer timer = new Timer();
    private long generationStartTime = 0;

    Controller(Habitat habitat, MainForm mainForm) {
        this.habitat = habitat;
        this.mainForm = mainForm;
        initializeMainForm();
        habitat.setHeight(mainForm.getDrawingPanel().getSize().height);
        habitat.setWidth(mainForm.getDrawingPanel().getSize().width);
    }

    private void initializeMainForm() {
        mainForm.setSize(habitat.getWidth(), habitat.getHeight());
        mainForm.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);//
        mainForm.setVisible(true);

        Action StartSimulationAction = new AbstractAction("START") {
            @Override
            public void actionPerformed(ActionEvent e) {
                startSimulation();
            }
        };

        Action BreakSimulationAction = new AbstractAction("STOP") {
            @Override
            public void actionPerformed(ActionEvent e) {
                breakSimulation();
            }
        };

        Action showTimeAction = new AbstractAction("Показывать время работы") {
            @Override
            public void actionPerformed(ActionEvent e) {
                mainForm.showTime();
            }
        };

        Action hideTimeAction = new AbstractAction("Скрывать время работы") {
            @Override
            public void actionPerformed(ActionEvent e) {
                mainForm.hideTime();
            }
        };

        Action statisticsAction = new AbstractAction("Показать статистику") {
            @Override
            public void actionPerformed(ActionEvent e) {
                habitat.changeShowingStatistics();
                mainForm.getInformationCheckbox().setSelected(habitat.isWillShowStatistics());
                mainForm.getInformationCheckBoxMenuItem().setSelected(habitat.isWillShowStatistics());
            }
        };

        mainForm.getUpdateLifeTimeButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Golden.setLifeTime(Integer.parseInt(mainForm.getGoldenLifeTimeTextField().getText()));
                Guppy.setLifeTime(Integer.parseInt(mainForm.getGuppyIntervalTextField().getText()));
            }
        });
        mainForm.getShowCurrentObject().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showInfo();
            }
        });
        mainForm.getStartButton().setAction(StartSimulationAction);
        mainForm.getStartSimulationMenuItem().setAction(StartSimulationAction);

        mainForm.getStopButton().setAction(BreakSimulationAction);
        mainForm.getStopSimulationMenuItem().setAction(BreakSimulationAction);
        mainForm.lockStopButton();
        mainForm.lockStopSimulationMenuItem();

        mainForm.getShowTimeRadioButton().setAction(showTimeAction);
        mainForm.getShowTimeMenuItem().setAction(showTimeAction);

        mainForm.getHideTimeRadioButton().setAction(hideTimeAction);
        mainForm.getHideTimeMenuItem().setAction(hideTimeAction);

        mainForm.getInformationCheckbox().setAction(statisticsAction);
        mainForm.getInformationCheckBoxMenuItem().setAction(statisticsAction);

        mainForm.getStartSimulationMenuItem().setAccelerator(KeyStroke.getKeyStroke("control B"));
        mainForm.getStopSimulationMenuItem().setAccelerator(KeyStroke.getKeyStroke("control E"));
        mainForm.getInformationCheckBoxMenuItem().setAccelerator(KeyStroke.getKeyStroke("control G"));
        mainForm.getShowTimeMenuItem().setAccelerator(KeyStroke.getKeyStroke("control T"));
        mainForm.getHideTimeMenuItem().setAccelerator(KeyStroke.getKeyStroke("control H"));

        mainForm.addComponentListener(new ComponentAdapter() {
            public void componentResized(ComponentEvent evt) {
                habitat.setHeight(mainForm.getDrawingPanel().getSize().height);
                habitat.setWidth(mainForm.getDrawingPanel().getSize().width);
            }
        });


    }

    private void startSimulation() {
        stopTimer();
        habitat.getFishList().clear();
        startTimer();
        mainForm.lockStartButton();
        mainForm.lockStartSimulationMenuItem();
        mainForm.unlockStopButton();
        mainForm.unlockStopSimulationMenuItem();
    }

    private void breakSimulation() {
        long time = (System.nanoTime() - generationStartTime) / 1000000000 + 1;
        if (habitat.isWillShowStatistics()) {
            new StatisticForm(mainForm, habitat.getFishList().getStatisticText(time), this);
        }
        else {
            stopSimulation();
        }
    }

    private void showInfo() {
        long time = (System.nanoTime() - generationStartTime) / 1000000000 + 1;
        new Info(mainForm, habitat.getFishList(), habitat.getTreeMap(), habitat.getHashSet(), this);
    }

    private void stopSimulation() {
        stopTimer();
        habitat.getFishList().clear();
        redrawFishPanel();
        mainForm.lockStopButton();
        mainForm.lockStopSimulationMenuItem();
        mainForm.unlockStartButton();
        mainForm.unlockStartSimulationMenuItem();
    }

    private void startTimer() {
        generationStartTime = System.nanoTime();
        timer = new Timer();
        try {
            timer.schedule(new Task(), 0, 1000);
        } catch (IllegalStateException e) {
            System.out.println(e);
        }
    }

    private void stopTimer() {
        try {
            timer.cancel();
            timer.purge();
        } catch (IllegalStateException e) {
            System.out.println(e);
        }
    }

    @Override
    public void onDialogOk() {
        stopSimulation();
    }

    class Task extends TimerTask {
        @Override
        public void run() {
            long time = (System.nanoTime() - generationStartTime) / 1000000000 + 1;
            mainForm.setTimeLabelText("Время с начала симуляции: " + time);
            habitat.update(time);
            redrawFishPanel();
        }
    }
    private void redrawFishPanel() {
        Graphics g = mainForm.getDrawingPanel().getGraphics();
        mainForm.getDrawingPanel().removeAll();
        mainForm.getDrawingPanel().paint(g);
        habitat.getFishList().draw(g);
    }
}
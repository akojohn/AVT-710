import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.*;
import java.beans.PropertyChangeListener;
import java.io.*;
import java.util.Collection;
import java.util.Collections;
import java.util.Timer;
import java.util.TimerTask;

class Controller implements StatisticForm.OnDialogListener {
    private Habitat habitat;
    private MainForm mainForm;
    private Console console;
    private Timer timer = new Timer();
    private long generationStartTime = 0;
    private boolean willShowStatistics = false;

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
                changeShowingStatistics();
                mainForm.getInformationCheckbox().setSelected(isWillShowStatistics());
                mainForm.getInformationCheckBoxMenuItem().setSelected(isWillShowStatistics());
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

        Action consoleAction = new AbstractAction("Консоль") {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Console was created");
                console = new Console(mainForm);

                console.getConsoleTextArea().getDocument().addDocumentListener(new DocumentListener() {
                    @Override
                    public void insertUpdate(DocumentEvent e) {
                        System.out.println("it should works (INSERTED)");
                        String buffer = console.getConsoleTextArea().getText();
                        if (buffer.charAt(buffer.length() - 1) == '\n') {
                            SwingUtilities.invokeLater(new Runnable()
                            {
                                public void run()
                                {
                                    processConsoleCommand(buffer);
                                    System.out.println("buffer was " + buffer);
                                    console.getConsoleTextArea().setText("");
                                }
                            });
                        }
                    }

                    @Override
                    public void removeUpdate(DocumentEvent e) {

                    }

                    @Override
                    public void changedUpdate(DocumentEvent e) {
                    }
                });
            }
        };

        Action saveFishesAction = new AbstractAction("Сохранить") {
            @Override
            public void actionPerformed(ActionEvent e) {
                saveFishes();
            }
        };

        Action loadFishesAction = new AbstractAction("Загрузить") {
            @Override
            public void actionPerformed(ActionEvent e) {
                loadFishes();
            }
        };

        mainForm.getConsoleMenuItem().setAction(consoleAction);
        mainForm.getSaveFishesMenuItem().setAction(saveFishesAction);
        mainForm.getLoadFishesMenuItem().setAction(loadFishesAction);

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

        mainForm.getUnpauseGuppyAI().addActionListener(e -> {
            mainForm.getPauseGuppyAI().setEnabled(true);
            mainForm.getUnpauseGuppyAI().setEnabled(false);
            habitat.unpauseGuppyAI();
        });

        mainForm.getPauseGuppyAI().addActionListener(e -> {
            mainForm.getPauseGuppyAI().setEnabled(false);
            mainForm.getUnpauseGuppyAI().setEnabled(true);
            habitat.pauseGuppyAI();
        });

        mainForm.getUnpauseGoldenAI().addActionListener(e -> {
            mainForm.getPauseGoldenAI().setEnabled(true);
            mainForm.getUnpauseGoldenAI().setEnabled(false);
            habitat.unpauseGoldenAI();
        });

        mainForm.getPauseGoldenAI().addActionListener(e -> {
            mainForm.getPauseGoldenAI().setEnabled(false);
            mainForm.getUnpauseGoldenAI().setEnabled(true);
            habitat.pauseGoldenAI();
        });

        mainForm.getGuppyPriorityComboBox().addActionListener(e -> habitat.setGuppyAIPriority((int)mainForm.getGuppyPriorityComboBox().getSelectedItem()));

        mainForm.getGoldenPriorityComboBox().addActionListener(e -> habitat.setGoldenAIPriority((int)mainForm.getGoldenPriorityComboBox().getSelectedItem()));


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

        mainForm.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                super.windowClosing(e);
                try {
                    saveSettings();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        });

        mainForm.addWindowListener(new WindowAdapter() {
            @Override
            public void windowOpened(WindowEvent e) {
                super.windowOpened(e);
                loadSettings();
            }
        });
    }



    void processConsoleCommand(String command) {
        //System.out.println("cutted processCommandArgument is " + command.substring(0, 20));
        if (command.length() == 21 && command.substring(0, 20).equals("getGoldenProbability")) {
            console.setConsoleOutputLabelText("Вероятность рождения золотой " + new Golden().getSpawnProbability() / 10);
        }

        if (command.length() > 22 && command.substring(0, 21).equals("setGoldenProbability ")) {
//            System.out.println("trimmed processCommandArgument is " + command.substring(21));
            Golden.setSpawnProbability(Double.parseDouble(command.substring(21)) * 10);
            System.out.println(Double.parseDouble(command.substring(21)) * 10);
            mainForm.getGoldenProbabilityComboBox().setSelectedIndex((int)(Double.parseDouble(command.substring(21)) * 10));
            console.setConsoleOutputLabelText("Новая вероятность успешно задана!");
        }
    }

    void changeShowingStatistics() {
        willShowStatistics = !willShowStatistics;
    }

    boolean isWillShowStatistics() {
        return willShowStatistics;
    }

    private void startSimulation() {
        stopTimer();
        //habitat.getFishList().clear();
        startTimer();
        mainForm.lockStartButton();
        mainForm.lockStartSimulationMenuItem();
        mainForm.unlockStopButton();
        mainForm.unlockStopSimulationMenuItem();
    }

    private void breakSimulation() {
        long time = (System.nanoTime() - generationStartTime) / 1000000000 + 1;
        if (isWillShowStatistics()) {
            new StatisticForm(mainForm, habitat.getFishList().getStatisticText(time), this);
        }
        else {
            stopSimulation();
        }
    }

    private void showInfo() {
        new InfoForm(mainForm, habitat.getFishList(), habitat.getTreeMap());
    }

    private void stopSimulation() {
        stopTimer();
        habitat.clearCollections();
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

    private void saveFishes() {
        try ( ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream("fishes.bin"))) {
            objectOutputStream.writeObject(habitat);
        }
        catch (IOException e) {
            System.out.println("Исключение при сериализации: " + e);
        }
    }

    private void loadFishes() {
        File file;
        JFileChooser fileopen = new JFileChooser();
        int ret = fileopen.showDialog(null, "Открыть файл");
        if (ret == JFileChooser.APPROVE_OPTION) {
            file = fileopen.getSelectedFile();
            try ( ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream(file))) {
                habitat = (Habitat) objectInputStream.readObject();
                stopTimer();
                redrawFishPanel();
                mainForm.lockStopButton();
                mainForm.lockStopSimulationMenuItem();
                mainForm.unlockStartButton();
                mainForm.unlockStartSimulationMenuItem();
                habitat.refreshAI();
            }
            catch (Exception e) {
                System.out.println("Исключение при десериализации: " + e);
            }
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

    void saveSettings() throws IOException {
        File file = new File("settings.txt");
        FileOutputStream fileOutputStream = new FileOutputStream(file);

        String settings = "";

        settings += "STATISTICS_WILL_SHOWN: " + mainForm.isViewInformationChecked() + '\n';
        settings += "SHOW_TIME: " + mainForm.isShowTimeRadioButtonChecked() + '\n';
        settings += "GUPPY_INTERVAL: " + new Guppy().getSpawnInterval() + '\n';
        settings += "GOLDEN_INTERVAL: " + new Golden().getSpawnInterval() + '\n';
        settings += "GUPPY_PROBABILITY: " +  new Guppy().getSpawnProbability() + '\n';
        System.out.println(new Golden().getSpawnProbability());
        settings += "GOLDEN_PROBABILITY: " + new Golden().getSpawnProbability() / 10 + '\n';
        settings += "GUPPY_LIFETIME: " + new Guppy().getLifeTime() + '\n';
        settings += "GOLDEN_LIFETIME: " + new Golden().getLifeTime() + '\n';

        fileOutputStream.write(settings.getBytes());
        fileOutputStream.close();
    }

    void loadSettings() {
        try {
            String str;
            BufferedReader br = new BufferedReader(new FileReader("settings.txt"));

            str = br.readLine();
            if (str == null) return;
            //System.out.println(str);
            if (str.indexOf('f') != -1) {
                willShowStatistics = false;
                mainForm.getInformationCheckbox().setSelected(false);
                mainForm.getInformationCheckBoxMenuItem().setSelected(false);
            }
            else {
                willShowStatistics = true;
                mainForm.getInformationCheckbox().setSelected(true);
                mainForm.getInformationCheckBoxMenuItem().setSelected(true);
            }

            str = br.readLine();
            //System.out.println(str);
            if (str.indexOf('f') != -1) {
                mainForm.hideTime();
            }
            else {
                mainForm.showTime();
            }

            str = br.readLine();
            //System.out.println(str);
            Guppy.setSpawnInterval(Integer.parseInt(str.substring(str.indexOf(' ') + 1)));
            mainForm.getGuppyIntervalTextField().setText(Integer.toString(Integer.parseInt(str.substring(str.indexOf(' ') + 1))));

            str = br.readLine();
            //System.out.println(str);
            Golden.setSpawnInterval(Integer.parseInt(str.substring(str.indexOf(' ') + 1)));
            mainForm.getGoldenIntervalTextField().setText(Integer.toString(Integer.parseInt(str.substring(str.indexOf(' ') + 1))));

            str = br.readLine();
            //System.out.println(str);
            Guppy.setSpawnProbability(Double.parseDouble(str.substring(str.indexOf(' ') + 1)));
            //System.out.println("*******" + (int)(Double.parseDouble(str.substring(str.indexOf(' ') + 1)) * 10) + "****");
            mainForm.getGuppyProbabilitySlider().setValue((int)(Double.parseDouble(str.substring(str.indexOf(' ') + 1)) * 10));

            str = br.readLine();
            //System.out.println(str);
            Golden.setSpawnProbability(Double.parseDouble(str.substring(str.indexOf(' ') + 1)));
            mainForm.getGoldenProbabilityComboBox().setSelectedIndex((int)(Double.parseDouble(str.substring(str.indexOf(' ') + 1)) * 10));

            str = br.readLine();
            //System.out.println(str);
            Guppy.setLifeTime(Integer.parseInt(str.substring(str.indexOf(' ') + 1)));
            mainForm.getGuppyLifeTimeTextField().setText(Integer.toString(Integer.parseInt(str.substring(str.indexOf(' ') + 1))));

            str = br.readLine();
            //System.out.println(str);
            Golden.setLifeTime(Integer.parseInt(str.substring(str.indexOf(' ') + 1)));
            mainForm.getGoldenLifeTimeTextField().setText(Integer.toString(Integer.parseInt(str.substring(str.indexOf(' ') + 1))));

            br.close();

        } catch (IOException exc) {
            System.out.println("IO error!" + exc);
        }
//        System.out.println(list.length);
    }
}
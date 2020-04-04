import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferStrategy;
import java.util.*;
import java.util.Timer;

public class HabitatView extends Canvas {
    private Habitat _habitat;

    //Ссылка на таймер, время между кадрами, время симуляции
    public static double ElapsedTime;
    private Timer _timer;
    private double _deltaTime;
    private boolean _showingTimer = true;
    private boolean _simulating = false;
    private boolean _firstRun = true;

    private Image _img1 = new ImageIcon("res/W.png").getImage();
    private Image _img2 = new ImageIcon("res/M.png").getImage();

    private JFrame frameParent = new JFrame("Labs");

    private JPanel panelUI;

    private JButton btnStart = new JButton("Start sim");
    private JButton btnStop = new JButton("Stop sim");
    private JButton btnStats = new JButton(" Stats  ");
    private JButton btnCurrentObj = new JButton("Current Obj");

    private JRadioButton radioShowTime = new JRadioButton("show timer");
    private JRadioButton radioHideTime = new JRadioButton("hide timer");

    private JSlider sliderChanceWorker = new JSlider(JSlider.VERTICAL,0, 100, 0);
    private JSlider sliderChanceManager = new JSlider(JSlider.VERTICAL,0, 100, 0);

    private JTextField txtFieldSpawnTimeWorker = new JTextField();
    private JTextField txtFieldSpawnTimeManager = new JTextField();
    private JTextField txtFieldLifeTimeWorker = new JTextField();
    private JTextField txtFieldLifeTimeManager = new JTextField();

    //меню бар
    private JMenuBar menuBar = new JMenuBar();
    private JMenu menuUI = new JMenu("Menu");
    private JMenuItem startMenu = new JMenuItem("start");
    private JMenuItem stopMenu = new JMenuItem("stop");
    private JMenuItem statMenu = new JMenuItem("statistic");

    //буффер и ссылка на графический контекст
    private BufferStrategy _bufferStrategy;
    private Graphics _graphics;

    public static int Width;
    public static int Height;
    public static int Margin;

    public HabitatView() {
        initComponents();
        initDefaultValue();
        stopSim();

        HabitatView.Width = this.getWidth();
        HabitatView.Height = this.getHeight();
        HabitatView.Margin = _img1.getHeight(null);
    }

    private void initDefaultValue()
    {
        radioShowTime.setSelected(true);
        sliderChanceWorker.setValue((int)(Worker.Chance * 100));
        sliderChanceManager.setValue((int)(Manager.Chance * 100));
        txtFieldSpawnTimeWorker.setText(String.valueOf(Worker.TimeBetweenSpawn));
        txtFieldSpawnTimeManager.setText(String.valueOf(Manager.TimeBetweenSpawn));
        txtFieldLifeTimeWorker.setText(String.valueOf(Worker.LifeTime));
        txtFieldLifeTimeManager.setText(String.valueOf(Manager.LifeTime));
    }

    private void initComponents() {
        frameParent.setPreferredSize(new Dimension(800, 800));
        frameParent.setMinimumSize(new Dimension(800, 800));
        frameParent.setLayout(new BorderLayout(0, 0));
        frameParent.setResizable(true);

        //Создаем панель UI, добавляем все элементы
        panelUI = new JPanel(new FlowLayout());
        panelUI.setPreferredSize(new Dimension(200,600));

        btnStart.addActionListener(e -> startSim());
        panelUI.add(btnStart);
        btnStop.addActionListener(e -> stopSim());
        panelUI.add(btnStop);
        btnStats.addActionListener(e -> showStats());
        panelUI.add(btnStats);
        btnCurrentObj.addActionListener(e -> showCurrent());
        panelUI.add(btnCurrentObj);

        // Кнопки включения\выключения
        ButtonGroup buttonGroup = new ButtonGroup();

        buttonGroup.add(radioShowTime);
        radioShowTime.addActionListener(e -> _showingTimer = true);
        panelUI.add(radioShowTime);

        buttonGroup.add(radioHideTime);
        radioHideTime.addActionListener(e ->  _showingTimer = false);
        panelUI.add(radioHideTime);

        //Слайдеры шанса появления объектов
        sliderChanceWorker.setBorder(BorderFactory.createTitledBorder("Chance Wrk"));
        sliderChanceWorker.setPreferredSize(new Dimension(95, 300));
        sliderChanceWorker.setMajorTickSpacing(10);
        sliderChanceWorker.setMinorTickSpacing(5);
        sliderChanceWorker.setPaintTicks(true);
        sliderChanceWorker.setPaintLabels(true);
        sliderChanceWorker.addChangeListener(e -> Worker.Chance = (((JSlider)e.getSource()).getValue()) / 100f);
        panelUI.add(sliderChanceWorker);

        sliderChanceManager.setBorder(BorderFactory.createTitledBorder("Part Mng"));
        sliderChanceManager.setPreferredSize(new Dimension(95, 300));
        sliderChanceManager.setMajorTickSpacing(10);
        sliderChanceManager.setMinorTickSpacing(5);
        sliderChanceManager.setPaintTicks(true);
        sliderChanceManager.setPaintLabels(true);
        sliderChanceManager.addChangeListener(e -> Manager.Chance = (((JSlider)e.getSource()).getValue()) / 100f);
        panelUI.add(sliderChanceManager);

        //Поля ввода (время между генерацией | время жизни)
        txtFieldSpawnTimeWorker.setName("SpawnTimeWorker");
        txtFieldSpawnTimeWorker.setBorder(BorderFactory.createTitledBorder("Time Spawn Worker"));
        txtFieldSpawnTimeWorker.setPreferredSize(new Dimension(200, 40));
        txtFieldSpawnTimeWorker.addActionListener(new ByTextFieldNameActionListener());
        panelUI.add(txtFieldSpawnTimeWorker);

        txtFieldSpawnTimeManager.setName("SpawnTimeManager");
        txtFieldSpawnTimeManager.setBorder(BorderFactory.createTitledBorder("Time Spawn Manager"));
        txtFieldSpawnTimeManager.setPreferredSize(new Dimension(200, 40));
        txtFieldSpawnTimeManager.addActionListener(new ByTextFieldNameActionListener());
        panelUI.add(txtFieldSpawnTimeManager);

        txtFieldLifeTimeWorker.setName("LifeTimeWorker");
        txtFieldLifeTimeWorker.setBorder(BorderFactory.createTitledBorder("Life time Worker"));
        txtFieldLifeTimeWorker.setPreferredSize(new Dimension(200, 40));
        txtFieldLifeTimeWorker.addActionListener(new ByTextFieldNameActionListener());
        panelUI.add(txtFieldLifeTimeWorker);

        txtFieldLifeTimeManager.setName("LifeTimeManager");
        txtFieldLifeTimeManager.setBorder(BorderFactory.createTitledBorder("Life time Manager"));
        txtFieldLifeTimeManager.setPreferredSize(new Dimension(200, 40));
        txtFieldLifeTimeManager.addActionListener(new ByTextFieldNameActionListener());
        panelUI.add(txtFieldLifeTimeManager);

        //Меню-бар
        menuUI.add(startMenu);
        startMenu.addActionListener(e -> startSim());
        menuUI.add(stopMenu);
        stopMenu.addActionListener(e -> stopSim());
        menuUI.add(statMenu);
        statMenu.addActionListener(e -> showStats());
        menuBar.add(menuUI);
        frameParent.setJMenuBar(menuBar);

        frameParent.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });

        this.addComponentListener(new ComponentAdapter() {
            public void componentResized(ComponentEvent e) {
                HabitatView.Width = getWidth();
                HabitatView.Height = getHeight();
            }
        });

        //Добавляем слушателя для клавиатуры
        addKeyListener(new processKeyEvent());

        frameParent.add(panelUI, BorderLayout.WEST);
        frameParent.add(this);

        frameParent.pack();
        frameParent.setVisible(true);

        this.createBufferStrategy(3);
        _bufferStrategy = getBufferStrategy();
    }

    private class ByTextFieldNameActionListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            JTextField tmp = (JTextField) e.getSource();
            float fieldValue;
            try {
                fieldValue = Float.parseFloat(tmp.getText());
            } catch (NumberFormatException ee) {
                fieldValue = 0;
            }
            if (fieldValue <= 0) fieldValue = 1;
            switch (tmp.getName()) {
                case ("SpawnTimeWorker"):
                    Worker.TimeBetweenSpawn = fieldValue;
                    break;
                case ("SpawnTimeManager"):
                    Manager.TimeBetweenSpawn = fieldValue;
                    break;
                case ("LifeTimeWorker"):
                    Worker.LifeTime = fieldValue;
                    break;
                case ("LifeTimeManager"):
                    Manager.LifeTime = fieldValue;
                    break;
            }
            tmp.setText(String.valueOf(fieldValue));
        }
    }

    //Слушатель нажатия клавиш
    private class processKeyEvent extends KeyAdapter {
        public void keyPressed(KeyEvent e) {
            switch (e.getKeyChar()) {
                case ('t'):
                    _showingTimer = !_showingTimer;
                    if (_showingTimer) radioShowTime.setSelected(true);
                    else radioHideTime.setSelected(true);
                    break;
                case ('b'):
                    if(_simulating) break;
                    startSim();
                    break;
                case ('e'):
                    stopSim();
                    break;
                case (27):
                    System.exit(0);
                    break;
                default:
                    break;
            }
        }
    }

    //демонстрация диалогово окна
    public void showStats(){
        pauseSim();
        int result = JOptionPane.showConfirmDialog(this,
                String.format("Emp amount: %d\nWrk amount: %d\nMng amount: %d\nelapsed time: %.3f", Employee.Amount, Worker.Amount, Manager.Amount, ElapsedTime),
                "Statistic", JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION)
            continueSim();
        else if (result == JOptionPane.CANCEL_OPTION)
            stopSim();
    }

    public void showCurrent(){
        pauseSim();
        JDialog container = new JDialog(frameParent, "Current object", true);
        JTextArea textArea = new JTextArea();
        JScrollPane scrollPane = new JScrollPane(textArea);

        container.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                continueSim();
                container.dispose(); }
        });

        StringBuilder str = new StringBuilder();
        for(Employee tmp : _habitat.ObjCollection)
            if(tmp instanceof Worker  && (ElapsedTime - tmp.getBornTime() < Worker.LifeTime))
                str.append(String.format("Worker:  %s, born time: %6.3f\n", tmp.getID(), tmp.getBornTime()));
            else if(tmp instanceof Manager && (ElapsedTime - tmp.getBornTime() < Manager.LifeTime))
                str.append(String.format("Manager: %s, born time: %6.3f\n", tmp.getID(), tmp.getBornTime()));

        textArea.setText(str.toString());

        textArea.setDisabledTextColor(Color.BLACK);
        container.setPreferredSize(new Dimension(600, 200));
        container.add(scrollPane);
        textArea.setEnabled(false);
        container.pack();
        container.setVisible(true);
    }

    private float _refTime = 5F;
    private float _refTimer = _refTime;
    //Генерация и отрисовка наших объектов
    private void update(){
        _habitat.update(_deltaTime);
        // удаление объектов, у которых истекло время жизни, каждые _refTime секунд
        _refTimer -= _deltaTime;
        if(_refTimer <= 0) {
            _refTimer = _refTime;
            synchronized (_habitat.ObjCollection) {
                for (Iterator<Employee> iterator = _habitat.ObjCollection.iterator(); iterator.hasNext(); ) {
                    Employee temp = iterator.next();

                    if (temp instanceof Worker && (ElapsedTime - temp.getBornTime() > Worker.LifeTime)) --Worker.Amount;
                    else if (temp instanceof Manager && (ElapsedTime - temp.getBornTime() > Manager.LifeTime)) --Manager.Amount;

                    if(Employee.Amount != Worker.Amount + Manager.Amount) {
                        UUID tmpID = temp.getID();
                        _habitat.IdCollection.remove(tmpID);
                        _habitat.BornTimeCollection.remove(tmpID);
                        iterator.remove();
                        Employee.Amount--;
                    }
                }
            }
        }
        paint();
    }

    //Отрисовка наших объектов и таймера при помощи буфера
    private void paint() {
        //Получаем графический контекст буфера
        _graphics = _bufferStrategy.getDrawGraphics();
        clearViewScreen();
        //Отрисовывыаем объекты
        for(Employee temp : _habitat.ObjCollection)
            if (temp instanceof Worker && (ElapsedTime - temp.getBornTime() < Worker.LifeTime)) _graphics.drawImage(_img1, temp.getX(), temp.getY(),this);
            else if (temp instanceof Manager  && (ElapsedTime - temp.getBornTime() < Manager.LifeTime)) _graphics.drawImage(_img2, temp.getX(), temp.getY(),this);
        //Отрисовывваем таймер
        if(_showingTimer)
            _graphics.drawString(String.format("%.3f", ElapsedTime), 0,  16);
        //освобождаем ресуры
        _graphics.dispose();
        //выводим изображения с буфера на экран
        _bufferStrategy.show();
    }


    private void clearViewScreen() {
        _graphics.setColor(Color.GRAY);
        _graphics.fillRect(0,0, getWidth(), getHeight());
        _graphics.setColor(Color.BLACK);
    }

    //вспмогательяные методы пауза. старт, стоп, продолжить
    private void startSim() {
        for(Component tmp : panelUI.getComponents())
            tmp.setEnabled(true);
        btnStart.setEnabled(false);
        startMenu.setEnabled(false);

        _firstRun = false;
        _simulating = true;

        _habitat = new Habitat();
        _timer = new Timer();
        _timer.schedule(new SimulationLoop(), 0, 10);

        Employee.Amount = 0;
        Manager.Amount = 0;
        Worker.Amount = 0;
    }

    private void continueSim(){ _simulating = true; }
    private void pauseSim() { _simulating = false; }

    private void stopSim() {
        for(Component tmp : panelUI.getComponents())
            tmp.setEnabled(false);
        btnStart.setEnabled(true);
        startMenu.setEnabled(true);

        _simulating = false;
        _graphics = this.getGraphics();
        clearViewScreen();

        if(!_firstRun) {
            int midWidth = Width / 2 - 100;
            int midHeight = Height / 2;
            _graphics.setFont(new Font("Consolas", Font.PLAIN, 20));
            _graphics.setColor(new Color(0, 255, 255, 255));
            _graphics.drawString(String.format("Emp amount: %d", Employee.Amount), midWidth, midHeight);
            _graphics.setFont(new Font("Arial", Font.ITALIC, 18));
            _graphics.setColor(new Color(255, 255, 255, 255));
            _graphics.drawString(String.format("Wrk amount: %d", Worker.Amount), midWidth, midHeight + 50);
            _graphics.setFont(new Font("Consolas", Font.BOLD, 22));
            _graphics.setColor(new Color(255, 0, 255, 255));
            _graphics.drawString(String.format("Mng amount: %d", Manager.Amount), midWidth, midHeight + 100);
            _graphics.setFont(new Font("Arial", Font.BOLD | Font.ITALIC, 30));
            _graphics.setColor(new Color(200, 170, 30, 255));
            _graphics.drawString(String.format("%.3f", ElapsedTime), midWidth, midHeight + 150);
            _timer.cancel();
        }
        else {
            _graphics.drawString("Press Start", getWidth()/2, getHeight()/2);
        }
    }

    public static void main(String[] args) {
        new HabitatView();
    }

    //класс таймера
    private class SimulationLoop extends TimerTask {
        private double _pauseTime;
        private double _startTime;
        private double _lastTime;

        public SimulationLoop()
        {
            _startTime = System.currentTimeMillis();
            _lastTime = _startTime;
            _pauseTime = 0;
        }

        @Override
        public void run() {
            if(_simulating) {
                double currentTime = System.currentTimeMillis();
                // Время, прошедшее от начала, в секундах
                ElapsedTime = (currentTime - _startTime - _pauseTime) / 1000.0;
                // Время, прошедшее с последнего обновления, в секундах
                _deltaTime = (currentTime - _lastTime) / 1000.0;
                _lastTime = currentTime;
                // Вызываем обновление
                update();
            }
            else {
                double currentTime = System.currentTimeMillis();
                _pauseTime = (currentTime - _startTime - ElapsedTime * 1000);
                _lastTime = currentTime;
            }
        }
    }

}

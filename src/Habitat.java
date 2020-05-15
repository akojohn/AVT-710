import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Date;

public class Habitat extends JFrame {

    public long theUniverseStartTime = 0;
    public  long theUniverseTime = 0;
    private int amountVehicles = 0;
    private ArrayList<JImage> universeVehicles = new ArrayList<>();

    private final double probabilityAutomobile = 0.5;
    private final double probabilityMotorcycle = 0.8;

    private final int frameWidth = 800;
    private final int frameHeight = 600;
    private final int motorcycleHeightSize = 64;
    private final int motorcycleWidthSize = 121;
    private final int autoHeightSize = 64;
    private final int autoWidthSize = 192;

    private final int autoWidth = frameWidth - autoWidthSize - 50;
    private final int autoHeight = frameHeight - autoHeightSize - 50;
    private final int motorcycleWidth = frameWidth - motorcycleWidthSize - 50;
    private final int motorcycleHeight = frameHeight - motorcycleHeightSize - 50;

    public JLabel jTimer = new JLabel();
    private boolean show = true;

    private JPanel statisticsPanel = new JPanel();
    private JLabel firstPart = new JLabel();
    private JLabel secondPart = new JLabel();

    Timer timerForAutomobile = new Timer(500, e -> {
        if (Math.random() <= probabilityAutomobile) {
            update(true);
        }
    });
    Timer timerForMotorcycle = new Timer(800, e -> {
        if (Math.random() <= probabilityMotorcycle) {
            update(false);
        }
    });

    public boolean isEnd = false;
    public boolean isStart = false;

    Habitat() {
        setBounds(283,84, frameWidth, frameHeight);
        add(new Terrain());
        setVisible(true);
        jTimer.setBounds(10, 15, 100, 12);
        add(jTimer,0);
        setLayout(null);

        statisticsPanel.setBounds(400, 200, 200, 200);
        statisticsPanel.setBackground(Color.getHSBColor(110,110,100));

        firstPart.setBounds(420,220,160, 20);
        secondPart.setBounds(420,260,160, 20);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    private void addVehicle(JImage Vehicle) {
        add(Vehicle, 0);                            // дополительная перегрузка (0) для отображения следующего
        universeVehicles.add(Vehicle);                     // над прдыдущим (друг на друга)
        amountVehicles++;
    }

    private void update(boolean is_automobile) {
        jTimer.setText(String.valueOf(new Date().getTime() - theUniverseStartTime));
        if (is_automobile) {
            int tempX = (int) (autoWidth * Math.random());
            int tempY = (int) (autoHeight * Math.random());
            addVehicle(new Automobile(tempX, tempY));
        } else {
            int tempX = (int) (motorcycleWidth * Math.random());
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
        while (universeVehicles.size() > 0)
            universeVehicles.remove(0);
        universeVehicles.clear();
        getContentPane().repaint();
    }

    void dateClearing() {
        amountVehicles = 0;
        theUniverseTime = 0;
    }

    void Statistics(){
        firstPart.setText("Количество элементов: " + amountVehicles);
        Font font = new Font("Italic", Font.BOLD, 11);
        firstPart.setFont(font);
        firstPart.setForeground(Color.blue);
        secondPart.setText("Время работы: " + theUniverseTime);
        add(statisticsPanel,0);
        add(firstPart,0);
        add(secondPart,0);

        setVisible(true);
        repaint();
    }
}

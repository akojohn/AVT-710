import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Dictionary;
import java.util.Hashtable;

import static java.lang.Character.isDigit;

class MenuPanel extends JPanel {
    private static final Color buttonsColor = new Color(198, 108, 255);
    private static final Color menuColor = new Color(234, 192, 243);
    private JButton startButton = new JButton("START");
    private JButton stopButton = new JButton("STOP");
    private JTextField guppyIntervalTextField = new JTextField("" + new Guppy().getSpawnInterval());
    private JTextField goldenIntervalTextField = new JTextField("" + new Golden().getSpawnInterval());
    private JRadioButton showTimeRadioButton = new JRadioButton("Показать время работы");
    private JRadioButton hideTimeRadioButton = new JRadioButton("Скрыть время работы", true);
    private Checkbox informationCheckbox = new Checkbox("Показывать информацию о генерации");

    MenuPanel() {
        Font italicFont12 = new Font("TimesRoman", Font.ITALIC, 12);
        Font boldFont12 = new Font("TimesRoman", Font.BOLD, 12);

        setLayout(null);
        setBackground(menuColor);
        setBounds(50, 50, 50, 50);
        setPreferredSize(new Dimension(300, 0));

        startButton.setBounds(4, 10, 100, 30);
        startButton.setBackground(buttonsColor);
        add(startButton);

        stopButton.setBounds(170, 10, 100, 30);
        stopButton.setBackground(buttonsColor);
        lockStopButton();
        add(stopButton);

        informationCheckbox.setBounds(10, 50, 260, 30);
        informationCheckbox.setFont(boldFont12);
        add(informationCheckbox);

        ButtonGroup timeInformation = new ButtonGroup();
        showTimeRadioButton.setBounds(10, 100, 200, 40);
        showTimeRadioButton.setBackground(menuColor);
        hideTimeRadioButton.setBounds(10, 130, 200, 40);
        hideTimeRadioButton.setBackground(menuColor);
        timeInformation.add(showTimeRadioButton);
        timeInformation.add(hideTimeRadioButton);
        add(showTimeRadioButton);
        add(hideTimeRadioButton);

        JLabel settingsTitleLabel = new JLabel("НАСТРОЙКИ ГЕНЕРАЦИИ");
        settingsTitleLabel.setBounds(70, 200, 150, 20);
        add(settingsTitleLabel);

        JLabel enterIntervalPrompt = new JLabel("1) Введите в окошки время в секундах");
        enterIntervalPrompt.setBounds(30, 230, 300, 20);
        enterIntervalPrompt.setFont(italicFont12);
        add(enterIntervalPrompt);

        JLabel guppyIntervalPrompt = new JLabel("Период создания гуппи:");
        guppyIntervalPrompt.setBounds(10, 260, 150, 20);

        guppyIntervalTextField.setBounds(175, 260, 40, 20);
        guppyIntervalTextField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                super.keyReleased(e);
                System.out.println(e);
                if (!isNumber(guppyIntervalTextField.getText())) {
                    guppyIntervalTextField.setText("" + new Guppy().getSpawnInterval());
                    JOptionPane.showMessageDialog(null, "You must enter digits!");
                }
                try {
                    guppyIntervalTextField.setText("" + Integer.parseInt(guppyIntervalTextField.getText()));
                    Guppy.setSpawnInterval(Integer.parseInt(guppyIntervalTextField.getText()));
                } catch (Exception ignored) {
                }
            }
        });

        add(guppyIntervalPrompt);
        add(guppyIntervalTextField);

        JLabel goldenIntervalPrompt = new JLabel("Период создания золотой:");
        goldenIntervalPrompt.setBounds(10, 300, 200, 20);

        goldenIntervalTextField.setBounds(175, 300, 40, 20);
        goldenIntervalTextField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                super.keyReleased(e);
                System.out.println(e);
                if (!isNumber(goldenIntervalTextField.getText())) {
                    goldenIntervalTextField.setText("" + new Golden().getSpawnInterval());
                    JOptionPane.showMessageDialog(null, "You must enter digits!");
                }
                try {
                    goldenIntervalTextField.setText("" + Integer.parseInt(goldenIntervalTextField.getText()));
                    Golden.setSpawnInterval(Integer.parseInt(goldenIntervalTextField.getText()));
                } catch (Exception ignored) {
                }
            }
        });

        add(goldenIntervalPrompt);
        add(goldenIntervalTextField);

        JLabel enterProbabilityPrompt = new JLabel("2) Выберите вероятность");
        enterProbabilityPrompt.setBounds(30, 330, 300, 20);
        enterProbabilityPrompt.setFont(italicFont12);
        add(enterProbabilityPrompt);

        JLabel guppyProbabilityLabel = new JLabel("Гуппи");
        guppyProbabilityLabel.setBounds(50, 360, 300, 20);
        guppyProbabilityLabel.setFont(italicFont12);
        add(guppyProbabilityLabel);

        JLabel goldenProbabilityLabel = new JLabel("Золотые");
        goldenProbabilityLabel.setBounds(50, 510, 300, 20);
        goldenProbabilityLabel.setFont(italicFont12);
        add(goldenProbabilityLabel);

//------------------------------------------------------------- вероятность гуппи
        JSlider guppyProbabilitySlider = new JSlider(0, 10, (int) (new Guppy().getSpawnProbability() * 10));
        guppyProbabilitySlider.setPaintLabels(true);
        guppyProbabilitySlider.setPaintTicks(true);
        guppyProbabilitySlider.setMajorTickSpacing(1);
        guppyProbabilitySlider.setSnapToTicks(false);

        Dictionary<Integer, JLabel> guppyProbabilitySliderLabels = new Hashtable<>();
        guppyProbabilitySliderLabels.put(0, new JLabel("0"));
        guppyProbabilitySliderLabels.put(5, new JLabel("50"));
        guppyProbabilitySliderLabels.put(10, new JLabel("100"));

        guppyProbabilitySlider.setLabelTable(guppyProbabilitySliderLabels);
        guppyProbabilitySlider.setBounds(20, 400, 250, 50);
        guppyProbabilitySlider.setBackground(menuColor);

        JLabel guppyProbabilityValueLabel = new JLabel("Вероятность рождения гуппи " + (int) (new Guppy().getSpawnProbability() * 100) + "%");
        guppyProbabilityValueLabel.setBounds(50, 470, 300, 20);
        add(guppyProbabilityValueLabel);

        guppyProbabilitySlider.addChangeListener(e -> {
            guppyProbabilityValueLabel.setText("Вероятность рождения гуппи " + guppyProbabilitySlider.getValue() * 10 + "%");
            Guppy.setSpawnProbability((double)(guppyProbabilitySlider.getValue()) / 10);
        });
        add(guppyProbabilitySlider);

//----------------------- вероятность золотых рыбок
        DefaultComboBoxModel<Integer> goldenProbabilityComboBoxModel = new DefaultComboBoxModel<>();
        int[] goldenProbabilityValues = new int[]{0, 10, 20, 30, 40, 50, 60, 70, 80, 90, 100};
        for (Integer value : goldenProbabilityValues) {
            goldenProbabilityComboBoxModel.addElement(value);
        }
        JComboBox goldenProbabilityComboBox = new JComboBox<>(goldenProbabilityComboBoxModel);
        goldenProbabilityComboBox.setSelectedIndex((int) (new Golden().getSpawnProbability() * 10));
        goldenProbabilityComboBox.setBounds(20, 550, 180, 30);
        goldenProbabilityComboBox.addActionListener(e -> {
            Golden.setSpawnProbability((double)(int)(goldenProbabilityComboBox.getSelectedItem()) / 10);
        });
        add(goldenProbabilityComboBox);
    }

    void lockStartButton() {
        startButton.setEnabled(false);
    }

    void lockStopButton() {
        stopButton.setEnabled(false);
    }

    void unlockStartButton() {
        startButton.setEnabled(true);
    }

    void unlockStopButton() {
        stopButton.setEnabled(true);
    }

    JButton getStartButton() {
        return startButton;
    }

    JButton getStopButton() {
        return stopButton;
    }

    JRadioButton getShowTimeRadioButton() {
        return showTimeRadioButton;
    }

    JRadioButton getHideTimeRadioButton() {
        return hideTimeRadioButton;
    }

    boolean isViewInformationChecked() {
        return informationCheckbox.getState();
    }

    private boolean isNumber(String string) {
        for (int i = 0; i < string.length(); i++) {
            if (!isDigit(string.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    void showTime() {
        showTimeRadioButton.setSelected(true);
    }

    void hideTime() {
        hideTimeRadioButton.setSelected(true);
    }

}
package Client;

import javax.swing.*;
import javax.swing.text.MaskFormatter;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.text.ParseException;
import java.util.Dictionary;
import java.util.Hashtable;

import static java.lang.Character.isDigit;

class SettingsPanel extends JPanel {
    private static final Color buttonsColor = new Color(198, 108, 255);
    private static final Color menuColor = new Color(234, 192, 243);
    private JButton startButton = new JButton("START");
    private JButton stopButton = new JButton("STOP");
    private JButton updateLifeTimeButton = new JButton("Задать время жизни");
    private JButton showCurrentObject = new JButton("Текущие объекты");

    JTextField getGoldenIntervalTextField() {
        return goldenIntervalTextField;
    }

    JTextField getGuppyIntervalTextField() {
        return guppyIntervalTextField;
    }

    private JTextField guppyIntervalTextField = new JTextField("" + new Guppy().getSpawnInterval());
    private JTextField goldenIntervalTextField = new JTextField("" + new Golden().getSpawnInterval());

    JFormattedTextField getGuppyLifeTimeTextField() {
        return guppyLifeTimeTextField;
    }

    private JFormattedTextField guppyLifeTimeTextField;
    private JFormattedTextField goldenLifeTimeTextField;
    private JRadioButton showTimeRadioButton = new JRadioButton("Показать время работы");
    private JRadioButton hideTimeRadioButton = new JRadioButton("Скрыть время работы", true);
    private JCheckBox informationCheckbox = new JCheckBox("Показать статистику");
    private JButton pauseGuppyAI = new JButton("Выкл. ИИ гуппи");
    private JButton unpauseGuppyAI = new JButton("Вкл. ИИ гуппи");
    private JButton pauseGoldenAI = new JButton("Выкл. ИИ золотых");
    private JButton unpauseGoldenAI = new JButton("Вкл. ИИ золотых");
    private JComboBox guppyPriorityComboBox;
    private JComboBox goldenPriorityComboBox;

    JComboBox getGoldenProbabilityComboBox() {
        return goldenProbabilityComboBox;
    }

    private JComboBox goldenProbabilityComboBox;

    JSlider getGuppyProbabilitySlider() {
        return guppyProbabilitySlider;
    }

    private JSlider guppyProbabilitySlider;

    SettingsPanel() {
        Font italicFont12 = new Font("TimesRoman", Font.ITALIC, 12);
        Font boldFont12 = new Font("TimesRoman", Font.BOLD, 12);

        setLayout(null);
        setBackground(menuColor);
        setBounds(50, 50, 50, 50);
        setPreferredSize(new Dimension(300, 0));

        startButton.setBounds(25, 10, 100, 30);
        startButton.setBackground(buttonsColor);
        add(startButton);

        stopButton.setBounds(170, 10, 100, 30);
        stopButton.setBackground(buttonsColor);

        lockStopButton();
        add(stopButton);

        informationCheckbox.setBounds(10, 50, 260, 30);
        informationCheckbox.setBackground(menuColor);
        informationCheckbox.setFont(boldFont12);
        add(informationCheckbox);

        ButtonGroup timeInformation = new ButtonGroup();
        showTimeRadioButton.setBounds(10, 75, 200, 40);
        showTimeRadioButton.setBackground(menuColor);
        hideTimeRadioButton.setBounds(10, 110, 200, 40);
        hideTimeRadioButton.setBackground(menuColor);
        timeInformation.add(showTimeRadioButton);
        timeInformation.add(hideTimeRadioButton);
        add(showTimeRadioButton);
        add(hideTimeRadioButton);

        JLabel settingsTitleLabel = new JLabel("НАСТРОЙКИ ГЕНЕРАЦИИ");
        settingsTitleLabel.setBounds(70, 150, 150, 20);
        add(settingsTitleLabel);

        JLabel enterIntervalPrompt = new JLabel("1) Введите в окошки время в секундах");
        enterIntervalPrompt.setBounds(30, 180, 300, 20);
        enterIntervalPrompt.setFont(italicFont12);
        add(enterIntervalPrompt);

        JLabel guppyIntervalPrompt = new JLabel("Период создания гуппи:");
        guppyIntervalPrompt.setBounds(10, 210, 150, 20);

        guppyIntervalTextField.setBounds(175, 210, 40, 20);
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
        goldenIntervalPrompt.setBounds(10, 240, 200, 20);

        goldenIntervalTextField.setBounds(175, 240, 40, 20);
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
        enterProbabilityPrompt.setBounds(30, 270, 300, 20);
        enterProbabilityPrompt.setFont(italicFont12);
        add(enterProbabilityPrompt);

        JLabel guppyProbabilityLabel = new JLabel("Гуппи");
        guppyProbabilityLabel.setBounds(50, 300, 300, 20);
        //guppyProbabilityLabel.setFont(italicFont12);
        add(guppyProbabilityLabel);

        JLabel goldenProbabilityLabel = new JLabel("Золотые");
        goldenProbabilityLabel.setBounds(50, 380, 300, 20);
        //goldenProbabilityLabel.setFont(italicFont12);
        add(goldenProbabilityLabel);

//------------------------------------------------------------- вероятность гуппи
        guppyProbabilitySlider = new JSlider(0, 10, (int) (new Guppy().getSpawnProbability() * 10));
        guppyProbabilitySlider.setPaintLabels(true);
        guppyProbabilitySlider.setPaintTicks(true);
        guppyProbabilitySlider.setMajorTickSpacing(1);
        guppyProbabilitySlider.setSnapToTicks(false);

        Dictionary<Integer, JLabel> guppyProbabilitySliderLabels = new Hashtable<>();
        guppyProbabilitySliderLabels.put(0, new JLabel("0"));
        guppyProbabilitySliderLabels.put(5, new JLabel("50"));
        guppyProbabilitySliderLabels.put(10, new JLabel("100"));

        guppyProbabilitySlider.setLabelTable(guppyProbabilitySliderLabels);
        guppyProbabilitySlider.setBounds(10, 330, 250, 50);
        guppyProbabilitySlider.setBackground(menuColor);

        JLabel guppyProbabilityValueLabel = new JLabel(" " + (int) (new Guppy().getSpawnProbability() * 100) + "%");
        guppyProbabilityValueLabel.setBounds(260, 330, 300, 20);
        add(guppyProbabilityValueLabel);

        guppyProbabilitySlider.addChangeListener(e -> {
            guppyProbabilityValueLabel.setText(" " + guppyProbabilitySlider.getValue() * 10 + "%");
            Guppy.setSpawnProbability((double)(guppyProbabilitySlider.getValue()) / 10);
        });
        add(guppyProbabilitySlider);

//----------------------- вероятность золотых рыбок
        DefaultComboBoxModel<Integer> goldenProbabilityComboBoxModel = new DefaultComboBoxModel<>();
        int[] goldenProbabilityValues = new int[]{0, 10, 20, 30, 40, 50, 60, 70, 80, 90, 100};
        for (Integer value : goldenProbabilityValues) {
            goldenProbabilityComboBoxModel.addElement(value);
        }
        goldenProbabilityComboBox = new JComboBox<>(goldenProbabilityComboBoxModel);
        goldenProbabilityComboBox.setSelectedIndex((int) (new Golden().getSpawnProbability() * 10));
        goldenProbabilityComboBox.setBounds(20, 410, 180, 30);
        goldenProbabilityComboBox.addActionListener(e -> {
            Golden.setSpawnProbability((double)(int)(goldenProbabilityComboBox.getSelectedItem()) / 10);
        });
        add(goldenProbabilityComboBox);

        try {
            MaskFormatter number = new MaskFormatter("*");
            number.setValidCharacters("0123456789");
            guppyLifeTimeTextField = new JFormattedTextField(number);
            goldenLifeTimeTextField = new JFormattedTextField(number);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        JLabel guppyLifeTimePrompt = new JLabel("Срок жизни гуппи (0 - 9):");
        guppyLifeTimePrompt.setBounds(10, 440, 200, 20);
        add(guppyLifeTimePrompt);

        guppyLifeTimeTextField.setBounds(175, 440, 40, 20);
        guppyLifeTimeTextField.setText("" + new Guppy().getLifeTime());
        add(guppyLifeTimeTextField);

        JLabel goldenLifeTimePrompt = new JLabel("Срок жизни золотой (0 - 9):");
        goldenLifeTimePrompt.setBounds(10, 470, 200, 20);
        add(goldenLifeTimePrompt);

        goldenLifeTimeTextField.setBounds(175, 470, 40, 20);
        goldenLifeTimeTextField.setText("" + new Golden().getLifeTime());
        add(goldenLifeTimeTextField);

        updateLifeTimeButton.setBounds(10, 500, 205, 30);
        updateLifeTimeButton.setBackground(buttonsColor);
        add(updateLifeTimeButton);

        showCurrentObject.setBounds(10, 530, 205, 30);
        showCurrentObject.setBackground(buttonsColor);
        add(showCurrentObject);

        pauseGuppyAI.setBounds(10, 560, 205, 30);
        pauseGuppyAI.setBackground(buttonsColor);
        add(pauseGuppyAI);

        unpauseGuppyAI.setBounds(10, 590, 205, 30);

        unpauseGuppyAI.setBackground(buttonsColor);
        unpauseGuppyAI.setEnabled(false);
        add(unpauseGuppyAI);

        DefaultComboBoxModel<Integer> guppyPriorityComboBoxModel = new DefaultComboBoxModel<>();
        DefaultComboBoxModel<Integer> goldenPriorityComboBoxModel = new DefaultComboBoxModel<>();

        int[] priorityValues = new int[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
        for (Integer value : priorityValues) {
            guppyPriorityComboBoxModel.addElement(value);
            goldenPriorityComboBoxModel.addElement(value);
        }

        guppyPriorityComboBox = new JComboBox<>(guppyPriorityComboBoxModel);
        goldenPriorityComboBox = new JComboBox<>(goldenPriorityComboBoxModel);

        guppyPriorityComboBox.setSelectedIndex(4);
        goldenPriorityComboBox.setSelectedIndex(4);

        guppyPriorityComboBox.setBounds(220, 560, 50, 30);
        goldenPriorityComboBox.setBounds(220, 620, 50, 30);
        add(guppyPriorityComboBox);
        add(goldenPriorityComboBox);

        pauseGoldenAI.setBounds(10, 620, 205, 30);
        pauseGoldenAI.setBackground(buttonsColor);
        add(pauseGoldenAI);

        unpauseGoldenAI.setBounds(10, 650, 205, 30);
        unpauseGoldenAI.setBackground(buttonsColor);
        unpauseGoldenAI.setEnabled(false);
        add(unpauseGoldenAI);
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

    JButton getUpdateLifeTimeButton() {
        return updateLifeTimeButton;
    }

    JButton getShowCurrentObject(){
        return showCurrentObject;
    }

    JButton getPauseGuppyAI() {
        return pauseGuppyAI;
    }

    JButton getUnpauseGuppyAI() {
        return unpauseGuppyAI;
    }

    JButton getPauseGoldenAI() {
        return pauseGoldenAI;
    }

    JButton getUnpauseGoldenAI() {
        return unpauseGoldenAI;
    }

    JFormattedTextField getGoldenLifeTimeTextField() {
        return goldenLifeTimeTextField;
    }

    //JFormattedTextField getGuppyIntervalTextField() {
    //    return guppyLifeTimeTextField;
    //}

    JRadioButton getShowTimeRadioButton() {
        return showTimeRadioButton;
    }

    JRadioButton getHideTimeRadioButton() {
        return hideTimeRadioButton;
    }

    JComboBox getGuppyPriorityComboBox() {
        return guppyPriorityComboBox;
    }

    JComboBox getGoldenPriorityComboBox() {
        return goldenPriorityComboBox;
    }

    boolean isViewInformationChecked() {
        return informationCheckbox.isSelected();
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

    JCheckBox getInformationCheckbox() {
        return informationCheckbox;
    }

    boolean isShowTimeRadioButtonChecked() {
        return showTimeRadioButton.isSelected();
    }
}
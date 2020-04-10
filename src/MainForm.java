import javax.swing.*;
import java.awt.*;

class MainForm extends JFrame {
    private Menu menu = new Menu();
    private SettingsPanel settingsPanel = new SettingsPanel();
    private JPanel drawingPanel = new JPanel();
    private JPanel timePanel = new JPanel();
    private JLabel timeLabel = new JLabel("");

    MainForm() {
        setTitle("Aquarium");

        add(settingsPanel);
        add(settingsPanel, BorderLayout.EAST);
        timePanel.setBackground(new Color(217, 214, 243));
        timePanel.setPreferredSize(new Dimension(0, 30));
        timePanel.setVisible(false);
        timePanel.add(timeLabel);
        add(timePanel, BorderLayout.SOUTH);
        drawingPanel.setBackground(new Color(175, 218, 243));
        add(drawingPanel);
        setJMenuBar(menu);
    }

    void setTimeLabelText(String text) {
        timeLabel.setText(text);
    }

    void hideTime() {
        menu.hideTime();
        settingsPanel.hideTime();
        timePanel.setVisible(false);
    }

    void showTime() {
        menu.showTime();
        settingsPanel.showTime();
        timePanel.setVisible(true);
    }

    JPanel getDrawingPanel() {
        return drawingPanel;
    }

    JButton getStopButton() {
        return settingsPanel.getStopButton();
    }

    JButton getStartButton() {
        return settingsPanel.getStartButton();
    }

    JButton getPauseGuppyAI() {
        return settingsPanel.getPauseGuppyAI();
    }

    JButton getUnpauseGuppyAI() {
        return settingsPanel.getUnpauseGuppyAI();
    }

    JButton getPauseGoldenAI() {
        return settingsPanel.getPauseGoldenAI();
    }

    JButton getUnpauseGoldenAI() {
        return settingsPanel.getUnpauseGoldenAI();
    }

    JFormattedTextField getGoldenLifeTimeTextField() {
        return settingsPanel.getGoldenLifeTimeTextField();
    }

    JTextField getGuppyIntervalTextField() {
        return settingsPanel.getGuppyIntervalTextField();
    }

    JFormattedTextField getGuppyLifeTimeTextField() {
        return settingsPanel.getGuppyLifeTimeTextField();
    }

    JButton getUpdateLifeTimeButton() {
        return settingsPanel.getUpdateLifeTimeButton();
    }

    JRadioButton getShowTimeRadioButton() {
        return settingsPanel.getShowTimeRadioButton();
    }

    JButton getShowCurrentObject() {
        return settingsPanel.getShowCurrentObject();
    }

    JRadioButton getHideTimeRadioButton() {
        return settingsPanel.getHideTimeRadioButton();
    }

    JCheckBoxMenuItem getInformationCheckBoxMenuItem() {
        return menu.getInformationCheckBoxMenuItem();
    }

    JComboBox getGuppyPriorityComboBox() {
        return settingsPanel.getGuppyPriorityComboBox();
    }

    JComboBox getGoldenPriorityComboBox() {
        return settingsPanel.getGoldenPriorityComboBox();
    }

    void lockStartButton() {
        settingsPanel.lockStartButton();
    }

    void lockStopButton() {
        settingsPanel.lockStopButton();
    }

    void unlockStartButton() {
        settingsPanel.unlockStartButton();
    }

    void unlockStopButton() {
        settingsPanel.unlockStopButton();
    }

    JMenuItem getStartSimulationMenuItem() {
        return menu.getStartSimulationMenuItem();
    }

    JMenuItem getStopSimulationMenuItem() {
        return menu.getStopSimulationMenuItem();
    }

    JRadioButtonMenuItem getHideTimeMenuItem() {
        return menu.getHideTimeMenuItem();
    }

    JRadioButtonMenuItem getShowTimeMenuItem() {
        return menu.getShowTimeMenuItem();
    }

    void lockStartSimulationMenuItem() {
        menu.lockStartSimulationMenuItem();
    }

    void lockStopSimulationMenuItem() {
        menu.lockStopSimulationMenuItem();
    }

    void unlockStartSimulationMenuItem() {
        menu.unlockStartSimulationMenuItem();
    }

    void unlockStopSimulationMenuItem() {
        menu.unlockStopSimulationMenuItem();
    }

    JCheckBox getInformationCheckbox() {
        return settingsPanel.getInformationCheckbox();
    }

    boolean isViewInformationChecked() {
        return settingsPanel.isViewInformationChecked();
    }

    boolean isShowTimeRadioButtonChecked() {
        return settingsPanel.isShowTimeRadioButtonChecked();
    }

    JTextField getGoldenIntervalTextField() {
        return settingsPanel.getGoldenIntervalTextField();
    }

    JComboBox getGoldenProbabilityComboBox() {
        return settingsPanel.getGoldenProbabilityComboBox();
    }

    JSlider getGuppyProbabilitySlider() {
        return settingsPanel.getGuppyProbabilitySlider();
    }
}

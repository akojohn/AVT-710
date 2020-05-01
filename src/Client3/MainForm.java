package Client3;

import Server.DataAction;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.UUID;

class MainForm extends JFrame {
    private Menu menu = new Menu();
    private SettingsPanel settingsPanel = new SettingsPanel();
    private JPanel drawingPanel = new JPanel();
    private JPanel timePanel = new JPanel();
    private JLabel timeLabel = new JLabel("");
    private JPanel onlinePanel = new JPanel();

    public JButton getConnectButton() {
        return connectButton;
    }

    public void setConnectButton(JButton connectButton) {
        this.connectButton = connectButton;
    }

    private JButton connectButton = new JButton("Подключиться");

    public JButton getDisconnectButton() {
        return disconnectButton;
    }

    public void setDisconnectButton(JButton disconnectButton) {
        this.disconnectButton = disconnectButton;
    }

    private JButton disconnectButton = new JButton("Отключиться");

    public JButton getImportButton() {
        return importButton;
    }

    public void setImportButton(JButton importButton) {
        this.importButton = importButton;
    }

    private JButton importButton = new JButton("Импортировать");

    public DefaultListModel<String> getDlm() {
        return dlm;
    }

    private DefaultListModel<String> dlm = new DefaultListModel<>();

    public JList<String> getIpsList() {
        return ipsList;
    }

    private JList<String> ipsList = new JList<>(dlm);

    MainForm() {
        setTitle("Aquarium");

        add(settingsPanel, BorderLayout.EAST);

        onlinePanel.setBackground(new Color(234, 192, 243));
        onlinePanel.setPreferredSize(new Dimension(150, 0));
        add(onlinePanel, BorderLayout.WEST);
        onlinePanel.add(connectButton);
        onlinePanel.add(disconnectButton);
        onlinePanel.add(importButton);
        onlinePanel.add(ipsList);

        timePanel.setBackground(new Color(217, 214, 243));
        timePanel.setPreferredSize(new Dimension(0, 30));
        timePanel.setVisible(false);
        timePanel.add(timeLabel);
        add(timePanel, BorderLayout.SOUTH);

        drawingPanel.setBackground(new Color(175, 218, 243));
        add(drawingPanel);

        setJMenuBar(menu);

        disconnectButton.setEnabled(false);
        importButton.setEnabled(false);
    }

    void addConnected(UUID uuid){
        dlm.addElement(uuid.toString());
        System.out.println("Added 1");
    }

    void addAllConnected(ArrayList<UUID> uuids){
        for (int i = 0; i < uuids.size(); i++){
            dlm.addElement(uuids.get(i).toString());
        }
        System.out.println("Added 2  " + uuids);
    }

    void deleteDisconnected(UUID uuid){
        dlm.removeElement(uuid.toString());
    }

    void setSettings(DataAction dataAction){
        settingsPanel.getGuppyIntervalTextField().setText(dataAction.getGuppyInterval());
        settingsPanel.getGuppyProbabilitySlider().setValue(dataAction.getGuppyProbability());
        settingsPanel.getGuppyLifeTimeTextField().setText(dataAction.getGuppyLifeTime());

        settingsPanel.getGoldenIntervalTextField().setText(dataAction.getGoldenInterval());
        settingsPanel.getGoldenProbabilityComboBox().setSelectedIndex(dataAction.getGoldenProbability());
        settingsPanel.getGoldenLifeTimeTextField().setText(dataAction.getGoldenLifeTime());

        Guppy.setSpawnInterval(Integer.parseInt(settingsPanel.getGuppyIntervalTextField().getText()));
        Guppy.setSpawnProbability((double)(settingsPanel.getGuppyProbabilitySlider().getValue()) / 10);
        //Guppy.setLifeTime(Integer.parseInt(settingsPanel.getGuppyLifeTimeTextField().getText()));

        Golden.setSpawnInterval(Integer.parseInt(settingsPanel.getGoldenIntervalTextField().getText()));
        Golden.setSpawnProbability((double)(int)(settingsPanel.getGoldenProbabilityComboBox().getSelectedItem()) / 10);
        //Golden.setLifeTime(Integer.parseInt(settingsPanel.getGoldenLifeTimeTextField().getText()));
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

    JMenuItem getConsoleMenuItem() {
        return menu.getConsoleMenuItem();
    }

    JMenuItem getLoadFishesMenuItem() {
        return menu.getLoadFishesMenuItem();
    }

    JMenuItem getSaveFishesMenuItem() {
        return menu.getSaveFishesMenuItem();
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

    public SettingsPanel getSettingsPanel() {
        return settingsPanel;
    }
}

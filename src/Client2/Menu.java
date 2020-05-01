package Client2;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;

class Menu extends JMenuBar {
    private JMenuItem startSimulationMenuItem = new JMenuItem("START");
    private JMenuItem stopSimulationMenuItem = new JMenuItem("STOP");
    private JRadioButtonMenuItem showTimeMenuItem = new JRadioButtonMenuItem("Показать время генерации");
    private JRadioButtonMenuItem hideTimeMenuItem = new JRadioButtonMenuItem("Скрыть время генерации", true);
    private JCheckBoxMenuItem informationCheckBoxMenuItem = new JCheckBoxMenuItem("Показать статистику");
    private JMenuItem consoleMenuItem = new JMenuItem("Консоль");
    private JMenuItem saveFishesMenuItem = new JMenuItem("Сохранить");
    private JMenuItem loadFishesMenuItem = new JMenuItem("Загрузить");

    Menu() {
        JMenu mainMenu = new JMenu("Главное меню");
        mainMenu.setBackground(new Color(234, 192, 243));
        mainMenu.add(consoleMenuItem);
        mainMenu.add(saveFishesMenuItem);
        mainMenu.add(loadFishesMenuItem);


        JMenu simulationMenu = new JMenu("Симуляция");
        setBackground(new Color(234, 192, 243));

        startSimulationMenuItem.setMnemonic(KeyEvent.VK_A);
        startSimulationMenuItem.setBackground(new Color(234, 192, 243));

        stopSimulationMenuItem.setMnemonic(KeyEvent.VK_O);
        stopSimulationMenuItem.setBackground(new Color(234, 192, 243));

        simulationMenu.setMnemonic(KeyEvent.VK_C);
        simulationMenu.setDisplayedMnemonicIndex(0);

        simulationMenu.add(startSimulationMenuItem);
        simulationMenu.add(stopSimulationMenuItem);

        JMenu settingMenu = new JMenu("Настройки");

        showTimeMenuItem.setMnemonic(KeyEvent.VK_G);
        showTimeMenuItem.setDisplayedMnemonicIndex(0);
        showTimeMenuItem.setBackground(new Color(234, 192, 243));

        hideTimeMenuItem.setMnemonic(KeyEvent.VK_C);
        hideTimeMenuItem.setDisplayedMnemonicIndex(0);
        hideTimeMenuItem.setBackground(new Color(234, 192, 243));

        informationCheckBoxMenuItem.setMnemonic(KeyEvent.VK_C);
        informationCheckBoxMenuItem.setDisplayedMnemonicIndex(10);
        informationCheckBoxMenuItem.setBackground(new Color(234, 192, 243));

        settingMenu.setMnemonic(KeyEvent.VK_Y);
        settingMenu.setDisplayedMnemonicIndex(0);

        ButtonGroup timeGroupMenu = new ButtonGroup();
        timeGroupMenu.add(showTimeMenuItem);
        timeGroupMenu.add(hideTimeMenuItem);

        settingMenu.add(informationCheckBoxMenuItem);
        settingMenu.add(showTimeMenuItem);
        settingMenu.add(hideTimeMenuItem);

        add(mainMenu);
        add(simulationMenu);
        add(settingMenu);
    }

    JMenuItem getStopSimulationMenuItem() {
        return stopSimulationMenuItem;
    }

    JMenuItem getStartSimulationMenuItem() {
        return startSimulationMenuItem;
    }

    JMenuItem getConsoleMenuItem() {
        return consoleMenuItem;
    }

    JMenuItem getLoadFishesMenuItem() {
        return loadFishesMenuItem;
    }

    JMenuItem getSaveFishesMenuItem() {
        return saveFishesMenuItem;
    }

    JRadioButtonMenuItem getHideTimeMenuItem() {
        return hideTimeMenuItem;
    }

    JRadioButtonMenuItem getShowTimeMenuItem() {
        return showTimeMenuItem;
    }

    JCheckBoxMenuItem getInformationCheckBoxMenuItem() {
        return informationCheckBoxMenuItem;
    }

    void showTime() {
        showTimeMenuItem.setEnabled(true);
    }

    void hideTime() {
        hideTimeMenuItem.setEnabled(true);
    }

    void lockStartSimulationMenuItem() {
        startSimulationMenuItem.setEnabled(false);
    }

    void lockStopSimulationMenuItem() {
        stopSimulationMenuItem.setEnabled(false);
    }

    void unlockStartSimulationMenuItem() {
        startSimulationMenuItem.setEnabled(true);
    }

    void unlockStopSimulationMenuItem() {
        stopSimulationMenuItem.setEnabled(true);
    }
}

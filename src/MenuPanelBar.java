import javax.swing.*;
import java.awt.*;

class MenuPanelBar extends JMenuBar {
    JMenuItem exitItem = new JMenuItem("Выход");
    JMenuItem simulationItem = new JMenuItem("Симуляция");
    JMenuItem startSimulating = new JMenuItem("Запуск");
    JMenuItem stopSimulating = new JMenuItem("Остановка");
    JCheckBox isShowResultTable = new JCheckBox("Результаты");
    JMenuItem console = new JMenuItem("Консоль");
    JMenuItem saveObjects = new JMenuItem("Сохранить");
    JMenuItem loadObjects = new JMenuItem("Загрузить");

    MenuPanelBar() {
        JMenu mainMenu = new JMenu("Main");
        mainMenu.add(simulationItem);
        mainMenu.addSeparator();
        mainMenu.add(saveObjects);
        mainMenu.addSeparator();
        mainMenu.add(loadObjects);
        mainMenu.addSeparator();
        mainMenu.add(exitItem);

        JMenu toolsPanel = new JMenu("Tools");
        toolsPanel.add(startSimulating);
        mainMenu.addSeparator();
        toolsPanel.add(stopSimulating);
        mainMenu.addSeparator();
        toolsPanel.add(isShowResultTable);

        JMenu consoleMenu = new JMenu("Console");
        consoleMenu.add(console);

        add(mainMenu);
        add(toolsPanel);
        add(consoleMenu);
    }
}

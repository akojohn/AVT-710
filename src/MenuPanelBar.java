import javax.swing.*;

class MenuPanelBar extends JMenuBar {
    JMenuItem exitItem = new JMenuItem("Выход");
    JMenuItem simulationItem = new JMenuItem("Симуляция");
    JMenuItem startSimulating = new JMenuItem("Запуск");
    JMenuItem stopSimulating = new JMenuItem("Остановка");
    JCheckBox isShowResultTable = new JCheckBox("Результаты");

    MenuPanelBar() {
        JMenu mainMenu = new JMenu("Main");
        mainMenu.addSeparator();
        mainMenu.add(simulationItem);
        mainMenu.add(exitItem);
        JMenu toolsPanel = new JMenu("Tools");
        toolsPanel.add(startSimulating);
        toolsPanel.add(stopSimulating);
        toolsPanel.add(isShowResultTable);
        add(mainMenu);
        add(toolsPanel);
    }
}

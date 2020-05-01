package Client;

import javax.swing.*;
import java.awt.*;

class Console extends JDialog {
    private JDialog dialog;
    JTextArea consoleTextArea;
    JLabel consoleOutputLabel;

    Console(JFrame owner) {
        dialog = new JDialog(owner, "Console", false);
        dialog.setSize(350, 300);
        dialog.setVisible(true);

        JPanel hintPanel = new JPanel();
        JLabel hintLabel = new JLabel("<html>Команды: <br>getGoldenProbability<br>setGoldenProbability N (Ex. 0.1)</html>");
        hintPanel.setBackground(new Color(175, 218, 243));
        hintPanel.setPreferredSize(new Dimension(0, 60));
        hintPanel.add(hintLabel);
        dialog.add(hintPanel, BorderLayout.NORTH);


        JPanel consoleOutputPanel = new JPanel();
        consoleOutputLabel = new JLabel();
        consoleOutputPanel.setBackground(new Color(175, 218, 243));
        consoleOutputPanel.setPreferredSize(new Dimension(0, 30));
        consoleOutputPanel.add(consoleOutputLabel);
        dialog.add(consoleOutputPanel, BorderLayout.SOUTH);

        JPanel mainPanel = new JPanel();
        mainPanel.setBackground(new Color(238, 238, 238));

        // FIXME: 14.05.2019 fix default size
        consoleTextArea = new JTextArea(15, 28);
        consoleTextArea.setLineWrap(true);
        mainPanel.add(consoleTextArea);

        dialog.add(mainPanel);
    }

    public JTextArea getConsoleTextArea() {
        return consoleTextArea;
    }

    public void setConsoleOutputLabelText(String text) {
        consoleOutputLabel.setText(text);
    }
}

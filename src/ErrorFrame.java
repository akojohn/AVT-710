import javax.swing.*;
import java.awt.*;

class ErrorFrame extends JDialog {
    JButton okButton = new JButton("Ok");
    ErrorFrame(JFrame parent){
        super(parent, "Error", true);
        setBounds(550, 300, 350, 100);
        setLayout(new FlowLayout());

        JLabel errorMessage = new JLabel("Неверное значение! Установлено значение по умолчанию.");
        okButton.addActionListener(e -> dispose());

        add(errorMessage,0);
        add(okButton);
    }
}

import javax.swing.*;
import java.awt.*;

class MessageFrame extends JDialog {
    MessageFrame(JFrame parent, String message){
        super(parent, "Message", true);
        setBounds(550, 300, 300, 100);
        setLayout(new GridBagLayout());

        GridBagConstraints gbcItems = new GridBagConstraints();
        gbcItems.weightx = 1.0;
        gbcItems.weighty = 1.0;
        gbcItems.gridwidth = GridBagConstraints.REMAINDER;
        gbcItems.insets = new Insets(0,0,1,0);

        JLabel messageText = new JLabel(message);
        JButton okButton = new JButton("Ok");
        okButton.addActionListener(e -> dispose());

        add(messageText,gbcItems);
        add(okButton, gbcItems);
        setVisible(true);
    }
}

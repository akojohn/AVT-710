import javax.swing.*;
import java.awt.*;
import java.util.TreeMap;

class InformationFrame extends JDialog {
    InformationFrame(JFrame parent, TreeMap objects) {
        super(parent, "Information", true);
        setBounds(550, 300, 250, 250);
        setLayout(new FlowLayout());

        TextArea objectsList = new TextArea("Existing objects \n" +
                "#  Id. number      Time of birth \n", 10,30, 1);
        int n = 1;
        for (Object key : objects.keySet()) {
            objectsList.append(n + ": " + key + "   " + objects.get(key) + "\n");
            n++;
        }

        objectsList.setEditable(false);
        JButton okButton = new JButton("Ok");
        okButton.addActionListener(e -> dispose());

        add(objectsList);
        add(okButton);
        setVisible(true);
    }
}


import javax.swing.*;
import java.awt.*;

class MessagePanel extends JDialog {

    JButton okButton = new JButton("OK");
    JButton cancelButton = new JButton("Cancel");

        MessagePanel(int countAutomobiles,int countVehicles, long timeUniverse, JFrame parent) {
        super(parent, "Result statistics", true);
        setBounds(550, 300, 400, 180);
        setLayout(new GridBagLayout());
        Insets insetItems = new Insets(0,0,1,0);

        GridBagConstraints gbcTextArea = new GridBagConstraints();
        gbcTextArea.weightx = 1.0;
        gbcTextArea.weighty = 1.0;
        gbcTextArea.fill = GridBagConstraints.HORIZONTAL;
        gbcTextArea.ipady = 30;
        gbcTextArea.gridwidth = GridBagConstraints.REMAINDER;
        gbcTextArea.insets = insetItems;

        GridBagConstraints gbcOkButton = new GridBagConstraints();
        gbcOkButton.weightx = 1.0;
        gbcOkButton.weighty = 1.0;
        gbcOkButton.ipady = 10;
        gbcOkButton.ipadx = 40;
        gbcOkButton.gridwidth = GridBagConstraints.REMAINDER;
        gbcOkButton.insets = insetItems;

        GridBagConstraints gbcCancelButton = new GridBagConstraints();
        gbcCancelButton.weightx = 1.0;
        gbcCancelButton.weighty = 1.0;
        gbcCancelButton.gridwidth = GridBagConstraints.REMAINDER;
        gbcCancelButton.insets = insetItems;

        TextArea simulationStatistics = new TextArea("-Время симуляции:" + timeUniverse + "\n"
                + "-Всего элементов: " + countVehicles +"\n"+"\t"+"-автомобилей: " + countAutomobiles +
                "\n"+"\t"+"-мотоциклов: " + (countVehicles-countAutomobiles), 2,50,0);
        simulationStatistics.setEditable(false);

        add(simulationStatistics, gbcTextArea);
        add(okButton, gbcOkButton);
        add(cancelButton, gbcCancelButton);
    }
}

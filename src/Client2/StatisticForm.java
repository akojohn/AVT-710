package Client2;

import javax.swing.*;
import java.awt.*;

class StatisticForm extends JDialog{
    private JDialog dialog;
    private static final Color buttonsColor = new Color(198, 108, 255);
    private static final Color menuColor = new Color(234, 192, 243);

    interface OnDialogListener{
        void onDialogOk();
//        void onDialogCancel();
    }

    private OnDialogListener listener;

    StatisticForm(JFrame owner, String statistics, OnDialogListener listener) {
        this.listener=listener;

        dialog = new JDialog(owner, "Statistic", true);

        JTextArea textArea = new JTextArea();
        textArea.setText(statistics);
        textArea.setBounds(10,10,310,30);
        textArea.setEditable(false);
        textArea.setBackground(menuColor);

        JButton cancelButton = new JButton("Cancel");
        cancelButton.setBounds(10,50,150,30);
        cancelButton.setBackground(buttonsColor);

        JButton okButton = new JButton("OK");
        okButton.setBounds(170, 50, 150, 30);
        okButton.setBackground(buttonsColor);


        okButton.addActionListener(e -> {
            this.listener.onDialogOk();
            dialog.dispose();
        });

        cancelButton.addActionListener(e -> {
//            listener.onDialogCancel();
            dialog.dispose();
        });
        dialog.setSize(350, 150);
        dialog.setLayout(null);
        dialog.add(textArea);
        dialog.add(okButton);
        dialog.add(cancelButton);
        dialog.setVisible(true);
    }
}

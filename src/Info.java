import javax.swing.*;
import java.awt.*;
import java.util.HashSet;
import java.util.TreeMap;

class Info extends JDialog{
    private JDialog dialog;
    private static final Color buttonsColor = new Color(198, 108, 255);
    private static final Color menuColor = new Color(234, 192, 243);

    interface OnDialogListener{
        void onDialogOk();
//        void onDialogCancel();
    }

    private OnDialogListener listener;

    Info(JFrame owner, FishList list, TreeMap<Long, Long> map, HashSet<Long> hash, OnDialogListener listener) {
        this.listener = listener;

        dialog = new JDialog(owner, "Info", true);

        JTextArea textArea = new JTextArea();


        if (list.size() == 0) textArea.setText("РЫБ НЕТ");
        else textArea.append("№      Тип         ID      Время рождения\n");
        for (int i = 0; i < list.size(); i++) {
            long FishID = list.getFishByI(i).getId();
            if (list.getFishById(FishID).getClass() == Guppy.class){
                textArea.append((i+1) + ") Гуппи:        " + FishID + "        " + map.get(FishID)+"c\n");
            }
            else textArea.append((i+1) + ") Золотая:   " + FishID + "        " + map.get(FishID)+"c\n");
        }

        textArea.setBounds(10,10,310,200);
        textArea.setEditable(false);
        textArea.setBackground(menuColor);

        JButton okButton = new JButton("OK");
        okButton.setBounds(170, 220, 150, 30);
        okButton.setBackground(buttonsColor);


        okButton.addActionListener(e -> {
            dialog.dispose();
        });

        dialog.setSize(350, 300);
        dialog.setLayout(null);
        dialog.add(textArea);
        dialog.add(okButton);
        dialog.setVisible(true);
    }
}
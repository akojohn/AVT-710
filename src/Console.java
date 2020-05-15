import javax.swing.*;
import javax.swing.text.BadLocationException;
import javax.swing.text.Utilities;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

class Console extends JDialog implements KeyListener {
    JTextArea textArea = new JTextArea();
    double arg = 0;
    public Console(JFrame parent){
        super(parent, false);
        setBounds(0, 0, 400, 300);
        setVisible(false);
        textArea.setPreferredSize(new Dimension(380, 220));
        add(textArea);
        textArea.getLineCount();
        textArea.addKeyListener(this);
    }

    @Override
    public void keyTyped(KeyEvent e) {

        if (e.getKeyChar() == KeyEvent.VK_ENTER){
            sendLine();
        }
    }

    public void sendLine() {
        int end = textArea.getDocument().getLength();
        int start;
        try {
            start = Utilities.getRowStart(textArea, end);
            while (start == end) {
                end--;
                start = Utilities.getRowStart(textArea, end);
            }
            int numb;
           if ((numb = commandChecker(textArea.getText(start, end - start))) > 0){
               arg = numb;
           }
           else{
               textArea.append("Wrong command!\n");
           }
        }
        catch (BadLocationException e) {
            e.printStackTrace();
        }
    }

    public int commandChecker(String string) {
        int numb = -1;
        string = string.toLowerCase();
        Pattern p = Pattern.compile("[0-9]+");
        Matcher m = p.matcher(string);
        while (m.find())
            if (!m.group().equals("")) {
                numb = Integer.parseInt(m.group());
            }
        if (numb > 0 && numb <= 100 && string.contains("сократить число мотоциклов на "))
            return numb;
        return 0;
    }

    @Override
    public void keyPressed(KeyEvent e) {
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }
}

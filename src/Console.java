import javax.swing.*;

import java.io.IOException;
import java.io.PipedReader;
import java.io.PipedWriter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Console extends Thread {
    JTextArea area;
    int n;
    private int lines = 2;
    String lastStr;
    Habitat habitat;

    PipedWriter pw = new PipedWriter();

    Console(JTextArea area, Habitat habitat, PipedReader pr){
        this.area = area;
        this.habitat = habitat;
        try {
            this.pw.connect(pr);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void run()
    {
        Pattern re1 = Pattern.compile("^ *?dismiss$",Pattern.MULTILINE);
        Pattern re2 = Pattern.compile("^ *?hire ([0-9]{1,3})$",Pattern.MULTILINE);

        while (true)
        {
            n = area.getLineCount();
            if (n > lines) {
                lines = n;
                try {
                    lastStr = area.getText();
                    lastStr = lastStr.substring(area.getLineEndOffset(n-3));
                    Matcher match1 = re1.matcher(lastStr);
                    Matcher match2 = re2.matcher(lastStr);
                    if (match1.find()) {
                        area.setText(area.getText() + "Все менеджеры уволены\n");
                        pw.write(0);

                        lines += 1;
                        area.setCaretPosition(area.getLineEndOffset(lines-2));
                    }
                    if(match2.find())
                    {
                        area.setText(area.getText() + "Нанято менеджеров " + match2.group(1) + "\n");
                        pw.write(1);
                        pw.write(match2.group(1));
                        lines += 1;
                        area.setCaretPosition(area.getLineEndOffset(lines-2));
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
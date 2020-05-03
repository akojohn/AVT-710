package aquarium;

import static aquarium.HabitatView.show;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.nio.charset.StandardCharsets;

public final class Console extends JDialog {

    private PipedInputStream inputStr = new PipedInputStream();
    final JTextArea consoleText = new JTextArea("");

    public Console(Main owner) {
        super(owner, false);
        setTitle("Console");
        setSize(380, 350);
        final JScrollPane scroll = new JScrollPane(consoleText);
        consoleText.setFont(new Font("Raster Fonts", Font.BOLD, 14));
        consoleText.setForeground(new Color(255, 255, 255));
        consoleText.setBackground(new Color(0, 0, 0));
        consoleText.setSelectedTextColor(new Color(255, 163, 26));
        consoleText.setSelectionColor(Color.WHITE);
        consoleText.setCaretColor(new Color(0, 255, 150));
        consoleText.setLineWrap(true);
        //consoleText.setWrapStyleWord(true);
        add(scroll);
        setVisible(true);
        setLocationRelativeTo(null);

        PipedOutputStream pos = new PipedOutputStream();
        try {
            pos.connect(inputStr);
            consoleText.getDocument().addDocumentListener(new DocumentChangeListener() {
                @Override
                public void changeText() {
                    try {
                        pos.write(consoleText.getText().getBytes());
                        pos.flush();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });
            Thread thread = new StreamThread(owner);
            thread.start();

            addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosing(WindowEvent e) {
                    super.windowClosing(e);
                    System.out.println("close");
                    thread.interrupt();
                    try {
                        inputStr.close();
                    } catch (IOException ignored) {
                    }
                }
            });
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }

    class StreamThread extends Thread {

        private final Main frame;

        private StreamThread(Main frame1) {
            frame = frame1;
        }

        @Override
        public void run() {
            try {
                while (!isInterrupted()) {
                    ByteArrayOutputStream bytesOutput = new ByteArrayOutputStream();
                    byte[] buffer = new byte[1024];
                    int length = inputStr.read(buffer);
                    if (length != -1) {
                        bytesOutput.write(buffer, 0, length);
                        String result = bytesOutput.toString(String.valueOf(StandardCharsets.UTF_8));
                        String[] lines = result.split("\n");
                        String[] words = lines[lines.length - 1].split(" ");
                        for (int i = 0; i < words.length; i++) {
                            System.out.println(words[i]);
                        }
                        if (result.charAt(result.length() - 1) == '\n') {

                            switch (words[0].toLowerCase()) {
                                case "get": {
                                    if (words[1].equals("Gold")) {
                                        consoleText.append(">Для золотой рыбки установлен шанс: " + String.valueOf(Gold.P1 + "\n"));
                                        break;
                                    }
                                    if (words[1].equals("Guppy")) {
                                        consoleText.append(">Для рыбки гуппи установлен шанс: " + String.valueOf(Guppy.P2) + "\n");
                                        break;
                                    }
                                    break;
                                }
                                case "set": {
                                    if (Float.parseFloat(words[2]) > 1 || Float.parseFloat(words[2]) < 0) {
                                        consoleText.append(">Неверные данные\n");
                                        break;
                                    } else {
                                        if (words[1].equals("Gold")) {
                                            Gold.P1 = Float.parseFloat(words[2]);
                                            System.out.println(words[2]);
                                            consoleText.append(">Установлен шанс появления для Gold: " + Float.parseFloat(words[2]) + "\n");
                                            Main.slider_chance_Gold.setValue((int) (Gold.P1 * 100));
                                            break;

                                        }
                                        if (words[1].equals("Guppy")) {
                                            Guppy.P2 = Float.parseFloat(words[2]);
                                            consoleText.append(">Установлен шанс появления для Guppy: " + Float.parseFloat(words[2]) + "\n");
                                            Main.slider_chance_Guppy.setValue((int) (Guppy.P2 * 100));
                                            break;
                                        }
                                    }
                                }
                                case "help": {
                                    consoleText.append(">Установить шанс: set Gold / Guppy \n>Получить шанс: get Gold / Guppy\n");
                                    break;
                                }
                                
                            }
                            consoleText.setCaretPosition(consoleText.getText().length());
                        }
                    }
                }
            } catch (Exception ignored) {

            }
        }
    }
}

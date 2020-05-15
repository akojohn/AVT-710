import javax.swing.*;
import java.awt.*;

public class Kostil extends JComponent {
    private final JImage jImage;

    public Kostil(JImage image) {
        jImage = image;
        setSize(jImage.getWidth(), jImage.getHeight());
        setBounds(jImage.getX(), jImage.getY(), jImage.getWidth(), jImage.getHeight());
        setLayout(null);
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        g.drawImage(jImage.getImage(), 0, 0, null);
    }
}

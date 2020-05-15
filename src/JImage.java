import javax.swing.*;
import java.awt.*;

public abstract class JImage extends JComponent implements IBehaviour {
    JImage(){
        setSize(getWidth(), getHeight());
        setBounds(getX(), getY(), getWidth(), getHeight());
        setLayout(null);
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        g.drawImage(getImage(), 0, 0, null);
    }
}

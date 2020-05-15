import javax.swing.*;
import java.awt.*;

public abstract class JImage extends JComponent implements IBehaviour {
    long bornTime;
    long lifeTime;
    long identificationNumber;

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

    public boolean isDeterminate(long currentUniverseTime) {
        if (currentUniverseTime >= bornTime + lifeTime)
            return true;
        else return false;
    }

    public void setBirthTime(long bt, int lt){
        bornTime = bt;
        lifeTime = lt;
    }
}

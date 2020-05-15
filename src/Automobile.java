import javax.swing.*;
import java.awt.*;

public class Automobile extends JImage {
    private final int automobileWidthSize = 192;
    private final int automobileHeightSize = 64;
    private static Image Automobile = new ImageIcon("res/car.png").getImage();
    private int automobileX, automobileY;

    Automobile(long identify, long bornTime, long lifeTime, int X, int Y) {
        automobileY = Y;
        automobileX = X;
        this.bornTime = bornTime;
        this.lifeTime = lifeTime;
        identificationNumber = identify;
    }

    @Override
    public int getX() { return automobileX; }

    @Override
    public int getY() { return automobileY; }

    @Override
    public int getHeight(){ return automobileHeightSize; }

    @Override
    public int getWidth(){ return automobileWidthSize;}

    @Override
    public void setX(int x) {
automobileX = x;
    }

    @Override
    public void setY(int y) {
automobileY = y;
    }

    @Override
    public Image getImage() { return Automobile;}

}

import javax.swing.*;
import java.awt.*;

public class Motorcycle extends JImage {
    private final int motorcycleHeightSize = 64;
    private final int motorcycleWidthSize = 121;
    private static Image Motorcycle = new ImageIcon("res/moto.png").getImage();
    private int motorcycleX, motorcycleY;

    Motorcycle(long identify, long bornTime, long lifeTime, int X, int Y) {
        motorcycleY = Y;
        motorcycleX = X;
        this.bornTime = bornTime;
        this.lifeTime = lifeTime;
        identificationNumber = identify;
    }

    @Override
    public int getX() {
        return motorcycleX;
    }

    @Override
    public int getY() {
        return motorcycleY;
    }

    @Override
    public int getHeight() {
        return motorcycleHeightSize;
    }

    @Override
    public int getWidth() {
        return motorcycleWidthSize;
    }

    @Override
    public void setX(int x) {
        motorcycleX = x;
    }

    @Override
    public void setY(int y) {
        motorcycleY = y;
    }

    @Override
    public Image getImage() {
        return Motorcycle;
    }
}

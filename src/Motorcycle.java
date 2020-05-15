import javax.swing.*;
import java.awt.*;

public class Motorcycle extends JImage {
    private final int motorcycleHeightSize = 64;
    private final int motorcycleWidthSize = 121;
    private static Image Motorcycle = new ImageIcon("res/moto.png").getImage();
    private int motorcycleX, motorcycleY;

    Motorcycle(int X, int Y) {
        motorcycleY = Y;
        motorcycleX = X;
    }

    @Override
    public int getX() { return motorcycleX; }

    @Override
    public int getY() { return motorcycleY; }

    @Override
    public int getHeight(){ return motorcycleHeightSize; }

    @Override
    public int getWidth(){ return motorcycleWidthSize;}

    @Override
    public Image getImage() { return Motorcycle;}
}

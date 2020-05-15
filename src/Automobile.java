import javax.swing.*;
import java.awt.*;

public class Automobile extends JImage {
    private final int autoWidthSize = 192;
    private final int autoHeightSize = 64;
    private static Image Automobile = new ImageIcon("res/car.png").getImage();
    private int automobileX, automobileY;

    Automobile(int X, int Y) {
        automobileY = Y;
        automobileX = X;
    }

    @Override
    public int getX() { return automobileX; }

    @Override
    public int getY() { return automobileY; }

    @Override
    public int getHeight(){ return autoHeightSize; }

    @Override
    public int getWidth(){ return autoWidthSize;}

    @Override
    public Image getImage() { return Automobile;}
}

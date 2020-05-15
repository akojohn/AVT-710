import javax.swing.*;
import java.awt.*;

public class Automobile extends JImage{
    private final int automobileWidthSize = 192;
    private final int automobileHeightSize = 64;
    private static Image Automobile = new ImageIcon("res/car.png").getImage();
    private final int playWidth = Habitat.getFrameWidth() - automobileWidthSize - InterfacePanel.getWidthSize() - 20;
    private int automobileX, automobileY;
    private int automobileX2 = playWidth;

    Automobile(long identify, long bornTime, long lifeTime, int X, int Y) {
        automobileY = Y;
        automobileX = X;
        this.bornTime = bornTime;
        this.lifeTime = lifeTime;
        identificationNumber = identify;
    }

    @Override
    public void move() {
        float dx;
        float v = 3;
        if (automobileX + v >= automobileX2)
            automobileX2 = 0;
        if (automobileX - v <= 0)
            automobileX2 = playWidth;
        dx = (float) (this.getX2() - this.getX()) / (float) Math.sqrt(Math.pow(this.getX2() - this.getX(), 2));
        setX(this.getX() + (int) (dx * v));
    }

    @Override
    public int getX() { return automobileX; }

    public int getX2() { return automobileX2; }

    @Override
    public int getY() { return automobileY; }

    @Override
    public int getHeight(){ return automobileHeightSize; }

    @Override
    public int getWidth(){ return automobileWidthSize;}

    @Override
    public Image getImage() { return Automobile;}

    public void setX(int x){
        automobileX = x;
    }

}

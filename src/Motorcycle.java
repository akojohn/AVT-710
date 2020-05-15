import javax.swing.*;
import java.awt.*;

public class Motorcycle extends JImage {
    private final int motorcycleHeightSize = 64;
    private final int motorcycleWidthSize = 121;
    private static Image Motorcycle = new ImageIcon("res/moto.png").getImage();
    private final int playHeight = Habitat.getFrameHeight() - 2 * motorcycleHeightSize;
    private int motorcycleX, motorcycleY;
    private int motorcycleY2 = playHeight;

    Motorcycle(long identify, long bornTime, long lifeTime, int X, int Y) {
        motorcycleY = Y;
        motorcycleX = X;
        this.bornTime = bornTime;
        this.lifeTime = lifeTime;
        identificationNumber = identify;
    }

    @Override
    public void move() {
        float dy;
        final float v = 3;
        if (motorcycleY + v >= motorcycleY2)
            motorcycleY2 = 0;
        if (motorcycleY - v  <= 0)
            motorcycleY2 = playHeight;
            dy = (float) (this.getY2() - this.getY()) / (float) Math.sqrt(Math.pow(this.getY2() - this.getY(), 2));
        this.setY(this.getY() + (int) (dy * v));
    }

    @Override
    public int getX() { return motorcycleX; }

    @Override
    public int getY() { return motorcycleY; }

    public int getY2(){
        return motorcycleY2;
    }

    @Override
    public int getHeight(){ return motorcycleHeightSize; }

    @Override
    public int getWidth(){ return motorcycleWidthSize;}

    @Override
    public Image getImage() { return Motorcycle;}

    public void setY(int y){
        motorcycleY = y;
    }
}

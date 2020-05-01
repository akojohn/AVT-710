package Client3;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;

abstract public class Fish implements IBehaviour, Serializable {

    static BufferedImage goldenImage = fillImage("src/golden.png");
    static BufferedImage guppyImage = fillImage("src/guppy.png");

    private double x, y;
    double v = 0.000002;
    private long id;

    void setParam(int sizeX, int sizeY, long id) {
        setX(sizeX);
        setY(sizeY);
        setId(id);
    }

    abstract public void move(int windowWidth, int windowHeight);

    public void setX(double x) {
        this.x = x;
    }

    public void setY(double y) {
        this.y = y;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    private void setId(long id) {
        this.id = id;
    }

    long getId() {
        return id;
    }

    private static BufferedImage fillImage(String pathname) {
        BufferedImage image;
        try {
            image = ImageIO.read(new File(pathname));
            return image;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    abstract BufferedImage getImage();

    void draw(Graphics g) {
        g.drawImage(getImage(), (int)getX(), (int)getY(), getImage().getWidth(), getImage().getHeight(), null);
    }

    abstract int getSpawnInterval();

    abstract int getDefaultInterval();

    abstract int getLifeTime();

    abstract double getSpawnProbability();

    boolean isSpawned(long time){
        double randomValue = Math.random();
        int a = getSpawnInterval();
        //System.out.println(this.getClass().getName() + " spawn interval is " + a);
        return time % getSpawnInterval() == 0 && randomValue <= getSpawnProbability();
    }
}

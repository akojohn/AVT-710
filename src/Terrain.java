import javax.swing.*;
import java.awt.*;

public class Terrain extends JImage{
    private int terrainX, terrainY;
    private int terrainWidth = 600;
    private int terrainHeigth = 600;
    private static Image Terrain = new ImageIcon("res/terrain.png").getImage();

    Terrain() {
        terrainY = 0;
        terrainX = 0;
    }

    @Override
    public int getX() {
        return terrainX;
    }

    @Override
    public int getY() {
        return terrainY;
    }

    @Override
    public int getHeight() {
        return terrainHeigth;
    }

    @Override
    public int getWidth() {
        return terrainWidth;
    }

    @Override
    public Image getImage() {
        return Terrain;
    }
}


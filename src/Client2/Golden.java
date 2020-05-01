package Client2;

import java.awt.image.BufferedImage;

class Golden extends Fish {
    private static double spawnProbability = 0.9;
    private static int spawnInterval = 3;
    private static int lifeTime = 4;
    private boolean isLeftGoing = true;

    @Override
    public void move(int windowWidth, int windowHeight) {
        setX(getX() + (isLeftGoing ? -v : v));
        if (getX() < 0 || getX() > windowWidth) {
            isLeftGoing = !isLeftGoing;
        }
    }

    @Override
    BufferedImage getImage() {
        return goldenImage;
    }

    @Override
    double getSpawnProbability() {
        return spawnProbability;
    }

    static void setSpawnProbability(double spawnProbability) {
        Golden.spawnProbability = spawnProbability;
    }

    @Override
    int getSpawnInterval() {
        return spawnInterval;
    }

    @Override
    int getDefaultInterval() {
        return 3;
    }

    @Override
    int getLifeTime() {
        return lifeTime;
    }

    static void setLifeTime(int lifeTime) {
        Golden.lifeTime = lifeTime;
    }

    static void setSpawnInterval(int spawnInterval) {
        Golden.spawnInterval = spawnInterval;
    }
}

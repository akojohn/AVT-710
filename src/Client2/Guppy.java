package Client2;

import java.awt.image.BufferedImage;

class Guppy extends Fish {
    private static double spawnProbability = 0.9;
    private static int spawnInterval = 2;
    private static int lifeTime = 3;
    private boolean isUpGoing = true;

    @Override
    public void move(int windowWidth, int windowHeight) {
        setY(getY() + (isUpGoing ? -v : v));
        if (getY() < 0 || getY() > windowHeight) {
            isUpGoing = !isUpGoing;
        }
    }

    @Override
    BufferedImage getImage() {
        return guppyImage;
    }

    @Override
    double getSpawnProbability() {
        return spawnProbability;
    }

    static void setSpawnProbability(double spawnProbability) {
        Guppy.spawnProbability = spawnProbability;
    }

    @Override
    int getSpawnInterval() {
        return spawnInterval;
    }

    @Override
    int getDefaultInterval() {
        return 2;
    }

    @Override
    int getLifeTime() {
        return lifeTime;
    }

    public static void setLifeTime(int lifeTime) {
        Guppy.lifeTime = lifeTime;
    }

    static void setSpawnInterval(int spawnInterval) {
        Guppy.spawnInterval = spawnInterval;
    }
}

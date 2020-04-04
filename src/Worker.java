public class Worker extends Employee{
    public static int Amount = 0;
    public static float LifeTime = 30;
    public static float TimeBetweenSpawn = 1;
    public static float Chance = 1F;

    public Worker(int x,int y) {
        super(x,y);
        Amount++;
        super.setBornTime(HabitatView.ElapsedTime);
        super.setID();
    }

    static boolean isCreated() {
        if (Chance >= Math.random())
            return true;
        else
            return false;
    }

    public void move() {
    }
}

public class Manager extends Employee{
    public static int Amount = 0;
    public static float LifeTime = 60;
    public static float TimeBetweenSpawn = 1;
    public static float Chance = 0.05F;

    public Manager(int x,int y) {
        super(x,y);
        Amount++;
        super.setBornTime(HabitatView.ElapsedTime);
        super.setID();
    }

    static boolean isCreated() {
        if (Employee.Amount != 0 && Amount / (float) Employee.Amount < Chance)
            return true;
        else
            return false;
    }

    public void move() {
    }
}

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

    public Worker(int x, int y, String id){
        super(x,y);
        Amount++;
        super.setID(id);
    }

    static boolean isCreated() {
        if (Chance >= Math.random())
            return true;
        else
            return false;
    }

    public void move() {
        if(super.getY() < HabitatView.Height + 30) {
            super.setY(super.getY()+1);
        }
        else super.setY(-25);
    }
}

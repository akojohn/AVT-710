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

    public Manager(int x, int y, String id){
        super(x,y);
        Amount++;
        super.setID(id);
    }

    static boolean isCreated() {
        if ( Amount / (float) (Employee.Amount + 1) < Chance)
            return true;
        else
            return false;
    }

    public void move() {
        if(super.getX() < HabitatView.Width + 30) {
            super.setX(super.getX()+2);
        }
        else super.setX(-25);
    }
}

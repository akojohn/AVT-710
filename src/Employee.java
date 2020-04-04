import java.util.UUID;

public abstract class Employee implements IBehaviour {
    private UUID _ID;
    private double _bornTime;
    private int _x;
    private int _y;
    public static int Amount = 0;

    public Employee() { _x = 0; _y = 0; Amount++;}
    public Employee(int x, int y) { _x = x; _y = y; Amount++; }

    public int getX() {
        return _x;
    }
    public void setX(int x) {
        _x = x;
    }
    public int getY() {
        return _y;
    }
    public void setY(int y) { _y = y; }

    public void setBornTime(double bornTime){
        _bornTime = bornTime;
    }
    public double getBornTime(){
        return _bornTime;
    }

    public void setID(){ _ID = UUID.randomUUID(); }
    public void setID(String id){
        _ID = UUID.fromString(id);
    }
    public void setID(UUID id){
        _ID = id;
    }
    public UUID getID(){
        return _ID;
    }
}

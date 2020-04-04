import java.util.LinkedList;

public class ManagerAI extends BaseAI {

    private LinkedList<Employee> temp;

    ManagerAI(LinkedList<Employee> temp){
        this.temp = temp;
    }

    public synchronized void step(){
        for(Employee temp : temp)
            if (temp instanceof Manager && (HabitatView.ElapsedTime - temp.getBornTime() < Manager.LifeTime))
                temp.move();
        try {
            Thread.sleep(10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

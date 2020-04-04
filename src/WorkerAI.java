import java.util.LinkedList;

public class WorkerAI extends BaseAI {

    private LinkedList<Employee> temp;

    WorkerAI(LinkedList<Employee> temp){
        this.temp = temp;
    }

    public synchronized void step(){
        for(Employee temp : temp)
            if (temp instanceof Worker && (HabitatView.ElapsedTime - temp.getBornTime() < Worker.LifeTime))
                temp.move();
        try {
            Thread.sleep(10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

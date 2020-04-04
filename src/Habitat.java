import java.util.*;

public class Habitat {
    // коллекции. 3л
    public LinkedList<Employee> ObjCollection = new LinkedList<>();
    public TreeSet<UUID> IdCollection = new TreeSet<>();
    public HashMap<UUID, Double> BornTimeCollection = new HashMap<>();

    private float _timer1 = 0;
    private float _timer2 = 0;

    // создание нового объекта. 1л
    // передаем не время прошедшее с начала симуляции, а время между кадрами
    public void update(double deltaTime) {
        _timer1 += deltaTime;
        _timer2 += deltaTime;

        if (_timer1 > Worker.TimeBetweenSpawn) {
            if (Worker.isCreated()) addToCollections(0);
            _timer1 = 0;
        }

        if (_timer2 > Manager.TimeBetweenSpawn) {
            if (Manager.isCreated()) addToCollections(1);
            _timer2 = 0;
        }
    }

    // добавление в коллекции. 3л
    public void addToCollections(int i) {
        int empAmount = Employee.Amount;

        int lowBound = HabitatView.Margin;
        int upBoundWidth = HabitatView.Width - lowBound;
        int upBoundHeight = HabitatView.Height - lowBound;

        if(i == 0) ObjCollection.add(empAmount, new Worker((int) (lowBound + Math.random() * upBoundWidth), (int) (lowBound + Math.random() * upBoundHeight)));
        else ObjCollection.add(empAmount, new Manager((int) (lowBound + Math.random() * upBoundWidth), (int) (lowBound + Math.random() * upBoundHeight)));

        UUID tmpID;
        // Объект точно получит уникальный ИД
        do{
            tmpID = UUID.randomUUID();
            ObjCollection.get(empAmount).setID(tmpID);
        } while (IdCollection.contains(tmpID));

        IdCollection.add(tmpID);
        BornTimeCollection.put(tmpID, ObjCollection.get(empAmount).getBornTime());
    }
}

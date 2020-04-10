import java.util.ArrayList;
import java.util.HashSet;
import java.util.Map;
import java.util.TreeMap;
//класс-модель программы. Хранит списки объектов (рыбок), содержит метод update, вызывающийся по
// таймеру и получающий на вход время, прошедшее от начала симуляции. В данном методе генерируются новые объекты.
class Habitat {
    private int width = 800;
    private int height = 800;
    private boolean willShowStatistics = false;

    private FishList fishList = new FishList();
    private HashSet<Long> idSet = new HashSet<>();//хранение и поиск уникальных айди
    private TreeMap<Long, Long> birthdayMap = new TreeMap<Long, Long>();//время рождения объектов

    int getHeight() {
        return height;
    }

    int getWidth() {
        return width;
    }

    void update(long time) {
        if (fishList.wasFishSpawned(time, width, height, Guppy.class, generateId())) {
            birthdayMap.put(fishList.getLast().getId(), time);
        }
        if (fishList.wasFishSpawned(time, width, height, Golden.class, generateId())) {
            birthdayMap.put(fishList.getLast().getId(), time);
        }
        ArrayList<Long> deletedIdArray = new ArrayList<Long>();
        for(Map.Entry<Long, Long> entry : birthdayMap.entrySet()) {
            if (time - entry.getValue() > fishList.getFishById(entry.getKey()).getLifeTime()) {
                fishList.remove(fishList.getFishById(entry.getKey()));
                idSet.remove(entry.getKey());
                deletedIdArray.add(entry.getKey());
            }
        }
        for(Long id : deletedIdArray) {
            birthdayMap.remove(id);
        }
    }

    FishList getFishList() {
        return fishList;
    }

    HashSet<Long> getHashSet(){
        return idSet;
    }

    TreeMap<Long, Long> getTreeMap(){
        return birthdayMap;
    }

    void setHeight(int height) {
        this.height = height;
    }

    void setWidth(int width) {
        this.width = width;
    }

    void changeShowingStatistics() {
        willShowStatistics = !willShowStatistics;
    }

    boolean isWillShowStatistics() {
        return willShowStatistics;
    }

    long generateId() {
        long id = (long) (Math.random() * 100);
        while (idSet.contains(id)) {
            id = (long) (Math.random() * 100);
        }
        idSet.add(id);
        return id;
    }
}

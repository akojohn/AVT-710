package Client;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Map;
import java.util.TreeMap;

class Habitat implements Serializable {
    private int width = 800;
    private int height = 800;

    private FishList fishList = new FishList();
    private HashSet<Long> idSet = new HashSet<>();
    private TreeMap<Long, Long> birthdayMap = new TreeMap<Long, Long>();

    transient GoldenAI goldenAI = new GoldenAI(fishList, width, height);
    transient GuppyAI guppyAI = new GuppyAI(fishList, width, height);

    void refreshAI() {
        if (goldenAI == null) {
            goldenAI = new GoldenAI(fishList, width, height);
        }
        if (guppyAI == null) {
            guppyAI = new GuppyAI(fishList, width, height);
        }
    }

    int getHeight() {
        return height;
    }

    int getWidth() {
        return width;
    }

    void pauseGuppyAI() {
        guppyAI.pause();
    }

    void unpauseGuppyAI() {
        guppyAI.unpause();
    }

    void pauseGoldenAI() {
        goldenAI.pause();
    }

    void unpauseGoldenAI() {
        goldenAI.unpause();
    }

    void setGoldenAIPriority(int priority) {
        goldenAI.setPriority(priority);
    }

    void setGuppyAIPriority(int priority) {
        guppyAI.setPriority(priority);
    }

    void update(long time) {
        synchronized (fishList) {
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
            //fishList.move(width, height);
        }
    }

    FishList getFishList() {
        return fishList;
    }

    void clearCollections() {
        fishList.clear();
        idSet.clear();
        birthdayMap.clear();
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

    private long generateId() {
        long id = (long) (Math.random() * 100);
        while (idSet.contains(id)) {
            id = (long) (Math.random() * 100);
        }
        idSet.add(id);
        return id;
    }
}

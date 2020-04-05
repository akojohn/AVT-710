/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package aquarium;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.TreeMap;
import java.util.UUID;

/**
 *
 * @author Dexp
 */
public class Habitat {

    public LinkedList<Fish> ObjCollection = new LinkedList<>();
    //private FishList ObjCollection = new FishList();
    public HashSet<UUID> idSet = new HashSet<>();
    public TreeMap<UUID, Double> birthdayMap = new TreeMap<UUID, Double>();
    
     float timerGold = 0;
     float timerGuppy = 0;
   
     public GoldAI ThreadGoldAI = null;
     public GuppyAI ThreadGuppyAI = null;
    

    public void start() {
        // Запускаем потоки
         if (ThreadGuppyAI == null) {
            ThreadGuppyAI = new GuppyAI(ObjCollection);
            ThreadGuppyAI.start();
        }
        if (ThreadGoldAI == null) {
            ThreadGoldAI = new GoldAI(ObjCollection);
            ThreadGoldAI.start();
        }
       
    }

    public void stop() {
        // Останавливаем потоки
        if (ThreadGuppyAI != null) ThreadGuppyAI.going = false;
        if (ThreadGoldAI != null) ThreadGoldAI.going = false;
    }
    
    void Update(double timer){
      timerGold += timer;
      timerGuppy += timer;
       if (timerGold > Gold.N1) {
            if (Gold.isCreated()) addToCollections(0);
            timerGold = 0;
        }

        if (timerGuppy > Guppy.N2) {
            if (Guppy.isCreated()) addToCollections(1);
            timerGuppy = 0;
        }
    }
    public void addToCollections(int i){
        int hi = HabitatView.heigthImg;
        int wi = HabitatView.widthImg;
        int upBoundWidth = HabitatView.Width - wi;
        int upBoundHeight = HabitatView.Height - hi;
        if(i == 0) ObjCollection.add(Fish.Sum, new Gold((int) ( Math.random() * upBoundWidth ), (int) ( Math.random() * upBoundHeight)));
        else ObjCollection.add(Fish.Sum, new Guppy((int) ( Math.random() * upBoundWidth ), (int) (Math.random() * upBoundHeight)));
      UUID tmpID;
        // Объект точно получит уникальный ИД
        do{
            tmpID = UUID.randomUUID();
            ObjCollection.get(Fish.Sum-1).setID(tmpID);
       } while (idSet.contains(tmpID));

        idSet.add(tmpID);
        birthdayMap.put(tmpID, ObjCollection.get(Fish.Sum-1).getBornTime());
    }
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package aquarium;

import java.util.LinkedList;

/**
 *
 * @author Dexp
 */
public class Habitat {

    public LinkedList<Fish> ObjCollection = new LinkedList<>();
     float timerGold = 0;
     float timerGuppy = 0;
   
    
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
     
    }
}

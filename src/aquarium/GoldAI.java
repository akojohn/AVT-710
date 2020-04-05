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
public class GoldAI extends BaseAI {
      private LinkedList<Fish> list;

    GoldAI(LinkedList<Fish> temp){
        list = temp;
    }



    public synchronized void step(){
        for(Fish temp : list)
            if (temp instanceof Gold && (HabitatView.ElapsedTime - temp.getBornTime() < Gold.life_time))
                temp.move(HabitatView.Width,HabitatView.Height);
        try {
            Thread.sleep(2);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

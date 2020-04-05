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
public class GuppyAI extends BaseAI{
          private LinkedList<Fish> list;

    GuppyAI(LinkedList<Fish> temp){
        list = temp;
    }



    public synchronized void step(){
        for(Fish temp : list)
            if (temp instanceof Guppy && (HabitatView.ElapsedTime - temp.getBornTime() < Guppy.life_time))
                temp.move(HabitatView.Width,HabitatView.Height);
        try {
            Thread.sleep(2);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


}

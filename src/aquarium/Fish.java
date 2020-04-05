/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package aquarium;

import java.util.UUID;

/**
 *
 * @author Dexp
 */
public abstract class Fish implements IBehaviour {
    private int x;
    private int y;
    public static int Sum=0;
    private UUID ID;
    private double bornTime;
    private boolean isUpGoing;
    public Fish(){
        x = 0;
        y = 0;
        Sum++;
    }
    public Fish(int x1, int y1){
        x = x1;
        y = y1;
         Sum++;
    }
    public int getX(){
        return x;
    }
    public int getY(){
        return y;
    }
    public void setX(int x1){
    x = x1;
    }
    public void setY(int y1){
        y = y1;
    } 
    public void setBornTime(double bornTime1){
        bornTime = bornTime1;
    }
    public double getBornTime(){
        return bornTime;
    }

    public void setID(){ 
        ID = UUID.randomUUID(); 
    }
    public void setID(String id){
        ID = UUID.fromString(id);
    }
    public void setID(UUID id){
        ID = id;
    }
    public UUID getID(){
        return ID;
    }

     public boolean get_going(){
        return isUpGoing;
    }
     public void set_going(boolean go){
         isUpGoing = go;
     }
    public void move(int windowWidth, int windowHeight){
    }
    
}

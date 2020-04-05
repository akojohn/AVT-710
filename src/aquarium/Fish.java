/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package aquarium;

/**
 *
 * @author Dexp
 */
public abstract class Fish implements IBehaviour {
   private int x;
    private int y;
    public static int Sum=0;
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
}

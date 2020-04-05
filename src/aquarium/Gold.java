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
public class Gold extends Fish {
    public static float P1 = 0.3f;
    public static float N1 = 5;
    public static int Sum1 = 0;
    
    Gold(int x, int y){
        super(x,y);
        Sum1++;
    }
    
    public void move(){
        
    }
    static boolean isCreated() {
        if (P1 >= Math.random())
            return true;
        else
            return false;
    }

    @Override
    public int getx() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int gety() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void setx(int x) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void sety(int y) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

   
}

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
public class Guppy extends Fish{
 public static float P2 = 0.5f;
        public static float N2 = 3;
        public static int Sum2 = 0;
        public static double life_time =30;
        
        double v = 1;
        
       Guppy(int x, int y){
        super(x,y);
        Sum2++;
        super.setBornTime(HabitatView.ElapsedTime);
        super.setID();
    }
 static boolean isCreated() {
        if (P2 >= Math.random())
            return true;
        else
            return false;
    }
    @Override
   public void move(int windowWidth, int windowHeight){
       setY((int) (getY() + (get_going() ? v : -v)));
        if (getY() < 0 || getY() > windowHeight-70) {
            set_going(!get_going());
        }
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

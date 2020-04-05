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

public abstract class BaseAI extends Thread{
    boolean going = true;
    boolean sleeping = true;

    public static Object locker = new Object();
    public void run() {
        while(going) {
            synchronized (locker) {
                if(sleeping) {
                    try {
                      locker.wait();
                     } catch (InterruptedException e) {
                        System.err.println(e.getMessage());
                    }
                }
                step();
            }
        }
    }

    public void step(){ }
}


public abstract class BaseAI extends Thread{
    boolean going = true;
    boolean sleeping = true;

    public static Object locker = new Object();
    public void run() {
        while(going) {
            synchronized (locker) {
                if(sleeping) {
                    try {
                        System.out.println("own:: Waiting");
                        locker.wait();
                        System.out.println("own:: Running again");
                    } catch (InterruptedException e) {
                        System.err.println("own:: Interrupted: "+e.getMessage());
                    }
                }
                step();
            }
        }
    }

    public void step(){ }
}
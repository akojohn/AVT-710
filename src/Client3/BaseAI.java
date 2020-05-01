package Client3;

abstract public class BaseAI implements Runnable {
    private Thread thrd;
    FishList fishList;
    int windowWidth, windowHeight;
    private boolean isRunning = true;
    private boolean isWaiting = false;

    BaseAI(FishList fishList, int windowWidth, int windowHeight) {
        this.fishList = fishList;
        this.windowHeight = windowHeight;
        this.windowWidth = windowWidth;
        thrd = new Thread(this);
        thrd.start();
    }

    @Override
    public void run() {
        //System.out.println(thrd.getName() + " - запуск");
        while (isRunning) {
            synchronized (fishList) {
                if (isWaiting) {
                    try {
                        fishList.wait();
                    } catch (InterruptedException e) {
                        System.out.println("Прерывание потока");
                    }
                }
                move();
            }
        }
        System.out.println(thrd.getName() + " - завершение");
    }

    void unpause() {
        isWaiting = false;
        synchronized (fishList) {
            fishList.notify();
        }
    }

    void pause() {
        isWaiting = true;
    }

    void setPriority(int priority) {
        thrd.setPriority(priority);
    }

    abstract void move();
}

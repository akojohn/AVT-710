//Вариант 8
//        Список транспортных средств на дороге состоит из двух категорий:
//        автомобили и мотоциклы. Автомобили генерируются каждые N1 секунд с вероятностью P1.
//        Мотоциклы генерируются каждые N2 секунд с вероятностью P
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Date;

public final class Main {

    public static void main(String[] args) {
        Habitat theUniverse = new Habitat();
        theUniverse.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
            }

            @Override
            public void keyPressed(KeyEvent e) {
                int key = e.getKeyCode();
                if  (key == KeyEvent.VK_B && !theUniverse.isStart && !theUniverse.isEnd) {
                    theUniverse.theUniverseStartTime = new Date().getTime();
                    theUniverse.timerForAutomobile.start();
                    theUniverse.timerForMotorcycle.start();
                }
                if (key == KeyEvent.VK_E && !theUniverse.isEnd && theUniverse.isStart) {
                    theUniverse.theUniverseTime = new Date().getTime() - theUniverse.theUniverseStartTime;
                    theUniverse.timerForAutomobile.stop();
                    theUniverse.timerForMotorcycle.stop();
                    theUniverse.frameClearing();
                    theUniverse.Statistics();
                    theUniverse.dateClearing();
                }
                if (key == KeyEvent.VK_T && theUniverse.isStart && !theUniverse.isEnd) {
                    theUniverse.jTimer.setVisible(theUniverse.isShow());
                }
            }
            @Override
            public void keyReleased(KeyEvent e) {
                int key = e.getKeyCode();
                if  (key == KeyEvent.VK_B){
                    theUniverse.isStart = true;
                }
                if (key == KeyEvent.VK_E) {
                    theUniverse.isEnd = true;
                }
            }
        });
    }
}

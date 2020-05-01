package Client;

import java.awt.*;
import java.util.LinkedList;

class FishList extends LinkedList {

    private int countByType(Class c) {
        int counter = 0;
        for (Object fish : this) {
            if (fish.getClass().getName().equals(c.getName())) {
                counter++;
            }
        }
        return counter;
    }

    boolean wasFishSpawned(long time, int width, int height, Class c, long id) {
        try {
            Fish f = (Fish) c.newInstance();
            if (f.isSpawned(time)) {
                f.setParam((int) (Math.random() * width), (int) (Math.random() * height), id);
                this.add(f);
                //System.out.println(c.getName() + " was created, number is " + this.countByType(c));
                return true;
            }
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }
        return false;
    }

    void draw(Graphics g) {
        for (Object fish : this) {
            ((Fish) fish).draw(g);
        }
    }

    void moveGuppy(int windowWidth, int windowHeight) {
        for (Object fish : this) {
            if (fish instanceof Guppy) {
                ((Fish) fish).move(windowWidth, windowHeight);
                //System.out.println("Guppy moved!");
            }
        }
    }

    void moveGolden(int windowWidth, int windowHeight) {
        for (Object fish : this) {
            if (fish instanceof Golden) {
                ((Fish) fish).move(windowWidth, windowHeight);
                //System.out.println("Golden moved!");
            }
        }
    }

    Fish getFishByI(int i) {
        int k = 0;
        for (Object fish : this) {
            if (k == i) {
                return (Fish)fish;
            }
            k++;
        }
        return null;
    }

    Fish getFishById(long id) {
        for (Object fish : this) {
            if (((Fish)fish).getId() == id) {
                return (Fish)fish;
            }
        }
        return null;
    }

    String getStatisticText(long time) {
        return "Рыбок гуппи " + this.countByType(Guppy.class) +
                ", золотых рыбок " + this.countByType(Golden.class) +
                " за время: " + time;
    }

    public Fish getLast() {
        return (Fish) this.peekLast();
    }
}

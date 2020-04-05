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
public interface IBehaviour {
    void move(int windowWidth, int windowHeight);
    int getx();
    int gety();
    void setx(int x);
    void sety(int y);
}

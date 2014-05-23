/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Entity;

import Communication.StopWatch;
import Map.Node;

/**
 *
 * @author Farazath Ahamed
 */
public class LifePack extends Node {
   
    private int value;
    private long life;
    private long startTime;
    private boolean isAlive;
    StopWatch stp = new StopWatch();
    
    public LifePack(int x,int y,int life){
        super(x, y);
        stp.start();
        this.life = life;
        this.isAlive= true;
    }

    public int getValue() {
        return value;
    }

    public boolean isIsAlive() {
        return isAlive;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public long getLife() {
        return life;
    }

    public void setLife(int life) {
        this.life = life;
    }
    
    public void updatePack(){
        life = life - stp.getElapsedTime();
        if(stp.getElapsedTime() > life ){
            this.isAlive = false;
        }
    }

    public void setIsAlive(boolean isAlive) {
        this.isAlive = isAlive;
    }
    
    
}

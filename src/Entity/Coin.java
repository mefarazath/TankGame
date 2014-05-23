/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Entity;

import Communication.StopWatch;
import Map.Node;
import AI.AI;
/**
 *
 * @author Farazath Ahamed
 */
public class Coin extends Node{
   
    private int value;
    private long life;
    private long startTime;
    private boolean isAlive;
    private AI warrior;
    StopWatch stp = new StopWatch();
    
    public Coin(int x,int y,int value,int life){
        super(x, y);
        stp.start();
        this.value = value;
        this.life = life;
        this.isAlive= true;
       
    }

    public boolean isIsAlive() {
        return isAlive;
    }

    public int getValue() {
        return value;
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
    
    public void updateCoin(){
        life = life - stp.getElapsedTime();
        if(life <= 0){
            this.isAlive = false;
        }    
    }

    public void setIsAlive(boolean isAlive) {
        this.isAlive = isAlive;
    }
    
}

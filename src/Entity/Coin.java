/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Entity;

import Communication.StopWatch;
import Map.Node;
import AI.AICore;
import Map.AStarHeuristic;


public class Coin extends Node implements Comparable<Node>{
   
    private int value;
    private long life;
    private long startTime;
    private boolean isAlive;
    private AICore warrior;
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
    
    public void updateCoin(int xPos,int yPos){
        stp.stop();
        long current = stp.getElapsedTime();
        System.out.println("Elapsed time");
        life = life - current;
        
        if (life <= 0) {
            this.isAlive = false;
        } 
        else {
            int newH = new AStarHeuristic().getEstimatedDistanceToGoal(xPos, yPos, this.getX(), this.getY());
            this.setHdistance(newH);
            System.out.println("UPDATE METHOD - (" + xPos + "," + yPos +","+this.getLife()+") (" + this.getX() + "," + this.getY() + ") " + this.getHdistance());
        }
        
        stp.start();
        
    }

    public void setIsAlive(boolean isAlive) {
        this.isAlive = isAlive;
    }
    
      public int compareTo(Coin c) {
        try{
            if( this.getHdistance()< c.getHdistance()){
                return -1;
            }else if(this.getHdistance() > c.getHdistance()){
                return 1;
            }else{
                    return 0;
            }
        }catch(NullPointerException ex){
            return -1;
        }    
            //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}

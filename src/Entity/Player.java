/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Entity;

/**
 *
 * @author Farazath Ahamed
 */
public class Player {
    protected int playerNumber;
    protected int health;
    
    protected int xPosition;
    protected int yPosition;
    protected int direction;
    
    protected int coin;
    protected int score;
    
    protected int shooting;

    public Player(int playerNumber, int xPosition, int yPosition,int direction) {
        this.playerNumber = playerNumber;
        this.health = 100;
        
        this.xPosition = xPosition;
        this.yPosition = yPosition;
        this.direction = direction;
        
        this.coin =0;
        this.score =0;
        this.shooting =0;
    }
    
    public void updatePlayer(int x,int y,int direction,int health,int coin,int score,int shoot){
            this.xPosition = x;
            this.yPosition = y;
            this.direction = direction;
            
            this.health = health;
            this.shooting= shoot;
            this.coin = coin;
            this.score = score;
    }

    public int getPlayerNumber() {
        return playerNumber;
    }

    public int getHealth() {
        return health;
    }

    public int getxPosition() {
        return xPosition;
    }

    public int getyPosition() {
        return yPosition;
    }

    public int getDirection() {
        return direction;
    }

    public int getCoin() {
        return coin;
    }

    public int getScore() {
        return score;
    }

    public int getShooting() {
        return shooting;
    }
    
    
    
}

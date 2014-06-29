/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package GUI.Entities;

import Communication.Communicator;
import GUI.Configuration.Config;
import GUI.GameGUI.GameWorld;
//import Configuration.Config;
//import PathFinder.PathFinder;
//import PathFinder.Point;
//import gamegui.GameWorld;
import it.randomtower.engine.ResourceManager;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import it.randomtower.engine.ME;
import java.io.IOException;
import java.util.ArrayList;
import javax.security.auth.login.Configuration;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;

/**
 *
 * @author Farazath Ahamed
 */
public class AI extends MapCell {

    private Image playerImage;
    public int health = 100;
    private int playerNo;
    private int direction = 0; // 0 North , 1 East , 2 South ,3 West 
    private int coins = 0;
    private int points = 0;
    boolean isShot = false;
    
    //game related
 
   // private String[] commands = {"LEFT#","SHOOT#","SHOOT#","RIGHT#","SHOOT#"};
   // public String currentMove="SHOOT#";
    private GameWorld myWorld;
    static int cmd=0;
    static Communication.Communicator com;
    static int gLock = 0; // lock to ensure that only one move is made during one second :P 
    static int timeMillis=0;
    int xCor;
    int yCor;
   // public static Point[][] map = PathFinder.map;
    private boolean outOfRange = false;
    
    public static ArrayList<Coin> coinList = new ArrayList<>();
    public static ArrayList<LifePack> LifePackList = new ArrayList<>();
    private String globleUpdate;

    public AI(float x, float y, int playerNo, int direction, GameWorld world) {
        super(x, y);
        
        this.playerNo = playerNo;
        this.direction = direction;
        this.depth = 10;
        this.myWorld = world;
        
        playerImage = ResourceManager.getImage("me_up");
        setGraphic(playerImage);
        setHitBox(0, 0, playerImage.getWidth(), playerImage.getHeight());
        
         int globalX = ((int)x - Config.startX)/Config.gap;
        int globalY = ((int)y - Config.startY)/Config.gap;

        globleUpdate = "P" + playerNo + ";" + globalX + "," + globalY + ";" + direction + ";0;" + health + ";" + coins + ";" + points;
        
        //UpdateMessage = "P" + playerNo + ";" + globalX + "," + globalY + ";" + direction + ";0;" + health + ";" + coins + ";" + points;
    }

    
    @Override
    public void update(GameContainer container, int delta) throws SlickException {

        super.update(container, delta);
         if(health <= 0){
            //C:0,0:21671:1147
            String coinMsg[] = new String[4];
            int coinX = ( (int)x - Config.startX )/ Config.gap;
            int coinY = ( (int)y - Config.startY )/ Config.gap;
            coinMsg[0] = "C";
            coinMsg[1] = coinX + "," + coinY;
            coinMsg[2]= Config.PLUNDER_COIN_LIFE+"";
            coinMsg[3] = this.coins+"";
            
            this.myWorld.setCoins(coinMsg);
            ME.world.remove(this);
            System.err.println("Player " +this.playerNo+" DEAD" );
        }
        else{
        handleCollision();
        globleUpdate();
        playerDirectionImage(direction);
        }
                
       
      
    }
    
    public void handleCollision(){
        String[] collidables = {Coin.COIN, LifePack.LIFE};
        collide(collidables,x,y);
    }
    
    private void globleUpdate() {

        String[] data = globleUpdate.split(";");
        String[] positionUpdate = data[1].split(",");
        x = Config.startX + Config.gap * Integer.parseInt(positionUpdate[0]);
        y = Config.startY + Config.gap * Integer.parseInt(positionUpdate[1]);
        
        this.setDirection(Integer.parseInt(data[2]));
        int shot = Integer.parseInt(data[3]);
        
        if (shot == 1) {
            isShot = true;
//            float xBullet=0;
//            float yBullet=0;
//            switch(this.direction){
//            case 0:
//                xBullet = this.x - 1 * Config.gap;
//                break;
//            case 1:
//                yBullet = this.y + 1 * Config.gap;                
//                break;
//            case 2:
//                xBullet = this.x + 1 * Config.gap;
//                break;
//            case 3:
//                yBullet = this.y - 1 * Config.gap;                
//                break; 
//            }
//            Bullet b = new Bullet(xBullet, yBullet, this.direction);
//            ME.world.add(b);
        }else{
            isShot =false;
        }
        health = Integer.parseInt(data[4]);
        coins = Integer.parseInt(data[5]);
        points = Integer.parseInt(data[6]);
    }

    public void setGlobleUpdate(String globleUpdate) {
        this.globleUpdate = globleUpdate;
    }
    
  
    public void playerDirectionImage(int direction){
        
            switch(direction){
                case 0:
                    playerImage = ResourceManager.getImage("me_up");
                    setGraphic(playerImage);
                    break;
                    
                case 1:
                    playerImage = ResourceManager.getImage("me_right");
                    setGraphic(playerImage);
                    break;
                    
                case 2:
                    playerImage = ResourceManager.getImage("me_down");
                    setGraphic(playerImage);
                    break;
                    
                case 3:
                    playerImage = ResourceManager.getImage("me_left");
                    setGraphic(playerImage);
                    break;
            }
    
    }

    public void setDirection(int direction) {
        this.direction = direction;
    }
    
    public String getPointsTableEntry() {
        String spacing = GUI.Configuration.Config.point_table_spacing_column;
        return String.format("%10s","AI") + spacing + String.format("%10s", points) + spacing + String.format("%10s", coins) + spacing + String.format("%10s", health + "%");
    }

    @Override
    public void render(GameContainer container, Graphics g) throws SlickException {
        super.render(container, g);

        if(!isShot){
            g.setColor(Color.yellow);
            g.drawString("AI", x + 5, y + 5);
        } 
        else {
            g.setColor(Color.yellow);
            g.drawString("AI", x + 5, y + 5);
            g.setColor(Color.red);
            g.drawString("X", x+27,y);
        }
        
    }    
    

     private void collisionHandle() {
        String[] collidables = {Coin.COIN, LifePack.LIFE};
        collide(collidables, x, y);        
    }
    
    
    
    
    
}


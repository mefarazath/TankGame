/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package GUI.Entities;


import GUI.Configuration.Config;
import GUI.GameGUI.GameWorld;
import it.randomtower.engine.ME;
import it.randomtower.engine.ResourceManager;



import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

/**
 *
 * @author Farazath Ahamed
 */
public class Players extends MapCell {

    private Image playerImage;
    public static String PLAYERTYPE = "player";
    private int health = 100;
    private int playerNo;
    private int direction = 0; // 0 North , 1 East , 2 South ,3 West 
    private String globleUpdate;
    private int coins = 0;
    private int points = 0;
    private boolean isShot = false;
    private GameWorld myWorld;
  

    public Players(float x, float y, int playerNo, int direction,GameWorld myWorld) {

        super(x, y);

        this.playerNo = playerNo;
        this.direction = direction;
        this.depth = 10;
        this.myWorld = myWorld;

        playerImage = ResourceManager.getImage("opponent_up");

        setGraphic(playerImage);
        setHitBox(0, 0, playerImage.getWidth(), playerImage.getHeight());
        addType(PLAYERTYPE);

        int globalX = ((int)x - Config.startX)/Config.gap;
        int globalY = ((int)y - Config.startY)/Config.gap;

        globleUpdate = "P" + playerNo + ";" + globalX + "," + globalY + ";" + direction + ";0;" + health + ";" + coins + ";" + points;
     //   globleUpdate = "P" + playerNo + ";" + (int) x + "," + (int) y + ";" + direction + ";0;" + health + ";" + coins + ";" + points;
    }

    public void deductCoins(int amount) {
        this.coins -= amount;
    }

    public int getPlayerNo() {
        return playerNo;
    }

    public void addCoins(int amount) {
        this.coins += amount;
    }

    public void setGlobleUpdate(String globleUpdate) {
        this.globleUpdate = globleUpdate;
    }

    public void setDirection(int direction) {
        this.direction = direction;
    }

    public void setPosition(float x, float y, int direction) {
        this.x = x;
        this.y = y;
        this.setDirection(direction);
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
        collisionHandle();
        globleUpdate();
        playerImageDirection(direction);
        }
    }
    
     @Override
    public void render(GameContainer container, Graphics g) throws SlickException {
        super.render(container, g);

        g.setColor(Color.white);
        g.drawString(playerNo+"", x + 7, y + 5);
        
        if(isShot){
        g.setColor(Color.white);
        g.drawString(playerNo+"", x +7, y + 5);
        g.setColor(Color.yellow);
        g.drawString("X", x+27,y);
        }
        else{
        g.setColor(Color.white);
        g.drawString(""+playerNo, x + 7, y + 5);
        }
        
        

    }
    
    

    private void playerImageDirection(int direction) {
        switch (direction) {
            case 0:
                playerImage = ResourceManager.getImage("opponent_up");
                setGraphic(playerImage);
                break;
            case 1:
                playerImage = ResourceManager.getImage("opponent_right");
                setGraphic(playerImage);
                break;
            case 2:
                playerImage = ResourceManager.getImage("opponent_down");
                setGraphic(playerImage);
                break;
            case 3:
                playerImage = ResourceManager.getImage("opponent_left");
                setGraphic(playerImage);
                break;
        }
    }

    public String getPointsTableEntry() {
        String spacing = GUI.Configuration.Config.point_table_spacing_column;
        return String.format("%10s", playerNo) + spacing + String.format("%10s", points) + spacing + String.format("%10s", coins) + spacing + String.format("%10s", health + "%");
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
//        float xBullet=0;
//        float yBullet=0;
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
//            Bullet b = new Bullet(xBullet, yBullet, direction);
//            ME.world.add(b);
        }
        health = Integer.parseInt(data[4]);
        coins = Integer.parseInt(data[5]);
        points = Integer.parseInt(data[6]);
    }

    private void collisionHandle() {
        String[] collidables = {Coin.COIN, LifePack.LIFE,Bullet.BULLET};
        collide(collidables, x, y);        
    }
}

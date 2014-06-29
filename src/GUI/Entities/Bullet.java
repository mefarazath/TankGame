/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package GUI.Entities;

import GUI.Configuration.Config;
import static GUI.Entities.Coin.CoinsAlive;
import it.randomtower.engine.ME;
import it.randomtower.engine.ResourceManager;
import it.randomtower.engine.entity.Entity;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

/**
 *
 * @author Farazath Ahamed
 */
public class Bullet extends MapCell {

    public static String BULLET = "bullet";
    private Image bullet;
    private int direction;


    

    public Bullet(float x, float y,int direction) {

        super(x, y);
        this.x = super.x;
        this.y = super.y;
        this.depth = 5;
        this.direction = direction;

        //bullet = ResourceManager.getImage("bullet");
        //setGraphic(bullet);
        bulletImageDirection(direction);
        setHitBox(0, 0, bullet.getWidth(), bullet.getHeight());
        addType(BULLET);
        //CoinsAlive++;
   
    }

  
    

    @Override
    public void render(GameContainer container, Graphics g) throws SlickException {
        super.render(container, g);

        g.setColor(Color.black);
       

    }

    @Override
    public void update(GameContainer container, int delta) throws SlickException {
        super.update(container, delta);
        
        float max =Config.startX + Config.gap*Config.MAP_SIZE;
       
        if(this.getPosX()< 0 || this.getPosY() < 0 || this.getPosX()>= max ||this.getPosY()>=max){
            ME.world.remove(this);
        }
        bulletImageDirection(direction);
        moveBullet();        
        
    }

    @Override
    public void collisionResponse(Entity other) {
        
        ME.world.remove(this);
       
//        System.out.println("removed : "+(x-configuration.config.startX)/configuration.config.gap+","+(y-configuration.config.startX)/configuration.config.gap);
    }
    
    public void moveBullet(){
     int d = this.direction;
     
        switch(d){
            case 0:
                this.x = this.x - 4 * Config.gap;
                break;
            case 1:
                this.y = this.y + 4 * Config.gap;                
                break;
            case 2:
                this.x = this.x + 4 * Config.gap;
                break;
            case 3:
                this.y = this.y - 4 * Config.gap;                
                break;
            
        }
    
    }
    
        private void bulletImageDirection(int direction) {
        switch (direction) {
            case 0:
                bullet = ResourceManager.getImage("bullet_up");
                setGraphic(bullet);
                break;
            case 1:
                bullet = ResourceManager.getImage("bullet_right");
                setGraphic(bullet);
                break;
            case 2:
                bullet = ResourceManager.getImage("bullet_down");
                setGraphic(bullet);
                break;
            case 3:
                bullet = ResourceManager.getImage("bullet_left");
                setGraphic(bullet);
                break;
        }
    }
    
}

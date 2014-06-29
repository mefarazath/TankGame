/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package GUI.Entities;

import it.randomtower.engine.ResourceManager;
import it.randomtower.engine.entity.Entity;
import java.awt.Font;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

/**
 *
 * @author Farazath Ahamed
 */
public class Bricks extends MapCell{
    public static String SOLID_WALL = "solid";
    public static String BLANK_WALL = "blank";
    private int health;
    private Image brick;
    private int damage;

    public Bricks(float x, float y) {
        super(x, y);
        health = 100;
        this.x = super.x;
        this.y = super.y;
        depth = 10;
        
        brick = ResourceManager.getImage("brick100");
        setGraphic(brick);
        setHitBox(0,0, brick.getWidth(), brick.getHeight());
        addType(SOLID_WALL);
    }
    
    @Override
    public void update(GameContainer container, int delta) throws SlickException {
        super.update(container, delta);
        this.setDamage(damage);
    }
    
    @Override
    public void render(GameContainer container, Graphics g) throws SlickException{
        super.render(container, g);
        g.setColor(Color.black);
       // g.drawString("Brick",x+5,y+10);
        g.drawString((100-damage)+"%", x+2,y+5);
    }
    
    public void setDamage(int damage){
             switch(damage){
                        case 0:
                            this.damage=0;
                            break;
                        case 1:
                            this.damage = 25;
                            break;
                        case 2:
                            this.damage = 50;
                            break;
                        case 3:
                            this.damage = 75;
                            break;
                        case 4:
                            this.damage = 100;
                            break;
                    }
            
            this.changeWallImage(damage);
    }
    
    public void changeWallImage(int damage){
                    switch(damage){
                        case 0:
                            brick = ResourceManager.getImage("brick100");
                            setGraphic(brick);
                            break;
                        case 25:
                            brick = ResourceManager.getImage("brick75");
                            setGraphic(brick);
                            break;
                        case 50:
                            brick = ResourceManager.getImage("brick50");
                            setGraphic(brick);
                            break;
                        case 75:
                            brick = ResourceManager.getImage("brick25");
                            setGraphic(brick);
                            break;
                        case 100:
                            brick = ResourceManager.getImage("brick0");
                            setGraphic(brick);
                            break;
                    }
    }
    
}

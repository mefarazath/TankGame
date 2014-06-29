/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package GUI.Entities;

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
public class LifePack extends MapCell {

    public int life;
    private Image lifeImage;
    private int timelived = 0;
    public static String LIFE = "LIFE";
    public static int LifePacksAlive = 0;
    public int xCor;
    public int yCor;

    public LifePack(float x, float y, int life,int xCor,int yCor) {
        super(x, y);

        this.life = life;
        this.xCor=xCor;
        this.yCor=yCor;
        lifeImage = ResourceManager.getImage("lifepack");
        setGraphic(lifeImage);
        setHitBox(0, 0, lifeImage.getWidth(), lifeImage.getHeight());
        addType(LIFE);
        LifePacksAlive++;

    }

    @Override
    public void render(GameContainer container, Graphics g) throws SlickException {
        super.render(container, g);
        g.setColor(Color.black);
        g.drawString(life/1000+"s", x+5, y+20);
        
    }
    
    @Override
    public void update(GameContainer container, int delta) throws SlickException {
        super.update(container, delta);

        this.life -= delta;
        if (life <= 0) {

            ME.world.remove(this);
          //  AI.LifePackList.remove(this);
          //  LifePacksAlive--;
        }
    }
    
       @Override
    public void collisionResponse(Entity other) {
        
        ME.world.remove(this);
   //     AI.LifePackList.remove(this);
     //   LifePacksAlive--;
//      System.out.println("removed : "+(x-configuration.config.startX)/configuration.config.gap+","+(y-configuration.config.startX)/configuration.config.gap);
        
    }
   
}

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
public class Coin extends MapCell {

    public static String COIN = "coin";
    private Image coin;
    public int life;
    public int value;
    public static int CoinsAlive = 0;
    private int fireRate = 50;
    private int miliCount = 0;
    private int milis = fireRate;
    private int miliStep = milis / 5;
    private int xCor;
    private int yCOr;

    public int getxCor() {
        return xCor;
    }

    public int getyCOr() {
        return yCOr;
    }
    

    public Coin(float x, float y, int value, int life,int xCor, int yCor) {

        super(x, y);
        this.value = value;
        this.life = life;
        this.x = super.x;
        this.y = super.y;
        this.depth = 5;
        this.xCor = xCor;
        this.yCOr = yCor;
        coin = ResourceManager.getImage("coin");
        setGraphic(coin);
        setHitBox(0, 0, coin.getWidth(), coin.getHeight());
        addType(COIN);
        CoinsAlive++;
   
    }

    public int getValue() {
        return value;
    }
    

    @Override
    public void render(GameContainer container, Graphics g) throws SlickException {
        super.render(container, g);

        g.setColor(Color.black);
        g.drawString(value + "", x + 5, y +5);
        g.drawString(life/1000 + "s", x + 5, y +15);

    }

    @Override
    public void update(GameContainer container, int delta) throws SlickException {
        super.update(container, delta);
        
        life -= delta;
        if (life <= 0 ) {
            ME.world.remove(this);
            AI.coinList.remove(this);
            CoinsAlive--;
        }    
        
    }

    @Override
    public void collisionResponse(Entity other) {
        
        ME.world.remove(this);
      //  AI.coinList.remove(this);
      //  CoinsAlive--;
      // System.out.println("removed : "+(x-configuration.config.startX)/configuration.config.gap+","+(y-configuration.config.startX)/configuration.config.gap);
    }
    
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package GUI.Entities;


import GUI.Configuration.Config;
import it.randomtower.engine.entity.Entity;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;

/**
 *
 * @author Farazath Ahamed
 */
public class MapCell extends Entity{
    
    final static int startX = Config.startX;
    final static int startY = Config.startY;
    final static int gap = Config.gap;
    
    private float posX;
    private float posY;
    
    

    public MapCell(float x, float y) {
        super(x, y);
        posX = x;
        posY = y;
    }

    public float getPosX() {
        return posX;
    }

    public float getPosY() {
        return posY;
    }
   
    @Override
    public void update(GameContainer gc, int delta) throws SlickException{
        super.update(gc, delta);
    }
    
}

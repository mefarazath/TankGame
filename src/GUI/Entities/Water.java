/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI.Entities;

import it.randomtower.engine.ResourceManager;
import org.newdawn.slick.Image;

/**
 *
 * @author Farazath Ahamed
 */
public class Water extends MapCell {

    public static String WATER = "water";
    private Image water;

    public Water(float x, float y) {
        super(x, y);
        this.x = super.x;
        this.y = super.y;
        depth = 10;
        water = ResourceManager.getImage("water");
        setGraphic(water);
        setHitBox(0, 0, water.getWidth(), water.getHeight());
        addType(WATER);
    }

}

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
public class Stones extends MapCell {

    public static String SOLID_WALL = "solid";
    private Image stone;

    public Stones(float x, float y) {
        super(x, y);
        this.x = super.x;
        this.y = super.y;
        depth = 10;
        stone = ResourceManager.getImage("stone");
        setGraphic(stone);
        setHitBox(0, 0, stone.getWidth(), stone.getHeight());
        addType(SOLID_WALL);
    }

}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package GUI.GameGUI;

import AI.AICore;
import GUI.Configuration.Config;
import it.randomtower.engine.ResourceManager;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

/**
 *
 * @author Farazath Ahamed
 */
public class Game_GUI extends StateBasedGame{
    public static int GAME_STATE = 1;
    public GUIListener listener;
    private AICore ai;
    
    public Game_GUI(String title,GUIListener l,AICore ai){
        super(title);
        this.listener = l;
        this.ai = ai;
    }
    
    public void startGame() throws SlickException{
        // TODO code application logic here
        AppGameContainer app = new AppGameContainer(new Game_GUI("Intelligent Warrior 2014",this.listener,this.ai));
        app.setDisplayMode(1250,768,false);
        app.setTargetFrameRate(60);
        app.start();
    }
    
    
    @Override
    public void initStatesList(GameContainer gc){
    
           try {
           ResourceManager.loadResources("data/resources.xml");
           Config.loadConfig();
            
        } catch (IOException ex) {
            Logger.getLogger(Game_GUI.class.getName()).log(Level.SEVERE, null, ex);
        }
        addState(new GameWorld(GAME_STATE, gc,this.listener,this.ai));
        enterState(GAME_STATE);
    }
    
}

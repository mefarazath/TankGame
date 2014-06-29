/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package iwarrior;

import AI.AICore;
import Communication.Communicator;
import GUI.GameGUI.GUIListener;
import GUI.GameGUI.Game_GUI;
import org.newdawn.slick.SlickException;

/**
 *
 * @author Farazath Ahamed
 */
public class Iwarrior {

    /**
     * @param args the command line arguments
     * @throws org.newdawn.slick.SlickException
     */
    public static void main(String[] args) throws SlickException {
        // TODO code application logic here
        //Communicator comm = new Communicator();
        //comm.sendData("JOIN#");
        
        AICore ai = new AICore(1,2,3,4,5);
        GUIListener listener = new GUIListener();
        ai.setListener(listener);
        //ai.joinGame();
        Game_GUI  game= new Game_GUI("Intelligent Warrior", listener,ai);
       
        game.startGame();
       // ai.recieveMessage();
        
    }
    
}

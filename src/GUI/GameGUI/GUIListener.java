/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package GUI.GameGUI;

/**
 *
 * @author Farazath Ahamed
 */
public class GUIListener {
private String currentMessage;    


public void setCurrentMessage(String message){

    this.currentMessage = message;
}


public String getCurrentMessage(){
    
    return currentMessage;
}    
}

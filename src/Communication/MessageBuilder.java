/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Communication;


import Map.Node;
import Map.Path;
import java.util.ArrayList;

/**
 *
 * @author Farazath Ahamed
 */
public class MessageBuilder {

private Path currentPath;
private int currentIndex=0;
private int currentdirection=0;
private ArrayList<String> commands;

public MessageBuilder(){
    commands = new ArrayList<>();
}

    public void setCurrentPath(Path currentPath) {
        this.currentPath = currentPath;
    }

    public void setCurrentIndex(int currentIndex) {
        this.currentIndex = currentIndex;
    }

    public Path getCurrentPath() {
        return currentPath;
    }

    public int getCurrentIndex() {
        return currentIndex;
    }

    public int getCurrentdirection() {
        return currentdirection;
    }

    public void setCurrentdirection(int currentdirection) {
        this.currentdirection = currentdirection;
    }

    public ArrayList<String> getCommands() {
        return commands;
    }

    public String getNextStep(Node current,Node next,int direction){
        //decide on the next step based on the next way point
        String move;
        int currentX = current.getX();
        int currentY = current.getY();
        int nextX = next.getX();
        int nextY = next.getY();
        
     if(currentX == nextX){
            if(currentY > nextY){                 // move to the left
                move = "LEFT#";
//                if(currentdirection != 3)
//          AICore        AI.moved = false;
            }else{
                // move to the right
                move = "RIGHT#";
//                    if(currentdirection != 1)
//     AICore             AI.moved = false; 
            }
            
        }else{
            if(currentX > nextX){
                //move upwards
                move = "UP#";
//                 if(currentdirection != 0)
//AIBrain                  AI.moved = false;
            
            }else{
                //move downwards
                move = "DOWN#";
//                 if(currentdirection != AICore
//                    AI.moved = false;
            }
        } 
        System.out.println("("+currentX+","+currentY+") to ("+nextX+","+nextY+") "+move);
        return move;
       
    }
    
//    public void buildCommands(int startX,int startY){
//        currentIndex=0;
//        int d = getCurrentdirection();
//        Node current;
//        Node next;
//        String nextStep;
//        
//        while(currentIndex != currentPath.getLength()-1){
//             current = currentPath.getWayPoint(currentIndex);
//             next =  currentPath.getWayPoint(currentIndex+1);
//             nextStep = getNextStep(current, next,currentdirection);
//             commands.add(nextStep);
//             System.out.println(nextStep +"|"+currentIndex+") ");
//             
//             if(nextStep.equals("UP#") && currentdirection!=0){
//                    currentdirection=0;
//             }
//             else if(nextStep.equals("RIGHT#") && currentdirection!=1){
//                    currentdirection=1;
//             }
//             else if(nextStep.equals("DOWN#") && currentdirection!=2){
//                    currentdirection=2;
//             }   
//             else if(nextStep.equals("LEFT#") && currentdirection!=3){
//                    currentdirection=3;
//             }else{
//                    currentIndex++;
//             } 
//                        
//        }
//    }


    
    
}

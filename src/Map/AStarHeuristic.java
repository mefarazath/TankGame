/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Map;

/**
 *
 * @author Farazath Ahamed
 */
public class AStarHeuristic {
     public int getEstimatedDistanceToGoal(int startX, int startY, int goalX, int goalY) {        
                int dx = goalX - startX;
                int dy = goalY - startY;
               
                //float result = (float) (Math.sqrt((dx*dx)+(dy*dy)));
               
                //Optimization! Changed to distance^2 distance: (but looks more "ugly")
               
                int result =  (dx*dx)+(dy*dy);
               
               
                return result;
        }

}

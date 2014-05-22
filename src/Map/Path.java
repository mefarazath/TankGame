/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Map;

import java.util.ArrayList;

/**
 *
 * @author Farazath Ahamed
 */
public class Path implements Comparable<Path>{

    // The waypoints in the path (list of coordiantes making up the path)
        private ArrayList<Node> waypoints = new ArrayList<>();
       
        public Path() {
        }
       
        public int getLength() {
                return waypoints.size();
        }

        public Node getWayPoint(int index) {
                return waypoints.get(index);
        }

        /**
         * Get the x-coordinate for the way point at the given index.
         *
         * @param index The index of the way point to get the x-coordinate of.
         * @return The x coordinate at the way point.
         */
        
        public int getX(int index) {
                return getWayPoint(index).getX();
        }

        /**
         * Get the y-coordinate for the way point at the given index.
         *
         * @param index The index of the way point to get the y-coordinate of.
         * @return The y coordinate at the way point.
         */
        public int getY(int index) {
                return getWayPoint(index).getY();
        }

        /**
         * Append a way point to the path.  
         *
         */
        public void appendWayPoint(Node n) {
                waypoints.add(n);
        }

        /**
         * Add a way point to the beginning of the path.  
         *
         * @param n
         * @param x The x coordinate of the waypoint.
         * @param y The y coordinate of the waypoint.
         */
        public void prependWayPoint(Node n) {
                waypoints.add(0, n);
        }

        /**
         * Check if this path contains the WayPoint
         *
         * @param x The x coordinate of the waypoint.
         * @param y The y coordinate of the waypoint.
         * @return True if the path contains the waypoint.
         */
        public boolean contains(int x, int y) {
                for(Node node : waypoints) {
                        if (node.getX() == x && node.getY() == y)
                                return true;
                }
                return false;
        }

    @Override
    public int compareTo(Path anotherPath) {
            if(this.getLength() < anotherPath.getLength()){
                return -1;
            }else if(this.getLength() > anotherPath.getLength()){
                return 1;
            }else{
                    return 0;
            }
            
            //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}

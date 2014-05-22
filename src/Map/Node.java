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
public class Node implements Comparable<Node>{
    
// Connected Nodes    
Node north;
Node east;
Node south;
Node west;

Node previousNode;

ArrayList<Node> neighbourList ;

boolean isObstacle;
boolean isStart;
boolean isGoal;
boolean visited;

int distanceFromStart;
int hDistance;

int x;
int y;

public Node(int x, int y) {
                neighbourList = new ArrayList<>();
                this.x = x;
                this.y = y;
                this.visited = false;
                this.distanceFromStart = Integer.MAX_VALUE;
                this.isObstacle = false;
                this.isStart = false;
                this.isGoal = false;
        }
       
public Node (int x, int y, boolean visited, int distanceFromStart, boolean isObstical, boolean isStart, boolean isGoal) {
                neighbourList = new ArrayList<>();
                this.x = x;
                this.y = y;
                this.visited = visited;
                this.distanceFromStart = distanceFromStart;
                this.isObstacle = isObstical;
                this.isStart = isStart;
                this.isGoal = isGoal;
        }
        
        
        public void setNorth(Node north) {
                //replace the old Node with the new one in the neighbourList
                if (neighbourList.contains(this.north))
                        neighbourList.remove(this.north);
                neighbourList.add(north);
               
                //set the new Node
                this.north = north;
        }


        public Node getEast() {
                return east;
        }

        public void setEast(Node east) {
                //replace the old Node with the new one in the neighbourList
                if (neighbourList.contains(this.east))
                        neighbourList.remove(this.east);
                neighbourList.add(east);
               
                //set the new Node
                this.east = east;
        }

        public Node getSouth() {
                return south;
        }

        public void setSouth(Node south) {
                //replace the old Node with the new one in the neighbourList
                if (neighbourList.contains(this.south))
                        neighbourList.remove(this.south);
                neighbourList.add(south);
               
                //set the new Node
                this.south = south;
        }

        public Node getWest() {
                return west;
        }

        public void setWest(Node west) {
                //replace the old Node with the new one in the neighbourList
                if (neighbourList.contains(this.west))
                        neighbourList.remove(this.west);
                neighbourList.add(west);
               
                //set the new Node
                this.west = west;
        }

       
        public ArrayList<Node> getNeighborList() {
                return neighbourList;
        }

        public boolean isVisited() {
                return visited;
        }

        public void setVisited(boolean visited) {
                this.visited = visited;
        }

        public int getDistanceFromStart() {
                return distanceFromStart;
        }

        public void setDistanceFromStart(int f) {
                this.distanceFromStart = f;
        }

        public Node getPreviousNode() {
                return previousNode;
        }

        public void setPreviousNode(Node previousNode) {
                this.previousNode = previousNode;
        }
       
        public int getHdistance() {
                return this.hDistance;
        }

        public void setHdistance(int h) {
                this.hDistance = h;
        }

        public int getX() {
                return x;
        }

        public void setX(int x) {
                this.x = x;
        }

        public int getY() {
                return y;
        }

        public void setY(int y) {
                this.y = y;
        }
       
        public boolean isObstacle() {
                return isObstacle;
        }

        public void setObstacle(boolean isObstacle) {
                this.isObstacle = isObstacle;
        }

        public boolean isStart() {
                return isStart;
        }

        public void setStart(boolean isStart) {
                this.isStart = isStart;
        }

        public boolean isGoal() {
                return isGoal;
        }

        public void setGoal(boolean isGoal) {
                this.isGoal = isGoal;
        }

        public boolean equals(Node node) {
                return (node.x == x) && (node.y == y);
        }

        
@Override
        public int compareTo(Node otherNode) {
                int totalDistanceFromGoal = hDistance + distanceFromStart;
                int otherTotalDistanceFromGoal = otherNode.getHdistance() + otherNode.getDistanceFromStart();
               
                if (totalDistanceFromGoal < otherTotalDistanceFromGoal) {
                        return -1;
                } else if (totalDistanceFromGoal > otherTotalDistanceFromGoal) {
                        return 1;
                } else {
                        return 0;
                }
        }
        

    
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package AI;

import Communication.Communicator;
import Communication.StopWatch;
import Entity.Player;
import Map.AStarHeuristic;
import Map.AreaMap;
import Map.Astar;
import Map.Path;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Farazath Ahamed
 */
public class AI extends Player implements Observer{

private String input;
private Communicator comm;

private double lastMsgTime;
private StopWatch watch;

public static int[][] obstacleMap;
public int mapSize=20;
public AreaMap map;

ArrayList<Player> playerList = new ArrayList<>();
Astar pathfinder;
AStarHeuristic h;
        


public AI(int playerNumber, int health, int xPosition, int yPosition,int direction){
        super(playerNumber,xPosition,yPosition,direction);
    
        this.input = null;
        this.comm = new Communicator();
        comm.setAI(this);
        this.watch = new StopWatch();
        this.watch.start();
        
        h = new AStarHeuristic();
        
        
}

    @Override
public void update(Observable o, Object arg) {
        this.input = (String)arg;
        
        System.out.println("RECIEVED MESSGAGE "+ this.watch.getElapsedTime());
        System.out.println(this.input);
        
        if(input.charAt(0) == 'I'){
               handleInitialMessage(input);
               
        
        }else if(input.charAt(0)== 'S'){
               handleStartMessage(input); 
               System.out.println(this.xPosition+","+this.yPosition +" POSITION");
               this.pathfinder.calcShortestPath(this.xPosition,this.yPosition,0,19);
               this.pathfinder.printPath();
        }else if(input.charAt(0)== 'C'){
               setCoin(input.split(":"));
        }else if(input.charAt(0)== 'L'){
        
        }
        
        //lastMsgTime = nowTime;
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
}

public void joinGame(){
        this.comm.sendData("JOIN#");
}
    
public void recieveMessage(){
        while(true){
            
            try {
                this.comm.reciveData();
            } catch (IOException ex) {
                Logger.getLogger(AI.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
}

public void handleInitialMessage(String message){
        String[] msg = message.split(":");
        playerNumber = Integer.parseInt(msg[1].substring(1,msg[1].length()));
        initializeObstacleMap(mapSize);
        setBricks(msg[2]);
        setStones(msg[3]);
        setWater(msg[4]);
        
        for(int i=0;i<mapSize;i++){
            for(int j=0;j<mapSize;j++){
                    System.out.print(obstacleMap[i][j]+",");
            }
            System.out.println("");
        }
        
        this.map = new AreaMap(mapSize,mapSize,obstacleMap);
        this.map.createMap();
        this.map.registerEdges();
        map.setStartLocation(this.xPosition,this.yPosition);
        
        pathfinder = new Astar(map, h);
//        if(this.map == null){
//            for(int i=0;i<mapSize;i++){
//                for(int j=0;j<mapSize;j++){
//                   
//                 }
//            System.out.println("");
//        }
        
}

public void handleStartMessage(String message){
        String[] playerMsg = message.split(":");
    for (int i = 1; i < playerMsg.length; i++) {

            int x, y, direction, num;
            String[] data = playerMsg[i].split(";");
            num = Integer.parseInt(data[0].charAt(1) + "");
            String[] position = data[1].split(",");
            x = Integer.parseInt(position[0]);
            y = Integer.parseInt(position[1]);
            direction = Integer.parseInt(data[2]);

            Player newPlayer;
            //public Player(int playerNumber, int health, int xPosition, int yPosition,int direction)
            if (num != this.playerNumber) {
                    newPlayer = new Player(num,x, y, direction);        
            } 
          else {
                 this.xPosition =x;
                 this.yPosition= y;
                 this.direction=direction;
                 newPlayer = new Player(this.playerNumber,this.xPosition,this.yPosition,this.direction);
            }

            playerList.add(newPlayer);    
            System.err.println("Player "+num+" ADDED ["+x+","+y+"]");
        }
}

public void initializeObstacleMap(int size){
        obstacleMap = new int[size][size];
        
        for(int i=0;i<size;i++){
            for(int j=0;j<size;j++){
                obstacleMap[i][j]=0;
            }
        }
}

public void setBricks(String brickMsg){
    
         String[] bricks = brickMsg.split(";");
        
        for(String i : bricks){
            String xy[] = i.split(",");
            
            int x = Integer.parseInt(xy[0]);
            int y = Integer.parseInt(xy[1]);
            
            obstacleMap[y][x] = 1;
            //map[x][y] = new Brick(x,y);
        }
}

public void setStones(String stoneMsg){
     String[] stones = stoneMsg.split(";");
        
        for(String i : stones){
            String xy[] = i.split(",");
            
            int x = Integer.parseInt(xy[0]);
            int y = Integer.parseInt(xy[1]);
            
            obstacleMap[y][x] = 1;
        }

}

public void setWater(String waterMsg){
      String[] water = waterMsg.split(";");
        
        for(String i : water){
            String xy[] = i.split(",");
            
            int x = Integer.parseInt(xy[0]);
            int y = Integer.parseInt(xy[1]);
            
            obstacleMap[y][x] = 1;
        }
}

public void setCoin(String coinMsg[]){
        //C:0,0:21671:1147
        
        String[] coord = coinMsg[1].split(",");
        int x = Integer.parseInt(coord[0]);
        int y = Integer.parseInt(coord[1]);
        int lifetime = Integer.parseInt(coinMsg[2]);
        int value = Integer.parseInt(coinMsg[3]);

        Path path = pathfinder.calcShortestPath(this.xPosition,this.yPosition,y,x);
        System.out.print("Path Found - ");
        for(int i=0 ; i<path.getLength();i++){
                System.out.print("("+path.getWayPoint(i).getY()+","+path.getWayPoint(i).getX()+") ");
        }
        System.out.println("");
        pathfinder.printPath();
        System.out.println("");
        System.err.println("Coin added to ["+x+","+y+"]" );
}

public void setLifePack(String lifeMsg){

}
}

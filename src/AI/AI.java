/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package AI;

import Communication.Communicator;
import Communication.StopWatch;
import Entity.Coin;
import Entity.LifePack;
import Entity.Player;
import Map.AStarHeuristic;
import Map.AreaMap;
import Map.Astar;
import Map.Path;
import Map.SortedPathList;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;
import java.util.Stack;
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

public static ArrayList<Coin> coinList = new ArrayList<>();
SortedPathList coinPaths = new SortedPathList();

public static ArrayList<LifePack> packList = new ArrayList<>();
SortedPathList packPaths = new SortedPathList();

ArrayList<String> currentCommands = new ArrayList<>();
        


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
        try{
        this.input = (String)arg;
       
        System.out.println("RECIEVED MESSGAGE "+ this.watch.getElapsedTime());
        System.out.println(this.input);
        
        if(input.charAt(0) == 'I'){
               handleInitialMessage(input);
               
        
        }else if(input.charAt(0)== 'S'){
               handleStartMessage(input); 
               System.out.println(this.xPosition+","+this.yPosition +" POSITION");
              // this.pathfinder.calcShortestPath(this.xPosition,this.yPosition,0,19);
              // this.pathfinder.printPath();
        }else if(input.charAt(0)== 'C'){
               setCoin(input.split(":"));
        }else if(input.charAt(0)== 'L'){
               setLifePack(input.split(":"));
        }else if(input.split(":")[0].equals("G")){
                    
              handleGlobalUpate(input);
            
            if(currentCommands.isEmpty()){
                this.comm.sendData("SHOOT#");
                if (coinPaths.size() != 0) {
                    for (int i = 0; i < coinPaths.getFirst().getLength(); i++) {
                        System.out.print("(" + coinPaths.getFirst().getWayPoint(i).getY() + "," + coinPaths.getFirst().getWayPoint(i).getX() + ") ");
                    }
                }
                System.out.println("");
                System.out.println("Coin list - "+coinList.size()+"  Paths for coins-" +coinPaths.size());
                for (int i = 0; i < coinList.size(); i++) {
                        System.out.print("["+coinList.get(i).getValue()+","+coinList.get(i).getLife()+"] ");
                    }
                System.out.println("------------");
            }
            else{
                    this.comm.sendData(currentCommands.get(0));
            }
            // make the move by the AI
            
        }
            
        }catch(ClassCastException ex){
              
               this.coinList.remove((Coin)(arg));
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

            int x, y, dir, num;
            String[] data = playerMsg[i].split(";");
            num = Integer.parseInt(data[0].charAt(1) + "");
            String[] position = data[1].split(",");
            x = Integer.parseInt(position[0]);
            y = Integer.parseInt(position[1]);
            dir = Integer.parseInt(data[2]);

            Player newPlayer;
            //public Player(int playerNumber, int health, int xPosition, int yPosition,int direction)
            if (num != this.playerNumber) {
                    newPlayer = new Player(num,x, y, dir);        
            } 
          else {
                 this.xPosition =x;
                 this.yPosition= y;
                 this.direction=dir;
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
        
        coinList.add(new Coin(x,y,value,lifetime,this));
        Path path = pathfinder.calcShortestPath(this.xPosition,this.yPosition,y,x);
        coinPaths.add(path);
        System.out.print("Path Found - ");
        for(int i=0 ; i<path.getLength();i++){
                System.out.print("("+path.getWayPoint(i).getY()+","+path.getWayPoint(i).getX()+") ");
        }
        System.out.println("");
        pathfinder.printPath();
        System.out.println("");
        System.err.println("Coin added to ["+x+","+y+"]" );
}

public void setLifePack(String lifeMsg[]){
    String[] coord = lifeMsg[1].split(",");
        int x = Integer.parseInt(coord[0]);
        int y = Integer.parseInt(coord[1]);
        int lifetime = Integer.parseInt(lifeMsg[2]);

        Path path = pathfinder.calcShortestPath(this.xPosition,this.yPosition,y,x);
        packPaths.add(path);
        System.out.print("Path Found - ");
        for(int i=0 ; i<path.getLength();i++){
                System.out.print("("+path.getWayPoint(i).getY()+","+path.getWayPoint(i).getX()+") ");
        }
        System.out.println("");
        pathfinder.printPath();
        System.out.println("");
        System.err.println("LifePack added to ["+x+","+y+"]" );
}

public void updatePlayers(String[] playerMsg){
    //P0;0,0;0;0;100;0;0
        for (int i = 1; i < playerMsg.length-1; i++) {
        String[] coord = playerMsg[i].split(";");
        String xy[] = coord[1].split(",");
        int playerNum = Integer.parseInt(coord[0].substring(1));
        int x = Integer.parseInt(xy[0]);
        int y = Integer.parseInt(xy[1]);
        int dir = Integer.parseInt(coord[2]);
        int shoot = Integer.parseInt(coord[3]);
        int life = Integer.parseInt(coord[4]);
        int coinsCollected = Integer.parseInt(coord[5]);
        int points = Integer.parseInt(coord[6]);

        playerList.get(playerNum).updatePlayer(x, y, dir, life, coinsCollected, points, shoot);
        if (playerNum == playerNumber) {
            updatePlayer(x, y, dir, life, coinsCollected,points, shoot);
        }
    }
}
public void handleGlobalUpate(String globalMsg){
         // G:P0;0,0;0;0;100;0;0:P1;0,9;0;0;100;0;0:P2;9,0;0;0;100;0;0:P3;9,9;0;0;100;0;0:P4;5,5;0;0;100;0;0:3,2,0;5,7,0;1,3,0;3,6,0;5,8,0#
           updatePlayers(globalMsg.split(":"));
           Stack coinRemoval = new Stack();
           Stack packRemoval = new Stack();
            for (Coin c : coinList) {
                    c.updateCoin();
                    if(c.getLife() < 0){
                        coinRemoval.add(c);
                    }
            }
            for(LifePack l : packList){
                l.updatePack();
                if(l.getLife() < 0){
                        packRemoval.add(l);
                    }
            }
            
            while(!coinRemoval.empty()){
                  Coin c = (Coin)coinRemoval.pop();         
                  Path removePath = coinPaths.getPathWithEndPoint(c);
                  if(removePath != null){
                      System.out.println(removePath.getLastWayPoint().getX()+","+removePath.getLastWayPoint().getY());
                  }else{
                      System.out.println("Damn shit");
                  }
                  coinPaths.remove(removePath);
                   
                  coinList.remove(c);
            }
            while(!packRemoval.empty()){
                  LifePack l = (LifePack)packRemoval.pop();
                  Path removePath = coinPaths.getPathWithEndPoint(l);
                  packPaths.remove(removePath);
                  packList.remove(l);
            }
            
}


}

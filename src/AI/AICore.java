/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package AI;

import Communication.Communicator;
import Communication.MessageBuilder;
import Communication.StopWatch;

import Entity.Coin;
import Entity.LifePack;
import Entity.Player;
import GUI.GameGUI.GUIListener;

import Map.AStarHeuristic;
import Map.AreaMap;
import Map.Astar;
import Map.Node;
import Map.Path;
import Map.SortedCoinList;
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
public class AICore extends Player implements Observer{

private String input;
private Communicator comm;
private MessageBuilder msgBuilder;
private GUIListener listener;

public static boolean moved;

private double lastMsgTime;
private StopWatch watch;

public static int[][] obstacleMap;
public int mapSize=20;
public AreaMap map;

ArrayList<Player> playerList = new ArrayList<>();
Astar pathfinder;
AStarHeuristic h;

public static SortedCoinList coinList = new SortedCoinList();
SortedPathList coinPaths = new SortedPathList();

public static ArrayList<LifePack> packList = new ArrayList<>();
SortedPathList packPaths = new SortedPathList();

public ArrayList<String> currentCommands;
public Path currentSelectedPath;
public int currentPos;
public String lastCommand;
public  boolean error=false;

        


public AICore(int playerNumber, int health, int xPosition, int yPosition,int direction){
        super(playerNumber,xPosition,yPosition,direction);
    
        this.input = null;
        this.comm = new Communicator();
        comm.setAICore(this);
        this.watch = new StopWatch();
        this.watch.start();
        
        h = new AStarHeuristic();
        msgBuilder = new MessageBuilder();
        currentPos =0;
                
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
              // this.pathfinder.calcShortestPath(this.xPosition,this.yPosition,0,19);
              // this.pathfinder.printPath();
        }else if(input.charAt(0)== 'C' && (input.charAt(1)!='E')){
               setCoin(input.split(":"));
        }else if(input.charAt(0)== 'L'){
               setLifePack(input.split(":"));
        }else if(input.split(":")[0].equals("G")){
                    
              handleGlobalUpate(input);
              
                   
//                    currentSelectedPath = coinPaths.getFirst();
                    msgBuilder.setCurrentdirection(this.getDirection());
                    String reply;
                    
//                    if(coinPaths.getFirst().getLength() != 1){
//                    Node current = currentSelectedPath.getWayPoint(0);
//                    Node next = currentSelectedPath.getWayPoint(1);
//                    reply = msgBuilder.getNextStep(current, next, this.getDirection());
//                    lastCommand = reply;
//                    }
                    
              
                    if(coinList.size() != 0){
                        Node s = map.getNode(this.xPosition,this.yPosition);
                        s.setPreviousNode(null);
                        Node g = coinList.getFirst();
                        
                        Path p = this.pathfinder.calcShortestPath(s.getX(), s.getY(), g.getX(), g.getY());
                        this.pathfinder.printPath();
                        p.prependWayPoint(s);
                        
                        if(p.getLength() != 1){
                        Node current = p.getWayPoint(0);
                        Node next = p.getWayPoint(1);
                        reply = msgBuilder.getNextStep(current, next, this.getDirection());
                        lastCommand = reply;
                        System.out.println(reply);
                        }
                    else{
                            reply = "SHOOT#";
                        }
                        
                    }
                    else{
                    reply = "SHOOT#";
                    }
          
                    if(!error){
                    this.comm.sendData(reply);
                    lastCommand = reply;
                    }
                    else{
                     this.comm.sendData(lastCommand);
                     error = false;
                    }
                    
                    System.err.print(moved +" || ("+this.xPosition+","+this.yPosition+") "+ error);

              
              
             
//              if(this.getHealth() > 50){
//                  if(coinPaths.size() != 0){
//                        msgBuilder.buildCommands(this.getxPosition(),this.getyPosition());
//                        currentCommands = msgBuilder.getCommands();
//                  }
//                  
//                  
//              }else{
//              
//              }
                  
//            if(currentCommands.isEmpty()){
//                this.comm.sendData("SHOOT#");
//                if (coinPaths.size() != 0) {
//                    for (int i = 0; i < coinPaths.getFirst().getLength(); i++) {
//                        System.out.print("(" + coinPaths.getFirst().getWayPoint(i).getY() + "," + coinPaths.getFirst().getWayPoint(i).getX() + ") ");
//                    }
//                }
//                System.out.println("");
//                System.out.println("Coin list - "+coinList.size()+"  Paths for coins-" +coinPaths.size());
//                  for (Coin coinList1 : coinList) {
//                      System.out.print("[" + coinList1.getValue() + "," + coinList1.getLife() + "] ");
//                  }
//                System.out.println("------------ coin paths -------------");
//                    for(int i=0;i<coinPaths.size();i++){
//                        Node n = coinPaths.getPathAt(i).getLastWayPoint();
//                        System.out.print(n.getY()+","+n.getX()+" - "+coinPaths.getPathAt(i).getLength()+"  ");
//                    }
//                
//            }
//            else{
//                    this.comm.sendData(currentCommands.get(0));
//            }
             
        }else if(input.equals("TOO_QUICK")){
                         this.error = true;
        }else if(input.equals("CELL_OCCUPIED")){
                        this.error =true;
                        lastCommand = "SHOOT#";
        }  
}

public void joinGame(){
        this.comm.sendData("JOIN#");
}
    
public void recieveMessage(){
        
            
            try {
                String message = this.comm.reciveData();
                this.listener.setCurrentMessage(message);
            } catch (IOException ex) {
                Logger.getLogger(AICore.class.getName()).log(Level.SEVERE, null, ex);
            }
        
}

    public void setListener(GUIListener listener) {
        this.listener = listener;
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
                    newPlayer = new Player(num,y, x, dir);        
            } 
          else {
                 this.xPosition =x;
                 this.yPosition= y;
                 this.direction=dir;
                 newPlayer = new Player(this.playerNumber,this.yPosition,this.xPosition,this.direction);
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
        
        coinList.add(new Coin(y,x,value,lifetime));
        //System.out.println("Path to calculate "+this.yPosition+","+this.xPosition+"-"+y+","+x);
//        
//        Path path = pathfinder.calcShortestPath(this.xPosition,this.yPosition,y,x);
//        path.prependWayPoint(new Node(this.xPosition,this.yPosition));
//      //  coinPaths.add(path);
//        System.out.println("Path Found  - ("+this.yPosition+","+this.xPosition+") to ("+x+","+y+")");
//        for(int i=0 ; i<path.getLength();i++){
//                System.out.print("("+path.getWayPoint(i).getY()+","+path.getWayPoint(i).getX()+") ");
//        }
//        System.out.println("");
//        pathfinder.printPath();
//        System.out.println("");
      //  msgBuilder.setCurrentPath(path);
       // msgBuilder.buildCommands(this.xPosition,this.yPosition);
      //  currentCommands = msgBuilder.getCommands();
        
//        for(String s: currentCommands)
//            System.out.print(s+" | ");
        System.err.println("Coin added to ["+x+","+y+"]" );
}

public void setLifePack(String lifeMsg[]){
    String[] coord = lifeMsg[1].split(",");
        int x = Integer.parseInt(coord[0]);
        int y = Integer.parseInt(coord[1]);
        int lifetime = Integer.parseInt(lifeMsg[2]);
        
        packList.add(new LifePack(y,x, lifetime));
//        Path path = pathfinder.calcShortestPath(this.xPosition,this.yPosition,y,x);
//        path.prependWayPoint(new Node(this.xPosition,this.yPosition));
//        //packPaths.add(path);
//        System.out.println("Pack Path Found  - ("+this.yPosition+","+this.xPosition+") to ("+x+","+y+")");
//        for(int i=0 ; i<path.getLength();i++){
//                System.out.print("("+path.getWayPoint(i).getY()+","+path.getWayPoint(i).getX()+") ");
//        }
//        System.out.println("");
//        pathfinder.printPath();
//        System.out.println("");
        System.err.println("LifePack added to ["+x+","+y+"]" );
}

public void updatePlayers(String[] playerMsg){
    //P0;0,0;0;0;100;0;0z
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
        
        for(int j=0;j<coinList.size();j++){
            if(coinList.getCoinAt(j).getY()== x && coinList.getCoinAt(j).getX() == y)
                    coinList.getCoinAt(j).setIsAlive(false);
        }
        for(LifePack l:packList){
            if(l.getY()==x && l.getX() == y)
                l.setIsAlive(false);
        }
        playerList.get(playerNum).updatePlayer(y,x, dir, life, coinsCollected, points, shoot);
        if (playerNum == playerNumber) {
            msgBuilder.setCurrentdirection(dir);
            updatePlayer(y, x, dir, life, coinsCollected,points, shoot);
        }
    }
}

public void handleGlobalUpate(String globalMsg){
         // G:P0;0,0;0;0;100;0;0: P1;0,9;0;0;100;0;0:P2;9,0;0;0;100;0;0:P3;9,9;0;0;100;0;0:P4;5,5;0;0;100;0;0:3,2,0;5,7,0;1,3,0;3,6,0;5,8,0#
           updatePlayers(globalMsg.split(":"));
           Stack coinRemoval = new Stack();
           Stack packRemoval = new Stack();
            for(int i=0;i<coinList.size();i++) {
                    Coin c = coinList.getCoinAt(i);
                    c.updateCoin(this.xPosition,this.yPosition);
                    System.out.print("("+c.getX()+","+c.getY()+","+c.getLife()+","+c.getHdistance()+") ");
                    if(!c.isIsAlive()){
                        coinRemoval.add(c);
                    }
            }
            System.out.println("");
            for(LifePack l : packList){
                l.updatePack();
                if(l.getLife() < 0){
                        packRemoval.add(l);
                    }
            }
             System.out.println("Coins to Remove " + coinRemoval.size());
           // Stack removeCoinPath = new Stack();
            while(!coinRemoval.empty()){
                  Coin c = (Coin)coinRemoval.pop();         
              //    Path removePath = coinPaths.getPathWithEndPoint(c);
             //     coinPaths.remove(removePath);
                  
                  coinList.remove(c);
            }
             System.out.println("Packs to Remove " + packRemoval.size());
            while(!packRemoval.empty()){
                  LifePack l = (LifePack)packRemoval.pop();
               //   Path removePath = coinPaths.getPathWithEndPoint(l);
               //   packPaths.remove(removePath);
                  packList.remove(l);
            }
           // coinPaths.clear();
           // packPaths.clear();
            
            System.out.println("Coin Paths Updating Num-"+coinList.size());
             for(int i=0;i<coinList.size();i++) {
                    Coin c = coinList.getCoinAt(i);
                    System.out.print("("+c.getX()+","+c.getY()+","+c.getLife()+","+c.getHdistance()+") ");
            }
            
           
//            for(Coin c:coinList){
//                Node newStart = map.getNode(this.xPosition,this.yPosition);
//                newStart.setPreviousNode(null);
//                Path p = pathfinder.calcShortestPath(newStart.getX(),newStart.getY(),c.getX(),c.getY());
//               // pathfinder.printPath();
//                p.prependWayPoint(newStart);
//                coinPaths.add(p);
//                System.out.println("("+c.getX()+","+c.getY()+","+c.getLife()+","+c.getHdistance()+") - "+p.getLength());
//  
//            }
            
            }
            }
//            
//            System.out.println("LifePacks Paths Updating Num-"+packList.size());
//            for(LifePack l : packList){
//                Node newStart =map.getNode(this.xPosition,this.yPosition);
//                newStart.setPreviousNode(null);
//                Path p = pathfinder.calcShortestPath(newStart.getX(),newStart.getY(),l.getX(),l.getY());
//                pathfinder.printPath();
//                p.prependWayPoint(newStart);
//                packPaths.add(p);
//                  System.out.println("("+l.getX()+","+l.getY()+") - "+p.getLength());
//            }
                        





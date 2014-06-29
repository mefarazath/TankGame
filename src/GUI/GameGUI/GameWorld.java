/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package GUI.GameGUI;

import AI.AICore;
import GUI.Configuration.Config;
import GUI.Entities.AI;
import GUI.Entities.Bricks;
import GUI.Entities.Coin;
import GUI.Entities.LifePack;
import GUI.Entities.MapCell;
import GUI.Entities.Players;
import GUI.Entities.Stones;
import GUI.Entities.Water;

import it.randomtower.engine.ResourceManager;
//import it.randomtower.engine.entity.Entity;
import it.randomtower.engine.World;
import it.randomtower.engine.entity.Entity;


import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;


/**
 *
 * @author Farazath Ahamed
 */
public class GameWorld extends World{
    
public static final int MAP_SIZE = Config.MAP_SIZE;
public static final int GAME_TIME = Config.GAME_TIME;
public static int time=0;
//static map as an array of cells 
public static MapCell[][] map = new MapCell[MAP_SIZE][MAP_SIZE];
// communicator
// arraylist Entity of players
public static int AIplayerNumber;
public static ArrayList<Entity> playerList;

private Image background, arena;
private GUIListener listener;
private AICore ai;
    
    
    

    public GameWorld(int GAME_STATE, GameContainer gc,GUIListener listen,AICore ai) {
        super(GAME_STATE,gc);//To change body of generated methods, choose Tools | Templates.
        playerList = new ArrayList<>();
        listener = listen;
        this.ai = ai;
    }
    
    
    @Override
    public void init(GameContainer gc, StateBasedGame game) throws SlickException {
          super.init(gc, game);
          gc.setAlwaysRender(true);
          gc.setUpdateOnlyWhenVisible(false);
          
          // create the background
          createBackground();
          container.setAlwaysRender(true);
          
          try{
              setup(game);
          }catch(IOException ex){
              Logger.getLogger(GameWorld.class.getName()).log(Level.SEVERE,null,ex);
              System.out.println("IOException during game setup "+ ex.toString());
          }
          
    }

    @Override
    public void enter(GameContainer gc, StateBasedGame game) throws SlickException {
          super.enter(gc, game);
    }

    @Override
    public void render(GameContainer gc, StateBasedGame game, Graphics g) throws SlickException {
        //render the background here
       g.drawImage(background,0,-130);
       g.drawImage(arena,Config.startX-1,Config.startY-1);
       
        super.render(gc, game, g);
        setPointsTable(g);
    }

    @Override
    public void update(GameContainer gc, StateBasedGame game, int delta) throws SlickException {
        super.update(gc, game,1000);
                    
        try {
           // G:P0;0,0;0;0;100;0;0:P1;0,9;0;0;100;0;0:P2;9,0;0;0;100;0;0:P3;9,9;0;0;100;0;0:P4;5,5;0;0;100;0;0:3,2,0;5,7,0;1,3,0;3,6,0;5,8,0#
            ai.recieveMessage();
            String serverReply = listener.getCurrentMessage();
            System.err.println(serverReply);
            String[] msg = serverReply.split(":");
            
            switch (msg[0]) {
                case "I":
                    //String[] msg = serverReply.split(":");
                    AIplayerNumber = Integer.parseInt(msg[1].substring(1,msg[1].length()));
                    System.err.println("MY NUM "+AIplayerNumber);
                    setBricks(msg[2]);
                    setStones(msg[3]);
                    setWater(msg[4]);
                    break;
                case "S":
                     setPlayers(msg);
                case "C":
                    setCoins(msg);
                    break;
                case "L":
                    setLifePacks(msg);
                    break;
                case "G":
                    updatePlayers(msg);
                    updateBricks(msg[msg.length-1]);
                    break;
                case "GAME_FINISHED":
                case "GAME_HAS_FINISHED":
                    System.err.println("GAME HAS FINISHED");
                    System.exit(0);
            }
            

        } catch (NullPointerException ex) {
            System.out.println("Null Pointer Exception occured");
        }
    }

    public void setup(StateBasedGame game) throws IOException{
                System.out.println("Set up the game here");
                //set up the players from the S update
                //set up water,brick and stones;
                
                BuildMap();
                
                ai.joinGame();
                //
                String serverReply;
                do{
                    ai.recieveMessage();
                    serverReply = listener.getCurrentMessage();
                    System.err.println(serverReply);
                    
                    if(serverReply.equals("PLAYERS_FULL")){
                        System.err.println("Cannot Join the game (PLAYERS_FULL)");
                        System.exit(0);
                    }
                    else if(serverReply.equals("GAME_ALREADY_STARTED")){
                        System.err.println("Cannot join the game (GAME_ALREADY_STARTED)");
                        System.exit(0);
                    }
                    
                    if( !(serverReply.equals("ALREADY_ADDED")) && !(serverReply.charAt(0) == 'I')){
                        ai.joinGame();
                    }
                    
                }while(!(serverReply.charAt(0) == 'I'));
                
                // split and take the player number from the S Update here
                String[] msg = serverReply.split(":");
                AIplayerNumber = Integer.parseInt(msg[1].substring(1,msg[1].length()));
                System.err.println("MY NUM "+AIplayerNumber);
                 //I:P4:8,4;7,6;9,3;1,3;3,2;2,1;4,8;6,8;2,4;4,7:6,3;3,1;5,3;7,2;0,4;8,6;0,8;2,3;7,1;7,8:8,3;3,6;1,4;9,8;4,3;8,1;6,7;4,2;5,7;3,8#
                //S:P0;0,0;0:P1;0,9;0:P2;9,0;0:P3;9,9;0:P4;5,5;0#
                setBricks(msg[2]);
                setStones(msg[3]);
                setWater(msg[4]);
                
                do{
                    ai.recieveMessage();
                    serverReply = listener.getCurrentMessage();
                }while(!(serverReply.charAt(0)=='S'));
                
                // set the players from the S message
                setPlayers(serverReply.split(":"));
                
               
    }
    
    public void createBackground(){
        background = ResourceManager.getImage("background2");
        arena = ResourceManager.getImage("arena");
       
        Entity back = new Entity(0,-130,background) {
        };
        back.depth=-100;
        
        Entity fore = new Entity(Config.startX-1,Config.startX-1, arena) {
        };
        fore.depth=-100;
    }
    
    public void setBricks(String brickMsg){
     //I:P4:8,4;7,6;9,3;1,3;3,2;2,1;4,8;6,8;2,4;4,7://6,3;3,1;5,3;7,2;0,4;8,6;0,8;2,3;7,1;7,8:8,3;3,6;1,4;9,8;4,3;8,1;6,7;4,2;5,7;3,8#        
        String[] bricks = brickMsg.split(";");
        
        for(String i : bricks){
            String xy[] = i.split(",");
            
            int x = Integer.parseInt(xy[0]);
            int y = Integer.parseInt(xy[1]);
            
            map[x][y] = new Bricks(map[x][y].getPosX(),map[x][y].getPosY());
            //PathFinder.map[y][x] = new Point(x,y,2);
            add(map[x][y]);
        }
    }
    
    public void updateBricks(String brickUpdate){
    String[] bricks = brickUpdate.split(";");
        
        for(String i : bricks){
            String xy[] = i.split(",");
            
            int x = Integer.parseInt(xy[0]);
            int y = Integer.parseInt(xy[1]);
            int damage = Integer.parseInt(xy[2]);
            
            Bricks b = (Bricks)map[x][y];
            b.setDamage(damage);
        }
    
    }
    
    public void setWater(String waterMsg){
            String[] water = waterMsg.split(";");
        
        for(String i : water){
            String xy[] = i.split(",");
            
            int x = Integer.parseInt(xy[0]);
            int y = Integer.parseInt(xy[1]);
            
            map[x][y] = new Water(map[x][y].getPosX(),map[x][y].getPosY());
           // PathFinder.map[y][x] = new Point(x, y,1);
            add(map[x][y]);
        }
    }
    
    public void setStones(String stoneMsg){
                String[] stones = stoneMsg.split(";");
        
        for(String i : stones){
            String xy[] = i.split(",");
            
            int x = Integer.parseInt(xy[0]);
            int y = Integer.parseInt(xy[1]);
            
            map[x][y] = new Stones(map[x][y].getPosX(),map[x][y].getPosY());
           // PathFinder.map[y][x] = new Point(x, y,1);
            add(map[x][y]);
        }
    }
    
    public void setCoins(String[] coinMsg){
       //C:0,0:21671:1147
        String[] coord = coinMsg[1].split(",");
        int x = Integer.parseInt(coord[0]);
        int y = Integer.parseInt(coord[1]);
        int lifetime = Integer.parseInt(coinMsg[2]);
        int value = Integer.parseInt(coinMsg[3]);

        Coin C = new Coin(map[x][y].getPosX(), map[x][y].getPosY(), value ,lifetime,x,y);
     //   AI.coinList.add(C);
        map[x][y] = C;
        add(map[x][y]);
        System.err.println("Coin added to ["+x+","+y+"] current_count = "+Coin.CoinsAlive);
    }
    
    public void setLifePacks(String[] lifeMsg){
        // L:2,0:7841
        String[] coord = lifeMsg[1].split(",");
        int x = Integer.parseInt(coord[0]);
        int y = Integer.parseInt(coord[1]);
        int lifetime = Integer.parseInt(lifeMsg[2]);
        
        LifePack L= new LifePack(map[x][y].getPosX(), map[x][y].getPosY(),lifetime,x,y);
       // AI.LifePackList.add(L);
        map[x][y] = L;
        add(map[x][y]);
        System.err.println("Life Pack added to ["+x+","+y+"] current_count = "+LifePack.LifePacksAlive);
    }
   
    
    public void setPlayers(String[] playerMsg){
        for (int i = 1; i < playerMsg.length; i++) {

            int x, y, direction, no;
            String[] data = playerMsg[i].split(";");
            no = Integer.parseInt(data[0].charAt(1) + "");
            String[] position = data[1].split(",");
            x = Integer.parseInt(position[0]);
            y = Integer.parseInt(position[1]);
            direction = Integer.parseInt(data[2]);

            Entity newPlayer;

            if (no != AIplayerNumber) {
                newPlayer = new Players(map[x][y].getPosX(), map[x][y].getPosY(), no, direction,this);
               // map[x][y] = newPlayer;
                
            } 
          else {
                newPlayer = new AI(map[x][y].getPosX(), map[x][y].getPosY(), no, direction,this);
            }

            add(newPlayer);
            playerList.add(newPlayer);    
            System.err.println("Player "+no+" ADDED ["+x+","+y+"]");
        }
    }
    
    public void updatePlayers(String[] updateMessage){
        
        for (int i = 0; i < playerList.size(); i++) {

            if (i == AIplayerNumber) {
                //update the AI player code goes here
                //not yet implemented
                //continue;
                AI AIget = (AI) playerList.get(i);
                AIget.setGlobleUpdate(updateMessage[i+1]);
                System.out.println(updateMessage[i+1]);

            } else {
                Players get = (Players) playerList.get(i);
                get.setGlobleUpdate(updateMessage[i + 1]);
                System.out.println(updateMessage[i+1]);
            }
        }
            
    }
    
    public static void BuildMap(){
            int X = Config.startX;
            int Y = Config.startY;
            int gap = Config.gap;
            
                for(int i=0;i<MAP_SIZE;i++){
                    for(int j=0;j<MAP_SIZE;j++){
                          map[i][j] = new MapCell( X + i*gap , Y + j*gap);
                        //  PathFinder.map[i][j] = new Point(i, j,0);
                    }    
                }
                
                
    }
    

    
    private void setPointsTable(Graphics g) {

        String spaceBetColumns = Config.point_table_spacing_column;
        int spaceBetRows = Config.point_table_spacing_row;
        int textPositionX = Config.textPositionX;
        int textPositionY = Config.textPositionY;

        g.setLineWidth(g.getLineWidth() * 3);
        g.setColor(Color.white);
        g.drawString(String.format("%10s", "Player ID") + spaceBetColumns + String.format("%10s", "Points") + spaceBetColumns + String.format("%10s", "Coins") + spaceBetColumns + String.format("%10s", "Health"), textPositionX, textPositionY);
        


        for (int i = 0; i < playerList.size(); i++) {
            String pointsTableEntry;
            if (i == AIplayerNumber) {
                g.setColor(Color.yellow);
                pointsTableEntry = ((AI) playerList.get(i)).getPointsTableEntry();

            } else {
                pointsTableEntry = ((Players) playerList.get(i)).getPointsTableEntry();
            }
            g.drawString(pointsTableEntry, textPositionX, textPositionY + spaceBetRows * (i + 1));

        }


    }
    
    
    
    
}

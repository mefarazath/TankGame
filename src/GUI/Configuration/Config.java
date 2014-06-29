/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI.Configuration;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Arrays;
import java.util.Properties;

/**
 *
 * @author Farazath Ahamed
 */
public class Config {

    public static String ServerIP;
    public static int clientPort;
    public static int serverPort;

    public static int startX;
    public static int startY;
    public static int gap;

    public static int MAP_SIZE;
    public static int BULLET_SPEED;
    public static int PLUNDER_COIN_LIFE;
    public static int BRICK_POINTS;
    public static int GAME_TIME;

    public static String point_table_spacing_column;
    public static int point_table_spacing_row;
    public static int textPositionX;
    public static int textPositionY;

    public static void loadConfig() throws IOException, FileNotFoundException {

        Properties prop = new Properties();
        FileInputStream input = new FileInputStream("Configuration/config_data.properties");

        // load the properties file to load the variables
        prop.load(input);

        ServerIP = prop.getProperty("SERVER_IP", "127.0.0.1");
        clientPort = Integer.parseInt(prop.getProperty("CLIENT_PORT", "7000"));
        serverPort = Integer.parseInt(prop.getProperty("SERVER_PORT", "6000"));

        startX = Integer.parseInt(prop.getProperty("startX"));
        startY = Integer.parseInt(prop.getProperty("startY"));
        gap = Integer.parseInt(prop.getProperty("gap"));

        // map details
        MAP_SIZE = Integer.parseInt(prop.getProperty("MAP_SIZE"));
        BULLET_SPEED = Integer.parseInt(prop.getProperty("BULLET_SPEED"));
        BRICK_POINTS = Integer.parseInt(prop.getProperty("BRICK_POINTS"));
        PLUNDER_COIN_LIFE = Integer.parseInt(prop.getProperty("PLUNDER_COIN_LIFE"));
        GAME_TIME = Integer.parseInt(prop.getProperty("GAME_TIME"));

        int length = Integer.parseInt(prop.getProperty("point_table_spacing_column"));
        char[] charArray = new char[length];
        Arrays.fill(charArray, ' ');
        
        point_table_spacing_column = new String(charArray);
        point_table_spacing_row = Integer.parseInt(prop.getProperty("point_table_spacing_row"));
        textPositionX = Integer.parseInt(prop.getProperty("textPositionX"));
        textPositionY = Integer.parseInt(prop.getProperty("textPositionY"));
    }
}

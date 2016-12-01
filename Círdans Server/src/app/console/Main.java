package app.console;

import engine.Engine;
import util.DBUtil;

public class Main {
    public static boolean DEBUG_WATCHER = false;
    /**
     * Partida
     * @param args
     * @throws InterruptedException 
     */
    public static void main(String []args) throws InterruptedException {
        ServerConsole.header();
        Engine engine = Engine.getInstance();
        
        boolean flag = false;
        while(!flag){
            ServerConsole.clearConsole();
            int response = ServerConsole.menu();
            switch(response){
                case 3:
                    DBUtil.DEBUG = true;
                case 2:
                    Main.DEBUG_WATCHER = true;
                case 1:
                    flag = true;
                    engine.start();
                    break;
                default:
                    break;
            }
        }
    }
    
    
}
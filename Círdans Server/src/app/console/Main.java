package app.console;

import engine.Engine;

public class Main {
    public static boolean DEBUG_WATCHER = true;
    /**
     * Partida
     * @param args
     * @throws InterruptedException 
     */
    public static void main(String []args) throws InterruptedException {
        Engine engine = Engine.getInstance();
        engine.start();
    }
}
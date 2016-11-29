package controller;

import util.UScene;

/**
 * Controlador abstrato padrão 
 * @author Marcelo Gomes Martins
 */
public abstract class AController {
    // referencia para o gerenciador de Scenes padrão
    protected UScene sceneManager;
    
    public UScene getSceneManager() { return sceneManager; }
    public void setSceneManager(UScene sceneManager) { this.sceneManager = sceneManager; }
}

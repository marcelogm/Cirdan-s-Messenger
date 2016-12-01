package controller;

import util.UStage;

/**
 * Controlador abstrato padrão 
 * @author Marcelo Gomes Martins
 */
public abstract class AController {
    // referencia para o gerenciador de Scenes padrão
    protected UStage sceneManager;
    
    public UStage getSceneManager() { return sceneManager; }
    public void setSceneManager(UStage sceneManager) { this.sceneManager = sceneManager; }
}

package engineTester;

import java.awt.SystemTray;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import models.RawModel;
import models.TexturedModel;

import objConverter.ModelData;
import objConverter.OBJFileLoader;

import org.lwjgl.*;
import org.lwjgl.glfw.*;
import org.lwjgl.opengl.*;
import org.lwjglx.util.vector.Vector3f;

import Entities.Camera;
import Entities.Entity;
import Entities.Light;
 
import static org.lwjgl.glfw.Callbacks.*;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.system.MemoryUtil.*;
import renderEngine.DisplayManager;
import renderEngine.Loader;
import renderEngine.MasterRender;
import renderEngine.OBJLoader;
import renderEngine.EntityRenderer;
import shaders.StaticShader;
import terrains.Terrain;
import textures.ModelTexture;
import textures.TerrainTexture;
import textures.TerrainTexturePack;

public class Main {   
 
    public static void main(String[] args) {
    	
        DisplayManager.createDisplay();
        GL.createCapabilities();
        
        Loader loader = new Loader();        
        
        ModelData treeData = OBJFileLoader.loadOBJ("tree");
        RawModel treeModel = loader.loadToVAO(treeData.getVertices(), 
        		treeData.getTextureCoords(), treeData.getNormals(), treeData.getIndices());
        
        ModelTexture treeTexture = new ModelTexture(loader.loadTexture("tree"));
        treeTexture.setReflectivity(3000);
        treeTexture.setShineDamper(500);
        TexturedModel tree = new TexturedModel(treeModel, treeTexture); 
        
        RawModel fernModel = OBJLoader.loadObjModel("fern", loader);
        ModelTexture fernTexture = new ModelTexture(loader.loadTexture("fern"));
        fernTexture.setReflectivity(3000);
        fernTexture.setShineDamper(500);
        fernTexture.setHasTransparency(true);
        fernTexture.setUseFakeLighting(true);
        TexturedModel fern = new TexturedModel(fernModel, fernTexture); 
        
        RawModel grassModel = OBJLoader.loadObjModel("grassModel", loader);
        ModelTexture grassTexture = new ModelTexture(loader.loadTexture("grass2"));
        grassTexture.setReflectivity(3000);
        grassTexture.setShineDamper(500);
        grassTexture.setHasTransparency(true);
        grassTexture.setUseFakeLighting(true);
        TexturedModel grass = new TexturedModel(grassModel, grassTexture); 
        
//      Random objects
        List<Entity> entities = new ArrayList<Entity>();
        Random r = new Random();
        for (int i = 0; i < 500; i++) {
        	float randX = (0.5f - r.nextFloat()) * 100;
        	float randZ = -1 * (r.nextFloat() * 100);
        	entities.add(new Entity(tree, new Vector3f(randX, 0, randZ), 0, 0, 0, 1));
        	randX = (0.5f - r.nextFloat()) * 100;
        	randZ = -1 * (r.nextFloat() * 100);
        	entities.add(new Entity(fern, new Vector3f(randX, 0, randZ), 0, 0, 0, 0.1f)); 
        	randX = (0.5f - r.nextFloat()) * 100;
        	randZ = -1 * (r.nextFloat() * 100);
        	entities.add(new Entity(grass, new Vector3f(randX, 0, randZ), 0, 0, 0, 0.1f)); 
        }       
        
//      Light & terrains
        Light light = new Light(new Vector3f(0, 40, 60), new Vector3f(1,1,1));
        TerrainTexture backgroundTexture = new TerrainTexture(loader.loadTexture("grass"));
        TerrainTexture rTexture = new TerrainTexture(loader.loadTexture("ground3"));
        TerrainTexture gTexture = new TerrainTexture(loader.loadTexture("ground2"));
        TerrainTexture bTexture = new TerrainTexture(loader.loadTexture("ground1"));
        TerrainTexture blendMap = new TerrainTexture(loader.loadTexture("blendmap"));
        TerrainTexturePack texturePack = new TerrainTexturePack(backgroundTexture, rTexture, gTexture, bTexture, blendMap);
        Terrain terrain = new Terrain(0, -1, loader, texturePack, blendMap);
        Terrain terrain2 = new Terrain(-1, -1, loader, texturePack, blendMap);
        
//      Cameras
        Camera camera = new Camera();
        camera.setPosition(new Vector3f(0.0f, 5.0f, 0.0f));
        MasterRender renderer = new MasterRender();
        while(!glfwWindowShouldClose(DisplayManager.getWindow())){
        	camera.move();
        	
        	renderer.processTerrain(terrain);
        	renderer.processTerrain(terrain2);
        	
        	for (Entity ent : entities) {
        		renderer.processEntity(ent);
        	}
        	
        	renderer.render(light, camera);
        	DisplayManager.updateDisplay();        	
        }
        
        renderer.cleanUp();
        loader.cleanUp();
        DisplayManager.closeDisplay();
    }
 
}
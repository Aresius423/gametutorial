package engineTest;

import java.util.ArrayList;
import java.util.List;

import org.lwjgl.opengl.Display;
import org.lwjgl.util.vector.*;

import entities.Camera;
import entities.Entity;
import entities.Light;
import gameEngine.DisplayMgr;
import gameEngine.Loader;
import gameEngine.MasterRenderer;
import gameEngine.EntityRenderer;
import importer.BlenderImporter;
import models.RawModel;
import models.TexturedModel;
import shader.StaticShader;
import terrains.Terrain;
import textures.ModelTexture;
import toolbox.Maths;

public class Tester {

	public static void main(String[] args) {
		DisplayMgr.createDisplay("Flevoland");
		Loader loader = new Loader();
		MasterRenderer renderer = new MasterRenderer();
		Light light = new Light(new Vector3f(20000,20000,2000), new Vector3f(1,1,1));
		
		List<Entity> entities = new ArrayList<Entity>();
		List<Terrain> terrains = new ArrayList<Terrain>();
		
		for(int i=0; i<1; i++)
		{
			entities.add(new Entity(BlenderImporter.importModel("dragon", loader), new Vector3f(20,5,20), 0, 180, 0, 1));
			entities.add(new Entity(BlenderImporter.importModel("bunny", loader), new Vector3f(00,0,20), 0, 90, 0, 1));
			entities.add(new Entity(BlenderImporter.importModel("stall", loader), new Vector3f(20,0,20), 0, 180, 0, 1));
		}
		
		terrains.add(new Terrain(0,0,loader,new ModelTexture(loader.loadTexture("grass"))));
		terrains.add(new Terrain(1,0,loader,new ModelTexture(loader.loadTexture("grass"))));
		terrains.add(new Terrain(0,1,loader,new ModelTexture(loader.loadTexture("grass"))));
		terrains.add(new Terrain(0,-1,loader,new ModelTexture(loader.loadTexture("grass"))));
		terrains.add(new Terrain(1,1,loader,new ModelTexture(loader.loadTexture("grass"))));
		terrains.add(new Terrain(-1,0,loader,new ModelTexture(loader.loadTexture("grass"))));
		terrains.add(new Terrain(-1,-1,loader,new ModelTexture(loader.loadTexture("grass"))));
        
		Camera camera = new Camera();
		
		while (!Display.isCloseRequested()) {			
			camera.move();
			
			for(Terrain terrain : terrains)
				renderer.processTerrain(terrain);
			
			for(Entity entity : entities)
				renderer.processEntity(entity);
			
			renderer.render(light, camera);
			DisplayMgr.updateDisplay();
		}

		renderer.cleanUp();
		loader.cleanUp();
		DisplayMgr.closeDisplay();

	}

}

package engineTest;

import org.lwjgl.opengl.Display;
import org.lwjgl.util.vector.*;

import entities.Camera;
import entities.Entity;
import gameEngine.DisplayMgr;
import gameEngine.Loader;
import gameEngine.Renderer;
import importer.BlenderImporter;
import models.RawModel;
import models.TexturedModel;
import shader.StaticShader;
import textures.ModelTexture;
import toolbox.Maths;

public class Tester {

	public static void main(String[] args) {
		DisplayMgr.createDisplay("Flevoland");
		Loader loader = new Loader();
		StaticShader shader = new StaticShader();
		Renderer renderer = new Renderer(shader);
		
		TexturedModel texturedModel = BlenderImporter.importModel("dragon", loader); //new TexturedModel(model, texture);
		Entity entity = new Entity(texturedModel, new Vector3f(0, -3, -15), 0, 150, 0, 1);
		Camera camera = new Camera();
		
		
		
		while (!Display.isCloseRequested()) {			
			renderer.prepare();
			shader.start();
			camera.move();
			entity.changeRotation(0, 2, 0);
			shader.loadViewMatrix(camera);
			renderer.render(entity, shader);
			shader.stop();
			DisplayMgr.updateDisplay();
		}

		shader.cleanUp();
		loader.cleanUp();
		DisplayMgr.closeDisplay();

	}

}

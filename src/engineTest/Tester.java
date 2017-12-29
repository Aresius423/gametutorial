package engineTest;

import org.lwjgl.opengl.Display;

import gameEngine.DisplayMgr;
import gameEngine.Loader;
import gameEngine.RawModel;
import gameEngine.Renderer;

public class Tester {

	public static void main(String[] args) {
		DisplayMgr.createDisplay("Flevoland");
		Loader loader = new Loader();
		Renderer renderer = new Renderer();
		
		double[] vertices =
			{ 
					-0.5,-0.5,
					-0.5,0.5,
					0.5,0.5,
					0.5,-0.5
			};
		
		
		int[] indices = 
			{
					0,2,1,
					0,3,2
			};
		
		/*for(int i=0;i<vertices.length;i++)
		{
			vertices[i] = vertices[i]/10;
		}*/
		
		RawModel model = loader.loadToVAO(Loader.convArrTo3D(vertices), indices);
		
		
		while(!Display.isCloseRequested())
		{
			renderer.prepare();
			renderer.render(model);
			DisplayMgr.updateDisplay();
		}
		
		loader.cleanUp();
		DisplayMgr.closeDisplay();
		
	}

}

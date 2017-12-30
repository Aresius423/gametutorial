package shader;

import org.lwjgl.util.vector.*;

import entities.Camera;
import toolbox.Maths;

public class StaticShader extends ShaderProgram {
	private static final String VERTEX_FILE = "src/shader/vertexShader.txt";
	private static final String FRAGMENT_FILE = "src/shader/fragmentShader.txt";
	
	private int trafoMatLoc;
	private int projMatLoc;
	private int viewMatLoc;
	
	public StaticShader()
	{
		super(VERTEX_FILE, FRAGMENT_FILE);
	}
	
	@Override
	protected void bindAttributes()
	{
		super.bindAttribute(0, "position");
		super.bindAttribute(1, "textureCoords");
	}
	
	public void loadTrafoMatrix(Matrix4f matrix)
	{
		super.setUniformMatrix(trafoMatLoc, matrix);
	}
	
	public void loadProjectionMatrix(Matrix4f matrix)
	{
		super.setUniformMatrix(projMatLoc, matrix);
	}
	
	public void loadViewMatrix(Camera camera)
	{
		Matrix4f viewMatrix = Maths.createViewMatrix(camera);
		super.setUniformMatrix(viewMatLoc, viewMatrix);
	}

	@Override
	protected void getAllUniformLocations() {
		trafoMatLoc = getUniformLocation("transformationMatrix");
		projMatLoc = getUniformLocation("projectionMatrix");
		viewMatLoc = getUniformLocation("viewMatrix");
	}
	
}

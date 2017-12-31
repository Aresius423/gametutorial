package shader;

import org.lwjgl.util.vector.Matrix4f;

import entities.Camera;
import entities.Light;
import toolbox.Maths;

public class TerrainShader extends ShaderProgram {
	private static final String VERTEX_FILE = "src/shader/terrainVshader.txt";
	private static final String FRAGMENT_FILE = "src/shader/terrainFshader.txt";

	private int trafoMatLoc;
	private int projMatLoc;
	private int viewMatLoc;
	private int lightMatLoc;
	private int lightColourLoc;
	private int lightReflectivityLoc;
	private int lightShineDamperLoc;

	public TerrainShader() {
		super(VERTEX_FILE, FRAGMENT_FILE);
	}

	@Override
	protected void bindAttributes() {
		super.bindAttribute(0, "position");
		super.bindAttribute(1, "textureCoords");
		super.bindAttribute(2, "normal");
	}

	public void loadLight(Light light) {
		super.setUniform3f(lightMatLoc, light.getPosition());
		super.setUniform3f(lightColourLoc, light.getColour());
	}

	public void loadTrafoMatrix(Matrix4f matrix) {
		super.setUniformMatrix(trafoMatLoc, matrix);
	}

	public void loadProjectionMatrix(Matrix4f matrix) {
		super.setUniformMatrix(projMatLoc, matrix);
	}

	public void loadViewMatrix(Camera camera) {
		Matrix4f viewMatrix = Maths.createViewMatrix(camera);
		super.setUniformMatrix(viewMatLoc, viewMatrix);
	}
	
	public void loadReflectivity(float reflectivity, float damper)
	{
		super.setUniformFloat(lightReflectivityLoc, reflectivity);
		super.setUniformFloat(lightShineDamperLoc, damper);
	}

	@Override
	protected void getAllUniformLocations() {
		trafoMatLoc = getUniformLocation("transformationMatrix");
		projMatLoc = getUniformLocation("projectionMatrix");
		viewMatLoc = getUniformLocation("viewMatrix");
		lightMatLoc = getUniformLocation("lightPosition");
		lightColourLoc = getUniformLocation("lightColour");
		lightReflectivityLoc = getUniformLocation("reflectivity");
		lightShineDamperLoc = getUniformLocation("shineDamper");
	}
}

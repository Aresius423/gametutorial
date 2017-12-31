package textures;

public class ModelTexture {
	private int textureID;
	private float reflectivity;
	private float shine_dampening;
	
	public float getReflectivity() {
		return reflectivity;
	}

	public void setReflectivity(float reflectivity) {
		this.reflectivity = reflectivity;
	}

	public float getShine_dampening() {
		return shine_dampening;
	}

	public void setShine_dampening(float shine_dampening) {
		this.shine_dampening = shine_dampening;
	}

	public int getTextureID() {
		return textureID;
	}

	public ModelTexture(int id)
	{
		textureID = id;
	}
}

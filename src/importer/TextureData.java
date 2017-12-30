package importer;

public class TextureData {
	public float getU() {
		return u;
	}
	public float getV() {
		return v;
	}
	private float u;
	private float v;
	
	public TextureData(float u, float v) {
		super();
		this.u = u;
		this.v = v;
	}
}

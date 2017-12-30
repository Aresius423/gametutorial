package importer;

import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;

public class VertexData {
	private Vector3f pos;
	
	private Vector2f tex;
	
	private Vector3f norm;
	
	VertexData(float x, float y, float z)
	{
		super();
		this.pos = new Vector3f(x,y,z);
	}

	public Vector3f getPos() {
		return pos;
	}

	public void setPos(Vector3f pos) {
		this.pos = pos;
	}

	public Vector2f getTex() {
		return tex;
	}

	public void setTex(Vector2f tex) {
		this.tex = tex;
	}

	public Vector3f getNorm() {
		return norm;
	}

	public void setNorm(Vector3f norm) {
		this.norm = norm;
	}
	
}

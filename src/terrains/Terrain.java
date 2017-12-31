package terrains;

import java.util.ArrayList;
import java.util.List;

import org.lwjgl.util.vector.*;

import gameEngine.Loader;
import importer.VertexData;
import models.RawModel;
import textures.ModelTexture;

public class Terrain {
	private static final float SIZE = 800;
	private static final int VERTEX_COUNT = 128;
	
	private float x;
	private float z;
	private RawModel model;
	private ModelTexture texture;
	
	public Terrain(float gridX, float gridZ, Loader loader, ModelTexture texture)
	{
		this.texture = texture;
		this.texture.setReflectivity(0);
		this.texture.setShine_dampening(10f);
		this.x = gridX * SIZE;
		this.z = gridZ * SIZE;
		this.model = generateTerrain(loader);
	}
	
	public float getX() {
		return x;
	}

	public float getZ() {
		return z;
	}

	public RawModel getModel() {
		return model;
	}

	public ModelTexture getTexture() {
		return texture;
	}
	
	public Vector3f getPos()
	{
		return new Vector3f(x,0,z);
	}

	private RawModel generateTerrain(Loader loader){
		List<VertexData> vertices = new ArrayList<VertexData>();
		List<Integer> indices = new ArrayList<Integer>();
		
		for(int i=0;i<VERTEX_COUNT;i++){
			for(int j=0;j<VERTEX_COUNT;j++){
				VertexData tmpvtx = new VertexData();
				tmpvtx.setPos(new Vector3f((float)j/((float)VERTEX_COUNT - 1) * SIZE, 0, (float)i/((float)VERTEX_COUNT - 1) * SIZE));
				tmpvtx.setNorm(new Vector3f(0,1,0));
				tmpvtx.setTex(new Vector2f((float)j/((float)VERTEX_COUNT - 1), (float)i/((float)VERTEX_COUNT - 1)));
				vertices.add(tmpvtx);
			}
		}
		for(int gz=0;gz<VERTEX_COUNT-1;gz++){
			for(int gx=0;gx<VERTEX_COUNT-1;gx++){
				int topLeft = (gz*VERTEX_COUNT)+gx;
				int topRight = topLeft + 1;
				int bottomLeft = ((gz+1)*VERTEX_COUNT)+gx;
				int bottomRight = bottomLeft + 1;
				indices.add(topLeft);
				indices.add(bottomLeft);
				indices.add(topRight);
				indices.add(topRight);
				indices.add(bottomLeft);
				indices.add(bottomRight);
			}
		}
		return loader.loadToVAO(vertices, indices);
	}
}

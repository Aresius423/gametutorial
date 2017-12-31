package gameEngine;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.List;

import org.lwjgl.BufferUtils;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;

import importer.VertexData;
import models.RawModel;
import toolbox.Maths;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL30.*;

public class Loader {
	private static List<Integer> vaos = new ArrayList<Integer>();
	private static List<Integer> vbos = new ArrayList<Integer>();
	private static List<Integer> textures = new ArrayList<Integer>();

	public RawModel loadToVAO(List<VertexData> vertexList, List<Integer> indexList)
	{
		float[] positions = new float[vertexList.size() * 3]; //xyz for each vertex
		float[] textureCoords = new float[vertexList.size() * 2]; //uv for each vertex
		float[] normalCoords = new float[vertexList.size() * 3]; //n1n2n3 for each vertex
		
		int[] indices = new int[indexList.size()];
		
		int numOfVx = 0;
		for(VertexData vx : vertexList)
		{
			positions[(numOfVx*3)] = vx.getPos().x;
			positions[(numOfVx*3+1)] = vx.getPos().y;
			positions[(numOfVx*3+2)] = vx.getPos().z;
			
			textureCoords[(numOfVx*2)] = vx.getTex().x;
			textureCoords[(numOfVx*2)+1] = 1-vx.getTex().y;
			
			normalCoords[(numOfVx*3)] = vx.getNorm().x;
			normalCoords[(numOfVx*3+1)] = vx.getNorm().y;
			normalCoords[(numOfVx*3+2)] = vx.getNorm().z;
			
			numOfVx++;
		}
		
		numOfVx = 0;
		for(Integer i : indexList)
			indices[numOfVx++] = i;
		
		int vaoID = createVAO();
		bindIndicesBuffer(indices);
		storeDataInAttributeList(0, 3, positions);
		storeDataInAttributeList(1, 2, textureCoords);
		storeDataInAttributeList(2, 3, normalCoords);
		unbindVAO();
		
		return new RawModel(vaoID, indices.length);
	}
	
	private void bindIndicesBuffer(int[] indices)
	{
		int vboID = glGenBuffers();
		vbos.add(vboID);
		glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, vboID);
		IntBuffer buffer = storeDataInIntBuffer(indices);
		glBufferData(GL_ELEMENT_ARRAY_BUFFER, buffer, GL_STATIC_DRAW);
	}
	
	private IntBuffer storeDataInIntBuffer(int[] data)
	{
		IntBuffer buffer = BufferUtils.createIntBuffer(data.length);
		buffer.put(data);
		buffer.flip();
		return buffer;
	}

	private FloatBuffer genFloatBuffer(float[] data) {
		FloatBuffer buffer = BufferUtils.createFloatBuffer(data.length);
		buffer.put(data);
		buffer.flip();
		return buffer;
	}

	public void cleanUp() {
		for (int vao : vaos)
			glDeleteVertexArrays(vao);
		for (int vbo : vbos)
			glDeleteBuffers(vbo);
		for (int texture : textures)
			glDeleteTextures(texture);
	}

	private int createVAO() {
		int vaoID = glGenVertexArrays();
		vaos.add(vaoID);
		glBindVertexArray(vaoID);
		return vaoID;
	}

	private void storeDataInAttributeList(int numOfList, int coordinateSize, float[] data) {
		int vboID = glGenBuffers();
		vbos.add(vboID);
		glBindBuffer(GL_ARRAY_BUFFER, vboID);
		FloatBuffer buffer = genFloatBuffer(data);
		glBufferData(GL_ARRAY_BUFFER, buffer, GL_STATIC_DRAW);
		glVertexAttribPointer(numOfList, coordinateSize, GL_FLOAT, false, 0, 0);
		glBindBuffer(GL_ARRAY_BUFFER, 0);
	}

	public int loadTexture(String fileName)
	{
		Texture texture = null;
		try {
			texture = TextureLoader.getTexture("PNG", new FileInputStream("res/"+fileName+".png"));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		textures.add(texture.getTextureID());
		
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_REPEAT);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_REPEAT);
		
		return texture.getTextureID();
	}

	private void unbindVAO() {
		glBindVertexArray(0);
	}
}

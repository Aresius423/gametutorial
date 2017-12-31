package gameEngine;

import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;
import static org.lwjgl.opengl.GL11.GL_TRIANGLES;
import static org.lwjgl.opengl.GL11.GL_UNSIGNED_INT;
import static org.lwjgl.opengl.GL11.glBindTexture;
import static org.lwjgl.opengl.GL11.glDrawElements;
import static org.lwjgl.opengl.GL13.GL_TEXTURE0;
import static org.lwjgl.opengl.GL13.glActiveTexture;
import static org.lwjgl.opengl.GL20.glDisableVertexAttribArray;
import static org.lwjgl.opengl.GL20.glEnableVertexAttribArray;
import static org.lwjgl.opengl.GL30.glBindVertexArray;

import java.util.List;

import org.lwjgl.util.vector.*;

import entities.Entity;
import models.TexturedModel;
import shader.TerrainShader;
import terrains.Terrain;
import textures.ModelTexture;
import toolbox.Maths;

public class TerrainRenderer {

	private TerrainShader shader;
	
	public TerrainRenderer(TerrainShader shader, Matrix4f projectionMatrix)
	{
		super();
		this.shader = shader;
		shader.start();
		shader.loadProjectionMatrix(projectionMatrix);
		shader.stop();
	}
	
	public void render(List<Terrain> terrains)
	{
		for(Terrain terrain: terrains)
		{
			prepareTerrain(terrain);
			loadModelMatrix(terrain);
			glDrawElements(GL_TRIANGLES, terrain.getModel().getVertexNum(), GL_UNSIGNED_INT, 0);
			unbindTexturedModel();
		}
	}
	
	private void prepareTerrain(Terrain terrain)
	{
		glBindVertexArray(terrain.getModel().getVaoID());
		glEnableVertexAttribArray(0);
		glEnableVertexAttribArray(1);
		glEnableVertexAttribArray(2);
		
		ModelTexture texture = terrain.getTexture();
		shader.loadReflectivity(texture.getReflectivity(), texture.getShine_dampening());
				
		glActiveTexture(GL_TEXTURE0);
		glBindTexture(GL_TEXTURE_2D, texture.getTextureID());
		
	}
	
	private void unbindTexturedModel()
	{
		glDisableVertexAttribArray(0);
		glDisableVertexAttribArray(1);
		glDisableVertexAttribArray(2);
		glBindVertexArray(0);
	}
	
	private void loadModelMatrix(Terrain terrain)
	{
		Matrix4f transformationMatrix = Maths.createTrafoMatrix(terrain.getPos(), 0,0,0,1);
		shader.loadTrafoMatrix(transformationMatrix);
	}
}

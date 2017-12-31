package gameEngine;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL13.*;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL30.*;

import java.util.List;
import java.util.Map;

import org.lwjgl.opengl.Display;
import org.lwjgl.util.vector.*;

import entities.Entity;
import models.RawModel;
import models.TexturedModel;
import shader.StaticShader;
import textures.ModelTexture;
import toolbox.Maths;

public class EntityRenderer {

	private StaticShader shader;
	
	public EntityRenderer(StaticShader shader, Matrix4f projectionMatrix) {
		this.shader = shader;
		shader.start();
		shader.loadProjectionMatrix(projectionMatrix);
		shader.stop();
	}
	
	public void render(Map<TexturedModel, List<Entity>> entities)
	{
		for(TexturedModel model:entities.keySet())
		{
			prepareTexturedModel(model);
			List<Entity> batch = entities.get(model);
			for(Entity entity:batch)
			{
				prepareInstance(entity);
				glDrawElements(GL_TRIANGLES, model.getRawModel().getVertexNum(), GL_UNSIGNED_INT, 0);
			}
			unbindTexturedModel();
		}
	}
	
	private void prepareTexturedModel(TexturedModel model)
	{
		glBindVertexArray(model.getRawModel().getVaoID());
		glEnableVertexAttribArray(0);
		glEnableVertexAttribArray(1);
		glEnableVertexAttribArray(2);
		
		ModelTexture texture = model.getTexture();
		shader.loadReflectivity(texture.getReflectivity(), texture.getShine_dampening());
				
		glActiveTexture(GL_TEXTURE0);
		glBindTexture(GL_TEXTURE_2D, model.getTexture().getTextureID());
		
	}
	
	private void unbindTexturedModel()
	{
		glDisableVertexAttribArray(0);
		glDisableVertexAttribArray(1);
		glDisableVertexAttribArray(2);
		glBindVertexArray(0);
	}
	
	private void prepareInstance(Entity entity)
	{
		Matrix4f transformationMatrix = Maths.createTrafoMatrix(entity.getPosition(), entity.getRotX(), entity.getRotY(), entity.getRotZ(), entity.getScale());
		shader.loadTrafoMatrix(transformationMatrix);
	}
}



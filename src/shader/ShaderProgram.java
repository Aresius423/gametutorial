package shader;

import java.io.*;
import java.nio.FloatBuffer;

import org.lwjgl.BufferUtils;
import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL30.*;

public abstract class ShaderProgram {
	private int programID;
	private int vertexShaderID;
	private int fragmentShaderID;
	
	private static FloatBuffer matrixBuffer = BufferUtils.createFloatBuffer(16);
	
	public ShaderProgram(String vertexFile, String fragmentFile)
	{
		vertexShaderID = loadShader(vertexFile, GL_VERTEX_SHADER);
		fragmentShaderID = loadShader(fragmentFile, GL_FRAGMENT_SHADER);
		programID = glCreateProgram();
		glAttachShader(programID, vertexShaderID);
		glAttachShader(programID, fragmentShaderID);
		bindAttributes();
		glLinkProgram(programID);
		glValidateProgram(programID);
		getAllUniformLocations();
	}
	
	public void start()
	{
		glUseProgram(programID);
	}
	
	public void stop()
	{
		glUseProgram(0);
	}
	
	public void cleanUp()
	{
		stop();
		glDetachShader(programID,  vertexShaderID);
		glDetachShader(programID, fragmentShaderID);
		glDeleteShader(vertexShaderID);
		glDeleteShader(fragmentShaderID);
		glDeleteProgram(programID);
	}
	
	protected int getUniformLocation(String uniformName)
	{
		return glGetUniformLocation(programID, uniformName);
	}
	
	protected void setUniformFloat(int uniformLoc, float value)
	{
		glUniform1f(uniformLoc, value);
	}
	
	protected void setUniform3f(int uniformLoc, Vector3f vec)
	{
		glUniform3f(uniformLoc, vec.x, vec.y, vec.z);
	}
	
	protected void setUniformBool(int uniformLoc, boolean value)
	{
		glUniform1f(uniformLoc, value?1:0);
	}
	
	protected void setUniformMatrix(int uniformLoc, Matrix4f matrix)
	{
		matrix.store(matrixBuffer);
		matrixBuffer.flip();
		glUniformMatrix4(uniformLoc, false, matrixBuffer);
	}
	
	protected abstract void getAllUniformLocations();
	
	protected abstract void bindAttributes();
	
	protected void bindAttribute(int attribute, String variableName)
	{
		glBindAttribLocation(programID, attribute, variableName);
	}

	private static int loadShader(String fileName, int type){
		  StringBuilder shaderSource = new StringBuilder();
		  try{
		   BufferedReader reader = new BufferedReader(new FileReader(fileName));
		   String line;
		   while((line = reader.readLine())!=null){
		    shaderSource.append(line).append("//\n");
		   }
		   reader.close();
		  }catch(IOException e){
		   e.printStackTrace();
		   System.exit(-1);
		  }
		  int shaderID = glCreateShader(type);
		  glShaderSource(shaderID, shaderSource);
		  glCompileShader(shaderID);
		  if(glGetShaderi(shaderID, GL_COMPILE_STATUS )== GL_FALSE){
		   System.out.println(glGetShaderInfoLog(shaderID, 500));
		   System.err.println("Could not compile shader!");
		   System.exit(-1);
		  }
		  return shaderID;
	}
}
